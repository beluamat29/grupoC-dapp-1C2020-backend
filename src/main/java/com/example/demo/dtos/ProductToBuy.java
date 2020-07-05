package com.example.demo.dtos;

import com.example.demo.model.AcquiredProduct;

public class ProductToBuy {

    private Long storeId;
    private AcquiredProduct productToBuy;


    public ProductToBuy(Long storeId, AcquiredProduct product) {
        this.storeId = storeId;
        this.productToBuy = product;
    }

    public Long getStoreId(){ return this.storeId;}
    public AcquiredProduct getProductToBuy(){ return this.productToBuy;}

}
