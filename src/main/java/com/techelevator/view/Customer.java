package com.techelevator.view;

public class Customer {
    private double currentBalance;

    public Customer() {
        this.currentBalance = 0;
    }

    public double getCurrentBalance() {
        return currentBalance;
    }


    public void feedMoney(double amountFed) {
        this.currentBalance += amountFed;
    }

    //Update customer balance,subtract the price of item from customer balance
    public void purchaseItem(double priceOfItem) {
        //Transaction takes place ,Only if balance is available
        if (priceOfItem < this.currentBalance)
            this.currentBalance -= priceOfItem;
    }
    //This class is to make things easier and hold all the values for the money in machine
    // for whoever works on Finish Transaction to easier, including a method to change the balance when an item is purchased

}
