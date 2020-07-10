package com.example.demo.serializers;

import com.example.demo.model.AcquiredProduct;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class AcquiredProductJsonSerializer extends JsonSerializer<AcquiredProduct> {

    @Override
    public void serialize(AcquiredProduct acquiredProduct, JsonGenerator jgen, SerializerProvider serializerProvider) throws IOException {
        jgen.writeStartObject();
        jgen.writeObjectField("merchandise", acquiredProduct.getMerchandise());
        jgen.writeNumberField("productQuantity", acquiredProduct.quantity());
        jgen.writeEndObject();
    }
}
