package com.example.demo.builders;

import com.example.demo.model.discounts.Discount;
import com.example.demo.model.discounts.NoDiscount;
import com.example.demo.model.merchandise.Merchandise;
import com.example.demo.model.merchandise.MerchandiseCategory;

import java.util.Random;

public class MerchandiseBuilder {

    private String merchandiseName = "Fideos";
    private String merchandiseBrand = "Matarazzo";
    private Integer merchandiseStock = 9;
    private Double merchandisePrice = 65.0;
    private String imageUrl = "https://mercanet.com.ar/server/Portal_0019782/img/products/fideo-matarazzo-mono-500-grs_5371692_xxl.jpg";
    private Discount discount = new NoDiscount();
    private MerchandiseCategory category = MerchandiseCategory.NON_CLASSIFIED_PRODUCT;

    public static MerchandiseBuilder aMerchandise() {
        return new MerchandiseBuilder();
    }

    public Merchandise build() {
        return new Merchandise(merchandiseName, merchandiseBrand, merchandisePrice, merchandiseStock, category, imageUrl);
    }

    public MerchandiseBuilder withName(String aName) {
        merchandiseName = aName;
        return this;
    }

    public MerchandiseBuilder withBrand(String aBrand) {
        merchandiseBrand = aBrand;
        return this;
    }

    public MerchandiseBuilder withStock(Integer aStock) {
        merchandiseStock = aStock;
        return this;
    }

    public MerchandiseBuilder withPrice(Double aPrice) {
        merchandisePrice = aPrice;
        return this;
    }

    public MerchandiseBuilder withDiscount(Discount aDiscount) {
        discount = aDiscount;
        return this;
    }

    public MerchandiseBuilder withCategory(MerchandiseCategory aCategory) {
        category = aCategory;
        return this;
    }

    public MerchandiseBuilder withImage(String url) {
        imageUrl = url;
        return this;
    }

    public Merchandise buildWithId() {
        Merchandise merchandise = this.build();
        merchandise.setId(new Random().nextLong());
        return merchandise;
    }
}
