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
        retrivedMerchandise.setCategory(merchandise.getCategory());
        retrivedMerchandise.setImageURL(merchandise.imageURL());

        return merchandiseRepository.save(retrivedMerchandise);
    }

    @Override
    public Merchandise getMerchandiseById(Long id) {
        return merchandiseRepository.findById(id).orElseThrow(NotFoundMerchandiseException::new);
    }

    @Override
    public Merchandise deactivateMerchandise(Long merchandiseId) {
        Merchandise retrievedMerchandise = this.getMerchandiseById(merchandiseId);
        retrievedMerchandise.deactivate();
        return merchandiseRepository.save(retrievedMerchandise);
    }

    @Override
    public Merchandise reactivateMerchandise(Long merchandiseId) {
        Merchandise retrievedMerchandise = this.getMerchandiseById(merchandiseId);
        retrievedMerchandise.activate();
        return merchandiseRepository.save(retrievedMerchandise);
    }
}
