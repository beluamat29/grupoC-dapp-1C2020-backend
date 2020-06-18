package com.example.demo.builders;

import com.example.demo.model.AcquiredProduct;

public class AdquiredProductBuilder {

    private String productName;
    private String productBrand;
    private Double productPrice;
    private Integer productQuantity;

    public static AdquiredProductBuilder aProduct(){ return new AdquiredProductBuilder(); }

    public AcquiredProduct build() {
        return new AcquiredProduct(productName, productBrand, productPrice, productQuantity);
    }


    public AdquiredProductBuilder withName(String name) {
        productName =name;
        return this;
    }

    public AdquiredProductBuilder withBrand(String brand) {
        productBrand = brand;
        return this;
    }

    public AdquiredProductBuilder withPrice(Double price) {
        productPrice = price;
        return this;
    }

    public AdquiredProductBuilder withQuantity(Integer quantity) {
        productQuantity = quantity;
        return this;
    }
}
