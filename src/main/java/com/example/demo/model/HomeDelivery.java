package com.example.demo.model;

import com.example.demo.model.exceptions.OptionNotAvailableForThisDeliveryType;

import java.time.LocalDateTime;

public class HomeDelivery implements DeliveryType {

    private String deliveryAddress;
    private LocalDateTime deliveryDate;

    public HomeDelivery(String address, LocalDateTime aDeliveryDate){
        deliveryAddress = address;
        deliveryDate = aDeliveryDate;
    }
    @Override
    public String deliveryAddress() {
        return this.deliveryAddress;
    }

    @Override
    public LocalDateTime pickUpDate() { return this.deliveryDate; }
}
