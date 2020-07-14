package com.example.demo.model.user;

import com.example.demo.model.exceptions.InvalidFieldForClass;
import com.example.demo.model.store.Store;
import com.example.demo.serializers.StoreAdminUserJsonSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

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
    public Double getMoneyLimit() {
        throw new InvalidFieldForClass("a store does not have a money limit");
    }

    @Override
    public void setMoneyLimit(Double moneyLimit) {
        throw new InvalidFieldForClass("a store does not have a money limit");
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
