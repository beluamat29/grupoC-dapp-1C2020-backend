package com.example.demo.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.example.demo.model.user.ClientUser;

import java.io.IOException;

public class UserJsonSerializer extends JsonSerializer<ClientUser> {

    ObjectMapper mapper = new ObjectMapper();

    @Override
    public void serialize(
            ClientUser clientUser, JsonGenerator jgen, SerializerProvider provider)
            throws IOException, JsonProcessingException {

        jgen.writeStartObject();
        jgen.writeNumberField("id", clientUser.id());
        jgen.writeStringField("username", clientUser.username());
        jgen.writeStringField("password", clientUser.password());
        jgen.writeStringField("address", clientUser.address());
        jgen.writeObjectField("isStoreAdmin", clientUser.isAdminOfStore());
        serializeBills(jgen, clientUser);
        jgen.writeEndObject();
    }

    private void serializeBills(JsonGenerator jgen, ClientUser clientUser) throws IOException {
        jgen.writeFieldName("userBills");
        jgen.writeStartArray();
        clientUser.getBills().stream().forEach(bill -> {
            try {
                jgen.writeObject(bill);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        jgen.writeEndArray();
    }
}
