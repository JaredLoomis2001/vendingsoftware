package com.techelevator.view;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class PurchaseProcess {
    private PrintWriter out;
    private Scanner in;


    public PurchaseProcess(InputStream input, OutputStream output) {
        this.out = new PrintWriter(output, true);
        this.in = new Scanner(input);
    }

    /*Implement purchase process
    1.Show the list of products available and allow the customer to enter a code to select an item.
    2.If the product code doesn't exist, the vending machine informs the customer
    3.If a product is currently sold out, the vending machine informs the customer
    4.If a customer selects a valid product, it's dispensed to the customer.
    5.Dispensing an item prints the item name, cost, and the money remaining and a message
    6.After the machine dispenses the product, the machine must update its balance
    accordingly and return the customer to the Purchase menu.
    */
    public void purchaseProduct(Inventory inventory, Customer customer) {
        out.println(System.lineSeparator() + "Enter the code of item to be dispensed");
        String code = in.nextLine();

        Map<String, Product> itemMap = inventory.getProducts();

        if (!itemMap.containsKey(code.toLowerCase())) {
            out.println("Invalid slot id entered " + code);

        } else {
            Product prod = itemMap.get(code.toLowerCase());
            if (prod.getStock() < 1)
                out.println("Item is out of stock");
                //Do the below process only if item is in stock
            else {
                out.println("code " + code);
                out.println(itemMap.get(code.toLowerCase()).getName());


                //Display details of the item selected by customer
                prod.printProductInfo();
                prod.updateStockInfo();
                out.println(prod.getStock());

                //Update the customer balance , subtract price of product from customer balance
                //when dispensing the product
                customer.purchaseItem(prod.getPrice());
                out.println("customer bal " + customer.getCurrentBalance());
                //Print appropriate message when dispensing product
                printMessage(prod.getType());

            }
        }
    }

    /*Method to print appropriate message according to the
    type of the item dispensed from vending machine
     */
    public void printMessage(String type) {
        switch (type) {
            case "Chip":
                out.println("Crunch Crunch, Yum!");
                break;
            case "Candy":
                out.println("Munch Munch, Yum!");
                break;
            case "Drink":
                out.println("Glug Glug, Yum!");
                break;
            case "Gum":
                out.println("Chew Chew, Yum!");
                break;
            default:
                throw new RuntimeException("Invalid code");
        }
    }

    public BigDecimal getFedMoney() {
        out.println(System.lineSeparator() + "Please give the amount you wish to feed in format (0.00): ");
        BigDecimal moneyFed = new BigDecimal(Double.parseDouble((in.nextLine())));
        return moneyFed;
    }
    public  void finishTransaction(Customer customer) {
      double change = 0;
        int nickels = 0;
        int quarters = 0;
        int dimes = 0;
        int penny = 0;

        change = customer.getCurrentBalance().doubleValue();
        while (change > 0) {
            if (change >= 0.25) {
                quarters++;
                change -= 0.25;
            } else if (change >= 0.10) {
                dimes++;
                change -= 0.10;
            } else if (change >= 0.05) {
                nickels++;
                change -= 0.05;
            } else {
                penny++;
                change -= 0.01;
            }
        }

        System.out.println("Dispensing Change: " + quarters +" Quarter(s) | " + dimes + " Dime(s) | " + nickels+ " Nickel(s) | " + penny + " Penny(s)");
        System.out.println("Your balance is now: 0.00");
    }
}


