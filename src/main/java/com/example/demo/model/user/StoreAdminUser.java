package com.example.demo.model.user;
import com.example.demo.model.store.Store;
import com.example.demo.serializers.StoreAdminUserJsonSerializer;
import com.example.demo.serializers.UserJsonSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.persistence.*;

@Entity
@DiscriminatorValue("STORE_ADMIN_USER")
@JsonSerialize(using = StoreAdminUserJsonSerializer.class)
public class StoreAdminUser extends User {

    @OneToOne(cascade = CascadeType.ALL)
    private Store store;

    public StoreAdminUser(String username, String password, Store store) {
        super(username, password);
        this.store = store;
    }

    public StoreAdminUser(){};

    public Store store() {
        return this.store;
    }

    @Override
    public void setStore(Store store) {
        this.store = store;
    }

    @Override
    public Boolean isAdminOfStore() { return true;}

    @Override
    public void setAddress(String newAddress) {
        this.store.setAddress(newAddress);
    }

    @Override
    public String address() {
        return this.store.address();
    }
}
