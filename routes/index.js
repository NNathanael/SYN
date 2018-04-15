var express = require('express');
var router = express.Router();
var Restaurant = require('./../models/restaurant/restaurantSchema');

router.get('/insert', function(req, res, next) {
	const r1 = new Restaurant({
		name: 'Brasserie de Montbenon',
		tables: [{name:'1'},{name:'2'},{name:'3'},{name:'4'},{name:'Table vip'}],
		ingredients: [ { name:'Jambon', qte: 420 },
		{ name:'Fromage', qte: 120 },
		{ name:'Oeuf', qte: 300 }, 
		{ name:'Champignon', qte: 350 },
		{ name:'Confiture', qte: 77 },
		{ name:'Nutella', qte: 42 }  
		],
		orders: undefined
	});
	const r2 = new Restaurant({
		name: 'Château de Villa',
		tables: [{name:'1'},{name:'2'},{name:'3'},{name:'4'},{name:'5'},{name:'6'},{name:'Table des seigneurs'}],
		ingredients: [ { name:'Poissons', qte: 150 },
		{ name:'Jambon', qte: 420 },
		{ name:'Fromage', qte: 100 },
		{ name:'Oeuf', qte: 345 }, 
		{ name:'Champignon', qte: 90 },
		{ name:'Sucre', qte: 500 },
		{ name:'Noix', qte: 30 }
		],
		orders: [
		{
			table : '1',
			foods : [ 'Jambon', 'Fromage', 'Oeuf' ]
		},
		{
			table : '4',
			foods : [ 'Champignon', 'Noix', 'Fromage' ]
		}

		]
	});
	const r3 = new Restaurant({
		name: 'Le Refuge des Gourmets',
		tables: [{name:'1'},{name:'2'},{name:'3'},{name:'4'},{name:'5'},{name:'6'}],
		ingredients: [ { name:'Jambon', qte: 420 },
		{ name:'Fromage', qte: 120 },
		{ name:'Oeuf', qte: 300 }, 
		{ name:'Champignon', qte: 350 },
		{ name:'Confiture', qte: 77 },
		{ name:'Nutella', qte: 42 } 
		],
		orders: [
		{
			table : '1',
			foods : [ 'Nutella' ]
		},
		{
			table : '2',
			foods : [ 'Confiture' ]
		},
		{
			table : '3',
			foods : [ 'Jambon', 'Fromage' ]
		}
		]
	});
	const r4 = new Restaurant({
		name: 'Ô Plaisir',
		tables: [{name:'1'},{name:'2'},{name:'3'},{name:'4'},{name:'5'}],
		ingredients: [ { name:'Poissons', qte: 150 },
		{ name:'Fromage', qte: 100 },
		{ name:'Oeuf', qte: 345 }, 
		{ name:'Champignon', qte: 90 },
		{ name:'Sucre', qte: 500 },
		{ name:'Noix', qte: 30 }
		],
		orders: undefined
	});
	r1.save(function (err, results) {
		console.log(results);
	});
	r2.save(function (err, results) {
		console.log(results);
	});
	r3.save(function (err, results) {
		console.log(results);
	});
	r4.save(function (err, results) {
		console.log(results);
	});
	res.send('Insertion de données');
});


/* GET home page. */
router.get('/', function (req, res, next) {   
	Restaurant.find({}, function (err, resto) {
		if (err) throw err;
		res.render('restaurants', { restaurants: resto });
	});
});

module.exports = router;
