package com.example.demo.serializers;

import com.example.demo.model.StoreSchedule;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class StoreScheduleSerializer extends JsonSerializer<StoreSchedule> {

    @Override
    public void serialize(StoreSchedule storeSchedule, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        serializeScheduleDays(jsonGenerator, storeSchedule);
        jsonGenerator.writeStringField("openingTime", storeSchedule.openingTime().toString());
        jsonGenerator.writeStringField("closingTime", storeSchedule.closingTime().toString());
        jsonGenerator.writeEndObject();
    }

    private void serializeScheduleDays(JsonGenerator jsonGenerator, StoreSchedule storeSchedule) throws IOException {
        jsonGenerator.writeFieldName("openingDays");
        jsonGenerator.writeStartArray();
        storeSchedule.days().stream().forEach(day -> {
            try {
                jsonGenerator.writeString(day.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        jsonGenerator.writeEndArray();
    }
}
