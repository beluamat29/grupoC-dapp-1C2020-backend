package com.example.demo.model.thresholds;

import com.example.demo.builders.*;
import com.example.demo.model.*;
import com.example.demo.model.Bill;
import com.example.demo.model.store.Store;
import com.example.demo.model.merchandise.MerchandiseCategory;
import com.example.demo.model.user.ClientUser;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class MoneyThresholdTest {

    @Test
    public void aMoneyThresholdHasALimitValue() {
        Double aLimit = 3000.0;
        MoneyThreshold moneyThreshold = MoneyThresholdBuilder.aMoneyThreshold().withMoneyLimit(aLimit).build();
        assertEquals(moneyThreshold.moneyLimit(), aLimit);
    }

    @Test
    public void whenCreatingAMoneyThresholdIsActiveByDefault() {
        MoneyThreshold moneyThreshold = MoneyThresholdBuilder.aMoneyThreshold().build();
        assertTrue(moneyThreshold.isActive());
    }

    @Test
    public void aMoneyThresholdCanHaveItsMoneyLimitUpdated() {
        Double originalMoneyLimit = 2000.0;
        MoneyThreshold moneyThreshold = MoneyThresholdBuilder.aMoneyThreshold().withMoneyLimit(originalMoneyLimit).build();
        moneyThreshold.updateMoneyLimit(originalMoneyLimit + 200.0);
        assertEquals(moneyThreshold.moneyLimit(), originalMoneyLimit + 200.0);

    }

    @Test
    public void aDisabledMoneyThresholdIsNoMoreActive() {
        MoneyThreshold moneyThreshold = MoneyThresholdBuilder.aMoneyThreshold().build();
        moneyThreshold.disable();
        assertFalse(moneyThreshold.isActive());
    }

    @Test
    public void aDisabledMoneyThresholdCanBeEnabled() {
        MoneyThreshold moneyThreshold = MoneyThresholdBuilder.aMoneyThreshold().whichIsDisabled();
        moneyThreshold.enable();
        assertTrue(moneyThreshold.isActive());
    }

    @Test
    public void aBillWithAHigherTotalPriceThanAnEnabledMoneyThresholdBreaksTheLimit() {
        MoneyThreshold moneyThreshold = MoneyThresholdBuilder.aMoneyThreshold().withMoneyLimit(30.0).build();
        Store store = StoreBuilder.aStore().withMerchandise("Mayonesa", "Hellmans", 15.0, 300, MerchandiseCategory.GROCERY);
        PurchaseFromStore purchase = PurchaseFromStoreBuilder.aPurchase().withProductOfStore("Mayonesa", "Hellmans", 4, store);
        ClientUser pepe = ClientUserBuilder.user().build();
        String paymentMethod = "Tarjeta de credito";
        DeliveryType deliveryType = new HomeDelivery("Alsina 123", LocalDateTime.now().plusDays(1));
        BillGenerator billGenerator = new BillGenerator();
        Bill bill = billGenerator.generateBill(Arrays.asList(purchase), pepe, paymentMethod, deliveryType);
        assertTrue(moneyThreshold.breaksTheLimitWith(bill));
    }

    @Test
    public void aPurchaseWithAHigherTotalPriceThanADisabledMoneyThresholdDoesNotBreakTheLimit() {
        MoneyThreshold moneyThreshold = MoneyThresholdBuilder.aMoneyThreshold().whichIsDisabled();
        Store store = StoreBuilder.aStore().withMerchandise("Mayonesa", "Hellmans", 15.0, 300, MerchandiseCategory.GROCERY);
        PurchaseFromStore purchase = PurchaseFromStoreBuilder.aPurchase().withProductOfStore("Mayonesa", "Hellmans", 4, store);
        ClientUser pepe = ClientUserBuilder.user().build();
        String paymentMethod = "Tarjeta de credito";
        DeliveryType deliveryType = new HomeDelivery("Alsina 123", LocalDateTime.now().plusDays(1));
        BillGenerator billGenerator = new BillGenerator();
        Bill bill = billGenerator.generateBill(Arrays.asList(purchase), pepe, paymentMethod, deliveryType);
        assertFalse(moneyThreshold.breaksTheLimitWith(bill));
    }

    @Test
    public void aPurchaseWithALowerTotalPriceThanAnEnabledMoneyThresholdDoesNotBreakTheLimit() {
        MoneyThreshold moneyThreshold = MoneyThresholdBuilder.aMoneyThreshold().withMoneyLimit(3000.0).build();
        Store store = StoreBuilder.aStore().withMerchandise("Mayonesa", "Hellmans", 15.0, 300, MerchandiseCategory.GROCERY);
        PurchaseFromStore purchase = PurchaseFromStoreBuilder.aPurchase().withProductOfStore("Mayonesa", "Hellmans", 4, store);
        ClientUser pepe = ClientUserBuilder.user().build();
        String paymentMethod = "Tarjeta de credito";
        DeliveryType deliveryType = new HomeDelivery("Alsina 123", LocalDateTime.now().plusDays(1));
        BillGenerator billGenerator = new BillGenerator();
        Bill bill = billGenerator.generateBill(Arrays.asList(purchase), pepe, paymentMethod, deliveryType);
        assertFalse(moneyThreshold.breaksTheLimitWith(bill));
    }

    @Test
    public void aPurchaseWithALowerTotalPriceThanADisabledMoneyThresholdDoesNotBreakTheLimit() {
        MoneyThreshold moneyThreshold = MoneyThresholdBuilder.aMoneyThreshold().withMoneyLimit(3000.0).whichIsDisabled();
        Store store = StoreBuilder.aStore().withMerchandise("Mayonesa", "Hellmans", 15.0, 300, MerchandiseCategory.GROCERY);
        PurchaseFromStore purchase = PurchaseFromStoreBuilder.aPurchase().withProductOfStore("Mayonesa", "Hellmans", 4, store);
        ClientUser pepe = ClientUserBuilder.user().build();
        String paymentMethod = "Tarjeta de credito";
        DeliveryType deliveryType = new HomeDelivery("Alsina 123", LocalDateTime.now().plusDays(1));
        BillGenerator billGenerator = new BillGenerator();
        Bill bill = billGenerator.generateBill(Arrays.asList(purchase), pepe, paymentMethod, deliveryType);
        assertFalse(moneyThreshold.breaksTheLimitWith(bill));
    }
}
