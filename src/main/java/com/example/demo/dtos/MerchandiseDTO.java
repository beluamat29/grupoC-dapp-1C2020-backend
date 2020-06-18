package com.example.demo.dtos;

import com.example.demo.model.merchandise.MerchandiseCategory;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class MerchandiseDTO {

    @JsonProperty
    private Long storeId;
    @JsonProperty
    private String merchandiseName;
    @JsonProperty
    private String merchandiseBrand;
    @JsonProperty
    private Double merchandisePrice;
    @JsonProperty
    private Integer merchandiseStock;
    @JsonProperty
    private MerchandiseCategory category;
    @JsonProperty
    private String imageURL;

    @JsonCreator
    public MerchandiseDTO(@JsonProperty("storeId") Long storeId, @JsonProperty("name")String name, @JsonProperty("brand")String brand,
                          @JsonProperty("price") Double price, @JsonProperty("stock") Integer stock,
                          @JsonProperty("category") MerchandiseCategory category, @JsonProperty("productImage") String imageURL){
        this.storeId = storeId;
        this.merchandiseName = name;
        this.merchandiseBrand = brand;
        this.merchandisePrice = price;
        this.merchandiseStock = stock;
        this.category = category;
        this.imageURL = imageURL;
    }

    public MerchandiseDTO(){};

    public Long getStoreId(){ return this.storeId;}
    public String getMerchandiseName(){ return this.merchandiseName;}
    public String getMerchandiseBrand(){ return this.merchandiseBrand;}
    public Double getMerchandisePrice(){ return this.merchandisePrice;}
    public Integer getMerchandiseStock(){ return this.merchandiseStock;}
    public MerchandiseCategory getCategory(){ return this.category;}
    public String getImageURL(){ return this.imageURL;}

    public void setStoreId(Long id){ this.storeId = id; }
    public void setMerchandiseName(String name){ this.merchandiseName = name; }
    public void setMerchandiseBrand(String brand){ this.merchandiseBrand = brand; }
    public void setMerchandisePrice(Double price){ this.merchandisePrice = price; }
    public void setMerchandiseStock(Integer stock){ this.merchandiseStock = stock; }
    public void setCategory(MerchandiseCategory aCategory){ this.category = aCategory;}
    public void setImageURL(String url){ this.imageURL = url;}
}
