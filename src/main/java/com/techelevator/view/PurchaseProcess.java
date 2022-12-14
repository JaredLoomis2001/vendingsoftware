package com.techelevator.view;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
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
      BigDecimal change = vendingMachineBalance.getCurrentBalance();
        int nickels = 0;
        int quarters = 0;
        int dimes = 0;
        int penny = 0;

        while (change.compareTo(BigDecimal.valueOf(0)) > 0) {
            if (change.doubleValue() >= 0.25) {
                quarters++;
                change = change.subtract(BigDecimal.valueOf(0.25));
            } else if (change.doubleValue() >= 0.10) {
                dimes++;
                change.subtract(BigDecimal.valueOf(0.10));
            } else if (change.doubleValue() >= 0.05) {
                nickels++;
                change.subtract(BigDecimal.valueOf(0.05));
            } else {
                penny++;
                change.subtract(BigDecimal.valueOf(0.01));
            }
        }

        System.out.println("Dispensing Change: " + quarters +" Quarter(s) | " + dimes + " Dime(s) | " + nickels+ " Nickel(s) | " + penny + " Penny(s)");
        System.out.println("Your balance is now: 0.00");
    }
}


