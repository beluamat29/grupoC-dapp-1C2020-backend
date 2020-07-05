package com.example.demo.dtos;

import com.example.demo.model.DeliveryType;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.tomcat.jni.Local;

import java.time.LocalDateTime;
import java.util.List;

public class PurchaseDTO {

    @JsonProperty
    private List<ProductToBuy> productList;
    @JsonProperty
    private Long userId;
    @JsonProperty
    private String deliveryType;
    @JsonProperty
    private LocalDateTime deliveryTime;
    @JsonProperty
    private String paymentMethod;

    public PurchaseDTO(@JsonProperty("productList")List<ProductToBuy> productList , @JsonProperty("userID")Long userId,
                       @JsonProperty("deliveryType")String deliveryType, @JsonProperty("deliveryTime")LocalDateTime deliveryTime, @JsonProperty("paymentMethod")String paymentMethod){

        this.userId = userId;
        this.productList = productList;
        this.deliveryType = deliveryType;
        this.deliveryTime = deliveryTime;
        this.paymentMethod = paymentMethod;
    }


    public List<ProductToBuy> getProductList() { return productList;  }
    public Long getUserId() { return userId; }
    public String getDeliveryType() { return deliveryType; }
    public String getPaymentMethod() { return paymentMethod;  }
    public LocalDateTime getDeliveryTime() { return this.deliveryTime;}

    public void setProductList(List<ProductToBuy> productList) { this.productList = productList;  }
    public void setUserId(Long userId) { this.userId = userId;  }
    public void setDeliveryType(String deliveryType) { this.deliveryType = deliveryType;  }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod;  }
    public void setDeliveryTime(LocalDateTime time) { this.deliveryTime = time;}
}
