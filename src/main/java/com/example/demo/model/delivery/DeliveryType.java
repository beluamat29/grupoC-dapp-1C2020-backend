package com.example.demo.model.delivery;

import java.time.LocalDateTime;

public interface DeliveryType {

    String deliveryAddress();
    LocalDateTime pickUpDate();

}
