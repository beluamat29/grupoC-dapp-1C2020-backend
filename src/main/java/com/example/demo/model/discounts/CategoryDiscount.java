package com.example.demo.model.discounts;

import com.example.demo.model.merchandise.Merchandise;
import com.example.demo.model.merchandise.MerchandiseCategory;

import javax.naming.InitialContext;
import java.time.LocalDate;

public class CategoryDiscount extends StoreDiscount{

    private MerchandiseCategory category;

    public CategoryDiscount(MerchandiseCategory aCategory, Integer discount, LocalDate aStartDate, LocalDate anEndDate){
        super(discount, aStartDate, anEndDate);
        category = aCategory;
    }

    public Boolean canApplyDiscountFor(Merchandise merchandise) {
        return this.isAvailableIn(LocalDate.now()) && merchandise.getCategory().equals(category);
    }
}
