package com.example.demo.model;

import com.example.demo.model.ticket.Ticket;
import com.example.demo.model.user.ClientUser;

import java.util.List;
import java.util.stream.Collectors;

public class BillGenerator {

    public Bill generateBill(List<Ticket> listOfTickets, ClientUser aClientUser, String paymentMethod, DeliveryType delivery) {
        Bill bill = new Bill(listOfTickets, delivery);
        aClientUser.addBillOfPurchase(bill);
        return bill;
    }

    /*private Ticket generateTicket(PurchaseFromStore purchase, String paymentMethod) {
        return new Ticket(purchase, paymentMethod);
    }*/
}
