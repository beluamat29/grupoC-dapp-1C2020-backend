package com.example.demo.model;


import com.example.demo.serializers.DeliveryTypeJsonSerializaer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@DiscriminatorValue("HOME_DELIVERY")
@JsonSerialize(using = DeliveryTypeJsonSerializaer.class)
public class HomeDelivery extends DeliveryType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String deliveryAddress;
    private LocalDateTime deliveryDate;

    public HomeDelivery(String address, LocalDateTime aDeliveryDate){
        deliveryAddress = address;
        deliveryDate = aDeliveryDate;
    }

    public HomeDelivery(){};

    @Override
    public String deliveryAddress() {
        return this.deliveryAddress;
    }

    @Override
    public LocalDateTime pickUpDate() { return this.deliveryDate; }

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public Boolean isStorePickUp() {
        return false;
    }
}
