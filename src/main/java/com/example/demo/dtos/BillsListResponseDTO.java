package com.example.demo.dtos;

import com.example.demo.model.Bill;
import com.example.demo.model.user.User;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class BillsListResponseDTO {

    @JsonProperty
    private List<Bill> userBills;
    @JsonProperty
    private User user;


    public BillsListResponseDTO(@JsonProperty("userBills") List<Bill> userBills, @JsonProperty("user") User user) {
        this.user = user;
        this.userBills = userBills;
    }

    public BillsListResponseDTO(){};

    public List<Bill> getUserBills() {  return userBills; }

    public void setUserBills(List<Bill> userBills) { this.userBills = userBills; }

    public User getUser() { return user; }

    public void setUser(User user) { this.user = user; }
}
