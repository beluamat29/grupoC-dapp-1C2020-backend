package com.example.demo.controllers;

import com.example.demo.builders.MerchandiseBuilder;
import com.example.demo.builders.StoreBuilder;
import com.example.demo.model.exceptions.InvalidMerchandiseException;
import com.example.demo.model.exceptions.NotFoundStoreException;
import com.example.demo.model.exceptions.RepeatedMerchandiseInStore;
import com.example.demo.model.merchandise.Merchandise;
import com.example.demo.model.merchandise.MerchandiseCategory;
import com.example.demo.model.store.StoreCategory;
import com.example.demo.services.StoreService;
import com.example.demo.model.store.Store;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class StoreControllerTest {

    @MockBean
    StoreService storeServiceMock;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void ifWeAskForStoresWeGetTheActualStoresList() throws Exception {
        List<Store> stores = StoreBuilder.storeList();
        when(storeServiceMock.getStores()).thenReturn(stores);

        mockMvc.perform(get("/stores"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].id", is(stores.get(0).id())))
                .andExpect(jsonPath("$[1].id", is(stores.get(1).id())))
                .andExpect(jsonPath("$[2].id", is(stores.get(2).id())))
                .andExpect(jsonPath("$[0].storeName", is(stores.get(0).name())))
                .andExpect(jsonPath("$[1].storeName", is(stores.get(1).name())))
                .andExpect(jsonPath("$[2].storeName", is(stores.get(2).name())));
    }

    @Test
    public void ifWeAskForStoresWithACategoryWeOnlyGetTheStoresThatHaveThatCategoryList() throws Exception {
        List<Store> stores = StoreBuilder.storeWithACategoryList(StoreCategory.CLEANING_SUPPLIES);
        when(storeServiceMock.getStoresWithACategory(StoreCategory.CLEANING_SUPPLIES.toString())).thenReturn(stores);

        mockMvc.perform(get("/stores?category=CLEANING_SUPPLIES"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].storeName", is(stores.get(0).name())));
    }

    @Test
    public void whenThereAreNoStoreWithASpecificCategoryItsReturns404() throws Exception {
        List<Store> stores = StoreBuilder.storeWithACategoryList(StoreCategory.CLEANING_SUPPLIES);
        when(storeServiceMock.getStoresWithACategory(StoreCategory.GROCERY.toString())).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/stores?category=GROCERY"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void askingForAnExistingStoreByIdReturnsTheStore() throws Exception {
        Store store = StoreBuilder.aStore().build();
        when(storeServiceMock.getStore(any())).thenReturn(store);
        mockMvc.perform(get("/stores/" + store.id().toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id", is(store.id())));
    }

    @Test
    public void askingForANonExistingStoreByIdReturns404() throws Exception {
        when(storeServiceMock.getStore(any())).thenThrow(new NotFoundStoreException());
        Long nonExistingId = new Random().nextLong();
        mockMvc.perform(get("/stores/" + nonExistingId.toString()))
                .andExpect(status().isNotFound());
    }

    @Test
    public void addingAMerchandiseInStoreReturnsTheMerchandiseAnd200Status() throws Exception {
        Store store = StoreBuilder.aStore().buildWithId();
        Merchandise merchandise = MerchandiseBuilder.aMerchandise().build();
        when(storeServiceMock.addMerchandiseToStore(any(), any())).thenReturn(merchandise);

        JSONObject body = generateMerchandiseToAddBody(merchandise);
        MvcResult mvcResult = mockMvc.perform(post("/stores/addMerchandise")
                .contentType(MediaType.APPLICATION_JSON)
                .content(String.valueOf(body)))
                .andExpect(status().isOk())
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        assertEquals(JsonPath.parse(response).read("name"), merchandise.name());
        assertEquals(JsonPath.parse(response).read("brand"), merchandise.brand());
        assertEquals(JsonPath.parse(response).read("price"), merchandise.price());
        assertEquals(JsonPath.parse(response).read("stock"), merchandise.stock());
        assertEquals(JsonPath.parse(response).read("category"), merchandise.getCategory().toString());
        assertEquals(JsonPath.parse(response).read("productImage"), merchandise.imageURL());

    }

    @Test
    public void addingAMerchandiseInANonExistingStoreReturns404() throws Exception {
        Merchandise merchandise = MerchandiseBuilder.aMerchandise().build();
        when(storeServiceMock.addMerchandiseToStore(any(), any())).thenThrow(new NotFoundStoreException());

        JSONObject body = generateMerchandiseToAddBody(merchandise);
        MvcResult mvcResult = mockMvc.perform(post("/stores/addMerchandise")
                .contentType(MediaType.APPLICATION_JSON)
                .content(String.valueOf(body)))
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    public void addingAnExistingMerchandiseInStoreReturnsBadRequest() throws Exception {
        Merchandise merchandise = MerchandiseBuilder.aMerchandise().build();
        when(storeServiceMock.addMerchandiseToStore(any(), any())).thenThrow(new RepeatedMerchandiseInStore());

        JSONObject body = generateMerchandiseToAddBody(merchandise);
        MvcResult mvcResult = mockMvc.perform(post("/stores/addMerchandise")
                .contentType(MediaType.APPLICATION_JSON)
                .content(String.valueOf(body)))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    public void addingAMerchandiseWithInvalidFieldReturnsBadRequest() throws Exception{
        Merchandise merchandise = MerchandiseBuilder.aMerchandise().withName("").build();
        when(storeServiceMock.addMerchandiseToStore(any(), any())).thenThrow(new InvalidMerchandiseException());

        JSONObject body = generateMerchandiseToAddBody(merchandise);
        MvcResult mvcResult = mockMvc.perform(post("/stores/addMerchandise")
                .contentType(MediaType.APPLICATION_JSON)
                .content(String.valueOf(body)))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    public void gettingMerchandiseFromASpecificStoreReturnsTheMerchandiseListAnd200Status() throws Exception{
        Store store = StoreBuilder.aStore().buildWithId();
        Merchandise bread = MerchandiseBuilder.aMerchandise().buildWithId();
        store.addMerchandise(bread);
        List<Merchandise> merchandiseList = store.listOfAvailableMerchandise();
        when(storeServiceMock.getProductsFromStore(any())).thenReturn(merchandiseList);
        when(storeServiceMock.getStore(any())).thenReturn(store);

        mockMvc.perform(get("/stores/" + (store.id()).toString() + "/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("merchandises", hasSize(1)))
                .andExpect(jsonPath("store.id", is(store.id())))
                .andExpect(jsonPath("merchandises[0].id", is(merchandiseList.get(0).id())))
                .andExpect(jsonPath("merchandises[0].name", is(merchandiseList.get(0).name())))
                .andExpect(jsonPath("merchandises[0].brand", is(merchandiseList.get(0).brand())))
                .andExpect(jsonPath("merchandises[0].price", is(merchandiseList.get(0).price())))
                .andExpect(jsonPath("merchandises[0].stock", is(merchandiseList.get(0).stock())))
                .andExpect(jsonPath("merchandises[0].category", is(merchandiseList.get(0).getCategory().toString())))
                .andExpect(jsonPath("merchandises[0].productImage", is(merchandiseList.get(0).imageURL())));
    }

    @Test
    public void gettingStoreProductsListFromNonExistingStoreReturns404() throws Exception{
        when(storeServiceMock.getProductsFromStore(any())).thenThrow((new NotFoundStoreException()));
        Long nonExistingId = (new Random().nextLong());

        mockMvc.perform(get("/stores/"+ nonExistingId.toString() +"/products"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void gettingStoreProductsListFromStoreWithoutMerchandiseReturnsAnEmptyList() throws Exception {
        Store store = StoreBuilder.aStore().buildWithId();
        when(storeServiceMock.getProductsFromStore(any())).thenReturn(new ArrayList<>());
        when(storeServiceMock.getStore(any())).thenReturn(store);

        mockMvc.perform(get("/stores/"+ store.id().toString() +"/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("merchandises", hasSize(0)))
                .andExpect(jsonPath("store.id", is(store.id())));
    }

    private JSONObject generateMerchandiseToAddBody(Merchandise merchandise) throws JSONException {
        merchandise.setId(new Random().nextLong());
        JSONObject merchandiseJson = new JSONObject();


        merchandiseJson.put("id", merchandise.id());
        merchandiseJson.put("merchandiseName", merchandise.name());
        merchandiseJson.put("merchandiseBrand", merchandise.brand());
        merchandiseJson.put("merchandisePrice", merchandise.price());
        merchandiseJson.put("merchandiseStock", merchandise.stock());
        merchandiseJson.put("category", merchandise.getCategory().toString());
        merchandiseJson.put("productImage", merchandise.imageURL());
        return merchandiseJson;

    }
}
