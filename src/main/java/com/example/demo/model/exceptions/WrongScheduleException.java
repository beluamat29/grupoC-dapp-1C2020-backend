package com.example.demo.model.exceptions;

public class WrongScheduleException extends RuntimeException {

    public WrongScheduleException(){
        super("El horario de cierre no puede ser anterior al horario de apertura");
    }
}
