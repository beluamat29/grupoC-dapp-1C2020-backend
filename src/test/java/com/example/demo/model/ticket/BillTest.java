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
        Ticket ticket1 = TicketBuilder.aTicket().withProductOfStore("Mayonesa", "Hellmans", aQuantity, storeWithProducts);
        Ticket ticket2 = TicketBuilder.aTicket().withProductOfStore("Fideos", "Marolio", anotherQuantity, anotherStoreWithProducts);
        Bill bill = BillBuilder.aBill().withTickets(Arrays.asList(ticket1, ticket2)).build();
        Double total = (aPrice * aQuantity) + (anotherPrice * anotherQuantity);

        assertEquals(total, bill.totalPrice());
    }

    @Test
    public void aUserThatChoosesHomeDeliveryBillHasAndAddressAndDeliveryDate() {
        ClientUser clientUser = ClientUserBuilder.user().build();
        Ticket ticket = TicketBuilder.aTicket().build();
        DeliveryType deliveryType = new HomeDelivery(clientUser.address(), LocalDateTime.now().plusDays(1));
        BillGenerator billGenerator = new BillGenerator();
        Bill bill = billGenerator.generateBill(Arrays.asList(ticket), clientUser, deliveryType);

        assertEquals(clientUser.address(), bill.addressOfDelivery());
        assertTrue(clientUser.hasBill(bill));
    }

    @Test
    public void aUserThatChoosesStorePickUpDeliveryTicketDoesNotHaveADeliveryAddress() {
        ClientUser clientUser = ClientUserBuilder.user().build();
        Ticket ticket = TicketBuilder.aTicket().build();
        DeliveryType deliveryType = new StorePickUp(LocalDateTime.now().plusDays(1));
        BillGenerator billGenerator = new BillGenerator();
        Bill bill = billGenerator.generateBill(Arrays.asList(ticket), clientUser, deliveryType);

        assertTrue(clientUser.hasBill(bill));
        assertThrows(OptionNotAvailableForThisDeliveryType.class, bill::addressOfDelivery);
    }

}
