package com.example.demo.model.acquiredProduct;

import com.example.demo.model.merchandise.Merchandise;
import com.example.demo.model.merchandise.MerchandiseCategory;

import javax.naming.ldap.LdapName;
import javax.persistence.*;

@Entity
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
}
