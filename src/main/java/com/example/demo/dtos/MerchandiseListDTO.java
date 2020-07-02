package com.example.demo.dtos;

import com.example.demo.model.merchandise.Merchandise;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.stream.Collectors;

public class MerchandiseListDTO {
    @JsonProperty
    private Long storeId;
    @JsonProperty
    private List<Merchandise> merchandiseList;

    @JsonCreator
    public MerchandiseListDTO(@JsonProperty("storeId") Long storeId, @JsonProperty("merchandiseList") List<Merchandise> merchandiseList){
        this.merchandiseList = merchandiseList;
        this.storeId = storeId;
    }

    public MerchandiseListDTO() {}

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public List<Merchandise> getMerchandiseList() {
        return merchandiseList;
    }

    public void setMerchandiseList(List<Merchandise> merchandiseList) {
        this.merchandiseList = merchandiseList;
    }

}
