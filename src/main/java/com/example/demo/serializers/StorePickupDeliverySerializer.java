package com.example.demo.serializers;
import com.example.demo.model.StorePickUp;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class StorePickupDeliverySerializer extends JsonSerializer<StorePickUp> {

    @Override
    public void serialize(StorePickUp storePickUp, JsonGenerator jgen, SerializerProvider serializerProvider) throws IOException {
        jgen.writeStartObject();
        jgen.writeObjectField("pickUpDate", storePickUp.pickUpDate().toString());
        jgen.writeEndObject();

    }
}
