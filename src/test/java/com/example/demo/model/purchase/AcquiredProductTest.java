package com.example.demo.model.purchase;

import com.example.demo.builders.AdquiredProductBuilder;
import com.example.demo.model.AcquiredProduct;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AcquiredProductTest {

    @Test
    public void anAdquiredProductHasAName(){
        AcquiredProduct product = AdquiredProductBuilder.aProduct().withName("Fideos").build();
        assertEquals("Fideos", product.name());
    }

    @Test
    public void anAdquiredProductHasABrand(){
        AcquiredProduct product = AdquiredProductBuilder.aProduct().withBrand("Knorr").build();
        assertEquals("Knorr", product.brand());
    }

    @Test
    public void anAdquiredProductHasAPrice(){
        AcquiredProduct product = AdquiredProductBuilder.aProduct().withPrice(40.2).build();
        assertEquals(40.2, product.price());
    }

    @Test
    public void anAdquiredProductHasAQuantity(){
        AcquiredProduct product = AdquiredProductBuilder.aProduct().withQuantity(4).build();
        assertEquals(4, product.quantity());
    }
}
