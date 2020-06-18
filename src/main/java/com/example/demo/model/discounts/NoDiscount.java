package com.example.demo.model.discounts;

import com.example.demo.model.discounts.Discount;
import com.example.demo.model.merchandise.Merchandise;

import java.time.LocalDate;

public class NoDiscount implements Discount {
    @Override
    public Boolean hasADiscount() {
        return false;
    }

    @Override
    public Integer percentOfDiscount() {
        return 0;
    }

    @Override
    public Boolean isAvailableIn(LocalDate date) {
        return false;
    }

    @Override
    public Boolean canApplyDiscountFor(Merchandise merchandise) {
        return false;
    }
}
