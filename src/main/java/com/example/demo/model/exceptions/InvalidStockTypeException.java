package com.example.demo.model.exceptions;

public class InvalidStockTypeException extends RuntimeException {

    public InvalidStockTypeException() {
        super("Impossible to add a negative amount of products to merchandise stock");
    }
}
