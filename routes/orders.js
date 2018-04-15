var express = require('express');
var router = express.Router();
var mongoose = require('mongoose');
var Restaurant = require('./../models/restaurant/restaurantSchema');

/* GET restaurant orders. */
router.route('/:name')

   .get(function (req, res, next) {
	mongoose.connect('mongodb://localhost/restaurant', function (err) {
	    if (err) throw err;
	    var nameResto = req.params.name;

	    Restaurant.find({ name: nameResto }, function(err, resto) {
            if (err) throw err;
            res.render('orders', { restaurant: resto[0] });
        });
    });
})
 .post(function (req, res) {
    mongoose.connect('mongodb://localhost/restaurant', function (err) {
        var idOrder = req.body.idOrder;
        var deleted = req.body.delete;
        var complete = req.body.complete
        if (deleted != undefined) {
            console.log(idOrder);
            console.log(deleted);
        }
        if (complete != undefined) {
            console.log(idOrder);
            console.log(complete);
        }
        var ObjectId = require('mongodb').ObjectId;
        var o_id = new ObjectId(idOrder);

        Restaurant.find({ "orders._id": idOrder }, function (err, resto) {
            console.log(resto[0].orders);
            res.send(resto + " /// " + idOrder + " " + deleted)
        });
    });
});

module.exports = router;

