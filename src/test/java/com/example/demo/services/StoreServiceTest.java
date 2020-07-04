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
    public void gettingStoreProductsListOnlyReturnsActiveMerchandiseFromStore() {
        Store store = StoreBuilder.aStore().buildWithId();
        Merchandise nesquick = new Merchandise("Nesquick", "Nestle", 20.4, 30, MerchandiseCategory.GROCERY, "foto nesquick");
        Merchandise fideos = new Merchandise("Fideos", "Marolio", 26.4, 23, MerchandiseCategory.GROCERY, "foto fideos");
        store.addMerchandise(nesquick);
        store.addMerchandise(fideos);
        store.deactivateProduct("Fideos", "Marolio");
        List<Merchandise> merchandiseList = store.listOfAvailableMerchandise();
        when(storeRepositoryMock.findById(any())).thenReturn(java.util.Optional.of(store));
        when(merchandiseRepository.getMerchandiseFromStore(any())).thenReturn(java.util.Optional.ofNullable(merchandiseList));

        assertEquals(1, storeService.getProductsFromStore(store.id(), true).size());
        assertTrue(storeService.getProductsFromStore(store.id(), true).contains(nesquick));
    }
    @Test
    public void addingAStoreReturnsTheStore() {
        Store store = StoreBuilder.aStore().build();
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

    @Test
    public void addingMultipleMerchandisesToAStore() {
        Merchandise merchandise1 = MerchandiseBuilder.aMerchandise().build();
        Merchandise merchandise2 = MerchandiseBuilder.aMerchandise().withName("Pure").build();
        List<Merchandise> merchandiseList = new ArrayList<Merchandise>() {{ add(merchandise1); add(merchandise2);}};
        Store store = StoreBuilder.aStore().buildWithId();
        when(storeRepositoryMock.save(any())).thenReturn(store);
        when(storeRepositoryMock.findById(any())).thenReturn(java.util.Optional.ofNullable(store));
        when(merchandiseRepository.getMerchandiseFromStore(any())).thenReturn(java.util.Optional.of(merchandiseList));

        storeService.addMultipleMerchandisesToStore(store.id(), merchandiseList);
        List <Merchandise> productsFromStore = storeService.getProductsFromStore(store.id(), true);
        assertEquals(productsFromStore.size(), 2);
    }

}
