package com.example.demo.model.exceptions;

public class ForbiddenAttributeUpdate extends RuntimeException{
    public ForbiddenAttributeUpdate(String message) {
        super(message);
    }
}
