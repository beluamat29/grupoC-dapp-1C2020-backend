package com.example.demo.builders;

import com.example.demo.model.PurchaseFromStore;
import com.example.demo.model.ticket.Ticket;

public class TicketBuilder {

    private String paymentMethod = "Credit Card";
    private PurchaseFromStore ticketPurchase = PurchaseFromStoreBuilder.aPurchase().build();

    public static TicketBuilder aTicket() {
        return new TicketBuilder();
    }

    public Ticket build(){
        return new Ticket(ticketPurchase, paymentMethod);
    }

    public TicketBuilder withPaymentMethod(String aPaymentMethod) {
        paymentMethod = aPaymentMethod;
        return this;
    }

    public TicketBuilder withPurchase(PurchaseFromStore purchase) {
        ticketPurchase = purchase;
        return this;
    }
}
