package com.example.demo.model;

import com.example.demo.model.ticket.Ticket;
import com.example.demo.model.user.ClientUser;

import java.util.List;
import java.util.stream.Collectors;

public class BillGenerator {

    public Bill generateBill(List<PurchaseFromStore> listOfPurchase, ClientUser aClientUser, String paymentMethod, DeliveryType delivery) {
        List<Ticket> tickets = listOfPurchase.stream().map(purchase -> this.generateTicket(purchase, paymentMethod)).collect(Collectors.toList());
        Bill bill = new Bill(tickets, delivery);
        aClientUser.addBillOfPurchase(bill);
        return bill;
    }

    private Ticket generateTicket(PurchaseFromStore purchase, String paymentMethod) {
        return new Ticket(purchase, paymentMethod);
    }
}
