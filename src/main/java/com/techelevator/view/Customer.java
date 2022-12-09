package com.techelevator.view;

public class Customer {
    public double getCurrentBalance() {
        return currentBalance;
    }

    private double currentBalance;

    public Customer () {
        this.currentBalance = 0;
    }

    public void feedMoney (double amountFed) {
        this.currentBalance += amountFed;
    }

    public void purchaseItem (double priceOfItem) {
        this.currentBalance -= priceOfItem;
    }
        //This class is to make things easier and hold all the values for the money in machine
        // for whoever works on Finish Transaction to easier, including a method to change the balance when an item is purchased

}
