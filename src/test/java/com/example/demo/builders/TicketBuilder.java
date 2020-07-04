package com.example.demo.builders;

import com.example.demo.model.store.Store;
import com.example.demo.model.ticket.Ticket;

public class TicketBuilder {

    private String paymentMethod = "Credit Card";
    private Store ticketStore = StoreBuilder.aStore().build();

    public static TicketBuilder aTicket() {
        return new TicketBuilder();
    }

    public Ticket build(){
        return new Ticket(paymentMethod, ticketStore);
    }

    public TicketBuilder withPaymentMethod(String aPaymentMethod) {
        paymentMethod = aPaymentMethod;
        return this;
    }

    public TicketBuilder withStore(Store store) {
        ticketStore = store;
        return this;
    }

    public Ticket withProductOfStore(String productName, String productBrand, Integer quantity, Store store) {
        ticketStore = store;
        Ticket ticket = build();
        ticket.addProduct(productName, productBrand, quantity);
        return ticket;
    }
}
