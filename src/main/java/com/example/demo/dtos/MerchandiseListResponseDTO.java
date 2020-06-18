package com.example.demo.dtos;

import com.example.demo.model.merchandise.Merchandise;
import com.example.demo.model.store.Store;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class MerchandiseListResponseDTO {

    @JsonProperty
    private Store store;
    @JsonProperty
    private List<Merchandise> merchandises;

    public MerchandiseListResponseDTO(@JsonProperty("merchandises")List<Merchandise> merchandises, @JsonProperty("store") Store store) {
        this.merchandises = merchandises;
        this.store = store;
    }

    public MerchandiseListResponseDTO(){};

    public Store getStore() {
        return this.store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public List<Merchandise> getMerchandises() {
        return merchandises;
    }

    public void setMerchandises(List<Merchandise> merchandises) {
        this.merchandises = merchandises;
    }
}
