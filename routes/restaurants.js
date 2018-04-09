var express = require('express');
var router = express.Router();

var mongoose = require('mongoose');
var Restaurant = require('./../models/restaurant/restaurantSchema');


// var restaurant2 = new Restaurant(
// {
// 	name: 'PizzaSpeedLoic',
// 	orders: [
// 		{
// 			table: 3,
// 			foods: [
// 				{
// 					tag: '05:3A:8F:2C:00:00:07:E0',
// 					name: 'fromage'
// 				}
// 			]
// 		}
// 	]
// });


/* GET restaurant list. */
router.get('/', function(req, res, next) {
	mongoose.connect('mongodb://localhost/restaurant', function (err) {
	if (err) throw err;
	//res.send('Successfully connected');
	Restaurant.find({},{name:1}, function(err, resto) {
  	if (err) throw err;
  	// object of the user

  	res.send(JSON.stringify(resto));
	});

		
	// restaurant2.save(function(err) {
	// 	if (err) throw err;
	// 	console.log('restaurant2 saved successfully!');
	// });
});
	
});

module.exports = router;
