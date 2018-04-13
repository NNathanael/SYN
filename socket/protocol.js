module.exports = function(io) {
  var app = require('express');
  var router = app.Router();

  io.on('connection', function(socket) { 

    socket.on('ORDER', function (data) {
      console.log('Order recieved');
      socket.emit('ORDER_CONFIRM', { status: true });
    });
    
  });
  return router;
}