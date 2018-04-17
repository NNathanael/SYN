module.exports = function(io) {
  var app = require('express');
  var router = app.Router();
  var mongoose = require('mongoose');
  var Restaurant = require('./../models/restaurant/restaurantSchema');

  io.on('connection', function(socket) { 

    socket.on('ORDER', function (data) {
      console.log('Order recieved');
      
      const idRestaurant = data.idRestaurant;
      const idTable = data.idTable;
      const allFoods = data.foods;

      let table_requiered = '';
      let food_requiered = [];

      Restaurant.findOne({_id:idRestaurant}, function (err, resto) {

        if (err) throw err;

        resto.tables.forEach(function(obj){
          if (obj._id == idTable) {
            table_requiered = obj.name;
          }
        });

        resto.ingredients.forEach(function(ingredient){
         allFoods.forEach(function(id_need){
          if (ingredient._id == id_need) {
            food_requiered.push(ingredient.name);
          }
         });
        });
        console.log('Required');
        console.log(table_requiered);
        console.log(food_requiered);
        console.log('');
        if (table_requiered !== '' && food_requiered.length !== 0) {
          const order_requiered = {
            _id: mongoose.Types.ObjectId(),
            createAt : Date.now(),
            table : table_requiered,
            foods : food_requiered
          };
          console.log(order_requiered);
          Restaurant.update({_id: idRestaurant}, {$push: {orders: order_requiered}},
             function (err, resto) {
              if (err) throw err;
          });
        }
      });      
      socket.emit('ORDER_CONFIRM', { status: true });
    });
  });
  return router;
}