package com.example.demo.model.discount;

import com.example.demo.builders.CategoryDiscountBuilder;
import com.example.demo.builders.MerchandiseBuilder;
import com.example.demo.model.discounts.CategoryDiscount;
import com.example.demo.model.merchandise.Merchandise;
import com.example.demo.model.merchandise.MerchandiseCategory;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CategoryDiscountTest {

    @Test
    public void anExpiredDiscountHasAPercentOfDiscountZero(){
        CategoryDiscount discount = CategoryDiscountBuilder.aDiscount().expired().build();
        assertEquals(0, discount.percentOfDiscount());
    }

    @Test
    public void aNonExpiredDiscountHasAPercentOfDiscount(){
        CategoryDiscount discount = CategoryDiscountBuilder.aDiscount().build();
        assertEquals(10, discount.percentOfDiscount());
    }

    @Test
    public void aCategoryDiscountCanBeAppliedToMerchandiseWithTheSameCategory(){
        CategoryDiscount discount = CategoryDiscountBuilder.aDiscount().withCategory(MerchandiseCategory.DAIRY_PRODUCTS).build();
        Merchandise merchandise = MerchandiseBuilder.aMerchandise().withCategory(MerchandiseCategory.DAIRY_PRODUCTS).build();
        assertTrue(discount.canApplyDiscountFor(merchandise));
    }

    @Test
    public void aCategoryDiscountCanNotBeAppliedToMerchandiseWithDiffrentCategory(){
        CategoryDiscount discount = CategoryDiscountBuilder.aDiscount().withCategory(MerchandiseCategory.DAIRY_PRODUCTS).build();
        Merchandise merchandise = MerchandiseBuilder.aMerchandise().build();
        assertFalse(discount.canApplyDiscountFor(merchandise));
    }
}
