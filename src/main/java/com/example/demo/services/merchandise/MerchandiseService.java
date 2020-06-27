package com.example.demo.services.merchandise;

import com.example.demo.model.exceptions.NotFoundMerchandiseException;
import com.example.demo.model.merchandise.Merchandise;
import com.example.demo.repositories.merchandise.MerchandiseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MerchandiseService implements IMerchandiseService {

    @Autowired
    MerchandiseRepository merchandiseRepository;

    @Override
    public Merchandise updateMerchandise(Long id, Merchandise merchandise) {
        Merchandise retrivedMerchandise = this.getMerchandiseById(id);
        retrivedMerchandise.setName(merchandise.name());
        retrivedMerchandise.setBrand(merchandise.brand());
        retrivedMerchandise.updatePrice(merchandise.price());
        retrivedMerchandise.setStock(merchandise.stock());

        return merchandiseRepository.save(retrivedMerchandise);
    }

    @Override
    public Merchandise getMerchandiseById(Long id) {
        return merchandiseRepository.findById(id).orElseThrow(NotFoundMerchandiseException::new);
    }
}
