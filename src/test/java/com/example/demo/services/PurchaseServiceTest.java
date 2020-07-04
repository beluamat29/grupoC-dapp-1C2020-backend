package com.example.demo.services;


import com.example.demo.builders.MerchandiseBuilder;
import com.example.demo.builders.StoreBuilder;
import com.example.demo.model.*;
import com.example.demo.model.merchandise.Merchandise;
import com.example.demo.model.store.Store;
import com.example.demo.model.ticket.Ticket;
import com.example.demo.services.purchase.PurchaseService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

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
    public void processingAPurchaseReturnsTheTicketWithAllTheAcquiredProducts(){
        Store store = StoreBuilder.aStore().buildWithId();
        Merchandise merchandise = MerchandiseBuilder.aMerchandise().build();
        Merchandise anotherMerchandise = MerchandiseBuilder.aMerchandise().withName("Cacao").withBrand("Nesquik").withPrice(35.2).build();
        String paymentMethod = "Efectivo";
        AcquiredProduct product = new AcquiredProduct(merchandise, 1);
        AcquiredProduct anotherProduct = new AcquiredProduct(anotherMerchandise, 1);
        List<AcquiredProduct> acquiredProductList = Arrays.asList(product, anotherProduct);
        Double total = 100.2;
        when(storeServiceMock.getAcquiredProductsFromStore(any(),any())).thenReturn(acquiredProductList);
        when(storeServiceMock.getStore(any())).thenReturn(store);

        Ticket ticket = purchaseService.processTicket(store.id(), acquiredProductList, paymentMethod);

        assertEquals(store, ticket.store());
        assertEquals(acquiredProductList, ticket.getListOfAdquiredProducts());
        assertEquals(paymentMethod, ticket.paymentMethod());
        assertEquals(total, ticket.getTotal());
    }


}
