# Data format

### Collections

#### Restaurants

```json
{
    _id,
    name,
    tables : [{ _id, name}],
    ingredients : [{ _id, name, qte}],
	orders : [{
        _id,
        table,
        foods : [ name ]
	}]
}
```

### Page restaurant

Route : ***/***

```
[{
	name, 
	orders : [
	    _id,
        table,
        foods : [ name ]
}]
```

### Page orders

Route : ***orders/:idRestaurant***

``` json
{
    _id,
    name,
    orders : [
        _id,
        table,
        foods : [ name ]
}
```

## MESSAGES

#### ORDER

```json
{
    idRestaurant,
    idTable,
    foods : [idIngredient]
 }
```

#### ORDER_CONFIRM

```json
{
    status : boolean,
    error : string
}
```

#### Tag RFID 

```ini
type : {
    table,
    food
}
```

```ini
idRestaurant:type:idObject
```

### Base de données

**Delete**

```shell
mongo restaurant
db.restaurants.drop()
```

**Export**

```shell
mongoexport --db restaurant --collection restaurants --out restaurants.json
```
**Import**

```shell
mongoimport --db restaurant --collection restaurants --file restaurants.json
```

```jso
{ _id: 5ad3620411d0b61592d40c70,
  name: 'Château de Villa',
  tables:
   [ { _id: 5ad3620411d0b61592d40c77, name: '1' },
     { _id: 5ad3620411d0b61592d40c76, name: '2' },
     { _id: 5ad3620411d0b61592d40c75, name: '3' },
     { _id: 5ad3620411d0b61592d40c74, name: '4' },
     { _id: 5ad3620411d0b61592d40c73, name: '5' },
     { _id: 5ad3620411d0b61592d40c72, name: '6' },
     { _id: 5ad3620411d0b61592d40c71, name: 'Table des seigneurs' } ],
  ingredients:
   [ { _id: 5ad3620411d0b61592d40c7e, name: 'Poissons', qte: 150 },
     { _id: 5ad3620411d0b61592d40c7d, name: 'Jambon', qte: 420 },
     { _id: 5ad3620411d0b61592d40c7c, name: 'Fromage', qte: 100 },
     { _id: 5ad3620411d0b61592d40c7b, name: 'Oeuf', qte: 345 },
     { _id: 5ad3620411d0b61592d40c7a, name: 'Champignon', qte: 90 },
     { _id: 5ad3620411d0b61592d40c79, name: 'Sucre', qte: 500 },
     { _id: 5ad3620411d0b61592d40c78, name: 'Noix', qte: 30 } ],
  orders:
   [ { foods: [Object], _id: 5ad3620411d0b61592d40c80, table: '1' },
     { foods: [Object], _id: 5ad3620411d0b61592d40c7f, table: '4' } ],
  __v: 0 }
{ _id: 5ad3620411d0b61592d40c64,
  name: 'Brasserie de Montbenon',
  tables:
   [ { _id: 5ad3620411d0b61592d40c69, name: '1' },
     { _id: 5ad3620411d0b61592d40c68, name: '2' },
     { _id: 5ad3620411d0b61592d40c67, name: '3' },
     { _id: 5ad3620411d0b61592d40c66, name: '4' },
     { _id: 5ad3620411d0b61592d40c65, name: 'Table vip' } ],
  ingredients:
   [ { _id: 5ad3620411d0b61592d40c6f, name: 'Jambon', qte: 420 },
     { _id: 5ad3620411d0b61592d40c6e, name: 'Fromage', qte: 120 },
     { _id: 5ad3620411d0b61592d40c6d, name: 'Oeuf', qte: 300 },
     { _id: 5ad3620411d0b61592d40c6c, name: 'Champignon', qte: 350 },
     { _id: 5ad3620411d0b61592d40c6b, name: 'Confiture', qte: 77 },
     { _id: 5ad3620411d0b61592d40c6a, name: 'Nutella', qte: 42 } ],
  orders: [],
  __v: 0 }
{ _id: 5ad3620411d0b61592d40c81,
  name: 'Le Refuge des Gourmets',
  tables:
   [ { _id: 5ad3620411d0b61592d40c87, name: '1' },
     { _id: 5ad3620411d0b61592d40c86, name: '2' },
     { _id: 5ad3620411d0b61592d40c85, name: '3' },
     { _id: 5ad3620411d0b61592d40c84, name: '4' },
     { _id: 5ad3620411d0b61592d40c83, name: '5' },
     { _id: 5ad3620411d0b61592d40c82, name: '6' } ],
  ingredients:
   [ { _id: 5ad3620411d0b61592d40c8d, name: 'Jambon', qte: 420 },
     { _id: 5ad3620411d0b61592d40c8c, name: 'Fromage', qte: 120 },
     { _id: 5ad3620411d0b61592d40c8b, name: 'Oeuf', qte: 300 },
     { _id: 5ad3620411d0b61592d40c8a, name: 'Champignon', qte: 350 },
     { _id: 5ad3620411d0b61592d40c89, name: 'Confiture', qte: 77 },
     { _id: 5ad3620411d0b61592d40c88, name: 'Nutella', qte: 42 } ],
  orders:
   [ { foods: [Object], _id: 5ad3620411d0b61592d40c90, table: '3' },
     { foods: [Object], _id: 5ad3620411d0b61592d40c8f, table: '1' },
     { foods: [Object], _id: 5ad3620411d0b61592d40c8e, table: '2' } ],
  __v: 0 }
{ _id: 5ad3620411d0b61592d40c91,
  name: 'Ô Plaisir',
  tables:
   [ { _id: 5ad3620411d0b61592d40c96, name: '1' },
     { _id: 5ad3620411d0b61592d40c95, name: '2' },
     { _id: 5ad3620411d0b61592d40c94, name: '3' },
     { _id: 5ad3620411d0b61592d40c93, name: '4' },
     { _id: 5ad3620411d0b61592d40c92, name: '5' } ],
  ingredients:
   [ { _id: 5ad3620411d0b61592d40c9c, name: 'Poissons', qte: 150 },
     { _id: 5ad3620411d0b61592d40c9b, name: 'Fromage', qte: 100 },
     { _id: 5ad3620411d0b61592d40c9a, name: 'Oeuf', qte: 345 },
     { _id: 5ad3620411d0b61592d40c99, name: 'Champignon', qte: 90 },
     { _id: 5ad3620411d0b61592d40c98, name: 'Sucre', qte: 500 },
     { _id: 5ad3620411d0b61592d40c97, name: 'Noix', qte: 30 } ],
  orders: [],
  __v: 0 }
```

