var express = require('express');
var router = express.Router();
var mongoose = require('mongoose');
var Restaurant = require('./../models/restaurant/restaurantSchema');

router.get('/', function(req, res, next) {
	res.render('android');
});

module.exports = router;