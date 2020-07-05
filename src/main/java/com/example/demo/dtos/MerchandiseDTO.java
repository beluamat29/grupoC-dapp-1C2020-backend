package com.example.demo.dtos;

import com.example.demo.model.AcquiredProduct;
import com.example.demo.model.merchandise.Merchandise;
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
    @JsonProperty
    private Boolean isActiveMerchandise;
    @JsonProperty
    private Integer quantity;

    @JsonCreator
    public MerchandiseDTO(@JsonProperty("storeId") Long storeId, @JsonProperty("name")String name, @JsonProperty("brand")String brand,
                          @JsonProperty("price") Double price, @JsonProperty("stock") Integer stock, @JsonProperty("isActiveMerchandise")Boolean isActiveMerchandise,
                          @JsonProperty("category") MerchandiseCategory category, @JsonProperty("productImageURL") String imageURL, @JsonProperty(value ="quantity", required = false) Integer quantity){
        this.storeId = storeId;
        this.merchandiseName = name;
        this.merchandiseBrand = brand;
        this.merchandisePrice = price;
        this.merchandiseStock = stock;
        this.category = category;
        this.imageURL = imageURL;
        this.isActiveMerchandise = isActiveMerchandise;
        this.quantity = quantity;
    }

    public MerchandiseDTO(){};

    public Long getStoreId(){ return this.storeId;}
    public String getMerchandiseName(){ return this.merchandiseName;}
    public String getMerchandiseBrand(){ return this.merchandiseBrand;}
    public Double getMerchandisePrice(){ return this.merchandisePrice;}
    public Integer getMerchandiseStock(){ return this.merchandiseStock;}
    public MerchandiseCategory getCategory(){ return this.category;}
    public String getImageURL(){ return this.imageURL;}
    public Boolean getIsActiveMerchandise() {return this.isActiveMerchandise;}
    public Integer getQuantity(){ return this.quantity;}

    public void setStoreId(Long id){ this.storeId = id; }
    public void setName(String name){ this.merchandiseName = name; }
    public void setBrand(String brand){ this.merchandiseBrand = brand; }
    public void setPrice(Double price){ this.merchandisePrice = price; }
    public void setStock(Integer stock){ this.merchandiseStock = stock; }
    public void setCategory(MerchandiseCategory aCategory){ this.category = aCategory;}
    public void setImageURL(String url){ this.imageURL = url;}
    public void setIsActiveMerchandise(Boolean activeMerchandise) {
        isActiveMerchandise = activeMerchandise;
    }
    public void setQuantity(Integer quantity){ this.quantity = quantity;}


    public Merchandise buildMerchandise() {
        Merchandise merchandise = new Merchandise(this.merchandiseName, this.merchandiseBrand, this.merchandisePrice, this.merchandiseStock, this.category, this.imageURL);
        return merchandise;

    }

    public ProductToBuy buildProductToBuy() {
        AcquiredProduct acquiredProduct = new AcquiredProduct(buildMerchandise(), this.quantity);
        ProductToBuy product = new ProductToBuy(storeId, acquiredProduct);
        return product;
    }
}
