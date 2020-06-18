package com.example.demo.builders;

import com.example.demo.model.discounts.Discount;
import com.example.demo.model.discounts.NoDiscount;

import java.time.LocalDate;

public class DiscountBuilder {

    private Integer percentOfDiscount = 10;
    private LocalDate startDate = LocalDate.now().minusDays(1);
    private LocalDate endDate = LocalDate.now().plusDays(1);

    public static DiscountBuilder aDiscount() { return new DiscountBuilder(); }


   /* public Discount build() {
        return new MerchandiseDiscount(percentOfDiscount,startDate, endDate);
    }
*/
    public Discount buildNoDiscount() {  return new NoDiscount();  }

    public DiscountBuilder withPercentOfDiscount(Integer aPercent) {
        percentOfDiscount = aPercent;
        return this;
    }

    public DiscountBuilder withStartDate(LocalDate date) {
        startDate = date;
        return this;
    }

    public DiscountBuilder withEndDate(LocalDate date) {
        endDate =date;
        return this;
    }
}
