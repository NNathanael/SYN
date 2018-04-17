package com.example.mayron.projetnomade;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.NfcEvent;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONArray;
import org.json.JSONObject;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import java.io.Console;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements
        NfcAdapter.CreateNdefMessageCallback,  NfcAdapter.OnNdefPushCompleteCallback {

    public static final String MIME_TEXT_PLAIN = "text/plain";
    public static final String TAG = "NfcDemo";
    static TextView lblIngredients;
    static ListView orderListView;
    NfcAdapter nfcAdapter;
    Button btnSend;
    static List<String> fruits_list;
    static ArrayAdapter<String> arrayAdapter;
    static JSONArray jsonArray = new JSONArray();
    static TextView lblTable;
    static TextView lblRestaurant;
    static String idRestaurant;
    static String idTable;
    static boolean tableDone = false;
    static boolean requestDone = false;
    public final static String SERVER_IP = "katyusha.eracnos.ch";
    public final static String SERVER_PORT = "5001";
    static JSONObject jsonRequestedObject;
    static String ingredient;
    static List<String> listIngr;

    private Socket socket;
    {
        try {
            socket = IO.socket("https://" + SERVER_IP);
        } catch (URISyntaxException ignored) {}
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lblRestaurant = (TextView) findViewById(R.id.lblRestaurant);
        lblTable = (TextView) findViewById(R.id.lblTable);
        lblIngredients = (TextView) findViewById(R.id.lblIngredients);
        orderListView = (ListView) findViewById(R.id.orderItems);
        btnSend = (Button) findViewById(R.id.btnSend);

        fruits_list = new ArrayList<String>();
        listIngr = new ArrayList<>();

        // Create an ArrayAdapter from List
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, fruits_list);

        orderListView.setAdapter(arrayAdapter);

        orderListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                AlertDialog.Builder adb=new AlertDialog.Builder(MainActivity.this);
                adb.setTitle("Delete?");
                adb.setMessage("Are you sure you want to delete " + arrayAdapter.getItem(position).toString());
                final int positionToRemove = position;
                adb.setNegativeButton("Cancel", null);
                adb.setPositiveButton("Ok", new AlertDialog.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        arrayAdapter.remove(arrayAdapter.getItem(positionToRemove).toString());
                        arrayAdapter.notifyDataSetChanged();
                    }});
                adb.show();
            }
        });

        socket.on("ORDER_CONFIRM", onOrderConfirm);
        socket.connect();

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String[] arrayToBeJsonified = new String[arrayAdapter.getCount()];

                if(idRestaurant != null && idTable != null && arrayAdapter.getCount() > 0){
                    JSONObject jsonObj = new JSONObject();
                    ArrayList<String> foodArray = new ArrayList<>();
                    /*put the restaurant name */
                try {
                    jsonObj.put("idRestaurant", idRestaurant);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                try {
                    jsonObj.put("idTable", idTable);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                    /*for each ingredient we add it to the json object under "food" label*/
                    for (int i=0;i<arrayAdapter.getCount();i++){
                        foodArray.add(arrayAdapter.getItem(i));

                        arrayToBeJsonified[i] = (listIngr.get(i));
                    }

                    try {
                        JSONArray array = new JSONArray();
                        for (int i=0;i<arrayAdapter.getCount();i++){
                            array.put(arrayToBeJsonified[i]);
                        }

                        jsonObj.put("foods", array);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    sendOrder(jsonObj);
                }
            }
        });

        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if(nfcAdapter == null){
            Toast.makeText(this, "NFC ADAPTER IS NOT VALID", Toast.LENGTH_LONG).show();
            finish();
            return;
        } else {
            Toast.makeText(this, "Please Read some tags", Toast.LENGTH_LONG).show();
        }

        handleIntent(getIntent());
    }

    public static void addFood(String food){
        fruits_list.add(food);
        arrayAdapter.notifyDataSetChanged();
    }

    private void handleIntent(Intent intent) {
        String action = intent.getAction();
        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)) {

            String type = intent.getType();
            if (MIME_TEXT_PLAIN.equals(type)) {
                Tag tag = (Tag) intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
                new NdefReaderTask().execute(tag);

            } else {
                Log.d(TAG, "Wrong mime type: " + type);
            }
        } else if (NfcAdapter.ACTION_TECH_DISCOVERED.equals(action)) {

            // In case we would still use the Tech Discovered Intent
            Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            String[] techList = tag.getTechList();
            String searchedTech = Ndef.class.getName();

            for (String tech : techList) {
                if (searchedTech.equals(tech)) {
                    new NdefReaderTask().execute(tag);
                    break;
                }
            }
        }
    }

    private String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("0x");
        if (src == null || src.length <= 0) {
            return null;
        }

        char[] buffer = new char[2];
        for (int i = 0; i < src.length; i++) {
            buffer[0] = Character.forDigit((src[i] >>> 4) & 0x0F, 16);
            buffer[1] = Character.forDigit(src[i] & 0x0F, 16);
            System.out.println(buffer);
            stringBuilder.append(buffer);
        }

        return stringBuilder.toString();
    }

    @Override
    protected void onResume() {
        super.onResume();

        setupForegroundDispatch(this, nfcAdapter);
    }

    @Override
    protected void onPause() {
        /**
         * Call this before onPause, otherwise an IllegalArgumentException is thrown as well.
         */
        stopForegroundDispatch(this, nfcAdapter);

        super.onPause();
    }


    public static void setupForegroundDispatch(final Activity activity, NfcAdapter adapter) {
        final Intent intent = new Intent(activity.getApplicationContext(), activity.getClass());
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

        final PendingIntent pendingIntent = PendingIntent.getActivity(activity.getApplicationContext(), 0, intent, 0);

        IntentFilter[] filters = new IntentFilter[1];
        String[][] techList = new String[][]{};

        // Notice that this is the same filter as in our manifest.
        filters[0] = new IntentFilter();
        filters[0].addAction(NfcAdapter.ACTION_NDEF_DISCOVERED);
        filters[0].addCategory(Intent.CATEGORY_DEFAULT);
        try {
            filters[0].addDataType(MIME_TEXT_PLAIN);
        } catch (IntentFilter.MalformedMimeTypeException e) {
            throw new RuntimeException("Check your mime type.");
        }

        adapter.enableForegroundDispatch(activity, pendingIntent, filters, techList);
    }

    public static void stopForegroundDispatch(final Activity activity, NfcAdapter adapter) {
        adapter.disableForegroundDispatch(activity);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    @Override
    public NdefMessage createNdefMessage(NfcEvent nfcEvent) {
        NdefRecord rtdUriRecord = NdefRecord.createUri("http://google.com");
        NdefMessage NdefMessageOut = new NdefMessage(rtdUriRecord);
        return NdefMessageOut;
    }

    @Override
    public void onNdefPushComplete(NfcEvent nfcEvent) {
        final String eventString = "onNodePushComplete\n" + nfcEvent.toString();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), eventString, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void sendOrder(JSONObject object){
        socket.emit("ORDER", object);
    }

    private Emitter.Listener onGetInfoRestaurant = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            final JSONObject data = (JSONObject) args[0];
            Log.println(Log.ASSERT, "MSG :", data.toString());

            MainActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    Boolean message;
                    try {
                        message = data.getBoolean("success");
                        Log.i("TRYMESSAGE", "message : " + message);
                    } catch (JSONException e) {
                        return;
                    }

                    Toast.makeText(MainActivity.this,Boolean.toString(message),Toast.LENGTH_SHORT).show();
                    // add the message to view
                    //addMessage(username, message);
                }
            });
        }
    };

    private Emitter.Listener onOrderConfirm = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            final JSONObject data = (JSONObject) args[0];
            Log.println(Log.ASSERT, "MSG :", data.toString());

            MainActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    Boolean message;
                    try {
                        message = data.getBoolean("success");
                        Log.i("TRYMESSAGE", "message : " + message);
                    } catch (JSONException e) {
                        return;
                    }

                    Toast.makeText(MainActivity.this,Boolean.toString(message),Toast.LENGTH_SHORT).show();
                    // add the message to view
                    //addMessage(username, message);
                }
            });
        }
    };
}
