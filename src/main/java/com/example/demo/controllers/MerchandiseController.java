package com.example.demo.controllers;

import com.example.demo.model.merchandise.Merchandise;
import com.example.demo.services.merchandise.IMerchandiseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class MerchandiseController {

    @Autowired
    private IMerchandiseService merchandiseService;

    @PutMapping(path="/merchandise/{id}")
    public ResponseEntity<Merchandise> updateMerchandise(@RequestBody Merchandise merchandise, @PathVariable("id") String id){
        Long merchandiseId = Long.parseLong(id);
        Merchandise savedMerchandise = merchandiseService.updateMerchandise(merchandiseId, merchandise);
        return new ResponseEntity<>(savedMerchandise, HttpStatus.OK);
    }
}
