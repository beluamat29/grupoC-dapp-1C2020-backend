package com.example.demo.controllers;

import com.example.demo.builders.ClientUserBuilder;
import com.example.demo.builders.MerchandiseBuilder;
import com.example.demo.builders.StoreBuilder;
import com.example.demo.model.*;
import com.example.demo.model.merchandise.Merchandise;
import com.example.demo.model.store.Store;
import com.example.demo.model.ticket.Ticket;
import com.example.demo.model.user.ClientUser;
import com.example.demo.services.purchase.PurchaseService;
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
import java.util.stream.Collectors;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class PurchaseControllerTest {

    @MockBean
    PurchaseService purchaseServiceMock;

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
        DeliveryType delivery = new HomeDelivery(clientUser.address(), LocalDateTime.of(2020, 07, 25, 14,00));
        String paymentMethod = "Efectivo";
        String deliveryType = "Home Delivery";
        LocalDateTime deliveryTime = LocalDateTime.of(2020, 07, 25, 14,00);
        List<AcquiredProduct> products = Arrays.asList(acquiredProduct);
        Ticket ticket = new Ticket(paymentMethod, store, products);
        addIdToTicket(ticket);
        List<Ticket> ticketList = Arrays.asList(ticket);
        Bill bill = new BillGenerator().generateBill(ticketList, clientWithId, delivery);
        when(purchaseServiceMock.processBill(any(), any(), any(), any(), any())).thenReturn(addIdToBill(bill));

        JSONObject body = generateBillToAddBody(clientWithId, bill, paymentMethod, products, deliveryType, deliveryTime);
        MvcResult mvcResult = mockMvc.perform(post("/purchase")
                .contentType(MediaType.APPLICATION_JSON)
                .content(String.valueOf(body)))
                .andExpect(status().isOk())
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

    private JSONObject generateBillToAddBody(ClientUser clientUser, Bill bill, String paymentMethod, List<AcquiredProduct> products, String deliveryType, LocalDateTime deliveryTime ) throws JSONException {
        bill.setId(new Random().nextLong());
        JSONObject purchaseDTOJson = new JSONObject();

        purchaseDTOJson.put("productList", generateProductsList(products));
        purchaseDTOJson.put("userID", clientUser.id());
        purchaseDTOJson.put("deliveryType", deliveryType);
        purchaseDTOJson.put("deliveryTime", deliveryTime);
        purchaseDTOJson.put("paymentMethod", paymentMethod);
        return purchaseDTOJson;
    }

    private JSONObject generateProductsList(List<AcquiredProduct> products) throws JSONException {
        JSONObject productsJson = new JSONObject();

        List<JSONObject> acquiredProductJson = products.stream().map(product -> {
            try {
                return generateJsonProduct(product);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }).collect(Collectors.toList());
        productsJson.put("productsList", acquiredProductJson);
        return productsJson;

    }

    private JSONObject generateJsonProduct(AcquiredProduct product) throws JSONException {
        JSONObject productJson = new JSONObject();

        productJson.put("name", product.name());
        productJson.put("brand", product.brand());
        productJson.put("price", product.price());
        productJson.put("quantity", product.quantity());
        return productJson;

    }

    private ClientUser addIdToClientUser(ClientUser aUser){
        aUser.setId(new Random().nextLong());
        return aUser;
    }
}
