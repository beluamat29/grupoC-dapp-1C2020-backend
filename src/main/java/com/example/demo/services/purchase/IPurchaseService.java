package com.example.demo.services.purchase;

import com.example.demo.model.AcquiredProduct;
import com.example.demo.model.merchandise.Merchandise;
import com.example.demo.model.ticket.Ticket;

import java.util.AbstractQueue;
import java.util.List;

public interface IPurchaseService {
    Ticket processTicket(Long id, List<AcquiredProduct> productsToBuy, String paymentMethod);
}
