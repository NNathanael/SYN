package com.example.mayron.projetnomade;

import android.annotation.SuppressLint;
import android.content.Context;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import org.json.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;

import static com.example.mayron.projetnomade.MainActivity.SERVER_IP;
import static com.example.mayron.projetnomade.MainActivity.TAG;
import static com.example.mayron.projetnomade.MainActivity.jsonArray;
import static com.example.mayron.projetnomade.MainActivity.tableDone;

/**
 * Created by Mayron on 09.04.2018.
 */

class NdefReaderTask extends AsyncTask<Tag, Void, String> {

    private final static int UPDATETABLE = 0;
    private final static int UPDATERESTAURANT = 1;
    String restaurantName = "";
    String table;
    String ingr;
    JSONArray tables;
    JSONArray ingredientsAvailable;
    String[] arrayToCheckType;


    private void updateRestaurant() throws JSONException {
        /*MainActivity.idRestaurant = arrayToCheckType[0];*/
        MainActivity.lblRestaurant.setText(MainActivity.lblRestaurant.getText() + " " + restaurantName);
        /*MainActivity.idRestaurant = restaurantName;*/
        MainActivity.idRestaurant = arrayToCheckType[0];

    }

    private void updateTable() throws JSONException {
        MainActivity.lblTable.setText(MainActivity.lblTable.getText() + table);
        /*MainActivity.idTable = table;*/
        MainActivity.idTable = arrayToCheckType[2];
    }


    public static JSONObject getJSONObjectFromURL(String urlString) throws IOException, JSONException {
        HttpURLConnection urlConnection = null;
        URL url = new URL(urlString);
        urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod("GET");
        urlConnection.setReadTimeout(10000 /* milliseconds */ );
        urlConnection.setConnectTimeout(15000 /* milliseconds */ );
        urlConnection.setDoOutput(true);
        urlConnection.connect();

        BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
        StringBuilder sb = new StringBuilder();

        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line + "\n");
        }
        br.close();

        String jsonString = sb.toString();

        return new JSONObject(jsonString);
    }

    @SuppressLint("HandlerLeak")
    private final Handler myHandler = new Handler() {
        public void handleMessage(Message msg) {
            final int what = msg.what;
            switch(what) {
                case UPDATETABLE:
                    try {
                        updateRestaurant();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case UPDATERESTAURANT:
                    try {
                        updateTable();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    };

    @Override
    protected String doInBackground(Tag... params) {
        Tag tag = params[0];
        String message = "";
        Ndef ndef = Ndef.get(tag);
        if (ndef == null) {
            // NDEF is not supported by this Tag.
            return null;
        }

        NdefMessage ndefMessage = ndef.getCachedNdefMessage();

        NdefRecord[] records = ndefMessage.getRecords();
        for (NdefRecord ndefRecord : records) {
            if (ndefRecord.getTnf() == NdefRecord.TNF_WELL_KNOWN && Arrays.equals(ndefRecord.getType(), NdefRecord.RTD_TEXT)) {
                try {
                    message = readText(ndefRecord);
                } catch (UnsupportedEncodingException e) {
                    Log.e(TAG, "Unsupported Encoding", e);
                }
            }
        }
        arrayToCheckType = message.split(":");

        String path = "https://" + SERVER_IP + "/android/" + arrayToCheckType[0];

        try {
            MainActivity.jsonRequestedObject = getJSONObjectFromURL(path);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(arrayToCheckType[1].equals("table") && !MainActivity.tableDone){
            try {
                restaurantName =  MainActivity.jsonRequestedObject.get("name").toString();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                tables = MainActivity.jsonRequestedObject.getJSONArray("tables");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            for(int i=0 ; i < tables.length() ; i++){
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject = tables.getJSONObject(i);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    if(jsonObject.getString("_id").compareTo(arrayToCheckType[2])==0){
                        table = jsonObject.getString("name");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            myHandler.sendEmptyMessage(UPDATERESTAURANT);
            myHandler.sendEmptyMessage(UPDATETABLE);

            MainActivity.requestDone = true;
            MainActivity.tableDone = true;
        }
        else if(arrayToCheckType[1].equals("food")){
            try {
                ingredientsAvailable = MainActivity.jsonRequestedObject.getJSONArray("ingredients");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            for(int i=0 ; i < ingredientsAvailable.length() ; i++){
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject = ingredientsAvailable.getJSONObject(i);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    if(jsonObject.getString("_id").compareTo(arrayToCheckType[2])==0){
                        ingr = jsonObject.getString("name");
                        MainActivity.listIngr.add(message.split(":")[2]);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        return ingr;
    }

    private String readText(NdefRecord record) throws UnsupportedEncodingException {
        byte[] payload = record.getPayload();

        // Get the Text Encoding
        String textEncoding = ((payload[0] & 128) == 0) ? "UTF-8" : "UTF-16";

        // Get the Language Code
        int languageCodeLength = payload[0] & 0063;

        // String languageCode = new String(payload, 1, languageCodeLength, "US-ASCII");
        // e.g. "en"

        // Get the Text
        return new String(payload, languageCodeLength + 1, payload.length - languageCodeLength - 1, textEncoding);
    }

    @Override
    protected void onPostExecute(String result) {
        if (result != null && !MainActivity.tableDone) {
            MainActivity.addFood(result);
        }
    }
}
