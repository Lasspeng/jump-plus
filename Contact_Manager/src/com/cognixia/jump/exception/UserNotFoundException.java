package com.cognixia.jump.exception;

public class UserNotFoundException extends Exception {
    
    public UserNotFoundException() {
        super("The input you entered does not match with the database. Please try again.");
    }
}
