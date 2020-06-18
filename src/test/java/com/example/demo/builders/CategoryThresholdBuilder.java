package com.example.demo.builders;

import com.example.demo.model.thresholds.CategoryMoneyThreshold;
import com.example.demo.model.merchandise.MerchandiseCategory;

public class CategoryThresholdBuilder {

    private Double moneyLimit = 1000.0;
    private MerchandiseCategory category = MerchandiseCategory.CEREALS;

    public static CategoryThresholdBuilder aCategoryThreshold() {
        return new CategoryThresholdBuilder();
    }

    public CategoryThresholdBuilder withMoneyLimit(Double aLimit) {
        moneyLimit = aLimit;
        return this;
    }

    public CategoryThresholdBuilder withCategory (MerchandiseCategory aCategory) {
        category = aCategory;
        return this;
    }

    public CategoryMoneyThreshold build() {
        return new CategoryMoneyThreshold(moneyLimit, category);
    }

    public CategoryMoneyThreshold whichIsDisabled() {
        CategoryMoneyThreshold categoryMoneyThreshold = new CategoryMoneyThreshold(moneyLimit, category);
        categoryMoneyThreshold.disable();
        return categoryMoneyThreshold;
    }
}
