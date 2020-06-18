package com.example.demo.model.exceptions;

public class InvalidMerchandiseException extends RuntimeException {
    public InvalidMerchandiseException(){ super("Invalid merchandise, please check if all fields are complete"); }
}
