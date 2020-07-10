package com.example.demo.controllers;

import com.example.demo.builders.ClientUserBuilder;
import com.example.demo.builders.MerchandiseBuilder;
import com.example.demo.builders.StoreBuilder;
import com.example.demo.dtos.MerchandiseDTO;
import com.example.demo.model.*;
import com.example.demo.model.merchandise.Merchandise;
import com.example.demo.model.store.Store;
import com.example.demo.model.ticket.Ticket;
import com.example.demo.model.user.ClientUser;
import com.example.demo.services.StoreService;
import com.example.demo.services.purchase.PurchaseService;
import com.example.demo.services.users.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Random;


import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class PurchaseControllerTest {

    @MockBean
    PurchaseService purchaseServiceMock;

    @MockBean
    UserService userServiceMock;

    @MockBean
    StoreService storeServiceMock;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void purchasingProductsReturnsABillWithTheTicketsAnd200Status() throws Exception {
        ClientUser clientUser = ClientUserBuilder.user().build();
        ClientUser clientWithId = addIdToClientUser(clientUser);
        Store store = StoreBuilder.aStore().buildWithId();
        Merchandise merchandise = MerchandiseBuilder.aMerchandise().build();
        AcquiredProduct acquiredProduct = new AcquiredProduct(merchandise, 2);
        MerchandiseDTO productToBuyDTO = new MerchandiseDTO(merchandise, store.id(), 2);
        DeliveryType delivery = new HomeDelivery(clientUser.address(), LocalDateTime.of(2020, 07, 25, 14,00));
        String paymentMethod = "Efectivo";
        String deliveryType = "Home Delivery";
        LocalDateTime deliveryTime = LocalDateTime.of(2020, 07, 25, 14,00);
        List<AcquiredProduct> acquiredProducts = Arrays.asList(acquiredProduct);
        List<MerchandiseDTO> products = Arrays.asList(productToBuyDTO);
        Ticket ticket = new Ticket(paymentMethod, store, acquiredProducts);
        addIdToTicket(ticket);
        List<Ticket> ticketList = Arrays.asList(ticket);
        Bill bill = new BillGenerator().generateBill(ticketList, clientWithId, delivery);
        addIdToBill(bill);
        when(purchaseServiceMock.processBill(any(), any(), any(), any(), any())).thenReturn(addIdToBill(bill));
        when(userServiceMock.getUserById(any())).thenReturn(clientWithId);
        when(storeServiceMock.getStore(any())).thenReturn(store);

        JSONObject body = generateBillToAddBody(clientWithId, bill, paymentMethod, products, deliveryType, deliveryTime);
        MvcResult mvcResult = mockMvc.perform(post("/purchase")
                .contentType(MediaType.APPLICATION_JSON)
                .content(String.valueOf(body)))
                .andExpect(status().isOk())
                .andExpect((jsonPath("id", is(bill.id()))))
                .andExpect((jsonPath("allTickets", hasSize(1))))
                .andExpect((jsonPath("allTickets[0].id", is(ticket.id()))))
                .andExpect((jsonPath("allTickets[0].paymentMethod", is(ticket.paymentMethod()))))
                .andExpect((jsonPath("allTickets[0].productList", hasSize(1))))
                .andExpect((jsonPath("allTickets[0].totalPrice",is(ticket.getTotal()))))
                .andExpect((jsonPath("allTickets[0].ticketStore.id", is(ticket.store().id()))))
                .andExpect((jsonPath("deliveryType.deliveryAddress", is(bill.addressOfDelivery()))))
                .andExpect((jsonPath("deliveryType.pickUpDate", is(bill.deliveryTime().toString()))))
                .andReturn();

    }

    @Test
    public void askingForAUsersBillsReturnsAllBillsAnd200Status() throws Exception {
        ClientUser clientUser = ClientUserBuilder.user().build();
        ClientUser clientWithId = addIdToClientUser(clientUser);
        Store store = StoreBuilder.aStore().buildWithId();
        Merchandise merchandise = MerchandiseBuilder.aMerchandise().build();
        AcquiredProduct acquiredProduct = new AcquiredProduct(merchandise, 2);
        MerchandiseDTO productToBuyDTO = new MerchandiseDTO(merchandise, store.id(), 2);
        DeliveryType delivery = new HomeDelivery(clientUser.address(), LocalDateTime.of(2020, 07, 25, 14,00));
        String paymentMethod = "Efectivo";
        String deliveryType = "Home Delivery";
        LocalDateTime deliveryTime = LocalDateTime.of(2020, 07, 25, 14,00);
        List<AcquiredProduct> acquiredProducts = Arrays.asList(acquiredProduct);
        List<MerchandiseDTO> products = Arrays.asList(productToBuyDTO);
        Ticket ticket = new Ticket(paymentMethod, store, acquiredProducts);
        addIdToTicket(ticket);
        List<Ticket> ticketList = Arrays.asList(ticket);
        Bill bill = new BillGenerator().generateBill(ticketList, clientWithId, delivery);
        addIdToBill(bill);
        when(purchaseServiceMock.processBill(any(), any(), any(), any(), any())).thenReturn(addIdToBill(bill));
        when(userServiceMock.getUserById(any())).thenReturn(clientWithId);
        when(storeServiceMock.getStore(any())).thenReturn(store);
        when(purchaseServiceMock.getUsersBills(any())).thenReturn(clientUser.getBills());


        mockMvc.perform(get("/purchase/" + clientUser.id()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("user.id", is(clientUser.id())))
                .andExpect(jsonPath("userBills", hasSize(1)))
                .andExpect(jsonPath("userBills.[0].allTickets", hasSize(1)))
                .andExpect(jsonPath("userBills.[0].deliveryType.deliveryAddress", is(bill.addressOfDelivery())))
                .andExpect(jsonPath("userBills.[0].deliveryType.pickUpDate", is(bill.deliveryTime().toString())))
                .andReturn();

    }

    private Ticket addIdToTicket(Ticket ticket) {
        ticket.setId(new Random().nextLong());
        return ticket;
    }

    private Bill addIdToBill(Bill bill) {
        bill.setId(new Random().nextLong());
        return bill;
    }

    private JSONObject generateBillToAddBody(ClientUser clientUser, Bill bill, String paymentMethod, List<MerchandiseDTO> products, String deliveryType, LocalDateTime deliveryTime ) throws JSONException {
        bill.setId(new Random().nextLong());
        JSONObject purchaseDTOJson = new JSONObject();

        purchaseDTOJson.put("productList", generateProductsList(products));
        purchaseDTOJson.put("userID", clientUser.id());
        purchaseDTOJson.put("deliveryType", deliveryType);
        purchaseDTOJson.put("deliveryTime", deliveryTime);
        purchaseDTOJson.put("paymentMethod", paymentMethod);
        return purchaseDTOJson;
    }

    private JSONArray generateProductsList(List<MerchandiseDTO> products) throws JSONException {
        JSONArray productsArray = new JSONArray();
        products.stream().forEach(product -> {
            try {
                productsArray.put(generateJsonProduct(product));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
        return productsArray;
    }

    private JSONObject generateJsonProduct(MerchandiseDTO product) throws JSONException {
        JSONObject productJson = new JSONObject();

        productJson.put("storeId", product.getStoreId());
        productJson.put("name", product.getMerchandiseName());
        productJson.put("brand", product.getMerchandiseBrand());
        productJson.put("price", product.getMerchandisePrice());
        productJson.put("quantity", product.getQuantity());
        productJson.put("category", product.getCategory());
        productJson.put("isActiveMerchandise", product.getIsActiveMerchandise());
        productJson.put("productImageURL", product.getImageURL());
        productJson.put("stock", product.getMerchandiseStock());
        return productJson;

    }

    private ClientUser addIdToClientUser(ClientUser aUser){
        aUser.setId(new Random().nextLong());
        return aUser;
    }
}
