package com.example.demo.services.purchase;

import com.example.demo.dtos.ProductToBuy;
import com.example.demo.model.AcquiredProduct;
import com.example.demo.model.Bill;
import com.example.demo.model.DeliveryType;
import com.example.demo.model.ticket.Ticket;
import com.example.demo.model.user.ClientUser;

import java.util.List;

public interface IPurchaseService {
    Ticket processTicket(Long id, List<ProductToBuy> productsToBuy, String paymentMethod);

    Bill processBill(List<ProductToBuy> productsToBuy, DeliveryType deliveryType, String paymentMethod, ClientUser user);
}
