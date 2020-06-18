package com.example.demo.model.thresholds;

import com.example.demo.builders.CategoryThresholdBuilder;
import com.example.demo.builders.PurchaseFromStoreBuilder;
import com.example.demo.builders.StoreBuilder;
import com.example.demo.builders.ClientUserBuilder;
import com.example.demo.model.*;
import com.example.demo.model.Bill;
import com.example.demo.model.store.Store;
import com.example.demo.model.merchandise.MerchandiseCategory;
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
        PurchaseFromStore purchase = PurchaseFromStoreBuilder.aPurchase().withProductOfStore("Mayonesa", "Hellmans", 1, store);
        Store anotherStore = StoreBuilder.aStore().withMerchandise("Fideos", "Marolio", 20.0, 300, MerchandiseCategory.GROCERY);
        PurchaseFromStore anotherPurchase = PurchaseFromStoreBuilder.aPurchase().withProductOfStore("Fideos", "Marolio", 2, anotherStore);
        ClientUser pepe = ClientUserBuilder.user().build();
        String paymentMethod = "Tarjeta de credito";
        DeliveryType deliveryType = new HomeDelivery("Alsina 123", LocalDateTime.now().plusDays(1));
        BillGenerator billGenerator = new BillGenerator();
        Bill bill = billGenerator.generateBill(Arrays.asList(purchase,anotherPurchase), pepe, paymentMethod, deliveryType);
        assertTrue(categoryMoneyThreshold.breaksTheLimitWith(bill));
    }

    @Test
    public void aPurchaseWithAHigherTotalPriceThanADisabledCategoryMoneyThresholdDoesNotBreakTheLimit() {
        CategoryMoneyThreshold categoryMoneyThreshold = CategoryThresholdBuilder.aCategoryThreshold().whichIsDisabled();
        Store store = StoreBuilder.aStore().withMerchandise("Mayonesa", "Hellmans", 15.0, 300, MerchandiseCategory.GROCERY);
        PurchaseFromStore purchase = PurchaseFromStoreBuilder.aPurchase().withProductOfStore("Mayonesa", "Hellmans", 4, store);
        ClientUser pepe = ClientUserBuilder.user().build();
        String paymentMethod = "Tarjeta de credito";
        DeliveryType deliveryType = new HomeDelivery("Alsina 123", LocalDateTime.now().plusDays(1));
        BillGenerator billGenerator = new BillGenerator();
        Bill bill = billGenerator.generateBill(Arrays.asList(purchase), pepe, paymentMethod, deliveryType);
        assertFalse(categoryMoneyThreshold.breaksTheLimitWith(bill));
    }

    @Test
    public void aPurchaseWithALowerTotalPriceThanAnEnabledCategoryMoneyThresholdDoesNotBreakTheLimit() {
        CategoryMoneyThreshold categoryMoneyThreshold = CategoryThresholdBuilder.aCategoryThreshold().withMoneyLimit(300.0).withCategory(MerchandiseCategory.GROCERY).build();
        Store store = StoreBuilder.aStore().withMerchandise("Mayonesa", "Hellmans", 15.0, 300, MerchandiseCategory.GROCERY);
        PurchaseFromStore purchase = PurchaseFromStoreBuilder.aPurchase().withProductOfStore("Mayonesa", "Hellmans", 4, store);
        Store anotherStore = StoreBuilder.aStore().withMerchandise("Fernet", "Branca", 400.0, 300, MerchandiseCategory.ALCOHOLIC_DRIKS);
        PurchaseFromStore anotherPurchase = PurchaseFromStoreBuilder.aPurchase().withProductOfStore("Fernet", "Branca", 1, anotherStore);
        ClientUser pepe = ClientUserBuilder.user().build();
        String paymentMethod = "Tarjeta de credito";
        DeliveryType deliveryType = new HomeDelivery("Alsina 123", LocalDateTime.now().plusDays(1));
        BillGenerator billGenerator = new BillGenerator();
        Bill bill = billGenerator.generateBill(Arrays.asList(purchase, anotherPurchase), pepe, paymentMethod, deliveryType);
        assertFalse(categoryMoneyThreshold.breaksTheLimitWith(bill));
    }

    @Test
    public void aPurchaseWithALowerTotalPriceThanADisabledMoneyThresholdDoesNotBreakTheLimit() {
        CategoryMoneyThreshold categoryMoneyThreshold = CategoryThresholdBuilder.aCategoryThreshold().withMoneyLimit(3000.0).withCategory(MerchandiseCategory.GROCERY).build();
        Store store = StoreBuilder.aStore().withMerchandise("Mayonesa", "Hellmans", 15.0, 300, MerchandiseCategory.GROCERY);
        PurchaseFromStore purchase = PurchaseFromStoreBuilder.aPurchase().withProductOfStore("Mayonesa", "Hellmans", 4, store);
        ClientUser pepe = ClientUserBuilder.user().build();
        String paymentMethod = "Tarjeta de credito";
        DeliveryType deliveryType = new HomeDelivery("Alsina 123", LocalDateTime.now().plusDays(1));
        BillGenerator billGenerator = new BillGenerator();
        Bill bill = billGenerator.generateBill(Arrays.asList(purchase), pepe, paymentMethod, deliveryType);
        assertFalse(categoryMoneyThreshold.breaksTheLimitWith(bill));
    }

}
