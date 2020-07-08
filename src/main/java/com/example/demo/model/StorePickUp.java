package com.example.demo.model;

import com.example.demo.model.exceptions.OptionNotAvailableForThisDeliveryType;
import com.example.demo.serializers.DeliveryTypeJsonSerializaer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@DiscriminatorValue("PICK_UP")
@JsonSerialize(using = DeliveryTypeJsonSerializaer.class)
public class StorePickUp extends DeliveryType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    @Override
    public Long getId() {
        return this.id;
    }
}
