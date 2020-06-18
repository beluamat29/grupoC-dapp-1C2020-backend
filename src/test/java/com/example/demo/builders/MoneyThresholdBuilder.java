package com.example.demo.builders;

import com.example.demo.model.thresholds.MoneyThreshold;

public class MoneyThresholdBuilder {

    private Double moneyLimit = 1000.0;

    public static MoneyThresholdBuilder aMoneyThreshold() {
        return new MoneyThresholdBuilder();
    }

    public MoneyThresholdBuilder withMoneyLimit(Double aLimit) {
        moneyLimit = aLimit;
        return this;
    }

    public MoneyThreshold build() {
        return new MoneyThreshold(moneyLimit);
    }

    public MoneyThreshold whichIsDisabled() {
        MoneyThreshold moneyThreshold = new MoneyThreshold(moneyLimit);
        moneyThreshold.disable();
        return moneyThreshold;
    }
}
