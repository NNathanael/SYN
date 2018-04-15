### Data format

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

### 