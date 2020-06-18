package com.example.demo.model.exceptions;

public class NotFoundProductInStore extends RuntimeException {
    public NotFoundProductInStore(){
        super("This store does not sell that product");
    }
}
