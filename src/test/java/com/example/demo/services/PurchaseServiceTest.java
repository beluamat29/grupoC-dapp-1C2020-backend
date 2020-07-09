package com.example.demo.services;


import com.example.demo.builders.ClientUserBuilder;
import com.example.demo.builders.MerchandiseBuilder;
import com.example.demo.builders.StoreBuilder;
import com.example.demo.dtos.MerchandiseDTO;
import com.example.demo.model.AcquiredProduct;
import com.example.demo.model.Bill;
import com.example.demo.model.BillGenerator;
import com.example.demo.model.DeliveryType;
import com.example.demo.model.exceptions.NotFoundStoreException;
import com.example.demo.model.merchandise.Merchandise;
import com.example.demo.model.store.Store;
import com.example.demo.model.ticket.Ticket;
import com.example.demo.model.user.ClientUser;
import com.example.demo.repositories.BillRepository;
import com.example.demo.repositories.TicketRepository;
import com.example.demo.services.purchase.PurchaseService;
import com.example.demo.services.users.UserService;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;


import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.function.BooleanSupplier;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class PurchaseServiceTest {

    @Mock
    StoreService storeServiceMock;

    @Mock
    UserService userServiceMock;

    @Mock
    TicketRepository ticketRepositoryMock;

    @Mock
    BillRepository billRepositoryMock;

    @InjectMocks
    PurchaseService purchaseService;

    @Test
    public void processingAPurchaseFromAStoreReturnsTheTicketWithAllTheAcquiredProducts() {
        Store store = StoreBuilder.aStore().buildWithId();
        Merchandise merchandise = MerchandiseBuilder.aMerchandise().build();
        Merchandise anotherMerchandise = MerchandiseBuilder.aMerchandise().withName("Cacao").withBrand("Nesquik").withPrice(35.2).build();
        MerchandiseDTO productToBuy = new MerchandiseDTO(merchandise, store.id(), 1);
        MerchandiseDTO anotherProductToBuy = new MerchandiseDTO(anotherMerchandise, store.id(), 2);
        String paymentMethod = "Efectivo";
        AcquiredProduct product = new AcquiredProduct(merchandise, 1);
        AcquiredProduct anotherProduct = new AcquiredProduct(anotherMerchandise, 1);
        List<AcquiredProduct> acquiredProductList = Arrays.asList(product, anotherProduct);
        List<MerchandiseDTO> productsToBuy = Arrays.asList(productToBuy, anotherProductToBuy);
        Ticket ticket = new Ticket(paymentMethod, store, acquiredProductList);
        Double total = 100.2;

        when(storeServiceMock.getAcquiredProductsFromStore(any(), any())).thenReturn(acquiredProductList);
        when(storeServiceMock.getStore(any())).thenReturn(store);
        when(ticketRepositoryMock.save(any())).thenReturn(ticket);

        Ticket retrievedTicket = purchaseService.processTicket(store.id(), productsToBuy, paymentMethod);

        assertEquals(store, retrievedTicket.store());
        assertEquals(acquiredProductList, retrievedTicket.getListOfAdquiredProducts());
        assertEquals(paymentMethod, retrievedTicket.paymentMethod());
        assertEquals(total, retrievedTicket.getTotal());
    }

    @Test
    public void processingAPurchaseFromAStoreWithANonExistingStoreIdReturnsNotFoundStore() {
        Store store = StoreBuilder.aStore().buildWithNoId();
        Merchandise merchandise = MerchandiseBuilder.aMerchandise().build();
        MerchandiseDTO productToBuy = new MerchandiseDTO(merchandise, store.id(), 1);
        String paymentMethod = "Efectivo";
        AcquiredProduct product = new AcquiredProduct(merchandise, 1);
        List<AcquiredProduct> acquiredProductList = Arrays.asList(product);
        List<MerchandiseDTO> productsToBuy = Arrays.asList(productToBuy);
        when(storeServiceMock.getAcquiredProductsFromStore(any(), any())).thenReturn(acquiredProductList);
        when(storeServiceMock.getStore(any())).thenThrow(NotFoundStoreException.class);

        assertThrows(NotFoundStoreException.class, () -> purchaseService.processTicket(store.id(), productsToBuy, paymentMethod));
    }
    @Test
    public void processingAPurchaseReturnsTheBill(){
        ClientUser user = ClientUserBuilder.user().build();
        Store store = StoreBuilder.aStore().buildWithId();
        Merchandise merchandise = MerchandiseBuilder.aMerchandise().build();
        Merchandise anotherMerchandise = MerchandiseBuilder.aMerchandise().withName("Cacao").withBrand("Nesquik").withPrice(35.2).build();
        store.addMerchandise(merchandise);
        store.addMerchandise(anotherMerchandise);
        String paymentMethod = "Efectivo";
        AcquiredProduct product = new AcquiredProduct(merchandise, 1);
        AcquiredProduct anotherProduct = new AcquiredProduct(anotherMerchandise, 1);
        List<AcquiredProduct> acquiredProductList = Arrays.asList(product, anotherProduct);
        Double total = 100.2;
        String deliveryType = "HOME_DELIVERY";
        LocalDateTime deliveryTime = LocalDateTime.of(2020,07,25,14,00);
        DeliveryType delivery = purchaseService.generateDelivery(deliveryType, user, deliveryTime);
        MerchandiseDTO product1 = new MerchandiseDTO(merchandise,store.id(), 1);
        MerchandiseDTO product2 = new MerchandiseDTO(anotherMerchandise, store.id(), 1);
        List<MerchandiseDTO> productsToBuy = Arrays.asList(product1, product2);
        BillGenerator billGenerator = new BillGenerator();
        Ticket ticket = new Ticket(paymentMethod,store, acquiredProductList);
        Bill aBill = billGenerator.generateBill(Arrays.asList(ticket), user, delivery);
        when(storeServiceMock.getStore(store.id())).thenReturn(store);
        when(userServiceMock.getUserById(any())).thenReturn(user);
        when(billRepositoryMock.save(any())).thenReturn(aBill);


        Bill bill = purchaseService.processBill(productsToBuy, deliveryType, deliveryTime, paymentMethod, user);

        assertEquals(1, bill.quantityTickets());
        assertEquals(total, bill.totalPrice());
    }

    @Test
    public void askingForAUsersBillsReturnsAllBills(){
        ClientUser user = ClientUserBuilder.user().build();
        Store store = StoreBuilder.aStore().buildWithId();
        Merchandise merchandise = MerchandiseBuilder.aMerchandise().build();
        Merchandise anotherMerchandise = MerchandiseBuilder.aMerchandise().withName("Cacao").withBrand("Nesquik").withPrice(35.2).build();
        store.addMerchandise(merchandise);
        store.addMerchandise(anotherMerchandise);
        String paymentMethod = "Efectivo";
        AcquiredProduct product = new AcquiredProduct(merchandise, 1);
        AcquiredProduct anotherProduct = new AcquiredProduct(anotherMerchandise, 1);
        List<AcquiredProduct> acquiredProductList = Arrays.asList(product, anotherProduct);
        String deliveryType = "HOME_DELIVERY";
        LocalDateTime deliveryTime = LocalDateTime.of(2020,07,25,14,00);
        DeliveryType delivery = purchaseService.generateDelivery(deliveryType, user, deliveryTime);
        MerchandiseDTO product1 = new MerchandiseDTO(merchandise,store.id(), 1);
        MerchandiseDTO product2 = new MerchandiseDTO(anotherMerchandise, store.id(), 1);
        List<MerchandiseDTO> productsToBuy = Arrays.asList(product1, product2);
        BillGenerator billGenerator = new BillGenerator();
        Ticket ticket = new Ticket(paymentMethod,store, acquiredProductList);
        Bill aBill = new Bill(Arrays.asList(ticket), delivery);
        when(storeServiceMock.getStore(store.id())).thenReturn(store);
        when(userServiceMock.getUserById(any())).thenReturn(user);
        when(billRepositoryMock.save(any())).thenReturn(aBill);


        Bill bill = purchaseService.processBill(productsToBuy, deliveryType, deliveryTime, paymentMethod, user);

        assertTrue(sameBills(user.getBills().get(0), bill));
        assertEquals(1, user.quantityOfBills());
    }

    private Boolean sameBills(Bill bill, Bill anotherBill) {
        return bill.totalPrice().equals(anotherBill.totalPrice())&& bill.addressOfDelivery().equals(anotherBill.addressOfDelivery()) &&
                bill.quantityTickets().equals(anotherBill.quantityTickets()) && sameTickets(bill.getTickets(), anotherBill.getTickets());
    }

    private Boolean sameTickets(List<Ticket> ticketList, List<Ticket> anotherTicketList) {
        return ticketList.size() == anotherTicketList.size()  && ticketList.stream().allMatch(ticket -> compareAllickets(ticket, anotherTicketList));
    }

    private Boolean compareAllickets(Ticket ticket, List<Ticket> anotherTicketList) {
       return  anotherTicketList.stream().anyMatch(ticketInList -> equalsTickets(ticket, ticketInList));
    }

    private Boolean equalsTickets(Ticket ticket, Ticket anotherTicket) {
        return ticket.paymentMethod().equals(anotherTicket.paymentMethod()) && ticket.getTotal().equals(anotherTicket.getTotal()) &&
                ticket.productsQuantity().equals(anotherTicket.productsQuantity()) && sameStore(ticket.store(), anotherTicket.store()) &&
                sameProducts(ticket.getListOfAdquiredProducts(), anotherTicket.getListOfAdquiredProducts());
    }

    private Boolean sameProducts(List<AcquiredProduct> listOfAdquiredProducts, List<AcquiredProduct> anotherListOfAdquiredProducts) {
        return listOfAdquiredProducts.size() == anotherListOfAdquiredProducts.size()  && listOfAdquiredProducts.stream().allMatch(product -> compareAllProducts(product, anotherListOfAdquiredProducts));
    }

    private Boolean compareAllProducts(AcquiredProduct product, List<AcquiredProduct> productList) {
        return  productList.stream().anyMatch(productInList -> equalsProducts(product, productInList));
    }

    private Boolean equalsProducts(AcquiredProduct product, AcquiredProduct anotherProduct) {
        return product.name().equals(anotherProduct.name()) && product.brand().equals(anotherProduct.brand());
    }

    private Boolean sameStore(Store store, Store anotherStore) {
        return store.name().equals(anotherStore.name()) && store.address().equals(anotherStore);
    }
}
