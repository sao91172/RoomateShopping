package edu.uga.cs.roommateshopping;

public class Item {

    private String key, name, user;
    private double price;
   // private Integer quantity;

    public Item() {
        this.key = null;
        this.name = null;
        this.price = 0.0;
        //this.quantity = 0;
    }

    public Item(String name, double price) {
        this.key = null;
        this.name = name;
        this.price = price;
       // this.quantity = 0;
    }

    public String getKey() {return this.key;}
    public String getName() {return this.name;}
    public double getPrice() {return this.price;}

    //public Integer getQuantity() {return this.quantity;}

    public void setKey(String keyIn) {this.key = keyIn;}
    public void setName(String nameIn) {this.name = nameIn;}
    public void setPrice(double priceIn) {this.price = priceIn;}

    //public void setQuantity(Integer quantityIn) {this.quantity = quantityIn;}

    public void setUser(String userIn) {this.user = userIn;}
    public String getUser() {return this.user;}

    public String toString() {return name + ": " + price;}


}
