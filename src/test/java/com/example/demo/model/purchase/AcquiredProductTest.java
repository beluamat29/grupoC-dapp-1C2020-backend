package com.example.demo.model.purchase;

import com.example.demo.builders.AdquiredProductBuilder;
import com.example.demo.builders.MerchandiseBuilder;
import com.example.demo.model.acquiredProduct.AcquiredProduct;
import com.example.demo.model.merchandise.Merchandise;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AcquiredProductTest {

    @Test
    public void anAdquiredProductHasAName(){
        Merchandise merchandise = MerchandiseBuilder.aMerchandise().build();
        AcquiredProduct product = AdquiredProductBuilder.aProduct().withMerchandise(merchandise).build();
        assertEquals("Fideos", product.name());
    }

    @Test
    public void anAdquiredProductHasABrand(){
        Merchandise merchandise = MerchandiseBuilder.aMerchandise().build();
        AcquiredProduct product = AdquiredProductBuilder.aProduct().withMerchandise(merchandise).build();
        assertEquals("Matarazzo", product.brand());
    }

    @Test
    public void anAdquiredProductHasAPrice(){
        Merchandise merchandise = MerchandiseBuilder.aMerchandise().build();
        AcquiredProduct product = AdquiredProductBuilder.aProduct().withMerchandise(merchandise).withQuantity(1).build();
        assertEquals(65.0, product.price());
    }

    @Test
    public void anAdquiredProductHasAQuantity(){
        AcquiredProduct product = AdquiredProductBuilder.aProduct().withQuantity(4).build();
        assertEquals(4, product.quantity());
    }
}
