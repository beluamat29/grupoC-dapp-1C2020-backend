package com.example.demo.model.ticket;

import com.example.demo.model.AcquiredProduct;
import com.example.demo.model.purchasePriceCalculator.PurchasePriceCalculator;
import com.example.demo.model.store.Store;
import com.example.demo.serializers.TicketJsonSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@JsonSerialize(using = TicketJsonSerializer.class)
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String paymentMethod;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<AcquiredProduct> productList = new ArrayList<>();
    private Double totalPrice;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Store ticketStore;

    public Ticket(String aPaymentMethod, Store aTicketStore) {
        paymentMethod = aPaymentMethod;
        ticketStore = aTicketStore;
    }

    public Ticket(String aPaymentMethod, Store aTicketStore, List<AcquiredProduct> aProductList) {
        paymentMethod = aPaymentMethod;
        ticketStore = aTicketStore;
        productList = aProductList;
    }

    public Ticket(){};


    public String paymentMethod() {
        return this.paymentMethod;
    }

    public Double getTotal() {
        return totalPrice = new PurchasePriceCalculator().calculatePriceFor(this.getListOfAdquiredProducts());
    }

    public Store store() { return this.ticketStore; }

    public Integer productsQuantity() { return this.productList.stream().mapToInt(AcquiredProduct::quantity).sum();  }

    public List<AcquiredProduct> getListOfAdquiredProducts() {
        return this.productList;
    }

    public void addProduct(String productName, String productBrand, Integer quantity) {
        this.productList.add(this.store().getProduct(productName, productBrand, quantity));
    }

    public Long id(){ return this.id;}

    public void setId(long id) {
        this.id = id;
    }
}
