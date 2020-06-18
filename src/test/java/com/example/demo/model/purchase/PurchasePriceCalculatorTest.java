package com.example.demo.model.purchase;

import com.example.demo.builders.PurchaseFromStoreBuilder;
import com.example.demo.builders.StoreBuilder;
import com.example.demo.model.PurchaseFromStore;
import com.example.demo.model.store.Store;
import com.example.demo.model.merchandise.MerchandiseCategory;
import com.example.demo.model.purchasePriceCalculator.PurchasePriceCalculator;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PurchasePriceCalculatorTest {

    @Test
    public void priceForAPurchaseWithNoProductsIsZero() {
        PurchaseFromStore purchase = PurchaseFromStoreBuilder.aPurchase().build();
        PurchasePriceCalculator calculator = new PurchasePriceCalculator();
        assertEquals(calculator.calculatePriceFor(purchase), 0);
    }

    @Test
    public void aPurchaseTotalIsEqualToTheSumOfAllItsProducts(){
        Double aPrice = 15.8;
        Integer aQuantity = 3;
        Store storeWithProducts = StoreBuilder.withMerchandise("Mayonesa", "Hellmans", aPrice, aQuantity + 1, MerchandiseCategory.GROCERY);
        PurchaseFromStore purchase = PurchaseFromStoreBuilder.aPurchase().withProductOfStore("Mayonesa", "Hellmans", aQuantity, storeWithProducts);
        PurchasePriceCalculator calculator = new PurchasePriceCalculator();
        assertEquals(aPrice* aQuantity, calculator.calculatePriceFor(purchase));
        assertEquals(aQuantity, purchase.productsQuantity());
    }
}
