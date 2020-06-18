package com.example.demo.model.exceptions;

public class NotAvailableUserNameException extends RuntimeException{
    public NotAvailableUserNameException() {
        super("This username is not available");
    }
}
