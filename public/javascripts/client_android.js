 const socket = io.connect('http://localhost:3000');
//5ad36fea8bccbc1f3a1e3dd9:food:5ad36fea8bccbc1f3a1e3de4
 const my_order = { 
 	idRestaurant : '5ad36fea8bccbc1f3a1e3dd9', // Brasserie de Montbenon
 	idTable : '5ad36fea8bccbc1f3a1e3ddd', // 2
 	foods : ['5ad36fea8bccbc1f3a1e3de4', '5ad36fea8bccbc1f3a1e3de3'] //jambon, fromage
 };
 
 const send = () => socket.emit('ORDER', my_order);
 
 document.getElementById('btn_send').onclick = send;

 socket.on('ORDER_CONFIRM', function (data) {
    console.log(data);
 });