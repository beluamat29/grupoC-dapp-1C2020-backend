package com.example.demo.model.exceptions;

public class OptionNotAvailableForThisDeliveryType extends RuntimeException {
    public OptionNotAvailableForThisDeliveryType(){ super("Option not available for this delivery type.");}
}

