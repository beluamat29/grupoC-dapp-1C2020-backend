package com.example.demo.model.exceptions;

public class InvalidAddressException extends RuntimeException{
    public InvalidAddressException(){ super ("The address cannot be empty"); }
}
