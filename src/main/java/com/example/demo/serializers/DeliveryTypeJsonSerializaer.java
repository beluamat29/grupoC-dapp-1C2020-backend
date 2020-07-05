package com.example.demo.serializers;

import com.example.demo.model.DeliveryType;
import com.example.demo.model.HomeDelivery;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class DeliveryTypeJsonSerializaer extends JsonSerializer<DeliveryType> {

    ObjectMapper mapper = new ObjectMapper();

    @Override
    public void serialize(DeliveryType deliveryType, JsonGenerator jgen, SerializerProvider serializerProvider) throws IOException {

        jgen.writeStartObject();
        jgen.writeStringField("deliveryAddress", deliveryType.deliveryAddress());
        jgen.writeObjectField("pickUpDate", deliveryType.pickUpDate());
        jgen.writeEndObject();


    }
}
