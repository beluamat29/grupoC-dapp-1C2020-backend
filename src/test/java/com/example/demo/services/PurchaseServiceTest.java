package com.example.demo.services;


import com.example.demo.builders.ClientUserBuilder;
import com.example.demo.builders.MerchandiseBuilder;
import com.example.demo.builders.StoreBuilder;
import com.example.demo.dtos.ProductToBuy;
import com.example.demo.model.*;
import com.example.demo.model.exceptions.NotFoundStoreException;
import com.example.demo.model.merchandise.Merchandise;
import com.example.demo.model.store.Store;
import com.example.demo.model.ticket.Ticket;
import com.example.demo.model.user.ClientUser;
import com.example.demo.services.purchase.PurchaseService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class PurchaseServiceTest {

    @Mock
    StoreService storeServiceMock;

    @InjectMocks
    PurchaseService purchaseService;

    @Test
    public void processingAPurchaseFromAStoreReturnsTheTicketWithAllTheAcquiredProducts() {
        Store store = StoreBuilder.aStore().buildWithId();
        Merchandise merchandise = MerchandiseBuilder.aMerchandise().build();
        Merchandise anotherMerchandise = MerchandiseBuilder.aMerchandise().withName("Cacao").withBrand("Nesquik").withPrice(35.2).build();
        String paymentMethod = "Efectivo";
        AcquiredProduct product = new AcquiredProduct(merchandise, 1);
        AcquiredProduct anotherProduct = new AcquiredProduct(anotherMerchandise, 1);
        ProductToBuy product1 = new ProductToBuy(store.id(), product);
        ProductToBuy product2 = new ProductToBuy(store.id(), anotherProduct);
        List<AcquiredProduct> acquiredProductList = Arrays.asList(product, anotherProduct);
        List<ProductToBuy> productsToBuy = Arrays.asList(product1, product2);
        Double total = 100.2;
        when(storeServiceMock.getAcquiredProductsFromStore(any(), any())).thenReturn(acquiredProductList);
        when(storeServiceMock.getStore(any())).thenReturn(store);

        Ticket ticket = purchaseService.processTicket(store.id(), productsToBuy, paymentMethod);

        assertEquals(store, ticket.store());
        assertEquals(acquiredProductList, ticket.getListOfAdquiredProducts());
        assertEquals(paymentMethod, ticket.paymentMethod());
        assertEquals(total, ticket.getTotal());
    }

    @Test
    public void processingAPurchaseFromAStoreWithANonExistingStoreIdReturnsNotFoundStore() {
        Store store = StoreBuilder.aStore().buildWithNoId();
        Merchandise merchandise = MerchandiseBuilder.aMerchandise().build();
        String paymentMethod = "Efectivo";
        AcquiredProduct product = new AcquiredProduct(merchandise, 1);
        ProductToBuy productToBuy = new ProductToBuy(new Random().nextLong(), product);
        List<AcquiredProduct> acquiredProductList = Arrays.asList(product);
        List<ProductToBuy> productsToBuy = Arrays.asList(productToBuy);
        when(storeServiceMock.getAcquiredProductsFromStore(any(), any())).thenReturn(acquiredProductList);
        when(storeServiceMock.getStore(any())).thenThrow(NotFoundStoreException.class);

        assertThrows(NotFoundStoreException.class, () -> purchaseService.processTicket(store.id(), productsToBuy, paymentMethod));
    }

    @Test
    public void processingAPurchaseReturnsTheBill(){
        ClientUser user = ClientUserBuilder.user().build();
        Store store = StoreBuilder.aStore().buildWithId();
        Store anotherStore = StoreBuilder.aStore().buildWithId();
        Merchandise merchandise = MerchandiseBuilder.aMerchandise().build();
        Merchandise anotherMerchandise = MerchandiseBuilder.aMerchandise().withName("Cacao").withBrand("Nesquik").withPrice(35.2).build();
        Merchandise helado = MerchandiseBuilder.aMerchandise().withName("Helado").withBrand("Frigor").withPrice(100.0).build();
        String paymentMethod = "Efectivo";
        AcquiredProduct product = new AcquiredProduct(merchandise, 1);
        AcquiredProduct anotherProduct = new AcquiredProduct(anotherMerchandise, 1);
        AcquiredProduct heladote = new AcquiredProduct(helado, 2);
        List<AcquiredProduct> acquiredProductList = Arrays.asList(product, anotherProduct);
        List<AcquiredProduct> heladoList = Arrays.asList(heladote);
        Double total = 300.2;
        DeliveryType deliveryType = new HomeDelivery(user.address(), LocalDateTime.of(2020,07,25,14,00));
        ProductToBuy product1 = new ProductToBuy(store.id(), product);
        ProductToBuy product2 = new ProductToBuy(store.id(), anotherProduct);
        ProductToBuy product3 = new ProductToBuy(anotherStore.id(), heladote);
        List<ProductToBuy> productsToBuy = Arrays.asList(product1, product2, product3);
        when(storeServiceMock.getAcquiredProductsFromStore(store.id(), acquiredProductList)).thenReturn(acquiredProductList);
        when(storeServiceMock.getAcquiredProductsFromStore(anotherStore.id(), heladoList)).thenReturn(heladoList);
        when(storeServiceMock.getStore(store.id())).thenReturn(store);
        when(storeServiceMock.getStore(anotherStore.id())).thenReturn(anotherStore);


        Bill bill = purchaseService.processBill(productsToBuy, deliveryType, paymentMethod, user);

        assertEquals(2, bill.quantityTickets());
        assertEquals(total, bill.totalPrice());
    }
}
