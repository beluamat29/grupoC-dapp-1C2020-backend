package com.example.demo.model.ticket;

import com.example.demo.builders.PurchaseFromStoreBuilder;
import com.example.demo.builders.ClientUserBuilder;
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
        PurchaseFromStore purchase = PurchaseFromStoreBuilder.aPurchase().build();
        String paymentMethod = "Tarjeta de credito";
        DeliveryType deliveryType = new HomeDelivery("Alsina 123", LocalDateTime.now().plusDays(1));
        BillGenerator billGenerator = new BillGenerator();
        Bill bill = billGenerator.generateBill(Arrays.asList(purchase), pepe, paymentMethod, deliveryType);
        assertTrue(pepe.hasBill(bill));
        assertEquals(1, pepe.quantityOfBills());
        assertEquals(1, bill.quantityTickets());
        assertTrue(bill.hasTicketOfPurchase(purchase));
    }

    @Test
    public void aUserMakesAPurchaseAndTheBillIsGeneratedWithAllTheCorrespondentTickets(){
        ClientUser pepe = ClientUserBuilder.user().build();
        PurchaseFromStore aPurchase = PurchaseFromStoreBuilder.aPurchase().build();
        PurchaseFromStore anotherPurchase = PurchaseFromStoreBuilder.aPurchase().build();
        String paymentMethod = "Tarjeta de credito";
        DeliveryType deliveryType = new HomeDelivery("Alsina 123", LocalDateTime.now().plusDays(1));
        BillGenerator billGenerator = new BillGenerator();
        Bill bill = billGenerator.generateBill(Arrays.asList(aPurchase, anotherPurchase), pepe, paymentMethod, deliveryType);
        assertEquals(2, bill.quantityTickets());
        assertTrue(bill.hasTicketOfPurchase(aPurchase));
        assertTrue(bill.hasTicketOfPurchase(anotherPurchase));
    }
}
