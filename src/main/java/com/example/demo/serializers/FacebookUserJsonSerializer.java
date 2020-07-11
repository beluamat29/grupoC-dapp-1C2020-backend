package com.example.demo.serializers;

import com.example.demo.model.user.FacebookUser;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class FacebookUserJsonSerializer extends JsonSerializer<FacebookUser> {
    @Override
    public void serialize(FacebookUser facebookUser, JsonGenerator jgen, SerializerProvider serializerProvider) throws IOException {

        jgen.writeStartObject();
        jgen.writeNumberField("id", facebookUser.id());
        jgen.writeStringField("mail", facebookUser.getMail());
        jgen.writeEndObject();
    }
}
