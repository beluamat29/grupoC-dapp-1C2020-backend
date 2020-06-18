package com.example.demo.model.thresholds;

import com.example.demo.model.merchandise.MerchandiseCategory;
import com.example.demo.model.Bill;

public class CategoryMoneyThreshold extends MoneyThreshold {

    private MerchandiseCategory category;

    public CategoryMoneyThreshold(Double moneyLimit, MerchandiseCategory aCategory) {
        super(moneyLimit);
        category = aCategory;
    }

    public MerchandiseCategory category() {
        return this.category;
    }

    @Override
    public Boolean breaksTheLimitWith(Bill bill) {
        return this.isActive()
                && bill.getTickets().stream().mapToDouble(ticket -> this.purchasePriceCalculator().calculatePriceForCategory(ticket.purchase(), this.category())).sum()> this.moneyLimit();
    }
}
