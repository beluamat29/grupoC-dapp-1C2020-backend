package com.example.demo.services.purchase;

import com.example.demo.dtos.ProductToBuy;
import com.example.demo.model.Bill;
import com.example.demo.model.ticket.Ticket;
import com.example.demo.model.user.ClientUser;

import java.time.LocalDateTime;
import java.util.List;

public interface IPurchaseService {
    Ticket processTicket(Long id, List<ProductToBuy> productsToBuy, String paymentMethod);

    Bill processBill(List<ProductToBuy> productsToBuy, String deliveryType, LocalDateTime deliveryTime, String paymentMethod, ClientUser user);
}
