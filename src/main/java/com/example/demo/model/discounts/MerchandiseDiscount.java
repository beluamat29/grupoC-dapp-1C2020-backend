package com.example.demo.model.discounts;

import com.example.demo.model.merchandise.Merchandise;
import com.example.demo.serializers.DiscountJsonSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.time.LocalDate;
import java.util.function.BooleanSupplier;

@JsonSerialize(using = DiscountJsonSerializer.class)
public class MerchandiseDiscount extends StoreDiscount {

    private Merchandise merchandise;

    public MerchandiseDiscount(Merchandise aMerchandise, Integer aPercent, LocalDate startDate, LocalDate endDate){
        super(aPercent, startDate, endDate);
        this.merchandise = aMerchandise;
    }

    public Boolean canApplyDiscountFor(Merchandise aMerchandise) {
        return this.isAvailableIn(LocalDate.now()) && this.merchandise.name().equals(aMerchandise.name()) && this.merchandise.brand().equals(aMerchandise.brand());
    }
}
