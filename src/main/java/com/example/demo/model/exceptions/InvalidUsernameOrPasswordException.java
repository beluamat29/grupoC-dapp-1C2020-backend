package com.example.demo.model.exceptions;

public class InvalidUsernameOrPasswordException extends RuntimeException{

    public InvalidUsernameOrPasswordException() {super("The username cannot be empty");}
}
