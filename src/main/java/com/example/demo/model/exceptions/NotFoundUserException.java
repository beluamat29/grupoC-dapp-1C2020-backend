package com.example.demo.model.exceptions;

public class NotFoundUserException  extends RuntimeException {
    public NotFoundUserException(){
        super("There is not a registered user with that username");
    }
}

