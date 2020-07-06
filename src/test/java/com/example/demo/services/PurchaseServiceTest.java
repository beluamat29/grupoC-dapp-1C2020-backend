package com.example.demo.services;


import com.example.demo.repositories.BillRepository;
import com.example.demo.repositories.TicketRepository;
import com.example.demo.services.purchase.PurchaseService;
import com.example.demo.services.users.UserService;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;


import static org.mockito.ArgumentMatchers.any;

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

   /* @Test
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
        when(ticketRepositoryMock.save(any())).thenReturn(ticket);

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
*/
  /*  @Test
    public void processingAPurchaseReturnsTheBill(){
        ClientUser user = ClientUserBuilder.user().build();
        Store store = StoreBuilder.aStore().buildWithId();
        Merchandise merchandise = MerchandiseBuilder.aMerchandise().build();
        Merchandise anotherMerchandise = MerchandiseBuilder.aMerchandise().withName("Cacao").withBrand("Nesquik").withPrice(35.2).build();
        String paymentMethod = "Efectivo";
        AcquiredProduct product = new AcquiredProduct(merchandise, 1);
        AcquiredProduct anotherProduct = new AcquiredProduct(anotherMerchandise, 1);
        List<AcquiredProduct> acquiredProductList = Arrays.asList(product, anotherProduct);
        Double total = 300.2;
        String deliveryType = "Home Delivery";
        LocalDateTime deliveryTime = LocalDateTime.of(2020,07,25,14,00);
        DeliveryType delivery = purchaseService.generateDelivery(deliveryType, user, deliveryTime);
        ProductToBuy product1 = new ProductToBuy(store.id(), product);
        ProductToBuy product2 = new ProductToBuy(store.id(), anotherProduct);
        List<ProductToBuy> productsToBuy = Arrays.asList(product1, product2);
        BillGenerator billGenerator = new BillGenerator();
        Ticket ticket = purchaseService.processTicket(store.id(),productsToBuy, paymentMethod);
        Bill aBill = billGenerator.generateBill(Arrays.asList(ticket), user, delivery);
        when(storeServiceMock.getAcquiredProductsFromStore(store.id(), acquiredProductList)).thenReturn(acquiredProductList);
        when(storeServiceMock.getStore(store.id())).thenReturn(store);
        when(ticketRepositoryMock.save(any())).thenReturn(ticket);
        when(userServiceMock.getUserById(any())).thenReturn(user);
        when(billRepositoryMock.save(any())).thenReturn(aBill);


        Bill bill = purchaseService.processBill(productsToBuy, deliveryType, deliveryTime, paymentMethod, user);

        assertEquals(1, bill.quantityTickets());
        assertEquals(total, bill.totalPrice());
    }*/
}
