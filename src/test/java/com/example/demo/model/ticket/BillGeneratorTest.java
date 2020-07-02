package com.example.demo.model.ticket;

import com.example.demo.builders.ClientUserBuilder;
import com.example.demo.builders.TicketBuilder;
import com.example.demo.model.*;
import com.example.demo.model.Bill;
import com.example.demo.model.user.ClientUser;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.*;

public class BillGeneratorTest {

    @Test
    public void whenAUserMakesAPurchaseInOneStoreOnlyOneTicketIsSavedInTeBill(){
        ClientUser pepe = ClientUserBuilder.user().build();
        Ticket ticket = TicketBuilder.aTicket().build();
        String paymentMethod = "Tarjeta de credito";
        DeliveryType deliveryType = new HomeDelivery("Alsina 123", LocalDateTime.now().plusDays(1));
        BillGenerator billGenerator = new BillGenerator();
        Bill bill = billGenerator.generateBill(Arrays.asList(ticket), pepe, paymentMethod, deliveryType);
        assertTrue(pepe.hasBill(bill));
        assertEquals(1, pepe.quantityOfBills());
        assertEquals(1, bill.quantityTickets());
        assertTrue(bill.hasTicket(ticket));
    }

    @Test
    public void aUserMakesAPurchaseAndTheBillIsGeneratedWithAllTheCorrespondentTickets(){
        ClientUser pepe = ClientUserBuilder.user().build();
        Ticket ticket = TicketBuilder.aTicket().build();
        Ticket anotherTicket = TicketBuilder.aTicket().build();
        String paymentMethod = "Tarjeta de credito";
        DeliveryType deliveryType = new HomeDelivery("Alsina 123", LocalDateTime.now().plusDays(1));
        BillGenerator billGenerator = new BillGenerator();
        Bill bill = billGenerator.generateBill(Arrays.asList(ticket, anotherTicket), pepe, paymentMethod, deliveryType);
        assertEquals(2, bill.quantityTickets());
        assertTrue(bill.hasTicket(ticket));
        assertTrue(bill.hasTicket(anotherTicket));
    }
}
