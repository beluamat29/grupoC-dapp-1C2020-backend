package com.example.demo.model;

import com.example.demo.model.merchandise.Merchandise;
import com.example.demo.model.merchandise.MerchandiseCategory;
import com.example.demo.serializers.AcquiredProductJsonSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.persistence.*;

@Entity
@JsonSerialize(using = AcquiredProductJsonSerializer.class)
public class AcquiredProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    private Merchandise merchandise;
    private Integer productQuantity;

    public AcquiredProduct(){};

    public AcquiredProduct(Merchandise aMerchandise, Integer quantity){
        this.merchandise = aMerchandise;
        this.productQuantity = quantity;
    }

    public String name() { return this.merchandise.name();  }

    public String brand() { return this.merchandise.brand(); }

    public Integer quantity() {  return this.productQuantity;  }

    public Double price() { return productQuantity * merchandise.price();  }

    public Integer stock() {
        return this.merchandise.stock();
    }

    public MerchandiseCategory category() {
        return this.category();
    }

    public Boolean isActiveProduct() {
        return this.merchandise.isActive();
    }

    public String imageURL() {
        return this.merchandise.imageURL();
    }

    public Merchandise getMerchandise(){ return this.merchandise; }

    public void setId(Long anId) {
        this.id = anId;
    }
}
