package com.example.demo.model.ticket;

import com.example.demo.builders.*;
import com.example.demo.model.*;
import com.example.demo.model.exceptions.OptionNotAvailableForThisDeliveryType;
import com.example.demo.model.merchandise.MerchandiseCategory;
import com.example.demo.model.store.Store;
import com.example.demo.model.user.ClientUser;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.Arrays;

public class BillTest {

    @Test
    public void aBillHasTwoTickets(){
        Ticket ticket1 = TicketBuilder.aTicket().build();
        Ticket ticket2 = TicketBuilder.aTicket().build();
        Bill bill = BillBuilder.aBill().withTickets(Arrays.asList(ticket1, ticket2)).build();
        assertEquals(2, bill.quantityTickets());
    }

    @Test
    public void aBillHasAHomeDelivery(){
        DeliveryType delivery = new HomeDelivery("Alsina 123", LocalDateTime.of(2020, 05, 20, 18, 00));
        Bill bill = BillBuilder.aBill().withDeliveyType(delivery).build();
        assertEquals("Alsina 123", bill.addressOfDelivery());
        assertEquals(LocalDateTime.of(2020, 05, 20, 18, 00), bill.deliveryTime());
    }

    @Test
    public void aBillTotalIsEqualsOfSumOfAllTickets(){
        Double aPrice = 15.8;
        Double anotherPrice = 32.5;
        Integer aQuantity = 3;
        Integer anotherQuantity = 2;
        Store storeWithProducts = StoreBuilder.withMerchandise("Mayonesa", "Hellmans", aPrice, aQuantity + 1, MerchandiseCategory.GROCERY);
        Store anotherStoreWithProducts = StoreBuilder.withMerchandise("Fideos", "Marolio", anotherPrice, anotherQuantity + 1, MerchandiseCategory.GROCERY);
        PurchaseFromStore purchase1 = PurchaseFromStoreBuilder.aPurchase().withProductOfStore("Mayonesa", "Hellmans", aQuantity, storeWithProducts);
        PurchaseFromStore purchase2 = PurchaseFromStoreBuilder.aPurchase().withProductOfStore("Fideos", "Marolio", anotherQuantity, anotherStoreWithProducts);
        Ticket ticket1 = TicketBuilder.aTicket().withPurchase(purchase1).build();
        Ticket ticket2 = TicketBuilder.aTicket().withPurchase(purchase2).build();
        Bill bill = BillBuilder.aBill().withTickets(Arrays.asList(ticket1, ticket2)).build();
        Double total = (aPrice * aQuantity) + (anotherPrice * anotherQuantity);
        assertEquals(total, bill.totalPrice());
    }

    @Test
    public void aUserThatChoosesHomeDeliveryBillHasAndAddressAndDeliveryDate() {
        ClientUser clientUser = ClientUserBuilder.user().build();
        PurchaseFromStore purchase = PurchaseFromStoreBuilder.aPurchase().withUser(clientUser).build();
        String paymentMethod = "Credit Card";
        DeliveryType deliveryType = new HomeDelivery("Alsina 123", LocalDateTime.now().plusDays(1));
        BillGenerator billGenerator = new BillGenerator();
        Bill bill = billGenerator.generateBill(Arrays.asList(purchase), clientUser, paymentMethod, deliveryType);
        assertEquals(bill.addressOfDelivery(), "Alsina 123");
    }

    @Test
    public void aUserThatChoosesStorePickUpDeliveryTicketDoesNotHaveADeliveryAddress() {
        ClientUser clientUser = ClientUserBuilder.user().build();
        PurchaseFromStore purchase = PurchaseFromStoreBuilder.aPurchase().withUser(clientUser).build();
        String paymentMethod = "Credit Card";
        DeliveryType deliveryType = new StorePickUp(LocalDateTime.now().plusDays(1));
        BillGenerator billGenerator = new BillGenerator();
        Bill bill = billGenerator.generateBill(Arrays.asList(purchase), clientUser, paymentMethod, deliveryType);
        assertThrows(OptionNotAvailableForThisDeliveryType.class, bill::addressOfDelivery);
    }

}
