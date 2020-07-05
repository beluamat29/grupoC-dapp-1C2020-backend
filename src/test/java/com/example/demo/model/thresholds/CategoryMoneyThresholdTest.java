package com.example.demo.model.thresholds;

import com.example.demo.builders.CategoryThresholdBuilder;
import com.example.demo.builders.StoreBuilder;
import com.example.demo.builders.ClientUserBuilder;
import com.example.demo.builders.TicketBuilder;
import com.example.demo.model.*;
import com.example.demo.model.bill.Bill;
import com.example.demo.model.delivery.DeliveryType;
import com.example.demo.model.delivery.HomeDelivery;
import com.example.demo.model.store.Store;
import com.example.demo.model.merchandise.MerchandiseCategory;
import com.example.demo.model.ticket.Ticket;
import com.example.demo.model.user.ClientUser;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class CategoryMoneyThresholdTest {

    @Test
    public void aCategoryThresholdHasALimitMoneyValueForACategory() {
        Double aLimit = 3000.0;
        MerchandiseCategory category = MerchandiseCategory.BAKERY;
        CategoryMoneyThreshold categoryMoneyThreshold = CategoryThresholdBuilder.aCategoryThreshold().withMoneyLimit(aLimit).withCategory(category).build();
        assertEquals(categoryMoneyThreshold.moneyLimit(), aLimit);
        assertEquals(categoryMoneyThreshold.category(), category);
    }

    @Test
    public void aPurchaseWithAHigherTotalPriceInASpecificCategoryThanAnEnabledCategoryMoneyThresholdBreaksTheLimit() {
        CategoryMoneyThreshold categoryMoneyThreshold = CategoryThresholdBuilder.aCategoryThreshold().withMoneyLimit(30.0).withCategory(MerchandiseCategory.GROCERY).build();
        Store store = StoreBuilder.aStore().withMerchandise("Mayonesa", "Hellmans", 15.0, 300, MerchandiseCategory.GROCERY);
        Ticket ticket = TicketBuilder.aTicket().withProductOfStore("Mayonesa", "Hellmans", 10, store);
        Store anotherStore = StoreBuilder.aStore().withMerchandise("Fideos", "Marolio", 20.0, 300, MerchandiseCategory.GROCERY);
        Ticket anotherTicket = TicketBuilder.aTicket().withProductOfStore("Fideos", "Marolio",5,anotherStore);
        ClientUser pepe = ClientUserBuilder.user().build();
        DeliveryType deliveryType = new HomeDelivery("Alsina 123", LocalDateTime.now().plusDays(1));
        BillGenerator billGenerator = new BillGenerator();
        Bill bill = billGenerator.generateBill(Arrays.asList(ticket,anotherTicket), pepe, deliveryType);
        assertTrue(categoryMoneyThreshold.breaksTheLimitWith(bill));
    }

    @Test
    public void aPurchaseWithAHigherTotalPriceThanADisabledCategoryMoneyThresholdDoesNotBreakTheLimit() {
        CategoryMoneyThreshold categoryMoneyThreshold = CategoryThresholdBuilder.aCategoryThreshold().whichIsDisabled();
        Store store = StoreBuilder.aStore().withMerchandise("Mayonesa", "Hellmans", 15.0, 300, MerchandiseCategory.GROCERY);
        Ticket purchase = TicketBuilder.aTicket().withStore(store).build();
        ClientUser pepe = ClientUserBuilder.user().build();
        DeliveryType deliveryType = new HomeDelivery("Alsina 123", LocalDateTime.now().plusDays(1));
        BillGenerator billGenerator = new BillGenerator();
        Bill bill = billGenerator.generateBill(Arrays.asList(purchase), pepe, deliveryType);
        assertFalse(categoryMoneyThreshold.breaksTheLimitWith(bill));
    }

    @Test
    public void aPurchaseWithALowerTotalPriceThanAnEnabledCategoryMoneyThresholdDoesNotBreakTheLimit() {
        CategoryMoneyThreshold categoryMoneyThreshold = CategoryThresholdBuilder.aCategoryThreshold().withMoneyLimit(300.0).withCategory(MerchandiseCategory.GROCERY).build();
        Store store = StoreBuilder.aStore().withMerchandise("Mayonesa", "Hellmans", 15.0, 300, MerchandiseCategory.GROCERY);
        Ticket ticket = TicketBuilder.aTicket().withStore(store).build();
        Store anotherStore = StoreBuilder.aStore().withMerchandise("Fernet", "Branca", 400.0, 300, MerchandiseCategory.ALCOHOLIC_DRIKS);
        Ticket anotherTicket = TicketBuilder.aTicket().withStore(anotherStore).build();
        ClientUser pepe = ClientUserBuilder.user().build();
        DeliveryType deliveryType = new HomeDelivery("Alsina 123", LocalDateTime.now().plusDays(1));
        BillGenerator billGenerator = new BillGenerator();
        Bill bill = billGenerator.generateBill(Arrays.asList(ticket, anotherTicket), pepe, deliveryType);
        assertFalse(categoryMoneyThreshold.breaksTheLimitWith(bill));
    }

    @Test
    public void aPurchaseWithALowerTotalPriceThanADisabledMoneyThresholdDoesNotBreakTheLimit() {
        CategoryMoneyThreshold categoryMoneyThreshold = CategoryThresholdBuilder.aCategoryThreshold().withMoneyLimit(3000.0).withCategory(MerchandiseCategory.GROCERY).build();
        Store store = StoreBuilder.aStore().withMerchandise("Mayonesa", "Hellmans", 15.0, 300, MerchandiseCategory.GROCERY);
        Ticket ticket = TicketBuilder.aTicket().withStore(store).build();
        ClientUser pepe = ClientUserBuilder.user().build();
        DeliveryType deliveryType = new HomeDelivery("Alsina 123", LocalDateTime.now().plusDays(1));
        BillGenerator billGenerator = new BillGenerator();
        Bill bill = billGenerator.generateBill(Arrays.asList(ticket), pepe, deliveryType);
        assertFalse(categoryMoneyThreshold.breaksTheLimitWith(bill));
    }

}
