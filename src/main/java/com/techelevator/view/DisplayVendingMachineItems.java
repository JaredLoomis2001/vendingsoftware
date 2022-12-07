package com.techelevator.view;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class DisplayVendingMachineItems {
    private static final String FILE_PATH   = "vendingmachine.csv";

    public void displayItems() {
        File dataFile = new File(FILE_PATH);
        System.out.println("Slot    Name                Price           TYPE");

        try (Scanner dataInput = new Scanner(dataFile)) {
            while (dataInput.hasNextLine()) {
                String lineOfInput = dataInput.nextLine();
                String[] inputArray = lineOfInput.split("\\|");
                for(int i=0;i<inputArray.length;i++) {
                    System.out.print(inputArray[i] + "\t\t");

                }
                System.out.println("");
            }
        } catch (FileNotFoundException e) {
            System.out.println("File does not exist.");
        }
    }
}
