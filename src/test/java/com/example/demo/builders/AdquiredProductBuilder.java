package com.example.demo.builders;

import com.example.demo.model.AcquiredProduct;
import com.example.demo.model.merchandise.Merchandise;

public class AdquiredProductBuilder {

    private Merchandise merchandise;
    private Integer productQuantity;

    public static AdquiredProductBuilder aProduct(){ return new AdquiredProductBuilder(); }

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
