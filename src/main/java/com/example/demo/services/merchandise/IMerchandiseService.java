package com.example.demo.services.merchandise;

import com.example.demo.model.merchandise.Merchandise;

public interface IMerchandiseService {
    Merchandise updateMerchandise(Long id, Merchandise updatedMerchandise);

    Merchandise getMerchandiseById(Long id);

    Merchandise deactivateMerchandise(Long merchandiseId);

    Merchandise reactivateMerchandise(Long merchandiseId);
}
