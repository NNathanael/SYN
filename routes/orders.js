var express = require('express');
var router = express.Router();
var mongoose = require('mongoose');
var Restaurant = require('./../models/restaurant/restaurantSchema');

/* GET restaurant orders. */
router.get('/:name', function(req, res, next) {
	mongoose.connect('mongodb://localhost/restaurant', function (err) {
	    if (err) throw err;
	    var nameResto = req.params.name;

	    Restaurant.find({ name: nameResto }, function(err, resto) {
            if (err) throw err;
            res.render('orders', { restaurant: resto[0] });
        });
    });
});

module.exports = router;

