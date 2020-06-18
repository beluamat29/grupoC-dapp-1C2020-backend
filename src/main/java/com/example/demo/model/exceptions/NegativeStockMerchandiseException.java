package com.example.demo.model.exceptions;

public class NegativeStockMerchandiseException extends RuntimeException {
    public NegativeStockMerchandiseException(){
        super("No se puede tener stock negativo de un producto");
    }

}
