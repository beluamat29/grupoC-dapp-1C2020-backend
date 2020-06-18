package com.example.demo.model.ticket;

import com.example.demo.builders.PurchaseFromStoreBuilder;
import com.example.demo.builders.TicketBuilder;
import com.example.demo.model.PurchaseFromStore;
import org.junit.Test;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;

public class TicketTest {

    @Test
    public void aTicketHasAPaymentMethod() {
        Ticket ticket = TicketBuilder.aTicket().withPaymentMethod("Credit card").build();
        assertEquals(ticket.paymentMethod(), "Credit card");
    }

    @Test
    public void aTicketHasAPurchase() {
        PurchaseFromStore purchase = PurchaseFromStoreBuilder.aPurchase().build();
        Ticket ticket = TicketBuilder.aTicket().withPurchase(purchase).build();
        assertEquals(ticket.purchase(), purchase);
    }
}
