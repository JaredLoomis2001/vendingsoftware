package com.techelevator.view;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class PurchaseProcess {
    private PrintWriter out;
    private Scanner in;


        public PurchaseProcess(InputStream input, OutputStream output) {
            this.out = new PrintWriter(output);
            this.in = new Scanner(input);
        }

        public void purchaseProduct(Inventory inventory) {
            out.println(System.lineSeparator() + "Enter the code of item to be dispensed");
            out.flush();
            String code = in.nextLine();
            out.println(code);

            Map<String, Product> itemMap = inventory.getProducts();

            if (!itemMap.containsKey(code))
                out.println("Invalid slot id entered " + code);
            else {
                // out.println();
                out.println(itemMap.get(code).getName());
                out.flush();
            }
        }

        public double getFedMoney () {
        out.print(System.lineSeparator() + "Please give the amount you wish to feed in format (0.00): ");
        out.flush();
        double moneyFed = Double.parseDouble(in.nextLine());
        return moneyFed;
        }
    }


