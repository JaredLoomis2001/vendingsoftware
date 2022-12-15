package com.techelevator.view;

import java.text.NumberFormat;

public class InvalidMoneyException extends NumberFormatException{

    @Override
    public String getMessage() {
        return message;
    }

    private String message = "You must feed money in whole dollar amounts.";

    public InvalidMoneyException () {
        super();
    }

    public InvalidMoneyException (String message) {
        super(message);
    }
}
