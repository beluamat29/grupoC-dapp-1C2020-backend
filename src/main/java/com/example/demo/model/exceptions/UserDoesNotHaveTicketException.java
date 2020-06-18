package com.example.demo.model.exceptions;

public class UserDoesNotHaveTicketException extends RuntimeException{
    public UserDoesNotHaveTicketException(){
        super("This user does not have that ticket");
    }
}
