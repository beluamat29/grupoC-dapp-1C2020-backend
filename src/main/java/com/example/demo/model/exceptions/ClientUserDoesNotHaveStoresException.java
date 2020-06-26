package com.example.demo.model.exceptions;

public class ClientUserDoesNotHaveStoresException extends RuntimeException{
    public ClientUserDoesNotHaveStoresException(){
        super("client user do not have stores");
    }
}
