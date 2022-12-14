package com.techelevator.view;

import java.io.*;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
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
    1.Show the list of products available and allow the vendingMachineBalance to enter a code to select an item.
    2.If the product code doesn't exist, the vending machine informs the vendingMachineBalance
    3.If a product is currently sold out, the vending machine informs the vendingMachineBalance
    4.If a vendingMachineBalance selects a valid product, it's dispensed to the vendingMachineBalance.
    5.Dispensing an item prints the item name, cost, and the money remaining and a message
    6.After the machine dispenses the product, the machine must update its balance
    accordingly and return the vendingMachineBalance to the Purchase menu.
    */
    public void purchaseProduct(Inventory inventory, VendingMachineBalance vendingMachineBalance) {
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


                //Display details of the item selected by vendingMachineBalance
                prod.printProductInfo();
                prod.updateStockInfo();
                out.println(prod.getStock());

                //Update the vendingMachineBalance balance , subtract price of product from vendingMachineBalance balance
                //when dispensing the product
                vendingMachineBalance.purchaseItem(prod.getPrice());
                out.println("vendingMachineBalance bal " + vendingMachineBalance.getCurrentBalance());
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

    public int getFedMoney() {
        out.println(System.lineSeparator() + "Please give the amount you wish to feed in format (0.00): ");
        int moneyFed = Integer.parseInt(in.nextLine());
        return moneyFed;
    }
    public  void finishTransaction(VendingMachineBalance vendingMachineBalance) {

        BigDecimal change = vendingMachineBalance.getCurrentBalance() ;
        int nickels = 0;
        int quarters = 0;
        int dimes = 0;
        int penny = 0;
        // Turns big decimal in to a double and then into a number easier to subtract from
        // Now the balance is turned into a whole number which will give more precise coins
        double coins = change.doubleValue() * 100;
        // try {
        while (coins > 0) {
            if (coins >= 25) {
                quarters++;
                coins -= 25;
            } else if (coins >= 10) {
                dimes++;
                coins -= 10;
            } else if (coins >= 5) {
                nickels++;
                coins -= 5;
            } else if (coins >=1) {
                penny++;
                coins -= 1;
            } else {
                coins = 0;
            }

        }
        //  }
        // trying to catch any errors that might occur dispensing change
        // catch (Exception e){
        //   System.out.println("There was an error dispensing your change");
        //  }

        System.out.println("Dispensing Change: " + quarters +" Quarter(s) | " + dimes + " Dime(s) | " + nickels+ " Nickel(s) | " + penny + " Penny(s)");
        // needs setBalance to return balance to 0
        System.out.println("Your balance is now: $0.00" );
    }
    public  void logTransaction (VendingMachineBalance balance , String message) {
        File log = new File("Log.txt");
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");


        if (!log.exists()) {
            try {
                log.createNewFile();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            try (PrintWriter logging = new PrintWriter(new FileOutputStream(log, true))) {

                logging.println(dateFormat.format(date) + message + balance.getCurrentBalance());

            } catch (Exception e) {
                System.out.println(e.getMessage());

            }

        }
        if (log.exists()) {
            try (PrintWriter logging = new PrintWriter(new FileOutputStream(log, true))) {
                logging.println(dateFormat.format(date)+  message +  balance.getCurrentBalance());
                //new log

            } catch (Exception e) {
                System.out.println("There was a problem writing to the log file.");
                System.out.println(e.getMessage());
            }


        }
    }
}


