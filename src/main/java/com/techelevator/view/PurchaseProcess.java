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

        public void purchaseProduct(File dataFile) {
            out.println(System.lineSeparator() + "Enter the code of item to be dispensed");
            out.flush();
            String code = in.nextLine();
            out.println(code);
            Inventory iv = new Inventory(dataFile);
            Map<String, Product> itemMap = new LinkedHashMap<>();
            itemMap = iv.getProducts();

            if (!itemMap.containsKey(code))
                out.println("Invalid slot id entered " + code);
            else {
                // out.println();
            }
        }
    }


