package com.example.demo.model.exceptions;

public class InvalidMailException extends RuntimeException {
    public InvalidMailException(){ super("Format mail is invalid");}
}
