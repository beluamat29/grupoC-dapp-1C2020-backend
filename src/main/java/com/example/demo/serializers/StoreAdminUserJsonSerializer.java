package com.example.demo.serializers;

import com.example.demo.model.user.StoreAdminUser;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class StoreAdminUserJsonSerializer extends JsonSerializer<StoreAdminUser> {

    @Override
    public void serialize(StoreAdminUser storeAdminUser, JsonGenerator jgen, SerializerProvider serializerProvider) throws IOException {
        jgen.writeStartObject();
        jgen.writeNumberField("id", storeAdminUser.id());
        jgen.writeStringField("username", storeAdminUser.username());
        jgen.writeStringField("password", storeAdminUser.password());
        jgen.writeObjectField("store", storeAdminUser.store());
        jgen.writeEndObject();
    }
}
