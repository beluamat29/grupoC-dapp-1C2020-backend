package com.example.demo.services;

import com.example.demo.dtos.MerchandiseDTO;
import com.example.demo.model.AcquiredProduct;
import com.example.demo.model.exceptions.NotFoundStoreException;
import com.example.demo.model.merchandise.Merchandise;
import com.example.demo.model.user.StoreAdminUser;
import com.example.demo.model.store.Store;
import com.example.demo.model.validator.EntityValidator;
import com.example.demo.repositories.StoreRepository;
import com.example.demo.repositories.merchandise.MerchandiseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StoreService implements IStoreService {

    private EntityValidator entityVAlidator = new EntityValidator();

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private MerchandiseRepository merchandiseRepository;

    @Override
    public List<Store> getStores() {
        return storeRepository.findAll();
    }

    public List<Store> getStoresWithACategory(String category) {
        return storeRepository.getStoresWithACategory(category);
    }

    public List<Merchandise> getProductsFromStore(Long storeId, Boolean activeProducts) {
        storeRepository.findById(storeId).orElseThrow(NotFoundStoreException::new);
        if (storeRepository.findById(storeId).get().listOfAvailableMerchandise().isEmpty()) {
            return new ArrayList<>();
        }
        if(activeProducts) {
            return merchandiseRepository.getMerchandiseFromStore(storeId).get().stream().filter(Merchandise::isActive).collect(Collectors.toList());
        } else {
            return merchandiseRepository.getMerchandiseFromStore(storeId).get();
        }
    }

    @Override
    public Store getStore(Long storeId) {
        return storeRepository.findById(storeId).orElseThrow(NotFoundStoreException::new);
    }

    @Override
    public List<Merchandise> getDiscountFromStores() {
        return null;
    }

    @Override
    public Store addStore(Store store) {
        validateStore(store);
        storeRepository.save(store);
        return store;
    }

    private void validateStore(Store aStore) {
        entityVAlidator.validateStore(aStore);
    }

    @Override
    public StoreAdminUser addAdmin() {
        return null;
    }

    @Override
    public Merchandise addMerchandiseToStore(Long storeId, Merchandise merchandise) {
        Store store = storeRepository.findById(storeId).orElseThrow(NotFoundStoreException::new);
        store.addMerchandise(merchandise);
        merchandiseRepository.save(merchandise);
        storeRepository.save(store);
        return merchandise;
    }

    @Override
    public List<Merchandise> addMultipleMerchandisesToStore(Long storeId, List<Merchandise> merchandiseList) {
        Store store = this.getStore(storeId);
        merchandiseList.stream().forEach(merchandise -> {
            store.addMerchandise(merchandise);
            merchandiseRepository.save(merchandise);
        });
        storeRepository.save(store);
        return merchandiseList;
    }

    @Override
    public List<AcquiredProduct> getAcquiredProductsFromStore(Long storeId, List<MerchandiseDTO> productsToBuy) {
        List<AcquiredProduct> products = new ArrayList<>();
        Store store = getStore(storeId);
        productsToBuy.stream().forEach(product -> {
            products.add(store.getProduct(product.getMerchandiseName(), product.getMerchandiseBrand(), product.getQuantity()));
        });
        return products;
    }
}
