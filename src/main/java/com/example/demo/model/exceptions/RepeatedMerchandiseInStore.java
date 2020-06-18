package com.example.demo.model.exceptions;

public class RepeatedMerchandiseInStore extends RuntimeException {
    public RepeatedMerchandiseInStore(){
        super("You can't add the same product in a store twice");
    }
}
