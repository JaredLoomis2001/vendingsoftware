package com.techelevator.view;

import java.math.BigDecimal;
import java.util.Objects;

/* This class is used to create Product objects. A constructor is provided to set each product's
* slotID within the vending machine, its name, its price, and its type. All products start with 5 items
* in stock, and a numSold variable to be used as a counter to keep track of how many of that item has been sold.
*
*  The printProductInfo method output's a product's slotID, name, price, and current quantity to the user.*/

public class Product {
    private String slotID;
    private String name;
    private BigDecimal price;
    private String type;
    private final static int STARTING_STOCK = 5; // Sets starting quantity to 5
    private int numSold = 0; // Used to keep track of how much of a product has been sold
    private int inStock;

    public Product(String slotID, String name, BigDecimal price, String type) {
        // Objects.requireNonNull throws a NullPointerException if the Object contains a null value
        this.slotID = Objects.requireNonNull(slotID, "Slot ID can't be null or empty.");
        this.name = Objects.requireNonNull(name, "Name can't be null or empty.");
        this.price = Objects.requireNonNull(price, "Price can't be null or empty.");
        this.type = Objects.requireNonNull(type, "Type can't be null or empty.");
        this.inStock = STARTING_STOCK;
    }

    public void printProductInfo(){
        // Prints a product's slotID, name, price, and current stock
        if (inStock >= 1) {
            System.out.printf("%-4s%-22s$%-6.2f%5d\n", slotID, name, price, inStock);
        } else {
            System.out.printf("%-4s%-22s$%-6.2f%s\n", slotID, name, price, "SOLD OUT");
        }
    }

    //Update stock details when dispensing product from vending machine
    public void updateStockInfo() {
        if (inStock > 0) {
            inStock--;
            numSold++;
        }
    }

    // Getters
    public String getSlotID() {
        return slotID;
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

