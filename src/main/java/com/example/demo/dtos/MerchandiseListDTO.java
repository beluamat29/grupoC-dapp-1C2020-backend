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
    private List<MerchandiseDTO> merchandiseList;

    @JsonCreator
    public MerchandiseListDTO(@JsonProperty("storeId") Long storeId, @JsonProperty("merchandiseList") List<MerchandiseDTO> merchandiseList){
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

    public List<MerchandiseDTO> getMerchandiseList() {
        return merchandiseList;
    }

    public void setMerchandiseList(List<MerchandiseDTO> merchandiseList) {
        this.merchandiseList = merchandiseList;
    }

    public List<Merchandise> getMerchandisesAsEntities() {
        List<Merchandise> merchandisesEntities = this.merchandiseList.stream().map(MerchandiseDTO::buildMerchandise).collect(Collectors.toList());
        return merchandisesEntities;
    }
}
