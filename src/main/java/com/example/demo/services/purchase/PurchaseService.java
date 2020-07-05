package com.example.demo.services.purchase;


import com.example.demo.dtos.ProductToBuy;
import com.example.demo.model.AcquiredProduct;
import com.example.demo.model.Bill;
import com.example.demo.model.BillGenerator;
import com.example.demo.model.DeliveryType;
import com.example.demo.model.store.Store;
import com.example.demo.model.ticket.Ticket;
import com.example.demo.model.user.ClientUser;
import com.example.demo.services.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PurchaseService implements IPurchaseService{

    @Autowired
    StoreService storeService;


    @Override
    public Ticket processTicket(Long storeId, List<ProductToBuy> productsToBuy, String paymentMethod) {
        List<AcquiredProduct> products = productsToBuy.stream().map(productToBuy -> productToBuy.getProductToBuy()).collect(Collectors.toList());
        List<AcquiredProduct> productsList = storeService.getAcquiredProductsFromStore(storeId, products);
        Store store = storeService.getStore(storeId);
        return new Ticket(paymentMethod, store, productsList);
    }

    @Override
    public Bill processBill(List<ProductToBuy> productsToBuy, DeliveryType deliveryType, String paymentMethod, ClientUser user) {
        Map<Long, List<ProductToBuy>> productsGroupByStore = productsToBuy.stream().collect(Collectors.groupingBy(ProductToBuy::getStoreId));
        List<Ticket> ticketList = new ArrayList<>();
        productsGroupByStore.forEach((storeId, products) -> ticketList.add(processTicket(storeId, products, paymentMethod)));
        BillGenerator billGenerator = new BillGenerator();
        return billGenerator.generateBill(ticketList, user, deliveryType);
    }
}
