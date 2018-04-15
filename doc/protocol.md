### Data format

### Collections

#### Restaurants

```json
{
    idRestaurant,
    name,
    tables : [{ idTable, name}],
    ingredients : [{ idIngredient, name, qte}],
	orders : [
        idOrder,
        table : { idTable, name},
        foods : [{idIngredient,name}]
    ]
}
```

### Page restaurant

Route : ***/***

```
[{
	name, 
	orders : [
	    idOrder,
        table : { idTable, name},
        foods : [{idIngredient,name}]]
}]
```

### Page orders

Route : ***orders/:idRestaurant***

``` json
{
    idRestaurant,
    name,
    orders : [
        idOrder,
        table : { idTable, name},
        foods : [{idIngredient,name}]]
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