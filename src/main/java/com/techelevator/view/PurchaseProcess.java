package com.techelevator.view;

import com.techelevator.view.Exceptions.InsufficientFundsException;
import com.techelevator.view.Exceptions.InvalidSlotIdException;
import com.techelevator.view.Exceptions.OutOfStockException;

import java.io.*;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

public class PurchaseProcess {
    private PrintWriter out;
    private Scanner in;

    private static PrintWriter dataOutput = null;// PrintWriter object for logging is created

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

    public void purchaseProduct(Inventory inventory, VendingMachineBalance vendingMachineBalance) throws
            InsufficientFundsException, OutOfStockException, InvalidSlotIdException {

        out.println(System.lineSeparator() + "Enter the code of item to be dispensed");
        String code = in.nextLine();

        Map<String, Product> itemMap = inventory.getProducts();

        if (!itemMap.containsKey(code.toLowerCase())) {
            throw new InvalidSlotIdException("Invalid slotID : " + code + " .Enter valid slotID");

        } else {
            Product prod = itemMap.get(code.toLowerCase());

            //Throw out of stock exception ,if the item is not in stock
            if (prod.getStock() < 1)
                throw new OutOfStockException(prod.getName() +" is out of stock");

                //Do the below process only if item is in stock
            else {

                //Display details of the item selected by customer
                System.out.printf("%-4s%-22s$%-6.2f\n", prod.getSlotID(), prod.getName(), prod.getPrice());

                if ((prod.getPrice().compareTo(vendingMachineBalance.getCurrentBalance()) < 1)) {
                    //Update the stock details of the item selected by customer
                    prod.updateStockInfo();

                    //Update the vendingMachineBalance balance , subtract price of product from vendingMachineBalance balance
                    //when dispensing the product
                    vendingMachineBalance.purchaseItem(prod.getPrice());

                    //Print appropriate message when dispensing product
                    printMessage(prod.getType());

                    //Log All transactions to Log.txt
                    //change the currency format to log inside Log.txt
                    NumberFormat nf = NumberFormat.getCurrencyInstance();
                    double balance = prod.getPrice().doubleValue();
                    String currency = nf.format(balance);
                    PurchaseProcess.logTransaction(" " + prod.getName() + "  " + prod.getSlotID()
                            + "  " + currency + "  " + vendingMachineBalance.balanceString());

                } else {

                    throw new InsufficientFundsException("Customer does not have enough Money.Transaction not allowed ");
                }
            }
        }
    }

    /*Method to print appropriate message according to the
    type of the item dispensed from vending machine
     */
    public void printMessage(String type) {
        try {
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
                    throw new IllegalArgumentException("Invalid code");
            }
        } catch (IllegalArgumentException exception) {
            out.println("Invalid code entered");
        }

    }

    public int getFedMoney() {
        out.println(System.lineSeparator() + "Please give the amount you wish to feed in whole dollar amounts: ");
                int moneyFed = 0;
            try {
                moneyFed = in.nextInt();
                if (moneyFed < 0) {
                    moneyFed = 0;
                    throw new NumberFormatException();
                }
            } catch (InputMismatchException e) {
                moneyFed = 0;
                System.out.println("ERR : Please do not input a non-integer character.");
                in.next();
            } catch (NumberFormatException e) {
                moneyFed = 0;
                System.out.println("ERR : Machine does not accept negative dollars nor non-whole dollar amounts.");
            }



        return moneyFed;
    }

    public void finishTransaction(VendingMachineBalance vendingMachineBalance) {

        BigDecimal change = vendingMachineBalance.getCurrentBalance();
        String changeString = vendingMachineBalance.balanceString();
        int nickels = 0;
        int quarters = 0;
        int dimes = 0;
        int penny = 0;
        // Turns big decimal in to a double and then into a number easier to subtract from
        // Now the balance is turned into a whole number which will give more precise coins
        double coins = change.doubleValue() * 100;
         try {
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
            } else if (coins >= 1) {
                penny++;
                coins -= 1;
            } else {
                coins = 0;
            }

        }
         }
        // trying to catch any errors that might occur dispensing change
        catch (Exception e){
          System.out.println("There was an error dispensing your change");
         }

        System.out.println("Dispensing Change: " + quarters + " Quarter(s) | " + dimes + " Dime(s) | " + nickels + " Nickel(s) | " + penny + " Penny(s)");
        // needs setBalance to return balance to 0
        vendingMachineBalance.setCurrentBalance(BigDecimal.valueOf(0.00));
        System.out.println("Your balance is now: " + vendingMachineBalance.balanceString());

        //log transaction to log.txt
        PurchaseProcess.logTransaction(" GIVE CHANGE : " + changeString + " " + vendingMachineBalance.balanceString());


    }

    /*
     The vending machine logs all transactions to prevent theft from the vending machine.
     Each purchase must generate a line in a file called Log.txt.
     The first dollar amount is the amount deposited, spent, or given as change.
     The second dollar amount is the new balance.
      */
    public static void logTransaction(String message) {

        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss aa");

        try {

            File logFile = new File("Log.txt");
            if (!logFile.exists())
                logFile.createNewFile();
            if (dataOutput == null) {
                dataOutput = new PrintWriter(
                        // Passing true to the FileOutputStream constructor says to append
                        new FileOutputStream(logFile, true));
            }

            dataOutput.print(dateFormat.format(date));
            dataOutput.println(" " + message);
            dataOutput.flush();


        } catch (FileNotFoundException e) {
            System.err.println("Exception  :" + e.getMessage());
        } catch (Exception exception) {
            System.err.println("Exception  :" + exception.getMessage());
        }

    }

}


