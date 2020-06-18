package com.example.demo.builders;

import com.example.demo.model.store.Store;
import com.example.demo.model.StoreSchedule;
import com.example.demo.model.merchandise.MerchandiseCategory;
import com.example.demo.model.store.StoreCategory;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class StoreBuilder {
    public static StoreBuilder aStore() {
        return new StoreBuilder();
    }

    public static List<Store> storeList() {
        Store store = aStore().build();
        Store anotherStore = aStore().withName("Coto").withCategory(Arrays.asList(StoreCategory.GROCERY)).build();
        Store store2 = aStore().withName("Lo de Tito").withCategory(Arrays.asList(StoreCategory.CLEANING_SUPPLIES)).build();
        return Arrays.asList(store, anotherStore, store2);
    }
    private String storeName = "Jumbo";
    private List<StoreCategory> storeCategories = Arrays.asList(StoreCategory.GROCERY);
    private String storeAddress = "Calchaqui 123";
    private String mail = "store@gmail.com";
    private Integer deliveryMaxDistanceInKm = 3;
    private List<String> availablePaymentMethods = Arrays.asList("Efectivo");
    private List<DayOfWeek> openingDays = Arrays.asList(DayOfWeek.FRIDAY);
    private StoreSchedule storeTimeSchedule = new StoreSchedule(openingDays, LocalTime.of(9,0), LocalTime.of(15, 0));
    private LocalDate openingDate = LocalDate.now();
    private String imageUrl = "https://k62.kn3.net/taringa/3/F/6/7/B/E/MMLPQTPario/1CB.jpg";

    public static List<Store> storeWithACategoryList(StoreCategory category) {
        return storeList().stream().filter(store -> store.storeCategories().contains(category)).collect(Collectors.toList());
    }

    public Store build() {
        Store store = new Store(storeName, storeCategories, storeAddress,
                                deliveryMaxDistanceInKm, availablePaymentMethods, storeTimeSchedule, openingDate, imageUrl);
        store.setId(new Random().nextLong());
        return store;
    }

    public StoreBuilder withName(String unNombre) {
        storeName = unNombre;
        return this;
    }

    public StoreBuilder withAddress(String unaDireccion) {
        storeAddress = unaDireccion;
        return this;
    }

    public StoreBuilder withDeliveryMaxDistance(Integer distanciaEnKm) {
        deliveryMaxDistanceInKm = distanciaEnKm;
        return this;
    }

    public StoreBuilder withCategory(List<StoreCategory> categories) {
        storeCategories = categories;
        return this;
    }

    public StoreBuilder withPaymentMethods(List<String> paymentMethods){
        availablePaymentMethods = paymentMethods;
        return this;
    }

    public StoreBuilder withStoreSchedule(StoreSchedule schedule) {
        storeTimeSchedule = schedule;
        return this;
    }

    public static Store withMerchandise(String productName, String productBrand, Double price, Integer stock, MerchandiseCategory aCategory) {
        Store store = aStore().build();
        store.addMerchandise(productName, productBrand, price, stock, aCategory, "");
        return store;
    }

    public StoreBuilder withOpeningDate(LocalDate anOpeningDate) {
        openingDate = anOpeningDate;
        return this;
    }

    public Store withoutDaysInSchedule() {
        Store store = aStore().build();
        store.setEmptyDaysOfWeek();
        return store;
    }

    public Store buildWithNoId() {
        return new Store(storeName, storeCategories, storeAddress,
                deliveryMaxDistanceInKm, availablePaymentMethods, storeTimeSchedule, openingDate, imageUrl);
    }

    public Store buildWithId() {
        Store store = this.build();
        store.setId(new Random().nextLong());
        return store;
    }
}
