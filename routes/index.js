var express = require('express');
var router = express.Router();
var mongoose = require('mongoose');
var Restaurant = require('./../models/restaurant/restaurantSchema');

/* GET home page. */
router.get('/', function (req, res, next) {
    mongoose.connect('mongodb://localhost/restaurant', function (err) {
        if (err) throw err;
        var nameResto = req.params.name;
        
        Restaurant.find({}, function (err, resto) {
            if (err) throw err;
            res.render('restaurants', { restaurants: resto });
        });
    });
});

module.exports = router;
