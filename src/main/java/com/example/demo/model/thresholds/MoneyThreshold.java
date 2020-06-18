package com.example.demo.model.thresholds;

import com.example.demo.model.Bill;
import com.example.demo.model.purchasePriceCalculator.PurchasePriceCalculator;

import javax.persistence.*;

@Entity
public class MoneyThreshold {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private Double moneyLimit;
    private Boolean isActive = true;

    @Transient
    private PurchasePriceCalculator purchasePriceCalculator;

    public MoneyThreshold(Double aMoneyLimit) {
        purchasePriceCalculator = new PurchasePriceCalculator();
        moneyLimit = aMoneyLimit;
    }

    public MoneyThreshold(){};

    public Double moneyLimit() {
        return this.moneyLimit;
    }

    public Boolean isActive() {
        return this.isActive;
    }

    public PurchasePriceCalculator purchasePriceCalculator() { return this.purchasePriceCalculator; }

    public void disable() {
        this.isActive = false;
    }

    public void enable() {
        this.isActive = true;
    }

    public Boolean breaksTheLimitWith(Bill bill) {
        return this.isActive() && bill.totalPrice() > this.moneyLimit();
    }

    public void updateMoneyLimit(Double newMoneyLimit) {
        this.moneyLimit = newMoneyLimit;
    }

}
