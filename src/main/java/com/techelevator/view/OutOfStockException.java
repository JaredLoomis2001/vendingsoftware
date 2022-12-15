package com.techelevator.view;

public class OutOfStockException extends Exception {

    public OutOfStockException() {
    }

    public OutOfStockException(String message) {
        super(message);
    }

    public OutOfStockException(String message, Exception cause) {
        super(message, cause);
    }


}
