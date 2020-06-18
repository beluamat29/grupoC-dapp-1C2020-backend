package com.example.demo.builders;

import com.example.demo.model.user.StoreAdminUser;
import com.example.demo.model.store.Store;

import java.util.Random;

public class StoreAdminBuilder {

    private String username = "StoreAdmin@gmail.com";
    private String password = "StoreAdminPassword";
    private Store store = StoreBuilder.aStore().withName("Carrefour").build();

    public static StoreAdminBuilder aStoreAdmin() {
        return new StoreAdminBuilder();
    }

    public StoreAdminUser build() {
        return new StoreAdminUser(username, password, store);
    }

    public StoreAdminBuilder withUsername(String aUsername) {
        username = aUsername;
        return this;
    }

    public StoreAdminBuilder withPassword(String aPassword) {
        password = aPassword;
        return this;
    }

    public StoreAdminUser withEmptyUsername() {
        StoreAdminUser storeAdminUser = this.build();
        storeAdminUser.setUsername("");
        return storeAdminUser;
    }

    public StoreAdminUser withEmptyPassword() {
        StoreAdminUser storeAdminUser = this.build();
        storeAdminUser.setPassword("");
        return storeAdminUser;
    }
}
