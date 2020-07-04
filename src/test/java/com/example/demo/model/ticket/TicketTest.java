package com.example.demo.model.ticket;


import com.example.demo.builders.StoreBuilder;
import com.example.demo.builders.TicketBuilder;
import com.example.demo.model.merchandise.MerchandiseCategory;
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

    @Test
    public void aTicketItsFromASpecificStore(){
        Store store = StoreBuilder.aStore().build();
        Ticket ticket = TicketBuilder.aTicket().withStore(store).build();
        assertEquals(store, ticket.store());
    }

    @Test
    public void aTotalPriceOfTicketWithoutProductsReturnsZero(){
        Ticket ticket = TicketBuilder.aTicket().build();
        assertEquals(0, ticket.getTotal());
    }

    @Test
    public void aTicketHasAQuantityOfAcquiredProducts(){
        Integer aQuantity = 5;
        Store store = StoreBuilder.aStore().withMerchandise("Fideos", "Marolio", 30.0, aQuantity +1, MerchandiseCategory.GROCERY);
        Ticket ticket = TicketBuilder.aTicket().withProductOfStore("Fideos", "Marolio", aQuantity, store);
        assertEquals(aQuantity, ticket.productsQuantity());
    }

    @Test
    public void aTicketHasATotalPrice(){
        Integer aQuantity = 5;
        Double price = 30.0;
        Store store = StoreBuilder.aStore().withMerchandise("Fideos", "Marolio", price, aQuantity +1, MerchandiseCategory.GROCERY);
        Ticket ticket = TicketBuilder.aTicket().withProductOfStore("Fideos", "Marolio", aQuantity, store);
        assertEquals(aQuantity * price, ticket.getTotal());
    }

}
