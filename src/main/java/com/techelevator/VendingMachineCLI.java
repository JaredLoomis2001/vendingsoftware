package com.techelevator;

import com.techelevator.view.Customer;
import com.techelevator.view.Inventory;
import com.techelevator.view.Menu;
import com.techelevator.view.PurchaseProcess;

import java.io.File;
import java.math.BigDecimal;

public class VendingMachineCLI {

    private static final String MAIN_MENU_OPTION_DISPLAY_ITEMS = "Display Vending Machine Items";
    private static final String MAIN_MENU_OPTION_PURCHASE = "Purchase";

    private static final String MAIN_MENU_OPTION_EXIT = "Exit";
    private static final String[] MAIN_MENU_OPTIONS = {MAIN_MENU_OPTION_DISPLAY_ITEMS, MAIN_MENU_OPTION_PURCHASE, MAIN_MENU_OPTION_EXIT};
    private static final String PURCHASE_MENU_FEED_MONEY = "Feed Money";
    private static final String PURCHASE_MENU_SELECT = "Select Product";
    private static final String PURCHASE_MENU_FINISH_TRANSACTION = "Finish Transaction";
    private static final String[] PURCHASE_MENU_OPTIONS = {PURCHASE_MENU_FEED_MONEY, PURCHASE_MENU_SELECT, PURCHASE_MENU_FINISH_TRANSACTION};


    private Menu menu;
    private File dataFile = new File("vendingmachine.csv"); // CSV File
    private Inventory inv = new Inventory(dataFile); // Inventory made with CSV File

    private PurchaseProcess purchaseProcess = new PurchaseProcess(System.in, System.out); //Purchase process Implementation

    private Customer customer = new Customer(); //new customer to hold balance for purchases

    public VendingMachineCLI(Menu menu) {
        this.menu = menu;
    }

    public void run() {
        while (true) {
            String choice = (String) menu.getChoiceFromOptions(MAIN_MENU_OPTIONS);

            if (choice.equals(MAIN_MENU_OPTION_DISPLAY_ITEMS)) {
                // display vending machine items
                inv.listProducts();
            } else if (choice.equals(MAIN_MENU_OPTION_PURCHASE)) {
                //Display the submenu
                displayPurchaseMenu();
            } else {
                System.exit(0);
            }
        }
    }


    public void displayPurchaseMenu() {
        while (true) {
            // do purchase
            String purchaseChoice = (String) menu.getChoiceFromOptions(PURCHASE_MENU_OPTIONS);

            //check if user wants to feed money in
            //also having difficulty thinking how to display the current balance above the menu as it is shown in the
            // example in the README as well as how to go about looping back to the purchase menu after depositing
            // to the balance
            if (purchaseChoice.equals(PURCHASE_MENU_FEED_MONEY)) {
                BigDecimal money = purchaseProcess.getFedMoney();
                customer.feedMoney(money);
                System.out.println(customer.getCurrentBalance());

            } else if (purchaseChoice.equals(PURCHASE_MENU_SELECT)) {

                //display vending machine items
                inv.listProducts();

					/*Implement purchase process
					Allow customer to select product,dispense product,update stock and balance
					 */
                purchaseProcess.purchaseProduct(inv, customer);

            } else if (purchaseChoice.equals(PURCHASE_MENU_FINISH_TRANSACTION)) {
                //do finish transaction
                purchaseProcess.finishTransaction(customer);
                return;
            }
        }
    }

    public static void main(String[] args) {
        Menu menu = new Menu(System.in, System.out);
        VendingMachineCLI cli = new VendingMachineCLI(menu);
        cli.run();
    }
}
