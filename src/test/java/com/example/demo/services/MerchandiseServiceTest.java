package com.example.demo.services;

import com.example.demo.builders.MerchandiseBuilder;
import com.example.demo.model.merchandise.Merchandise;
import com.example.demo.model.merchandise.MerchandiseCategory;
import com.example.demo.repositories.merchandise.MerchandiseRepository;
import com.example.demo.services.merchandise.MerchandiseService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class MerchandiseServiceTest {

    @Mock
    MerchandiseRepository merchandiseRepositoryMock;

    @InjectMocks
    MerchandiseService merchandiseService;

    @Test
    public void aMerchandiseCanHaveItsNameAndBrandUpdated(){
        Merchandise merchandise = MerchandiseBuilder.aMerchandise().withName("Fileos").withBrand("Mariolio").build();
        Merchandise updatedMerchandise = MerchandiseBuilder.aMerchandise().withName("Fideos").withBrand("Marolio").build();
        updatedMerchandise.setId(merchandise.id());

        when(merchandiseRepositoryMock.findById(any())).thenReturn(java.util.Optional.ofNullable(merchandise));
        when(merchandiseRepositoryMock.save(any())).thenReturn(updatedMerchandise);

        Merchandise retrievedMerchandise = merchandiseService.updateMerchandise(merchandise.id(), updatedMerchandise);
        assertEquals("Fideos", retrievedMerchandise.name());
        assertEquals("Marolio", retrievedMerchandise.brand());
    }

    @Test
    public void aMerchandiseCanHaveItsPriceAndStockUpdated(){
        Merchandise merchandise = MerchandiseBuilder.aMerchandise().withPrice(40.0).withStock(10).build();
        Merchandise updatedMerchandise = MerchandiseBuilder.aMerchandise().withPrice(45.5).withStock(100).build();
        updatedMerchandise.setId(merchandise.id());
        Double price = 45.5;
        Integer stock = 100;

        when(merchandiseRepositoryMock.findById(any())).thenReturn(java.util.Optional.ofNullable(merchandise));
        when(merchandiseRepositoryMock.save(any())).thenReturn(updatedMerchandise);

        Merchandise retrievedMerchandise = merchandiseService.updateMerchandise(merchandise.id(), updatedMerchandise);
        assertEquals(price, retrievedMerchandise.price());
        assertEquals(stock, retrievedMerchandise.stock());
    }

    @Test
    public void aMerchandiseCanHaveItsCategoryAndImageUpdated(){
        Merchandise merchandise = MerchandiseBuilder.aMerchandise().withCategory(MerchandiseCategory.GROCERY).withImage("image").build();
        Merchandise updatedMerchandise = MerchandiseBuilder.aMerchandise().withCategory(MerchandiseCategory.BAKERY).withImage("anotherimage").build();
        updatedMerchandise.setId(merchandise.id());

        when(merchandiseRepositoryMock.findById(any())).thenReturn(java.util.Optional.ofNullable(merchandise));
        when(merchandiseRepositoryMock.save(any())).thenReturn(updatedMerchandise);

        Merchandise retrievedMerchandise = merchandiseService.updateMerchandise(merchandise.id(), updatedMerchandise);
        assertEquals(MerchandiseCategory.BAKERY, retrievedMerchandise.getCategory());
        assertEquals("anotherimage", retrievedMerchandise.imageURL());
    }
}
