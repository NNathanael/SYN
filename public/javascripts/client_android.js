 const socket = io.connect('http://localhost:3000');

 const send = () => socket.emit('ORDER', { user: 'data' });
 
 document.getElementById('btn_send').onclick = send;

 socket.on('ORDER_CONFIRM', function (data) {
    console.log(data);
 });