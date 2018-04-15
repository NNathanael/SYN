var express = require('express');
var router = express.Router();
var mongoose = require('mongoose');
var Restaurant = require('./../models/restaurant/restaurantSchema');

/* GET restaurant orders. */
router.route('/:name')

   .get(function (req, res, next) {

	    var nameResto = req.params.name;

	    Restaurant.findOne({ name: nameResto }, function(err, resto) {
            if (err) throw err;
            res.render('orders', { restaurant: resto });
        });
})
 .post(function (req, res) {
   
        var idOrder = req.body.idOrder;
        var deleted = req.body.delete;
        var complete = req.body.complete
        if (deleted != undefined) {
            console.log("idOrder : " + idOrder);
            console.log(deleted);
        }
        
        if (complete != undefined) {
            Restaurant.update({"orders._id":idOrder} ,{$set:{ "orders.$.finish":true}}, function (err, order) {
             if (err) throw err;
        });
        }

        
});

module.exports = router;

