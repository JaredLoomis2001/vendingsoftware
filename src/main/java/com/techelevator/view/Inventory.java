package com.techelevator.view;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/* This class is used to create the vending machine's inventory. It populates a LinkedHashMap with a dataFile by looping
 * through each line in the dataFile and splitting the line into a '|' delimited array. The array then contains 4 indexes:
 *
 * 0 - The item's slotID in the machine
 * 1 - The item's name
 * 2 - The item's price
 * 3 - The item's type
 *
 *  The item's location is then added to the map as a key using index 0, and a new product is created and added as a value;
 * the product being created with the four indexes listed above.
 *
 *  A method (listProducts) is also provided which loops through each product in the map and calls its printProductInfo()
 * method (provided by the Product class).*/

public class Inventory {
    // HashMap kept placing the products out of order, so switched to LinkedHashMap
    // to keep entries in the order in which they are added to the map
    private static Map<String, Product> items = new LinkedHashMap<>();

    // Constructor used for testing purposes
    public Inventory(List<Product> productList) {
        for (Product product : productList) {
            items.put(product.getSlotID().toLowerCase(), product);
        }
    }

    // Constructor for VendingMachineCLI
    public Inventory(File dataFile) {
        try (Scanner dataInput = new Scanner(dataFile)) {
            while (dataInput.hasNextLine()) {
                // Splitting each line into an array
                String[] itemArray = dataInput.nextLine().split("\\|");
                // Array indexes will always be 0: Location, 1: Item Name, 2: Price, and 3: Type
                // Location is used for the key, then a new Product is added as the value
                items.put(itemArray[0].toLowerCase(), new Product(itemArray[0], itemArray[1],
                        new BigDecimal(Double.valueOf(itemArray[2])), itemArray[3]));
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found at path: " + dataFile.getAbsolutePath());
        }
    }

    public Map<String, Product> getProducts() {
        return items;
    }

    public void listProducts() {
        // Runs through each product in the inventory and lists its slotID, Name, Price, and current stock
            System.out.printf("%-4s%-22s%-7s%5s\n", "##", "Product Name", "Price", "Qty.");
            System.out.print("---------------------------------------\n");
            for (Map.Entry<String, Product> item : items.entrySet()) {
                if (item.getKey() != null && item.getValue() != null) {
                    item.getValue().printProductInfo();
                } else {
                    throw new NullPointerException("Null value found in current inventory.");
                }
            }
            System.out.print("---------------------------------------\n\n");
    }
}

