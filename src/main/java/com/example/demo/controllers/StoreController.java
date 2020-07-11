package com.example.demo.controllers;

import com.example.demo.dtos.MerchandiseDTO;
import com.example.demo.dtos.MerchandiseListDTO;
import com.example.demo.dtos.MerchandiseListResponseDTO;
import com.example.demo.model.merchandise.Merchandise;
import com.example.demo.model.store.Store;
import com.example.demo.services.IStoreService;
import com.example.demo.services.users.IUserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController

public class StoreController {

    @Autowired
    private IStoreService storeService;
    @Autowired
    private IUserService userService;


    @RequestMapping("/stores")
    public List<Store> getStores(@RequestParam(defaultValue = "") String category) throws JsonProcessingException {
        if(category.isEmpty()){
            return storeService.getStores();
        }
        return storeService.getStoresWithACategory(category);
    }

    @RequestMapping("/stores/{id}")
    public ResponseEntity<Store> getStore(@PathVariable("id") String id){
        Long storeId = Long.parseLong(id);
        return new ResponseEntity<>(storeService.getStore(storeId), HttpStatus.OK);
    }

    @RequestMapping(path="/stores/{id}/products")
    public ResponseEntity<Object> getProducts(@PathVariable("id") Long storeId, @RequestParam() Boolean activeProducts) {
        Store store = storeService.getStore(storeId);
        List<Merchandise> merchandises = storeService.getProductsFromStore(storeId, activeProducts);
        return generateProductsResponse(merchandises, store);
    }

    @RequestMapping(path="/stores/discounts")
    public List<Merchandise> getDiscountFromAllStores(){
        return storeService.getDiscountFromStores();
    }

    private ResponseEntity<Object> generateProductsResponse(List<Merchandise> merchandises, Store store) {
        return new ResponseEntity<>(new MerchandiseListResponseDTO(merchandises, store), HttpStatus.OK);
    }

    @PostMapping(path="/stores/addMerchandise", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Merchandise> addMerchandise(@RequestBody MerchandiseDTO merchandiseDTO){
        Merchandise merchandise = new Merchandise(merchandiseDTO.getMerchandiseName(), merchandiseDTO.getMerchandiseBrand(),
                merchandiseDTO.getMerchandisePrice(), merchandiseDTO.getMerchandiseStock(), merchandiseDTO.getCategory(),
                merchandiseDTO.getImageURL());
        return addMerchandiseToStore(merchandiseDTO.getStoreId(), merchandise);
    }

    @PostMapping(path="/stores/addMerchandiseList", consumes = "application/json", produces = "application/json")
    public ResponseEntity<List<Merchandise>> addMerchandiseList(@RequestBody MerchandiseListDTO merchandiseListDTO){
        List<Merchandise> addedMerchandises = storeService.addMultipleMerchandisesToStore(merchandiseListDTO.getStoreId(), merchandiseListDTO.getMerchandisesAsEntities());
        return new ResponseEntity<>(addedMerchandises, HttpStatus.OK);
    }

    private ResponseEntity<Merchandise> addMerchandiseToStore(Long storeId, Merchandise merchandise) {
        return new ResponseEntity<>(storeService.addMerchandiseToStore(storeId, merchandise), HttpStatus.OK);
    }
}
