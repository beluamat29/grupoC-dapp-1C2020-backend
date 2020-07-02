package com.example.demo.model;

import com.example.demo.model.merchandise.Merchandise;

import javax.persistence.*;

@Entity
public class AcquiredProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Transient
    private Merchandise merchandise;
    private Integer productQuantity;

    public AcquiredProduct(){};

    public AcquiredProduct(Merchandise aMerchandise, Integer quantity){
        this.merchandise = aMerchandise;
        this.productQuantity = quantity;
    }

    public Double price() {  return this.merchandise.price();  }

    public String name() { return this.merchandise.name();  }

    public String brand() { return this.merchandise.brand(); }

    public Integer quantity() {  return this.productQuantity;  }

    public Double totalPrice() { return productQuantity * merchandise.price();  }
}
