package com.example.demo.builders;

import com.example.demo.model.AcquiredProduct;
import com.example.demo.model.merchandise.Merchandise;

import java.util.Random;

public class AdquiredProductBuilder {

    private Merchandise merchandise;
    private Integer productQuantity;

    public static AdquiredProductBuilder aProduct(){ return new AdquiredProductBuilder(); }

    public static AcquiredProduct acquiredProduct(Merchandise merchandise, Integer quantity) {
        AcquiredProduct acquiredProduct = new AcquiredProduct(merchandise, quantity);
        acquiredProduct.setId(new Random().nextLong());
        return acquiredProduct;
    }

    public AcquiredProduct build() {
        return new AcquiredProduct(merchandise, productQuantity);
    }


    public AdquiredProductBuilder withMerchandise(Merchandise aMerchandise) {
        merchandise = aMerchandise;
        return this;
    }

    public AdquiredProductBuilder withQuantity(Integer quantity) {
        productQuantity = quantity;
        return this;
    }
}
