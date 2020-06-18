package com.example.demo.services;

import com.example.demo.builders.MerchandiseBuilder;
import com.example.demo.builders.StoreBuilder;
import com.example.demo.model.exceptions.NotFoundStoreException;
import com.example.demo.model.exceptions.RepeatedMerchandiseInStore;
import com.example.demo.model.merchandise.Merchandise;
import com.example.demo.model.merchandise.MerchandiseCategory;
import com.example.demo.model.store.StoreCategory;
import com.example.demo.repositories.StoreRepository;
import com.example.demo.model.store.Store;
import com.example.demo.repositories.merchandise.MerchandiseRepository;
import com.example.demo.repositories.storeSchedule.StoreScheduleRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class StoreServiceTest {

    @Mock
    StoreRepository storeRepositoryMock;

    @Mock
    StoreScheduleRepository storeScheduleRepository;

    @Mock
    MerchandiseRepository merchandiseRepository;

    @InjectMocks
    StoreService storeService;

    @Test
    public void whenWeAskStoreServiceForStoresItReturnsTheListOfActualStores() {
        List<Store> stores = StoreBuilder.storeList();
        when(storeRepositoryMock.findAll()).thenReturn(stores);

        assertEquals(stores, storeService.getStores());
    }

    @Test
    public void whenWeAskStoreServiceForStoresWithACategoryItReturnsOnlyTheListOfStoresWithThatCategory() {
        List<Store> stores = StoreBuilder.storeList();
        when(storeRepositoryMock.getStoresWithACategory(StoreCategory.GROCERY.toString())).thenReturn(stores);

        assertEquals(stores, storeService.getStoresWithACategory(StoreCategory.GROCERY.toString()));
    }

    @Test
    public void whenThereAreNoStoreWithASpecificCategoryItsReturnAnEmptyList() {
        List<Store> stores = StoreBuilder.storeList();
        when(storeRepositoryMock.getStoresWithACategory(StoreCategory.HYGIENE_PRODUCTS.toString())).thenReturn(new ArrayList<>());

        assertTrue(storeService.getStoresWithACategory(StoreCategory.HYGIENE_PRODUCTS.toString()).isEmpty());
    }

    @Test
    public void gettingStoreProductsList() {
        Store store = StoreBuilder.aStore().buildWithId();
        store.addMerchandise("Nesquick", "Nestle", 20.4, 30, MerchandiseCategory.GROCERY, "foto nesquik");
        List<Merchandise> merchandiseList = store.listOfAvailableMerchandise();
        when(storeRepositoryMock.findById(any())).thenReturn(java.util.Optional.of(store));
        when(merchandiseRepository.getMerchandiseFromStore(any())).thenReturn(java.util.Optional.ofNullable(merchandiseList));

        assertEquals(merchandiseList, storeService.getProductsFromStore(store.id()));
    }
    @Test
    public void addingAStoreReturnsTheStore() {
        Store store = StoreBuilder.aStore().build();
        when(storeScheduleRepository.save(any())).thenReturn(store.storeSchedule());
        when(storeRepositoryMock.save(any())).thenReturn(store);

        assertEquals(storeService.addStore(store), store);
    }

    @Test
    public void gettingAStoreByIdReturnsTheStore() {
        Store store = StoreBuilder.aStore().buildWithId();
        when(storeRepositoryMock.findById(any())).thenReturn(java.util.Optional.ofNullable(store));

        Store retrievedStore = storeService.getStore(store.id());
        assertEquals(retrievedStore.id(), store.id());
    }

    @Test
    public void addingAMerchandiseToAStore() {
        Store store = StoreBuilder.aStore().buildWithId();
        Merchandise merchandise = MerchandiseBuilder.aMerchandise().build();
        when(storeRepositoryMock.findById(any())).thenReturn(java.util.Optional.ofNullable(store));
        when(merchandiseRepository.save(any())).thenReturn(merchandise);

        storeService.addMerchandiseToStore(store.id(), merchandise);
        Store retrievedStore = storeService.getStore(store.id());

        assertTrue(retrievedStore.sellsMerchandise(merchandise.name(), merchandise.brand()));
    }

    @Test
    public void isNotPossibleToAddARepeatedMerchandiseToAStore() {
        Store store = StoreBuilder.aStore().buildWithId();
        Merchandise merchandise = MerchandiseBuilder.aMerchandise().build();
        when(storeRepositoryMock.findById(any())).thenReturn(java.util.Optional.ofNullable(store));
        when(merchandiseRepository.save(any())).thenReturn(merchandise);

        storeService.addMerchandiseToStore(store.id(), merchandise);

        assertThrows(RepeatedMerchandiseInStore.class , ()-> storeService.addMerchandiseToStore(store.id(), merchandise));
    }

    @Test
    public void isNotPossibleToAddAMerchandiseToANonExistingStore() {
        Merchandise merchandise = MerchandiseBuilder.aMerchandise().build();
        when(storeRepositoryMock.findById(any())).thenReturn(java.util.Optional.ofNullable(null));

        assertThrows(NotFoundStoreException.class , ()-> storeService.addMerchandiseToStore((long) 0, merchandise));
    }
}
