package com.example.demo.services;

import com.example.demo.model.exceptions.NotFoundStoreException;
import com.example.demo.model.merchandise.Merchandise;
import com.example.demo.model.store.StoreCategory;
import com.example.demo.model.user.StoreAdminUser;
import com.example.demo.model.store.Store;
import com.example.demo.model.validator.EntityValidator;
import com.example.demo.repositories.StoreRepository;
import com.example.demo.repositories.merchandise.MerchandiseRepository;
import com.example.demo.repositories.storeSchedule.StoreScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StoreService implements IStoreService {

    private EntityValidator entityVAlidator = new EntityValidator();

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private StoreScheduleRepository storeScheduleRepository;
    @Autowired
    private MerchandiseRepository merchandiseRepository;

    @Override
    public List<Store> getStores() {
        return storeRepository.findAll();
    }

    public List<Store> getStoresWithACategory(String category) {
        return storeRepository.getStoresWithACategory(category);
    }

    public List<Merchandise> getProductsFromStore(Long storeId) {
        storeRepository.findById(storeId).orElseThrow(NotFoundStoreException::new);
        if(storeRepository.findById(storeId).get().listOfAvailableMerchandise().isEmpty()){
            return new ArrayList<>();
        }
        return merchandiseRepository.getMerchandiseFromStore(storeId).get();

    }

   @Override
    public Store getStore(Long storeId) {
        return storeRepository.findById(storeId).orElseThrow(NotFoundStoreException::new);
    }

    @Override
    public List<Merchandise> getDiscountFromStores() {  return null; }

   @Override
   public Store addStore(Store store) {
       validateStore(store);
       storeScheduleRepository.save(store.storeSchedule());
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
}
