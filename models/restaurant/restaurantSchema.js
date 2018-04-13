// grab the things we need
var mongoose = require('mongoose');
var Schema = mongoose.Schema;

// create a schema
var restaurantSchema = new Schema({
  name: { type: String, required: true},
  orders: [
    {
      table: Number,
      foods: [{ tag: { type: String, required: true}, name: { type: String, required: true} }]
    }
  ]
});

// the schema is useless so far
// we need to create a model using it
var Food = mongoose.model('restaurant', restaurantSchema);

// make this available to our users in our Node applications
module.exports = Food;