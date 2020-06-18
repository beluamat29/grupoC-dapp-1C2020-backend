package com.example.demo.deserializers;

import com.example.demo.model.StoreSchedule;
import com.example.demo.model.store.Store;
import com.example.demo.model.store.StoreCategory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class StoreJsonDeserializer extends JsonDeserializer<Store> {

    @Override
    public Store deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(jsonParser);

        String storeName = jsonNode.get("storeName").textValue();
        String storeAddress = jsonNode.get("storeAddress").textValue();
        Integer distanceInKm = jsonNode.get("deliveryDistanceInKm").asInt();

        List<String> categoriesList = objectMapper.readValue(String.valueOf(jsonNode.get("storeCategories")), new TypeReference<List<String>>(){});
        List<StoreCategory> categories =  categoriesList.stream().map(StoreCategory::valueOf).collect(Collectors.toList());

        List<String> paymentMethods = objectMapper.readValue(String.valueOf(jsonNode.get("availablePaymentMethods")), new TypeReference<List<String>>(){});

        StoreSchedule storeSchedule = objectMapper.treeToValue(jsonNode.get("storeSchedule"), StoreSchedule.class);

        String imageUrl = jsonNode.get("storeImageURL").textValue();;
        return new Store(storeName, categories, storeAddress, distanceInKm, paymentMethods, storeSchedule, LocalDate.now(), imageUrl);
    };
}
