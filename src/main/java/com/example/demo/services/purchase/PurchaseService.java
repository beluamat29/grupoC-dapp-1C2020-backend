package com.example.demo.services.purchase;


import com.example.demo.model.AcquiredProduct;
import com.example.demo.model.store.Store;
import com.example.demo.model.ticket.Ticket;
import com.example.demo.services.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PurchaseService implements IPurchaseService{

    @Autowired
    StoreService storeService;


    @Override
    public Ticket processTicket(Long storeId, List<AcquiredProduct> productsToBuy, String paymentMethod) {
        List<AcquiredProduct> productsList = storeService.getAcquiredProductsFromStore(storeId, productsToBuy);
        Store store = storeService.getStore(storeId);
        return new Ticket(paymentMethod, store, productsList);
    }
}
