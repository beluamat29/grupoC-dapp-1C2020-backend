package com.example.demo.model;

import com.example.demo.serializers.DeliveryTypeJsonSerializaer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="delivery_type",
        discriminatorType = DiscriminatorType.STRING)
@JsonSerialize(using = DeliveryTypeJsonSerializaer.class)
public abstract class DeliveryType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    public DeliveryType(){};

    public abstract String deliveryAddress();
    public abstract LocalDateTime pickUpDate();
    public abstract Long getId();

    public abstract Boolean isStorePickUp();
}
