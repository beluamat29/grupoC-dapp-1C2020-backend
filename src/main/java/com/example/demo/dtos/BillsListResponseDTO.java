package com.example.demo.dtos;

import com.example.demo.model.Bill;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class BillsListResponseDTO {

    @JsonProperty
    private List<Bill> userBills;
    @JsonProperty
    private Long userId;


    public BillsListResponseDTO(@JsonProperty("userBills") List<Bill> userBills, @JsonProperty("userId") Long userId) {
        this.userId = userId;
        this.userBills = userBills;
    }

    public BillsListResponseDTO(){};

    public List<Bill> getUserBills() {  return userBills; }

    public void setUserBills(List<Bill> userBills) { this.userBills = userBills; }

    public Long getUser() { return this.userId; }

    public void setUserId(Long userId) { this.userId = userId; }
}
