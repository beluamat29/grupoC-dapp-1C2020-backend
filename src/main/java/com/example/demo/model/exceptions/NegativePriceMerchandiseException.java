package com.example.demo.model.exceptions;

public class NegativePriceMerchandiseException extends RuntimeException {
    public NegativePriceMerchandiseException(){
        super("No se puede tener un producto con precio negativo");
    }
}
