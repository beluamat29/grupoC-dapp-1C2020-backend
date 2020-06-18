package com.example.demo.model.purchasePriceCalculator;

import com.example.demo.model.AcquiredProduct;
import com.example.demo.model.PurchaseFromStore;
import com.example.demo.model.store.Store;
import com.example.demo.model.merchandise.MerchandiseCategory;

import java.util.List;
import java.util.stream.Collectors;

public class PurchasePriceCalculator {

    public Double calculatePriceFor(PurchaseFromStore purchase) {
        return purchase.getListOfAdquiredProducts().stream().mapToDouble(AcquiredProduct::totalPrice).sum();
    }

    public Double calculatePriceForCategory(PurchaseFromStore purchase, MerchandiseCategory category) {
        Store purchaseStore = purchase.store();
        List<AcquiredProduct> purchaseProductsWithCategory = purchase.getListOfAdquiredProducts().stream().filter(acquiredProduct -> purchaseStore.isProductFromCategory(acquiredProduct, category)).collect(Collectors.toList());
        return purchaseProductsWithCategory.stream().mapToDouble(AcquiredProduct::totalPrice).sum();
    }
}
