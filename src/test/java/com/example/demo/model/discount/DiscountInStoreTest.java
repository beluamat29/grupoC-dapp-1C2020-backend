package com.example.demo.model.discount;

import com.example.demo.builders.StoreBuilder;
import com.example.demo.model.store.Store;
import com.example.demo.model.merchandise.MerchandiseCategory;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DiscountInStoreTest {

    @Test
    public void aMerchandiseThatHasAMerchandiseDiscountAndItsValidHasItsPriceDecreased() {
        Store store = StoreBuilder.aStore().build();
        Double originalPrice = 10.0;
        store.addMerchandise("Mayonesa", "Hellmans", originalPrice, 100, MerchandiseCategory.GROCERY, "https://i4-unilevermx.a8e.net.br/gg/mayonesa-hellmans-real-105g_170477870_7501005151931.jpg");
        store.addMerchandiseDiscountFor("Mayonesa", "Hellmans", 20, LocalDate.now().plusDays(1));
        assertEquals(store.priceOf("Mayonesa", "Hellmans"), originalPrice - (originalPrice * 20 / 100));
    }

    @Test
    public void aMerchandiseThatDoesNotHaveADiscountDoesNotGetItsPriceDecreased() {
        Store store = StoreBuilder.aStore().build();
        Double originalPrice = 10.0;
        store.addMerchandise("Mayonesa", "Hellmans", originalPrice, 100, MerchandiseCategory.GROCERY, "https://i4-unilevermx.a8e.net.br/gg/mayonesa-hellmans-real-105g_170477870_7501005151931.jpg");
        assertEquals(originalPrice, store.priceOf("Mayonesa", "Hellmans"));
    }

    @Test
    public void aMerchandiseThatHasACategoryDiscountAndItsValidHasItsPriceDecreased() {
        Store store = StoreBuilder.aStore().build();
        Double originalPrice = 10.0;
        store.addMerchandise("Mayonesa", "Hellmans", originalPrice, 100, MerchandiseCategory.GROCERY, "https://i4-unilevermx.a8e.net.br/gg/mayonesa-hellmans-real-105g_170477870_7501005151931.jpg");
        store.addCategoryDiscount(MerchandiseCategory.GROCERY, 20, LocalDate.now().plusDays(1));
        assertEquals(store.priceOf("Mayonesa", "Hellmans"), originalPrice - (originalPrice * 20 / 100));
    }

    @Test
    public void aMerchandiseThatDoesNotHaveACategoryDiscountDoesNotGetItsPriceDecreased() {
        Store store = StoreBuilder.aStore().build();
        Double originalPrice = 10.0;
        store.addMerchandise("Mayonesa", "Hellmans", originalPrice, 100, MerchandiseCategory.GROCERY, "https://i4-unilevermx.a8e.net.br/gg/mayonesa-hellmans-real-105g_170477870_7501005151931.jpg");
        assertEquals(originalPrice, store.priceOf("Mayonesa", "Hellmans"));
    }

}
