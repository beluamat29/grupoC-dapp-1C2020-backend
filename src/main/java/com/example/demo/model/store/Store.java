package com.example.demo.model.store;

import com.example.demo.deserializers.StoreJsonDeserializer;
import com.example.demo.model.AcquiredProduct;
import com.example.demo.model.StoreSchedule;
import com.example.demo.model.discounts.*;
import com.example.demo.model.exceptions.InsufficientMerchandiseStockException;
import com.example.demo.model.exceptions.InvalidMerchandiseException;
import com.example.demo.model.merchandise.Merchandise;
import com.example.demo.model.merchandise.MerchandiseCategory;
import com.example.demo.model.turnsSystem.TurnsSystem;
import com.example.demo.serializers.StoreJsonSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.example.demo.model.exceptions.NotFoundProductInStore;
import com.example.demo.model.exceptions.RepeatedMerchandiseInStore;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@JsonSerialize(using = StoreJsonSerializer.class)
@JsonDeserialize(using = StoreJsonDeserializer.class)
@Entity
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String storeName;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    private List<StoreCategory> storeCategories;

    private String storeAddress;
    private Integer deliveryDistanceInKm;
    private LocalDateTime proximoTurnoDeLocal;
    private String storeImageUrl;
    @ElementCollection
    private List<String> availablePaymentMethods;
    @OneToOne(cascade = CascadeType.MERGE)
    private StoreSchedule storeTimeSchedule;
    private String mail = "";
    @Transient
    private List<Discount> discountList = new ArrayList<>();
    @OneToMany(fetch = FetchType.EAGER)
    private List<Merchandise> merchandiseList = new ArrayList<>();

    public Store(String name, List<StoreCategory> categories, String address, Integer distanceInKm,
                 List<String> paymentMethods, StoreSchedule timeSchedule, LocalDate openingDateTime, String imageUrl) {
         storeName = name;
         storeCategories = categories;
         storeAddress = address;
         deliveryDistanceInKm = distanceInKm;
         availablePaymentMethods =  paymentMethods;
         storeTimeSchedule = timeSchedule;
         storeImageUrl = imageUrl;
         if(openingDateTime != null) {
             proximoTurnoDeLocal = TurnsSystem.primerTurnoDeLocal(openingDateTime, this.storeTimeSchedule);
         } else {
             proximoTurnoDeLocal = TurnsSystem.primerTurnoDeLocal(LocalDate.now(), this.storeTimeSchedule);
         }
    }

    public Store(){};

    public Long id() { return this.id; }

    public String name() {
        return this.storeName;
    }

    public String address() {
        return this.storeAddress;
    }

    public Integer deliveryDistanceInKm() {
        return this.deliveryDistanceInKm;
    }

    public List<StoreCategory> storeCategories() {
        return this.storeCategories;
    }

    public List<String> availablePaymentMethods() { return this.availablePaymentMethods; }

    public Integer amountOfAvailablePaymentMethods() { return availablePaymentMethods.size();   }

    public Boolean isOpenAt(DayOfWeek dia, LocalTime hora) {
        return storeTimeSchedule.isAvailableOn(dia, hora);
    }

    public Boolean sellsProduct(String nombreProducto, String marcaProducto) {
      return merchandiseList.stream().anyMatch(mercaderia -> this.equalMerchandise(mercaderia, nombreProducto, marcaProducto));
    }

    private Boolean equalMerchandise(Merchandise mercaderia, String unNombreProducto, String unaMarcaProducto) {
        return mercaderia.name().equals(unNombreProducto) && mercaderia.brand().equals(unaMarcaProducto);
    }

    private Merchandise findMerchandise(String nombreProducto, String marcaProducto) {
        return merchandiseList.stream()
                .filter(mercaderia -> this.equalMerchandise(mercaderia, nombreProducto, marcaProducto))
                .findFirst()
                .orElseThrow(NotFoundProductInStore::new);
    }

    public Boolean canPayWith(String medioDePago) {
        return availablePaymentMethods.contains(medioDePago);
    }

    public Boolean hasMerchandises() {
        return !merchandiseList.isEmpty();
    }

    public Merchandise addMerchandise(String name, String brand, Double price, Integer stock, MerchandiseCategory aCategory, String imageUrl) {
        Merchandise newMerchandise = new Merchandise(name, brand, price, stock, aCategory, imageUrl);
        canAddMerchandise(newMerchandise);
        merchandiseList.add(newMerchandise);
        return newMerchandise;
    }

    public void addMerchandise(Merchandise merchandise){
        canAddMerchandise(merchandise);
        merchandiseList.add(merchandise);
    }

    private Boolean canAddMerchandise(Merchandise merchandise) {
        if(merchandise.name().isEmpty() || merchandise.brand().isEmpty()){
            throw new InvalidMerchandiseException();
        }
        if(this.sellsProduct(merchandise.name(), merchandise.brand())) { throw new RepeatedMerchandiseInStore();}
        return true;
    }

    public Boolean sellsMerchandise(String name, String brand) {
        return merchandiseList.stream().anyMatch(merchandise -> this.equalMerchandise(merchandise, name, brand));
    }

    public Integer stockOf(String name, String brand) {
        return this.findMerchandise(name, brand).stock();
    }

    public Double priceOf(String name, String brand) {
        Merchandise merchandise = this.findMerchandise(name, brand);
        Discount discount = discountList.stream().filter(aDiscount -> aDiscount.canApplyDiscountFor(merchandise)).findFirst().orElse(new NoDiscount());
        return merchandise.price() - (discount.percentOfDiscount() * merchandise.price() / 100);
    }

    public void updatePriceFor(String name, String brand, Double newPrice) {
        this.findMerchandise(name, brand).updatePrice(newPrice);
    }

    public void addStock(String name, String brand, Integer newStock) {
        this.findMerchandise(name, brand).addStock(newStock);
    }

    public void decreaseStock(String name, String brand, Integer stockToDecrese) {
        this.findMerchandise(name, brand).decreaseStock(stockToDecrese);
    }

    public List<Merchandise> listOfAvailableMerchandise() {
        return this.merchandiseList.stream().filter(merchandise -> merchandise.stock() > 0).collect(Collectors.toList());
    }

    public List<Merchandise> getMerchandisesFromCategory(MerchandiseCategory category) {
        return this.merchandiseList.stream().filter(merchandise -> merchandise.getCategory().equals(category)).collect(Collectors.toList());
    }

    public AcquiredProduct getProduct(String productName, String productBrand, Integer quantity) {
        Merchandise merchandise = this.findMerchandise(productName, productBrand);
        if (this.stockOf(productName, productBrand) < quantity){
            throw new InsufficientMerchandiseStockException();
        }
        this.decreaseStock(productName, productBrand, quantity);
        return new AcquiredProduct(merchandise.name(), merchandise.brand(), merchandise.price(), quantity);
    }


    public Merchandise getMerchandise(String productName, String productBrand) {
        return this.findMerchandise(productName, productBrand);
    }

    public void addMerchandiseDiscountFor(String productName, String productBrand, Integer percentOfDiscount, LocalDate endDate) {
        Merchandise merchandise = this.getMerchandise(productName, productBrand);
        this.discountList.add(new MerchandiseDiscount(merchandise, percentOfDiscount, LocalDate.now(), endDate));
    }

    public void addCategoryDiscount(MerchandiseCategory category, Integer percentOfDiscount, LocalDate endDate) {
        this.discountList.add(new CategoryDiscount(category, percentOfDiscount, LocalDate.now(), endDate));
    }

    public LocalDateTime nextTurn(LocalDateTime aDate) {
        if (aDate.toLocalDate().isBefore(proximoTurnoDeLocal.toLocalDate())) {
            LocalDateTime turnoADar = proximoTurnoDeLocal;
            this.updateNextTurn(aDate.toLocalDate());
            return turnoADar;
        }
        else {
            LocalDateTime turnoADar = TurnsSystem.primerTurnoDeLocal(aDate.toLocalDate(), this.storeTimeSchedule);
            this.updateNextTurn(aDate.toLocalDate());
            return turnoADar;
        }
    }

    private void updateNextTurn(LocalDate aDate) {
        if(this.isOpenAt(proximoTurnoDeLocal.getDayOfWeek(), LocalTime.of(proximoTurnoDeLocal.getHour(), proximoTurnoDeLocal.getMinute()).plusMinutes(15))){
            this.proximoTurnoDeLocal = proximoTurnoDeLocal.plusMinutes(15);
        }else{
            this.proximoTurnoDeLocal = TurnsSystem.primerTurnoDeLocal(aDate, this.storeTimeSchedule);
        }
    }

    public LocalDateTime homeDeliveryTime() {
        return LocalDateTime.now().plusDays(1);
    }

    public Boolean isProductFromCategory(AcquiredProduct acquiredProduct, MerchandiseCategory category) {
        return this.findMerchandise(acquiredProduct.name(), acquiredProduct.brand()).getCategory().equals(category);
    }

    public Boolean hasACategory(StoreCategory category) {
        return this.storeCategories.contains(category);
    }

    public LocalDateTime proximoTurnoDelLocal() {
        return this.proximoTurnoDeLocal;
    }

    public StoreSchedule storeSchedule() { return this.storeTimeSchedule;}

    public List<DayOfWeek> openingDays() {
        return storeTimeSchedule.days();
    }

    public void setEmptyDaysOfWeek() {
        storeTimeSchedule.setEmptyDays();
    }

    public void setId(Long storeId) {
        this.id = storeId;
    }

    public String imageURL() {
        return this.storeImageUrl;
    }
}

