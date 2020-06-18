package com.example.demo.model;

import com.example.demo.model.store.Store;
import com.example.demo.model.user.ClientUser;

import java.util.ArrayList;
import java.util.List;

public class PurchaseFromStore {

    private Store purchaseStore;
    private ClientUser purchaseClientUser;
    private List<AcquiredProduct> productList = new ArrayList<>();

    public PurchaseFromStore(Store store, ClientUser name){
        purchaseStore = store;
        purchaseClientUser = name;
    }

    public Store store() { return this.purchaseStore; }

    public ClientUser user() { return this.purchaseClientUser;  }

    public Integer productsQuantity() { return this.productList.stream().mapToInt(AcquiredProduct::quantity).sum();  }

    public List<AcquiredProduct> getListOfAdquiredProducts() {
        return this.productList;
    }

    public void addProduct(String productName, String productBrand, Integer quantity) {
        this.productList.add(this.store().getProduct(productName, productBrand, quantity));
    }
}