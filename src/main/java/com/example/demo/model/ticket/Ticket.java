package com.example.demo.model.ticket;

import com.example.demo.model.AcquiredProduct;
import com.example.demo.model.PurchaseFromStore;
import com.example.demo.model.purchasePriceCalculator.PurchasePriceCalculator;
import com.example.demo.model.store.Store;

import java.util.ArrayList;
import java.util.List;


public class Ticket {

    private PurchaseFromStore ticketPurchase;
    private String paymentMethod;
    private Double totalPrice;
    private List<AcquiredProduct> productList = new ArrayList<>();
    private Store tickeStore;

    public Ticket(PurchaseFromStore purchase, String aPaymentMethod/*,Store aTickeStore*/) {
        ticketPurchase = purchase;
        paymentMethod = aPaymentMethod;
        /*tickeStore = aTickeStore;*/
        totalPrice = new PurchasePriceCalculator().calculatePriceFor(ticketPurchase);
    }

    public PurchaseFromStore purchase() {
        return this.ticketPurchase;
    }

    public String paymentMethod() {
        return this.paymentMethod;
    }

    public Double getTotal() {
        return this.totalPrice;
    }
}
