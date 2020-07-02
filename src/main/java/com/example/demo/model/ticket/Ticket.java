package com.example.demo.model.ticket;

import com.example.demo.model.AcquiredProduct;
import com.example.demo.model.purchasePriceCalculator.PurchasePriceCalculator;
import com.example.demo.model.store.Store;

import java.util.ArrayList;
import java.util.List;


public class Ticket {

    private String paymentMethod;
    private Double totalPrice;
    private List<AcquiredProduct> productList = new ArrayList<>();
    private Store tickeStore;

    public Ticket(String aPaymentMethod, Store aTickeStore) {
        paymentMethod = aPaymentMethod;
        tickeStore = aTickeStore;
    }


    public String paymentMethod() {
        return this.paymentMethod;
    }

    public Double getTotal() {
        totalPrice = new PurchasePriceCalculator().calculatePriceFor(productList);
        return totalPrice;
    }

    public Store store() { return this.tickeStore; }

    public Integer productsQuantity() { return this.productList.stream().mapToInt(AcquiredProduct::quantity).sum();  }

    public List<AcquiredProduct> getListOfAdquiredProducts() {
        return this.productList;
    }

    public void addProduct(String productName, String productBrand, Integer quantity) {
        this.productList.add(this.store().getProduct(productName, productBrand, quantity));
    }
}
