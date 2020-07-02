package com.example.demo.model.purchasePriceCalculator;

import com.example.demo.model.AcquiredProduct;
import com.example.demo.model.store.Store;
import com.example.demo.model.merchandise.MerchandiseCategory;
import com.example.demo.model.ticket.Ticket;

import java.util.List;
import java.util.stream.Collectors;

public class PurchasePriceCalculator {

    public Double calculatePriceFor(List<AcquiredProduct> products) {
        return products.stream().mapToDouble(AcquiredProduct::price).sum();
    }

    public Double calculatePriceForCategory(Ticket ticket, MerchandiseCategory category) {
        Store ticketStore = ticket.store();
        List<AcquiredProduct> purchaseProductsWithCategory = ticket.getListOfAdquiredProducts().stream().filter(acquiredProduct -> ticketStore.isProductFromCategory(acquiredProduct, category)).collect(Collectors.toList());
        return purchaseProductsWithCategory.stream().mapToDouble(AcquiredProduct::price).sum();
    }
}
