package com.example.demo.model.merchandise;

import com.example.demo.model.discounts.NoDiscount;
import com.example.demo.model.discounts.Discount;
import com.example.demo.model.exceptions.InvalidStockTypeException;
import com.example.demo.model.exceptions.NegativePriceMerchandiseException;
import com.example.demo.model.exceptions.InsufficientMerchandiseStockException;
import com.example.demo.model.exceptions.NegativeStockMerchandiseException;
import com.example.demo.model.store.Store;
import com.example.demo.serializers.MerchandiseJsonSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.persistence.*;

@Entity
@JsonSerialize(using = MerchandiseJsonSerializer.class)
public class Merchandise {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String merchandiseName;
    private String merchandiseBrand;
    private Double merchandisePrice;
    private Integer merchandiseStock;
    private String imageURL;
    private MerchandiseCategory category;
    @Transient
    private Discount discountToApply = new NoDiscount();
    /*@ManyToOne
    private Store store;*/

    public Merchandise(String aName, String aBrand, Double aPrice, Integer aStock, MerchandiseCategory aCategory, String url) {
        if(aStock < 0) { throw new NegativeStockMerchandiseException();}
        if(aPrice < 0) { throw new NegativePriceMerchandiseException();}
        merchandiseName = aName;
        merchandiseBrand = aBrand;
        merchandisePrice = aPrice;
        merchandiseStock = aStock;
        category = aCategory;
        imageURL = url;
    }

    public Merchandise(){};

    /*public void setStore(Store store) {
        this.store = store;
    }*/
    public String name() {
        return this.merchandiseName;
    }

    public String brand() {
        return this.merchandiseBrand;
    }

    public Integer stock() {
        return this.merchandiseStock;
    }

    public void decreaseStock(Integer quantityToDecrease) {
        if(merchandiseStock - quantityToDecrease < 0) { throw new InsufficientMerchandiseStockException();}
        merchandiseStock -= quantityToDecrease;
    }

    public void updatePrice(Double newPrice) {
        if(newPrice < 0) { throw new NegativePriceMerchandiseException();}
        merchandisePrice = newPrice;
    }

    public Double price() {
        return this.merchandisePrice - (this.merchandisePrice * this.percentOfDiscount() / 100);
    }

    public void addStock(Integer stockToAdd) {
        if(stockToAdd < 0) { throw new InvalidStockTypeException();}
        merchandiseStock += stockToAdd;
    }

    public Integer percentOfDiscount() {
        return this.discountToApply.percentOfDiscount();
    }

    public MerchandiseCategory getCategory() {
        return this.category;
    }

    public String imageURL() {
        return this.imageURL;
    }

    public Long id() {
        return this.id;
    }

    public Long setId(Long merchandiseID) { return this.id = merchandiseID; }
}
