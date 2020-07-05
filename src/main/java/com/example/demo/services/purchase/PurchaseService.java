package com.example.demo.services.purchase;


import com.example.demo.dtos.ProductToBuy;
import com.example.demo.model.*;
import com.example.demo.model.store.Store;
import com.example.demo.model.ticket.Ticket;
import com.example.demo.model.user.ClientUser;
import com.example.demo.model.user.User;
import com.example.demo.repositories.BillRepository;
import com.example.demo.repositories.TicketRepository;
import com.example.demo.services.StoreService;
import com.example.demo.services.users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class PurchaseService implements IPurchaseService{

    @Autowired
    StoreService storeService;

    @Autowired
    UserService userService;

    @Autowired
    TicketRepository ticketRepository;

    @Autowired
    BillRepository billRepository;

    @Override
    public Ticket processTicket(Long storeId, List<ProductToBuy> productsToBuy, String paymentMethod) {
        List<AcquiredProduct> products = productsToBuy.stream().map(productToBuy -> productToBuy.getProductToBuy()).collect(Collectors.toList());
        List<AcquiredProduct> productsList = storeService.getAcquiredProductsFromStore(storeId, products);
        Store store = storeService.getStore(storeId);
        Ticket ticket = generateTicketWithId(paymentMethod, store, productsList);
        return ticketRepository.save(ticket);
    }

    @Override
    public Bill processBill(List<ProductToBuy> productsToBuy, String aDeliveryTipe, LocalDateTime deliveryTime, String paymentMethod, ClientUser user) {
        Map<Long, List<ProductToBuy>> productsGroupByStore = productsToBuy.stream().collect(Collectors.groupingBy(ProductToBuy::getStoreId));
        List<Ticket> ticketList = new ArrayList<>();
        productsGroupByStore.forEach((storeId, products) -> ticketList.add(processTicket(storeId, products, paymentMethod)));
        BillGenerator billGenerator = new BillGenerator();
        User clientUser = userService.getUserById(user.id());
        DeliveryType deliveryType = generateDelivery(aDeliveryTipe, clientUser, deliveryTime);
        Bill bill = billGenerator.generateBill(ticketList,(ClientUser) clientUser, deliveryType);
        return billRepository.save(bill);
    }

    public DeliveryType generateDelivery(String deliveryType, User user, LocalDateTime deliveryTime) {
        if(deliveryType.equals("Home Delivery")){
            return new HomeDelivery(user.address(), deliveryTime);
        }
        return new StorePickUp(deliveryTime);
    }

    private Ticket generateTicketWithId(String paymentMethod, Store store, List<AcquiredProduct> productsList) {
        Ticket ticket = new Ticket(paymentMethod, store, productsList);
        ticket.setId(new Random().nextLong());
        return ticket;
    }
}
