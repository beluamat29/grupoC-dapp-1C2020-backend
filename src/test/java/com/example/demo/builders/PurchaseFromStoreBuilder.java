package com.example.demo.builders;

import com.example.demo.model.*;
import com.example.demo.model.store.Store;
import com.example.demo.model.user.ClientUser;

public class PurchaseFromStoreBuilder {
    public static PurchaseFromStoreBuilder aPurchase() {
        return new PurchaseFromStoreBuilder();
    }

    private Store purchaseStore = StoreBuilder.aStore().build();
    private ClientUser clientUser = ClientUserBuilder.user().build();

    public PurchaseFromStore build(){
        return new PurchaseFromStore(purchaseStore, clientUser);
    }

    public PurchaseFromStoreBuilder withStore(Store store) {
        purchaseStore = store;
        return this;
    }

    public PurchaseFromStoreBuilder withUser(ClientUser aClientUser) {
        clientUser = aClientUser;
        return this;
    }

    public PurchaseFromStore withProductOfStore(String producName, String productBrand, Integer quantity, Store aStore) {
        purchaseStore = aStore;
        PurchaseFromStore purchase = build();
        purchase.addProduct(producName, productBrand, quantity);
        return purchase;
    }
}