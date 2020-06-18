package com.example.demo.model.ticket;

import com.example.demo.model.PurchaseFromStore;
import com.example.demo.model.purchasePriceCalculator.PurchasePriceCalculator;


public class Ticket {

    private PurchaseFromStore ticketPurchase;
    private String paymentMethod;
    private Double totalPrice;

    public Ticket(PurchaseFromStore purchase, String aPaymentMethod) {
        ticketPurchase = purchase;
        paymentMethod = aPaymentMethod;
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
