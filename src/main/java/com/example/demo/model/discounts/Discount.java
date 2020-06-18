package com.example.demo.model.discounts;

import com.example.demo.model.merchandise.Merchandise;

import java.time.LocalDate;

public interface Discount {

    public Boolean hasADiscount();

    public Integer percentOfDiscount();

    public Boolean isAvailableIn(LocalDate date);

    Boolean canApplyDiscountFor(Merchandise merchandise);

}
