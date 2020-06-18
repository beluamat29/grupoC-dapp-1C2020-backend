package com.example.demo.model.purchase;

import com.example.demo.builders.PurchaseFromStoreBuilder;
import com.example.demo.builders.StoreBuilder;
import com.example.demo.builders.ClientUserBuilder;
import com.example.demo.model.PurchaseFromStore;
import com.example.demo.model.store.Store;
import com.example.demo.model.user.ClientUser;
import com.example.demo.model.exceptions.InsufficientMerchandiseStockException;
import com.example.demo.model.exceptions.NotFoundProductInStore;
import com.example.demo.model.merchandise.MerchandiseCategory;
import org.junit.Test;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;

public class PurchaseFromStoreTest {

    @Test
    public void aNewPurchaseHasNoProducts() {
        PurchaseFromStore purchase = PurchaseFromStoreBuilder.aPurchase().build();
        assertEquals(purchase.productsQuantity(), 0);
    }

    @Test
    public void aPurchaseHasAStore(){
        Store coto = StoreBuilder.aStore().build();
        PurchaseFromStore purchase = PurchaseFromStoreBuilder.aPurchase().withStore(coto).build();
        assertEquals(coto, purchase.store());
    }

    @Test
    public void aPurchaseHasAUser(){
        ClientUser pepe = ClientUserBuilder.user().build();
        PurchaseFromStore purchase = PurchaseFromStoreBuilder.aPurchase().withUser(pepe).build();
        assertEquals(pepe, purchase.user());
    }


    @Test
    public void isNotPossibleToAddAProductThatIsNotAvailableInTheStore(){
        PurchaseFromStore purchase = PurchaseFromStoreBuilder.aPurchase().build();
        assertThrows(NotFoundProductInStore.class, () -> { purchase.addProduct("Mayonesa", "Hellmans", 3); });
    }

    @Test
    public void isNotPossibleToAddAProductIfTheStoreCannotSatisfyTheStock(){
        Store storeWithProducts = StoreBuilder.aStore().build();
        storeWithProducts.addMerchandise("Mayonesa", "Hellmans", 15.8, 1, MerchandiseCategory.GROCERY, "https://i4-unilevermx.a8e.net.br/gg/mayonesa-hellmans-real-105g_170477870_7501005151931.jpg");
        PurchaseFromStore purchase = PurchaseFromStoreBuilder.aPurchase().withStore(storeWithProducts).build();
        assertThrows(InsufficientMerchandiseStockException.class, () -> { purchase.addProduct("Mayonesa", "Hellmans", 3); });
    }

}