package com.example.demo.model.delivery;
import com.example.demo.model.HomeDelivery;
import com.example.demo.model.StorePickUp;
import com.example.demo.model.exceptions.OptionNotAvailableForThisDeliveryType;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.*;


import java.time.LocalDateTime;

public class DeliveryTypeTest {

    @Test
    public void aHomeDeliveryHasAnAddressAndADeliveryTime() {
        String address = "Alsina 123";
        LocalDateTime deliveryTime =  LocalDateTime.of(2020, 5, 15, 14, 30);
        HomeDelivery homeDelivery = new HomeDelivery(address, deliveryTime);
        assertEquals(address, homeDelivery.deliveryAddress());
        assertEquals(deliveryTime, homeDelivery.pickUpDate());
    }

    @Test
    public void aStorePickUpHasADeliveryTimeButNotAnAddress() {
        LocalDateTime deliveryTime =  LocalDateTime.of(2020, 5, 15, 14, 30);
        StorePickUp storePickUp = new StorePickUp(deliveryTime);
        assertEquals(deliveryTime, storePickUp.pickUpDate());
        assertThrows(OptionNotAvailableForThisDeliveryType.class , ()-> storePickUp.deliveryAddress());
    }
}
