package com.techelevator.view;

import java.math.BigDecimal;
import java.text.NumberFormat;

//This class is to make things easier and hold all the values for the money in machine,
// including a method to change the balance when an item is purchased

public class VendingMachineBalance {
    private BigDecimal currentBalance;

    public VendingMachineBalance() {
        this.currentBalance = new BigDecimal(0.00);
    }

    public BigDecimal getCurrentBalance() {
        return currentBalance;
    }


    public void feedMoney(BigDecimal amountFed) {
        this.currentBalance = currentBalance.add(amountFed);
    }

    //Update customer balance,subtract the price of item from customer balance
    public void purchaseItem(BigDecimal priceOfItem) {
        //Transaction takes place ,Only if balance is available
        if (priceOfItem.compareTo(this.currentBalance) <= 0)
            this.currentBalance =(currentBalance.subtract(priceOfItem));
    }

    // Change balance to a currency formatted string
    public String balanceString() {
        NumberFormat nf = NumberFormat.getCurrencyInstance();
        double balance = currentBalance.doubleValue();
        String currency = nf.format(balance);
        return currency;
    }

}
