package com.example.demo.services;

import com.example.demo.model.store.Store;
import com.example.demo.model.merchandise.Merchandise;
import com.example.demo.model.store.StoreCategory;
import com.example.demo.model.user.StoreAdminUser;

import java.util.List;

public interface IStoreService {
    List<Store> getStores();

    List<Store> getStoresWithACategory(String category);

    List<Merchandise> getProductsFromStore(Long storeId);

    Store getStore(Long storeId);

    List<Merchandise> getDiscountFromStores();

    Store addStore(Store store);

    StoreAdminUser addAdmin();

    Merchandise addMerchandiseToStore(Long storeId, Merchandise merchandise);
}
