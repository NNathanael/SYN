// grab the things we need
var mongoose = require('mongoose');
var Schema = mongoose.Schema;

// create a schema
var restaurantSchema = new Schema({
  name: { type: String, required: true},
  tables: [ { name: String } ],
  ingredients: [ { name: String, qte: Number } ],
  orders: [ {
  	createAt : { type: Date, default: Date.now() },
    table: String,
    finish: Boolean,
    foods: []
  } ]
});

// the schema is useless so far
// we need to create a model using it
var Restaurant = mongoose.model('restaurant', restaurantSchema);

// make this available to our users in our Node applications
module.exports = Restaurant;