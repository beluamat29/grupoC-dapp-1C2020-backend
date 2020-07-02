package com.example.demo.model.ticket;


import com.example.demo.builders.StoreBuilder;
import com.example.demo.builders.TicketBuilder;
import com.example.demo.model.store.Store;
import org.junit.Test;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;

public class TicketTest {

    @Test
    public void aTicketHasAPaymentMethod() {
        Ticket ticket = TicketBuilder.aTicket().withPaymentMethod("Credit card").build();
        assertEquals(ticket.paymentMethod(), "Credit card");
    }

}
