package com.example.demo.model.user;

import com.example.demo.model.Bill;
import com.example.demo.model.exceptions.ClientUserDoesNotHaveStoresException;
import com.example.demo.model.exceptions.InvalidAddressException;
import com.example.demo.model.exceptions.NotFoundCategoryMoneyThresholdForThisUser;
import com.example.demo.model.merchandise.MerchandiseCategory;
import com.example.demo.model.store.Store;
import com.example.demo.model.thresholds.CategoryMoneyThreshold;
import com.example.demo.model.thresholds.MoneyThreshold;
import com.example.demo.serializers.UserJsonSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("CLIENT_USER")
@JsonSerialize(using = UserJsonSerializer.class)
    public class ClientUser extends User {

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Bill> billOfPurchase ;

    @OneToOne
    private MoneyThreshold moneyThresold = new MoneyThreshold(0.0);
    @Transient
    private List<CategoryMoneyThreshold> categoryMoneyThresholds = new ArrayList<>();
    private String address;
    private Boolean isFacebookUser;

    public ClientUser(String username, String password, String anAddress){
        super(username, password);
        if(anAddress.isEmpty()){
            throw new InvalidAddressException();
        }
        this.address = anAddress;
        this.billOfPurchase = new ArrayList<>();
        this.setIsFacebookUser(false);
    }

    private void setIsFacebookUser(Boolean isFacebookUser) {
        this.isFacebookUser = isFacebookUser;
    }

    public static ClientUser createFacebookUser(String username, String password, String anAddress) {
        ClientUser facebookUser = new ClientUser(username, password, anAddress);
        facebookUser.setIsFacebookUser(true);
        return facebookUser;
    }

    public ClientUser(){};

    @Override
    public Boolean isAdminOfStore() { return false;}

    public Boolean hasMoneyThreshold() {
        return this.moneyThresold.isActive() && this.moneyThresold.moneyLimit() > 0.0;
    }

    public void setMoneyThreshold(MoneyThreshold moneyThreshold) {
        this.moneyThresold = moneyThreshold;
    }

    public void addCategoryMoneyThreshold(MerchandiseCategory category, Double moneyLimit) {
        if(!this.hasCategoryLimitOf(category)) {
            this.categoryMoneyThresholds.add(new CategoryMoneyThreshold(moneyLimit, category));
        }
    }

    public String address(){
        return this.address;
    }

    @Override
    public Store store() {
        throw new ClientUserDoesNotHaveStoresException();
    }

    @Override
    public void setStore(Store store) {
        throw new ClientUserDoesNotHaveStoresException();
    }

    public void disableMoneyThreshold() {
        this.moneyThresold.disable();
    }

    public void updateMoneyThreshold(Double newMoneyLimit) {
        this.moneyThresold.updateMoneyLimit(newMoneyLimit);
    }

    public Double moneyThresholdLimit() {
        return this.moneyThresold.moneyLimit();
    }

    public MoneyThreshold moneyThreshold() { return this.moneyThresold; }

    public Boolean hasCategoryLimitOf(MerchandiseCategory category) {
        return this.categoryMoneyThresholds.stream().anyMatch(categoryMoneyThreshold -> categoryMoneyThreshold.category().equals(category));
    }

    public void updateCategoryMoneyThreshold(MerchandiseCategory category, Double newMoneyLimit) {
        this.categoryMoneyThresholdOf(category).updateMoneyLimit(newMoneyLimit);
    }

    public MoneyThreshold categoryMoneyThresholdOf(MerchandiseCategory category) {
        return this.categoryMoneyThresholds.stream().filter(categoryMoneyThreshold -> categoryMoneyThreshold.category().equals(category))
                .findFirst()
                .orElseThrow(NotFoundCategoryMoneyThresholdForThisUser::new);
    }

    public void addBillOfPurchase(Bill bill) {
        this.billOfPurchase.add(bill);
    }

    public Integer quantityOfBills() {
        return this.billOfPurchase.size();
    }

    public Boolean hasBill(Bill aBill) {
        return this.billOfPurchase.contains(aBill);
    }

    public List<Bill> getBills() {
        return billOfPurchase;
    }

    public void setAddress(String anAddress){  this.address = anAddress; }

    public Boolean isFacebookUser() {
        return this.isFacebookUser;
    }
}
