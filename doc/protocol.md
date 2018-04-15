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
        createAt,
        table,
        foods : [ name ]
	}]
}
```

### Page restaurant

Route : ***/***

```
[name]
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
{ _id: 5ad36fea8bccbc1f3a1e3de5,
  name: 'Château de Villa',
  tables:
   [ { _id: 5ad36fea8bccbc1f3a1e3dec, name: '1' },
     { _id: 5ad36fea8bccbc1f3a1e3deb, name: '2' },
     { _id: 5ad36fea8bccbc1f3a1e3dea, name: '3' },
     { _id: 5ad36fea8bccbc1f3a1e3de9, name: '4' },
     { _id: 5ad36fea8bccbc1f3a1e3de8, name: '5' },
     { _id: 5ad36fea8bccbc1f3a1e3de7, name: '6' },
     { _id: 5ad36fea8bccbc1f3a1e3de6, name: 'Table des seigneurs' } ],
  ingredients:
   [ { _id: 5ad36fea8bccbc1f3a1e3df3, name: 'Poissons', qte: 150 },
     { _id: 5ad36fea8bccbc1f3a1e3df2, name: 'Jambon', qte: 420 },
     { _id: 5ad36fea8bccbc1f3a1e3df1, name: 'Fromage', qte: 100 },
     { _id: 5ad36fea8bccbc1f3a1e3df0, name: 'Oeuf', qte: 345 },
     { _id: 5ad36fea8bccbc1f3a1e3def, name: 'Champignon', qte: 90 },
     { _id: 5ad36fea8bccbc1f3a1e3dee, name: 'Sucre', qte: 500 },
     { _id: 5ad36fea8bccbc1f3a1e3ded, name: 'Noix', qte: 30 } ],
  orders:
   [ { createAt: 2018-04-15T15:29:33.875Z,
       foods: [Object],
       _id: 5ad36fea8bccbc1f3a1e3df5,
       table: '1' },
     { createAt: 2018-04-15T15:29:33.875Z,
       foods: [Object],
       _id: 5ad36fea8bccbc1f3a1e3df4,
       table: '4' } ],
  __v: 0 }
{ _id: 5ad36fea8bccbc1f3a1e3df6,
  name: 'Le Refuge des Gourmets',
  tables:
   [ { _id: 5ad36fea8bccbc1f3a1e3dfc, name: '1' },
     { _id: 5ad36fea8bccbc1f3a1e3dfb, name: '2' },
     { _id: 5ad36fea8bccbc1f3a1e3dfa, name: '3' },
     { _id: 5ad36fea8bccbc1f3a1e3df9, name: '4' },
     { _id: 5ad36fea8bccbc1f3a1e3df8, name: '5' },
     { _id: 5ad36fea8bccbc1f3a1e3df7, name: '6' } ],
  ingredients:
   [ { _id: 5ad36fea8bccbc1f3a1e3e02, name: 'Jambon', qte: 420 },
     { _id: 5ad36fea8bccbc1f3a1e3e01, name: 'Fromage', qte: 120 },
     { _id: 5ad36fea8bccbc1f3a1e3e00, name: 'Oeuf', qte: 300 },
     { _id: 5ad36fea8bccbc1f3a1e3dff, name: 'Champignon', qte: 350 },
     { _id: 5ad36fea8bccbc1f3a1e3dfe, name: 'Confiture', qte: 77 },
     { _id: 5ad36fea8bccbc1f3a1e3dfd, name: 'Nutella', qte: 42 } ],
  orders:
   [ { createAt: 2018-04-15T15:29:33.875Z,
       foods: [Object],
       _id: 5ad36fea8bccbc1f3a1e3e05,
       table: '1' },
     { createAt: 2018-04-15T15:29:33.875Z,
       foods: [Object],
       _id: 5ad36fea8bccbc1f3a1e3e04,
       table: '2' },
     { createAt: 2018-04-15T15:29:33.875Z,
       foods: [Object],
       _id: 5ad36fea8bccbc1f3a1e3e03,
       table: '3' } ],
  __v: 0 }
{ _id: 5ad36fea8bccbc1f3a1e3e06,
  name: 'Ô Plaisir',
  tables:
   [ { _id: 5ad36fea8bccbc1f3a1e3e0b, name: '1' },
     { _id: 5ad36fea8bccbc1f3a1e3e0a, name: '2' },
     { _id: 5ad36fea8bccbc1f3a1e3e09, name: '3' },
     { _id: 5ad36fea8bccbc1f3a1e3e08, name: '4' },
     { _id: 5ad36fea8bccbc1f3a1e3e07, name: '5' } ],
  ingredients:
   [ { _id: 5ad36fea8bccbc1f3a1e3e11, name: 'Poissons', qte: 150 },
     { _id: 5ad36fea8bccbc1f3a1e3e10, name: 'Fromage', qte: 100 },
     { _id: 5ad36fea8bccbc1f3a1e3e0f, name: 'Oeuf', qte: 345 },
     { _id: 5ad36fea8bccbc1f3a1e3e0e, name: 'Champignon', qte: 90 },
     { _id: 5ad36fea8bccbc1f3a1e3e0d, name: 'Sucre', qte: 500 },
     { _id: 5ad36fea8bccbc1f3a1e3e0c, name: 'Noix', qte: 30 } ],
  orders: [],
  __v: 0 }
{ _id: 5ad36fea8bccbc1f3a1e3dd9,
  name: 'Brasserie de Montbenon',
  tables:
   [ { _id: 5ad36fea8bccbc1f3a1e3dde, name: '1' },
     { _id: 5ad36fea8bccbc1f3a1e3ddd, name: '2' },
     { _id: 5ad36fea8bccbc1f3a1e3ddc, name: '3' },
     { _id: 5ad36fea8bccbc1f3a1e3ddb, name: '4' },
     { _id: 5ad36fea8bccbc1f3a1e3dda, name: 'Table vip' } ],
  ingredients:
   [ { _id: 5ad36fea8bccbc1f3a1e3de4, name: 'Jambon', qte: 420 },
     { _id: 5ad36fea8bccbc1f3a1e3de3, name: 'Fromage', qte: 120 },
     { _id: 5ad36fea8bccbc1f3a1e3de2, name: 'Oeuf', qte: 300 },
     { _id: 5ad36fea8bccbc1f3a1e3de1, name: 'Champignon', qte: 350 },
     { _id: 5ad36fea8bccbc1f3a1e3de0, name: 'Confiture', qte: 77 },
     { _id: 5ad36fea8bccbc1f3a1e3ddf, name: 'Nutella', qte: 42 } ],
  orders: [],
  __v: 0 }

```

