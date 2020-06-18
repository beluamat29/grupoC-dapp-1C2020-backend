package com.example.demo.serializers;

import com.example.demo.model.discounts.MerchandiseDiscount;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class DiscountJsonSerializer extends JsonSerializer<MerchandiseDiscount> {


    ObjectMapper mapper = new ObjectMapper();

    @Override
    public void serialize(MerchandiseDiscount merchandiseDiscount, JsonGenerator jgen, SerializerProvider provider)
            throws IOException, JsonProcessingException {

        jgen.writeStartObject();
        jgen.writeNumberField("percentOfDiscount", merchandiseDiscount.percentOfDiscount());
        jgen.writeEndObject();
    }
}
