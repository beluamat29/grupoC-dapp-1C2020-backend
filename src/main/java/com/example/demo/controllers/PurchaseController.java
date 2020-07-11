package com.example.demo.controllers;

import com.example.demo.dtos.BillsListResponseDTO;
import com.example.demo.dtos.PurchaseDTO;
import com.example.demo.model.Bill;
import com.example.demo.model.user.ClientUser;
import com.example.demo.model.user.User;
import com.example.demo.services.purchase.IPurchaseService;
import com.example.demo.services.users.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class PurchaseController {

    @Autowired
    private IPurchaseService purchaseService;

    @Autowired
    private IUserService userService;

    @PostMapping(path="/purchase")
    public ResponseEntity<Bill> addBill(@RequestBody PurchaseDTO purchaseDTO){
        User user = userService.getUserById(purchaseDTO.getUserId());
        Bill bill = purchaseService.processBill(purchaseDTO.getProductList(), purchaseDTO.getDeliveryType(), purchaseDTO.getDeliveryTime(), purchaseDTO.getPaymentMethod(), (ClientUser) user);
        return new ResponseEntity<>(bill, HttpStatus.OK);
    }

    @RequestMapping(path="/purchase/{id}")
    public ResponseEntity<BillsListResponseDTO> getBillsFromUser(@PathVariable("id") String id){
        Long userId = Long.parseLong(id);
        User user = userService.getUserById(userId);
        List<Bill> usersBills = purchaseService.getUsersBills(userId);
        return generateBillsResponse(usersBills, user);
    }

    private ResponseEntity<BillsListResponseDTO> generateBillsResponse(List<Bill> bills, User user) {
        return new ResponseEntity<>(new BillsListResponseDTO(bills, user.id()), HttpStatus.OK);
    }
}
