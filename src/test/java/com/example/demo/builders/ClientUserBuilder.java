package com.example.demo.builders;

import com.example.demo.model.store.Store;
import com.example.demo.model.user.StoreAdminUser;
import com.example.demo.model.user.ClientUser;

import java.util.Random;

public class ClientUserBuilder {

    private String username = "Pepe@gmail.com";
    private String password = "123456";
    private String address = "Av. Springfield 111";

    public static ClientUserBuilder user() {
        return new ClientUserBuilder();
    }

    public ClientUser build() {
        return new ClientUser(username, password, address);
    }

    public ClientUserBuilder withUsername(String aUsername) {
        username = aUsername;
        return this;
    }

    public ClientUserBuilder withPassword(String aPassword) {
        password = aPassword;
        return this;
    }

    public ClientUserBuilder withAddress(String anAddress) {
        address = anAddress;
        return this;
    }

    public StoreAdminUser adminOfStore(Store store) {
        return new StoreAdminUser(username, password, store);
    }

    public ClientUser withMoneyThreshold(Double moneyLimit) {
        ClientUser clientUser = new ClientUser(username, password, address);
        clientUser.updateMoneyThreshold(moneyLimit);
        return clientUser;
    }

    public ClientUser withEmptyUsername() {
        ClientUser client = this.build();
        client.setUsername("");
        return client;
    }

    public ClientUser withEmptyPassword() {
        ClientUser client = this.build();
        client.setPassword("");
        return client;
    }

    public ClientUser withEmptyAddress() {
        ClientUser client = this.build();
        client.setAddress("");
        return client;
    }
}
