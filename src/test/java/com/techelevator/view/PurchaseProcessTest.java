package com.techelevator.view;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

public class PurchaseProcessTest {
    private ByteArrayOutputStream output;
    private ByteArrayInputStream  input;

    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    private Product product;

    private Inventory inv;
    private VendingMachineBalance vendingMachineBalance = new VendingMachineBalance();


    @Before
    public void initialize() {

        // Set system output to new PrintStream to capture output
        System.setOut(new PrintStream(outputStreamCaptor));

        //new vendingMachineBalance to hold balance for purchases
        VendingMachineBalance vendingMachineBalance = new VendingMachineBalance();

        File dataFile = new File("vendingmachine.csv"); // CSV File
        inv = new Inventory(dataFile); // Inventory made with CSV File


        //New constructor of product
        product = new Product("C1", "Test Snack", new BigDecimal("1.00"), "Chip");

    }

    @Test
    public void PurchaseProcess_TEST_finishTransaction(){
        input   =   new ByteArrayInputStream("A1\n".getBytes());
        output  =    new ByteArrayOutputStream();


        vendingMachineBalance.setCurrentBalance(BigDecimal.valueOf(1.10));
        PrintStream printStream = new PrintStream(output,true);
        PurchaseProcess purchaseProcess = new PurchaseProcess(input , printStream);
        purchaseProcess.finishTransaction(vendingMachineBalance);
        String expected = "Dispensing Change: 4 Quarter(s) | 1 Dime(s) | 0 Nickel(s) | 0 Penny(s)\r\n";
        String expectedBalance = "Your balance is now: $0.00\r\n";

       Assert.assertEquals(expected + expectedBalance, outputStreamCaptor.toString());
    }


    @Test
    public void purchaseProcess_TEST_getFedMoney(){
        input= new ByteArrayInputStream("10\n".getBytes());
        PurchaseProcess purchaseProcess = new PurchaseProcess(input,System.out);
        int money  =   purchaseProcess.getFedMoney();

        String expected="10";
        String actual = String.valueOf(money);
        Assert.assertEquals(expected,actual);
    }



    @Test
    public void PurchaseProcess_TEST_printMessage_According_To_Product_Type(){

        PurchaseProcess purchaseProcess = new PurchaseProcess(System.in, System.out);
        purchaseProcess.printMessage(product.getType());
        String expected="Crunch Crunch, Yum!";
        Assert.assertEquals(expected,outputStreamCaptor.toString().trim());

    }


    @After
    public void tearDown() {
        // Return system output to System.out
        System.setOut(standardOut);
    }
}

