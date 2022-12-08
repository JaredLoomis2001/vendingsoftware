package com.techelevator.view;

import java.math.BigDecimal;

/* This class is used to create Product objects. A constructor is provided to set each product's
* location within the vending machine, its name, its price, and its type. All products start with 5 items
* in stock, and a numSold variable to be used as a counter to keep track of how many of that item has been sold.
*
*  The printProductInfo method output's a product's location, name, price, and current quantity to the user.*/

public class Product {
    private String location;
    private String name;
    private BigDecimal price;
    private String type;
    private int inStock = 5; // Sets starting quantity to 5
    private int numSold = 0; // Used to keep track of how much of a product has been sold

    public Product(String location, String name, BigDecimal price, String type) {
        this.location = location;
        this.name = name;
        this.price = price;
        this.type = type;
    }

    public void printProductInfo(){
        // Prints a product's location, name, price, and current stock
        if (inStock >= 1) {
            System.out.printf("%-4s%-22s$%-6.2f%5d\n", location, name, price, inStock);
        } else {
            System.out.printf("%-4s%-22s$%-6.2f%s\n", location, name, price, "SOLD OUT");
        }
    }

    // Getters
    public String getLocation() {
        return location;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getType() {
        return type;
    }

    public int getStock() {
        return inStock;
    }

    // Setters (inStock and numSold should be the only variables that change)
    public void setInStock(int inStock) {
        this.inStock = inStock;
    }

    public void setNumSold(int numSold) {
        this.numSold = numSold;
    }
}

