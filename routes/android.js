var express = require('express');
var router = express.Router();
var mongoose = require('mongoose');
var Restaurant = require('./../models/restaurant/restaurantSchema');

router.get('/', function(req, res, next) {
	res.render('android');
});

router.get('/:id', function(req, res, next) {
	 
	 	var idResto = req.params.id;
	 	Restaurant.findOne({_id:idResto},function(err,resto) {
	 		if (err) throw err;
	 		const data = {
	 			name : resto.name,
	 			tables : resto.tables,
	 			ingredients : resto.ingredients,
	 			finish : false
	 		};
	 		res.send(data);
	 	});
});

module.exports = router;