package com.example.demo.builders;

import com.example.demo.model.discounts.CategoryDiscount;
import com.example.demo.model.merchandise.MerchandiseCategory;

import java.time.LocalDate;

public class CategoryDiscountBuilder {

    private Integer percentOfDiscount = 10;
    private LocalDate startDate = LocalDate.now().minusDays(1);
    private LocalDate endDate = LocalDate.now().plusDays(1);
    private MerchandiseCategory category = MerchandiseCategory.NON_CLASSIFIED_PRODUCT;

    public static CategoryDiscountBuilder aDiscount() { return new CategoryDiscountBuilder(); }


    public CategoryDiscount build() {
        return new CategoryDiscount(category, percentOfDiscount,startDate, endDate);
    }

    public CategoryDiscountBuilder expired() {
        startDate = LocalDate.now().minusDays(10);
        endDate = LocalDate.now().minusDays(5);
        return this;
    }

    public CategoryDiscountBuilder withCategory(MerchandiseCategory aCategory) {
        category = aCategory;
        return this;
    }
}
