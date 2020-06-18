package com.example.demo.model.discounts;

import com.example.demo.model.merchandise.Merchandise;

import java.time.LocalDate;
import java.util.function.BooleanSupplier;

public abstract class StoreDiscount implements Discount {

    private Integer percentOfDiscount;
    private LocalDate startDate;
    private LocalDate endDate;

    public StoreDiscount(Integer aPercent, LocalDate aStartDate, LocalDate anEndDate) {
        this.percentOfDiscount = aPercent;
        this.startDate = aStartDate;
        this.endDate = anEndDate;
    }


    public LocalDate startDate() {
        return this.startDate;
    }

    public LocalDate endDate() {
        return this.endDate;
    }

    public Boolean isAvailableIn(LocalDate date) {
        return (startDate.isBefore(date) || startDate.isEqual(date)) && (endDate.isAfter(date) || endDate.isEqual(date));
    }

    public Integer percentOfDiscount() {
        if(isAvailableIn(LocalDate.now())){
            return this.percentOfDiscount;
        }
        return 0;
    }

    public Boolean hasADiscount() {
        return true;
    }

    public abstract Boolean canApplyDiscountFor(Merchandise merchandise);
}
