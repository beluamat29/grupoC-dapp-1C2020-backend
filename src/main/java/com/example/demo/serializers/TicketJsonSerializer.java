package com.example.demo.serializers;

import com.example.demo.model.ticket.Ticket;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class TicketJsonSerializer extends JsonSerializer<Ticket> {
    @Override
    public void serialize(Ticket ticket, JsonGenerator jgen, SerializerProvider serializerProvider) throws IOException {

        jgen.writeStartObject();
        jgen.writeNumberField("id", ticket.id());
        jgen.writeStringField("paymentMethod", ticket.paymentMethod());
        jgen.writeNumberField("totalPrice", ticket.getTotal());
        jgen.writeObjectField("ticketStore", ticket.store());
        serializeProductList(jgen, ticket);
        jgen.writeEndObject();
    }

    private void serializeProductList(JsonGenerator jgen, Ticket ticket) throws IOException {
        jgen.writeFieldName("productList");
        jgen.writeStartArray();
        ticket.getListOfAdquiredProducts().stream().forEach(product -> {
            try {
                jgen.writeObject(product);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        jgen.writeEndArray();
    }
}
