package com.example.demo.builders;

import com.example.demo.model.discounts.MerchandiseDiscount;
import com.example.demo.model.merchandise.Merchandise;
import com.example.demo.model.merchandise.MerchandiseCategory;

import java.time.LocalDate;

public class MerchandiseDiscountBuilder {


    private Integer percentOfDiscount = 10;
    private LocalDate startDate = LocalDate.now().minusDays(1);
    private LocalDate endDate = LocalDate.now().plusDays(1);
    private Merchandise merchandise = new Merchandise("Fideos", "Matarazzo", 40.0, 100, MerchandiseCategory.GROCERY, "https://walmartar.vteximg.com.br/arquivos/ids/848517-1000-1000/Fideos-Tallarin-Matarazzo-500gr-1-13350.jpg?v=636994053716730000");

    public static MerchandiseDiscountBuilder aDiscount() { return new MerchandiseDiscountBuilder(); }


    public MerchandiseDiscount build() {
        return new MerchandiseDiscount(merchandise, percentOfDiscount,startDate, endDate);
    }

    public MerchandiseDiscountBuilder expired() {
        startDate = LocalDate.now().minusDays(10);
        endDate = LocalDate.now().minusDays(5);
        return this;
    }

    public MerchandiseDiscountBuilder withMerchandise(Merchandise aMerchandise) {
        merchandise = aMerchandise;
        return this;
    }
}
