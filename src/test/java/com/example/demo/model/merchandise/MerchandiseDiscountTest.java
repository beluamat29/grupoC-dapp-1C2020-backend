package com.example.demo.model.merchandise;


import com.example.demo.builders.MerchandiseBuilder;
import com.example.demo.builders.MerchandiseDiscountBuilder;
import com.example.demo.model.discounts.MerchandiseDiscount;
import com.example.demo.model.merchandise.Merchandise;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MerchandiseDiscountTest {

    @Test
    public void anExpiredDiscountHasAPercentOfDiscountZero(){
        MerchandiseDiscount discount = MerchandiseDiscountBuilder.aDiscount().expired().build();
        assertEquals(0, discount.percentOfDiscount());
    }

    @Test
    public void aNonExpiredDiscountHasAPercentOfDiscount(){
        MerchandiseDiscount discount = MerchandiseDiscountBuilder.aDiscount().build();
        assertEquals(10, discount.percentOfDiscount());
    }

    @Test
    public void aMerchandiseDiscountCanBeAppliedToTheSameMerchandise(){
        Merchandise merchandise = MerchandiseBuilder.aMerchandise().build();
        MerchandiseDiscount discount = MerchandiseDiscountBuilder.aDiscount().withMerchandise(merchandise).build();
        assertTrue(discount.canApplyDiscountFor(merchandise));
    }

    @Test
    public void aMerchandiseDiscountCanNotBeAppliedToTheDiffrentMerchandise(){
        Merchandise merchandise = MerchandiseBuilder.aMerchandise().build();
        Merchandise otherMerchandise = MerchandiseBuilder.aMerchandise().withName("Lasagna").build();
        MerchandiseDiscount discount = MerchandiseDiscountBuilder.aDiscount().withMerchandise(merchandise).build();
        assertFalse(discount.canApplyDiscountFor(otherMerchandise));
    }
}
