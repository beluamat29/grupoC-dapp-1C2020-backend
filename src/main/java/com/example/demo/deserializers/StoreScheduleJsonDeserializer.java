package com.example.demo.deserializers;

import com.example.demo.model.StoreSchedule;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class StoreScheduleJsonDeserializer extends JsonDeserializer<StoreSchedule>{
    @Override
    public StoreSchedule deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(jsonParser);
        List<DayOfWeek> openingDays = deserializeOpeningDays(objectMapper, jsonNode);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime openingTime = LocalTime.parse(jsonNode.get("openingTime").textValue(), formatter);
        LocalTime closingTime = LocalTime.parse(jsonNode.get("closingTime").textValue(), formatter);

        return new StoreSchedule(openingDays, openingTime, closingTime);
    }

    private List<DayOfWeek> deserializeOpeningDays(ObjectMapper objectMapper, JsonNode jsonNode) throws IOException {
        List<String> days = objectMapper.readValue(String.valueOf(jsonNode.get("openingDays")), new TypeReference<List<String>>(){});
        return days.stream().map(DayOfWeek::valueOf).collect(Collectors.toList());
    }
}
