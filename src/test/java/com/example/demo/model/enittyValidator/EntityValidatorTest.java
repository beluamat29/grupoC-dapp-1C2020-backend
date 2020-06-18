package com.example.demo.model.enittyValidator;

import com.example.demo.builders.StoreBuilder;
import com.example.demo.model.store.Store;
import com.example.demo.model.store.StoreCategory;
import com.example.demo.model.validator.EntityValidator;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EntityValidatorTest {

    @Test
    public void aStoreCannotHaveAnEmptyCategoryList(){
        EntityValidator validator = new EntityValidator();
        List<StoreCategory> emptyList = new ArrayList<>();
        Store store = StoreBuilder.aStore().withCategory(emptyList).build();

        try{
            validator.validateStore(store);
        }catch (Exception e){
            assertEquals(e.getMessage(), "Store must have at least one category");
        }
    }

    @Test
    public void aStoreCannotHaveAnEmptyPaymentMethods(){
        EntityValidator validator = new EntityValidator();
        List<String> emptyList = new ArrayList<>();
        Store store = StoreBuilder.aStore().withPaymentMethods(emptyList).build();

        try{
            validator.validateStore(store);
        }catch (Exception e){
            assertEquals(e.getMessage(), "Store must have at least one payment method");
        }
    }

    @Test
    public void aStoreCannotHaveAnInvalidSchedule(){
        EntityValidator validator = new EntityValidator();
        Store store = StoreBuilder.aStore().withoutDaysInSchedule();

        try{
            validator.validateStore(store);
        }catch (Exception e){
            assertEquals(e.getMessage(), "Invalid schedule, there must be at least one opening day and opening time must be previous to closing time ");
        }
    }
}
