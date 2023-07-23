The base class is the same, we can add features to it.

For eg : 
1. car 
  1. AC
  2. power steering
  3. seat covers
  4. fog lamps
  5. bumper 
  6. refrigerator
2. Base pizza
  1. cheese
  2. mushroom...

Decorator has a base pizza (Base + topping), it is also a pizza.

# Class explosion is avoided using decorator pattern 
Its very simple we have a base pizza class they can be diff types of base pizza we can instantiate the diff objects for the base pizza like Farm pizza, Veggie pizza and these can have the different toppings so we create a abstract class for the toppings, like cheese topping, mushroom topping.

#### As the topping pizza is also a pizza so it extends the pizza class and its a additional layer over the base pizza so it has the base pizza

## it has both `is-a` and `has-a` relationship.
```
public abstract class Pizza {
    public abstract double getPrice();

}
```

```
public class VeggeDelight extends Pizza{
    @Override
    public double getPrice(){
        return 100;
    }
}
```

```
public abstract class ToppingDecorator extends Pizza{

}
```

```
public class CheeseToping extends ToppingDecorator{
    Pizza pizza;
    CheeseToping(Pizza pizza){
        this.pizza = pizza;
    }

    @Override
    public double getPrice(){
        return this.pizza.getPrice() + 20;
    }

}```
