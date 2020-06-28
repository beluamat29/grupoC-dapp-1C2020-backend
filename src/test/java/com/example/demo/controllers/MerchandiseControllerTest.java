package com.example.demo.controllers;


import com.example.demo.builders.MerchandiseBuilder;
import com.example.demo.model.exceptions.NegativePriceMerchandiseException;
import com.example.demo.model.exceptions.NegativeStockMerchandiseException;
import com.example.demo.model.exceptions.NotFoundMerchandiseException;
import com.example.demo.model.merchandise.Merchandise;
import com.example.demo.model.merchandise.MerchandiseCategory;
import com.example.demo.services.merchandise.MerchandiseService;
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

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class MerchandiseControllerTest {

    @MockBean
    MerchandiseService merchandiseServiceMock;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void updatingAMerchandiseReturnsTheUpdatedMerchandise() throws Exception {
        Merchandise merchandise = MerchandiseBuilder.aMerchandise().build();
        merchandise.setId(new Random().nextLong());
        when(merchandiseServiceMock.updateMerchandise(any(), any())).thenReturn(merchandise);

        JSONObject body = generateMerchandiseToAddBody(merchandise);
        MvcResult mvcResult = mockMvc.perform(put("/merchandise/" + merchandise.id())
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
    public void tryingToUpdateANonExistingMerchandiseReturns404() throws Exception {
        when(merchandiseServiceMock.updateMerchandise(any(),any())).thenThrow(new NotFoundMerchandiseException());
        Merchandise merchandise = MerchandiseBuilder.aMerchandise().build();
        Long randomId = new Random().nextLong();
        JSONObject body = generateMerchandiseToAddBody(merchandise);

        MvcResult mvcResult = mockMvc.perform(put("/merchandise/" + randomId.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .content(String.valueOf(body)))
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    public void tryingToUpdateAMerchandiseWithStockLowerThanZeroReturnsBadRequest() throws Exception {
        when(merchandiseServiceMock.updateMerchandise(any(),any())).thenThrow(new NegativeStockMerchandiseException());
        Long randomId = new Random().nextLong();
        JSONObject bodyToFail = generateMerchandiseToFailBody(randomId, -10, 20.0);

        MvcResult mvcResult = mockMvc.perform(put("/merchandise/" + randomId.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .content(String.valueOf(bodyToFail)))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    public void tryingToUpdateAMerchandiseWithPriceLowerThanZeroReturnsBadRequest() throws Exception {
        when(merchandiseServiceMock.updateMerchandise(any(),any())).thenThrow(new NegativePriceMerchandiseException());
        Long randomId = new Random().nextLong();
        JSONObject bodyToFail = generateMerchandiseToFailBody(randomId, 100, -20.0);

        MvcResult mvcResult = mockMvc.perform(put("/merchandise/" + randomId.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .content(String.valueOf(bodyToFail)))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    private JSONObject generateMerchandiseToFailBody(Long id, Integer stock, Double price) throws JSONException {
        JSONObject merchandiseJson = new JSONObject();

        merchandiseJson.put("id", id);
        merchandiseJson.put("merchandiseName", "Nombre");
        merchandiseJson.put("merchandiseBrand", "Marca");
        merchandiseJson.put("merchandisePrice", price);
        merchandiseJson.put("merchandiseStock", stock);
        merchandiseJson.put("category", MerchandiseCategory.NON_CLASSIFIED_PRODUCT.toString());
        merchandiseJson.put("productImage", "");
        return merchandiseJson;
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
