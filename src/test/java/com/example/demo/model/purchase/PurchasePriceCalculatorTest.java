package com.example.demo.model.purchase;

import com.example.demo.builders.StoreBuilder;
import com.example.demo.builders.TicketBuilder;
import com.example.demo.model.store.Store;
import com.example.demo.model.merchandise.MerchandiseCategory;
import com.example.demo.model.purchasePriceCalculator.PurchasePriceCalculator;
import com.example.demo.model.ticket.Ticket;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PurchasePriceCalculatorTest {

    @Test
    public void priceForAPurchaseWithNoProductsIsZero() {
        Ticket ticket = TicketBuilder.aTicket().build();
        PurchasePriceCalculator calculator = new PurchasePriceCalculator();
        assertEquals(calculator.calculatePriceFor(ticket.getListOfAdquiredProducts()), 0);
    }

    @Test
    public void aPurchaseTotalIsEqualToTheSumOfAllItsProducts(){
        Double aPrice = 15.8;
        Integer aQuantity = 3;
        Store storeWithProducts = StoreBuilder.withMerchandise("Mayonesa", "Hellmans", aPrice, aQuantity + 1, MerchandiseCategory.GROCERY);
        Ticket ticket = TicketBuilder.aTicket().withProductOfStore("Mayonesa", "Hellmans", aQuantity, storeWithProducts);
        PurchasePriceCalculator calculator = new PurchasePriceCalculator();
        assertEquals(aPrice* aQuantity, calculator.calculatePriceFor(ticket.getListOfAdquiredProducts()));
        assertEquals(aQuantity, ticket.productsQuantity());
    }
}
