package com.example.demo.serializers;

import com.example.demo.model.Bill;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class BillJsonSerializer extends JsonSerializer<Bill> {
    @Override
    public void serialize(Bill bill, JsonGenerator jgen, SerializerProvider serializerProvider) throws IOException {

        jgen.writeStartObject();
        jgen.writeNumberField("id", bill.id());
        serializerTickets(jgen, bill);
        jgen.writeObjectField("deliveryType", bill.getDeliveryType());
        jgen.writeEndObject();
    }

    private void serializerTickets(JsonGenerator jgen, Bill bill) throws IOException {
        jgen.writeFieldName("allTickets");
        jgen.writeStartArray();
        bill.getTickets().stream().forEach(ticket -> {
            try {
                jgen.writeObject(ticket);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        jgen.writeEndArray();
    }
}
