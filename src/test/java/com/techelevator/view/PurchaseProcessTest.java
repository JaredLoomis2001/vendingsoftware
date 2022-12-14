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
    private VendingMachineBalance vendingMachineBalance;


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
    public void purchaseProcess_Test_PurchaseProduct_INVALID_SLOTID(){

        input   =   new ByteArrayInputStream("A12\n".getBytes());
        output  =    new ByteArrayOutputStream();

        PrintStream printStream = new PrintStream(output,true);
        System.setOut(printStream);
        PurchaseProcess purchaseProcess = new PurchaseProcess(input, printStream);
        purchaseProcess.purchaseProduct(inv, vendingMachineBalance);

        // System.out.println(outputStreamCaptor.toString());
        String expected="Invalid slot id entered A12";
        Assert.assertEquals(expected,output.toString().substring(40).trim());
        //substring of 40 so that only second line -only the error message is taken to assert
        try {
            output.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void purchaseProcess_Test_PurchaseProduct_OUT_OF_STOCK(){

    }

    @Test
    public void PurchaseProcess_TEST_finishTransaction(){

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

