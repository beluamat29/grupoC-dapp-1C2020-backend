package com.example.demo.builders;

import com.example.demo.model.DeliveryType;
import com.example.demo.model.HomeDelivery;
import com.example.demo.model.Bill;
import com.example.demo.model.ticket.Ticket;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class BillBuilder {
    
    private List<Ticket> multiPurchases = Arrays.asList(TicketBuilder.aTicket().build());
    private DeliveryType deliveryMethod = new HomeDelivery("Alsina 123", LocalDateTime.now().plusDays(1));

    public static BillBuilder aBill() {  return new BillBuilder();   }

    public BillBuilder withTickets(List<Ticket> tickets) {
        multiPurchases = tickets;
        return this;
    }

    public Bill build() {
        return new Bill(multiPurchases, deliveryMethod);
    }

    public BillBuilder withDeliveyType(DeliveryType delivery) {
        deliveryMethod = delivery;
        return this;
    }
}
