package com.example.demo.model.validator;

import com.example.demo.model.exceptions.InvalidStoreException;
import com.example.demo.model.store.Store;

public class EntityValidator {

    public void validateStore(Store aStore) {
        validPaymentMethod(aStore);
        validSchedule(aStore);
        validCategories(aStore);
    }

    private void validCategories(Store aStore) {
        if(aStore.storeCategories().isEmpty()){
            throw new InvalidStoreException("Store must have at least one category");
        }
    }

    private void validSchedule(Store aStore) {
        if(!aStore.storeSchedule().isValid()){
            throw new InvalidStoreException("Invalid schedule, there must be at least one opening day and opening time must be previous to closing time ");
        }
    }

    private void validPaymentMethod(Store aStore) {
        if(aStore.availablePaymentMethods().isEmpty()){
            throw new InvalidStoreException("Store must have at least one payment method");
        }
    }
}
