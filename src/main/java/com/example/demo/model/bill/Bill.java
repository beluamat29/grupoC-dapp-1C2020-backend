package com.example.demo.model.bill;

import com.example.demo.model.delivery.DeliveryType;
import com.example.demo.model.ticket.Ticket;

import java.time.LocalDateTime;
import java.util.List;

public class Bill {

    private List<Ticket> allTickets;
    private DeliveryType deliveryType;

    public Bill(List<Ticket> tickets, DeliveryType delivery){
        this.allTickets = tickets;
        this.deliveryType = delivery;
    }

    public Integer quantityTickets() {
        return this.allTickets.size();
    }

    public List<Ticket> getTickets() {
        return this.allTickets;
    }

    public String addressOfDelivery() {
        return this.deliveryType.deliveryAddress();
    }

    public LocalDateTime deliveryTime() {
        return this.deliveryType.pickUpDate();
    }

    public Double totalPrice() {
        return allTickets.stream().mapToDouble(Ticket::getTotal).sum();
    }

    public Boolean hasTicket(Ticket aTicket) {
        return allTickets.stream().anyMatch(ticket -> ticket.equals(aTicket));
    }
}
