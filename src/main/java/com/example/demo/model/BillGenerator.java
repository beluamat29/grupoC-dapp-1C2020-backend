package com.example.demo.model;

import com.example.demo.model.ticket.Ticket;
import com.example.demo.model.user.ClientUser;
import com.example.demo.sendMail.QuarantineMailSender;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class BillGenerator {

    @Autowired
    private QuarantineMailSender mailSender;

    public Bill generateBill(List<Ticket> listOfTickets, ClientUser aClientUser, DeliveryType delivery) {
        Bill bill = new Bill(listOfTickets, delivery);
        aClientUser.addBillOfPurchase(bill);
        return bill;
    }

}
