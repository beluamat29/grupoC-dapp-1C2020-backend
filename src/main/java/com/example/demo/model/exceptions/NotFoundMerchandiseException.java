package com.example.demo.model.exceptions;

public class NotFoundMerchandiseException extends RuntimeException{
    public NotFoundMerchandiseException(){ super("There is not a merchandise with that name and brand"); }
}
