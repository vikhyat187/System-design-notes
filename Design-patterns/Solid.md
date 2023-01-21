OOPs design principles
1. DRY 
2. SRP
3. Open closed
4. liskov substitution
5. dependency inversion
6. interface segregation
7. delegation



## S -> single responsibility

``` 
class Marker {
  String name;
  String color;
  Integer year;
  Integer price;
public Marker(String name, String color, Integer year, Integer price){
  this.name = name;
  this.color = color;
  this.year = year;
  this.price = price;
  }

}

class Invoice{
  private Marker marker;
  private int qty;
  
  public Invoice(Marker marker, int qty){
    this.marker = marker;
    this.qty = qty;
   }
   
   public int calculateTotal(){
    int price = (maker.price) * this.quantity;
    return price;
   }
   
   public void printInvoice(){    ----> move to invoice printer class
    }
    
    public void saveToDb(){      ---> move to dao class
    }
    
 }
 
 
 ```
 
 ## Open close principle
 
 - Open for extension, closed for modifications.

```
class InvoiceDao{
  Invoice invoice;
  
  public InvoiceDao(Invoice invoice){
    this.invoice = invoice;
   }
   
   public void saveToDb(){
   
   }
   
   public void saveToFile(){
    //  wrong do not modify functionality in tested code.
   }
  }
  
  Interface InvoiceDao{
    public void save();
   }
   
   class SaveToDb implements InvoiceDao{
      public void save(){
<!--         specific code -->
      
      }
     }
     
     class SaveToFile implements InvoiceDao{
      public void save(){
<!--         specific code -->
      
      }
     }
  ```
   
 ## Liskov substitution principle 
 
 If class B is an subtype of class A then we can replace the obj of class A by class B without breaking the code.


## Interface segmented principle

Divide the interface into smaller peices so that the user need not implement unnecessary things.
```
Interface RestaurantEmployee{
  public void washDish();
  public void cookFood();
  public void serveCustomer();
}

Interface waiterInterface(){
  public void serveFood();
  public void takeOrder();
 }
 
 
 Interface chefInterface{
   public void cookFood();
   public void decideMenu();

```
 
 ## Dependency inversion

class should dependent on interface rather than concreate implementation


```
class Macbook{
  private Keyboard keyboard;
  private Mouse mouse;
  
  public Macbook(){
    this.keyboard = wiredKeyboard;
    this.mouse = wiredMouse;
  }
}


class Macbook{
  private Keyboard keyboard;
  private Mouse mouse;
  
  public Macbook(Keyboard keyboard, Mouse mouse){
    this.keyboard = keyboard;
    this.mouse = mouse;
    }
 }
 
 ```
 

