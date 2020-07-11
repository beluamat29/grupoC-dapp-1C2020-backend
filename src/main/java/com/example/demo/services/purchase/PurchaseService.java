package com.example.demo.services.purchase;


import com.example.demo.dtos.MerchandiseDTO;
import com.example.demo.model.*;
import com.example.demo.model.exceptions.NotFoundUserException;
import com.example.demo.model.store.Store;
import com.example.demo.model.ticket.Ticket;
import com.example.demo.model.user.ClientUser;
import com.example.demo.model.user.User;
import com.example.demo.repositories.BillRepository;
import com.example.demo.repositories.TicketRepository;
import com.example.demo.repositories.users.UserRepository;
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
public class PurchaseService implements IPurchaseService {

    @Autowired
    StoreService storeService;

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TicketRepository ticketRepository;

    @Autowired
    BillRepository billRepository;

    @Autowired
    BillGenerator billGenerator;

    @Override
    public Ticket processTicket(Long storeId, List<MerchandiseDTO> productsToBuy, String paymentMethod) {
        List<AcquiredProduct> productsList = storeService.getAcquiredProductsFromStore(storeId, productsToBuy);
        Store store = storeService.getStore(storeId);
        Ticket ticket = generateTicketWithId(paymentMethod, store, productsList);
        return ticketRepository.save(ticket);
    }

    @Override
    public Bill processBill(List<MerchandiseDTO> productsToBuy, String aDeliveryType, LocalDateTime deliveryTime, String paymentMethod, ClientUser user) {
        Map<Long, List<MerchandiseDTO>> productsGroupByStore = productsToBuy.stream().collect(Collectors.groupingBy(MerchandiseDTO::getStoreId));
        List<Ticket> ticketList = new ArrayList<>();
        productsGroupByStore.forEach((storeId, products) -> {
            Store store = storeService.getStore(storeId);
            ticketList.add(new Ticket(paymentMethod, store, generateAcquiredProducts(products, store)));
        });
        User clientUser = userService.getUserById(user.id());
        DeliveryType deliveryType = generateDelivery(aDeliveryType, clientUser, deliveryTime);
        Bill bill = billGenerator.generateBill(ticketList, (ClientUser) clientUser, deliveryType);
        return billRepository.save(bill);
    }

    @Override
    public List<Bill> getUsersBills(Long userId) {
        ClientUser user = (ClientUser) userRepository.findById(userId).orElseThrow(NotFoundUserException::new);
        return user.getBills();

    }

    private List<AcquiredProduct> generateAcquiredProducts(List<MerchandiseDTO> productsToBuy, Store store) {
        return productsToBuy.stream().map(product -> store.getProduct(product.getMerchandiseName(), product.getMerchandiseBrand(), product.getQuantity())).collect(Collectors.toList());
    }

    public DeliveryType generateDelivery(String deliveryType, User user, LocalDateTime deliveryTime) {
        if (deliveryType.equals("HOME_DELIVERY")) {
            return new HomeDelivery(user.address(), deliveryTime);
        }
        return new StorePickUp(LocalDateTime.now().plusDays(2));
    }

    private Ticket generateTicketWithId(String paymentMethod, Store store, List<AcquiredProduct> productsList) {
        Ticket ticket = new Ticket(paymentMethod, store, productsList);
        ticket.setId(new Random().nextLong());
        return ticket;
    }
}
