package com.example.demo.model;

import com.example.demo.model.exceptions.OptionNotAvailableForThisDeliveryType;

import java.time.LocalDateTime;

public class StorePickUp implements DeliveryType {

    private LocalDateTime pickUpDate;

    public StorePickUp(LocalDateTime hour){
        this.pickUpDate = hour;
    }

    @Override
    public String deliveryAddress() { throw new OptionNotAvailableForThisDeliveryType(); }

    @Override
    public LocalDateTime pickUpDate() {
        return this.pickUpDate;
    }
}
