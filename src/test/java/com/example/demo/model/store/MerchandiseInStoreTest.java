package com.example.demo.model.store;

import com.example.demo.builders.StoreBuilder;
import com.example.demo.builders.DiscountBuilder;
import com.example.demo.model.discounts.Discount;
import com.example.demo.model.exceptions.NotFoundProductInStore;
import com.example.demo.model.exceptions.RepeatedMerchandiseInStore;
import com.example.demo.model.merchandise.MerchandiseCategory;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MerchandiseInStoreTest {

    @Test
    public void whenAStoreIsNewIsDoesNotHaveMerchandise() {
        Store newStore = StoreBuilder.aStore().build();
        assertFalse(newStore.hasMerchandises());
    }

    @Test
    public void whenAStoreAddsANewMerchandiseItNowBelongsToTheStore() {
        Store store = StoreBuilder.aStore().build();
        store.addMerchandise("Fideos", "Marolio", 34.45, 23, MerchandiseCategory.GROCERY, "https://www.maxiconsumo.com/media/catalog/product/cache/42/image/300x/9df78eab33525d08d6e5fb8d27136e95/2/0/20672_23.jpg");
        assertTrue(store.sellsMerchandise("Fideos", "Marolio"));
        assertEquals(store.stockOf("Fideos", "Marolio"), 23);
    }

    @Test
    public void aStoreKnowsThePriceOfAProductItSells() {
        Store store = StoreBuilder.aStore().build();
        store.addMerchandise("Fideos", "Marolio", 34.45, 23, MerchandiseCategory.GROCERY, "https://www.maxiconsumo.com/media/catalog/product/cache/42/image/300x/9df78eab33525d08d6e5fb8d27136e95/2/0/20672_23.jpg");
        assertEquals(store.priceOf("Fideos", "Marolio"), 34.45);
    }

    @Test
    public void aStoreDoesNotKnowThePriceOfAProductItDoesNotSell() {
        Store store = StoreBuilder.aStore().build();
        assertThrows(NotFoundProductInStore.class, () -> store.priceOf("Un producto", "Que no existe"));
    }

    @Test
    public void aStoreKnowsTheStockOfAProductItSells() {
        Store store = StoreBuilder.aStore().build();
        store.addMerchandise("Fideos", "Marolio", 34.45, 23, MerchandiseCategory.GROCERY, "https://www.maxiconsumo.com/media/catalog/product/cache/42/image/300x/9df78eab33525d08d6e5fb8d27136e95/2/0/20672_23.jpg");
        assertEquals(store.stockOf("Fideos", "Marolio"), 23);
    }

    @Test
    public void aStoreDoesNotKnowTheStockOfAProductItDoesNotSell() {
        Store store = StoreBuilder.aStore().build();
        assertThrows(NotFoundProductInStore.class, () -> store.stockOf("Un producto", "Que no existe"));
    }


    @Test
    public void aStoreCannotAddTheSameProductTwice() {
        //por mismo producto se entiende mismo nombre y marca
        Store store = StoreBuilder.aStore().build();
        store.addMerchandise("Fideos", "Marolio", 34.45, 23, MerchandiseCategory.GROCERY, "https://lh3.googleusercontent.com/proxy/oD8G3JZgagk3G0siApWqcsBg5J6BE0sF-cmVZ4g3xLhWKNA2GrwpV5iU7QW-WcRN5YS3TLFqUyliKb34m3dTkT0r5F1L1LDpqjhXj9sai7d1EorC");
        assertThrows(RepeatedMerchandiseInStore.class, () -> store.addMerchandise("Fideos", "Marolio", 34.45, 23, MerchandiseCategory.GROCERY, "https://lh3.googleusercontent.com/proxy/oD8G3JZgagk3G0siApWqcsBg5J6BE0sF-cmVZ4g3xLhWKNA2GrwpV5iU7QW-WcRN5YS3TLFqUyliKb34m3dTkT0r5F1L1LDpqjhXj9sai7d1EorC"));
    }

    @Test
    public void aStoreCanUpdateThePriceOfAnExistingProduct() {
        Store store = StoreBuilder.aStore().build();
        store.addMerchandise("Fideos", "Marolio", 34.45, 23, MerchandiseCategory.GROCERY, "https://lh3.googleusercontent.com/proxy/oD8G3JZgagk3G0siApWqcsBg5J6BE0sF-cmVZ4g3xLhWKNA2GrwpV5iU7QW-WcRN5YS3TLFqUyliKb34m3dTkT0r5F1L1LDpqjhXj9sai7d1EorC");
        store.updatePriceFor("Fideos", "Marolio", 36.45);
        assertEquals(store.priceOf("Fideos", "Marolio"), 36.45);
    }

    @Test
    public void aStoreCanNotUpdateThePriceOfAnExistingProduct() {
        Store store = StoreBuilder.aStore().build();
        assertThrows(NotFoundProductInStore.class, () -> store.updatePriceFor("Fideos", "Marolio", 36.45));
    }

    @Test
    public void aStoreCanAddStockForAnExistingProduct() {
        Store store = StoreBuilder.aStore().build();
        store.addMerchandise("Fideos", "Marolio", 34.45, 23, MerchandiseCategory.GROCERY, "https://www.maxiconsumo.com/media/catalog/product/cache/42/image/300x/9df78eab33525d08d6e5fb8d27136e95/2/0/20672_23.jpg");
        store.addStock("Fideos", "Marolio", 20);
        assertEquals(store.stockOf("Fideos", "Marolio"), 43);
    }

    @Test
    public void aStoreCanNotAddStockForANonExistingProduct() {
        Store store = StoreBuilder.aStore().build();
        assertThrows(NotFoundProductInStore.class, () -> store.addStock("Fideos", "Marolio", 10));
    }

  /*  @Test
    public void aStoreApplyADiscountToAProduct(){
        Store store = ComercioBuilder.unComercio().build();
        MerchandiseDiscount discount = new MerchandiseDiscount(50, LocalDate.of(2020,5,1), LocalDate.of(2020, 5, 20));
        store.addMerchandise("Mayonesa", "Hellmans", 20.0, 200, MerchandiseCategory.GROCERY);
        Merchandise merchandise = store.getMerchandise("Mayonesa", "Hellmans");
        store.applyDiscountOn(merchandise, discount);
        assertTrue(merchandise.hasADiscount());
        assertEquals(50, merchandise.percentOfDiscount());
        assertEquals(10.0, merchandise.price());
    }*/

    @Test
    public void unComercioPuedeDecrementarStockParaUnProductoExistente() {
        Store store = StoreBuilder.aStore().build();
        Discount noDiscount = DiscountBuilder.aDiscount().buildNoDiscount();
        store.addMerchandise("Fideos", "Marolio", 34.45, 23, MerchandiseCategory.GROCERY, "https://lh3.googleusercontent.com/proxy/oD8G3JZgagk3G0siApWqcsBg5J6BE0sF-cmVZ4g3xLhWKNA2GrwpV5iU7QW-WcRN5YS3TLFqUyliKb34m3dTkT0r5F1L1LDpqjhXj9sai7d1EorC");
        store.decreaseStock("Fideos", "Marolio", 20);
        assertEquals(store.stockOf("Fideos", "Marolio"), 3);
    }

    @Test
    public void unComercioNoPuedeDecrementarStockParaUnProductoInexistente() {
        Store store = StoreBuilder.aStore().build();
        assertThrows(NotFoundProductInStore.class, () -> store.decreaseStock("Fideos", "Marolio", 10));
    }

    @Test
    public void aMerchandiseInStoreOriginallyDoesNotHaveADiscount() {
        Store store = StoreBuilder.aStore().build();
        Double originalPrice = 10.0;
        store.addMerchandise("Mayonesa", "Hellmans", originalPrice, 100, MerchandiseCategory.GROCERY, "foto mayonesa");
        assertEquals(store.priceOf("Mayonesa", "Hellmans"), originalPrice);
    }

}
