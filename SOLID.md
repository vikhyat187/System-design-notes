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
 

 
