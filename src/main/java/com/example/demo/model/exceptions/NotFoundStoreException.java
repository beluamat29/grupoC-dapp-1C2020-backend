package com.example.demo.model.exceptions;

public class NotFoundStoreException extends RuntimeException{
    public NotFoundStoreException() { super("Store was not found");}
}
