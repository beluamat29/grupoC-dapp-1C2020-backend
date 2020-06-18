package com.example.demo.repositories;

import com.example.demo.builders.MerchandiseBuilder;
import com.example.demo.builders.StoreBuilder;
import com.example.demo.model.merchandise.Merchandise;
import com.example.demo.model.store.Store;
import com.example.demo.repositories.merchandise.MerchandiseRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class MerchandiseRepositoryTest {

    @Autowired
    private MerchandiseRepository merchandiseRepository;

    @Autowired
    private StoreRepository storeRepository;

    @Test
    public void aMerchandiseIsRetrieved() {
        Merchandise merchandise = MerchandiseBuilder.aMerchandise().build();
        merchandiseRepository.save(merchandise);

        Optional<Merchandise> retrievedMerchandise = merchandiseRepository.findById(merchandise.id());
        assertThat(retrievedMerchandise).hasValue(merchandise);
    }

    @Test
    public void getMerchandiseFromSpecificStore() {
        Store store = StoreBuilder.aStore().build();
        store.setId((long) 1);
        Merchandise fideos = MerchandiseBuilder.aMerchandise().withName("Fideos").build();
        Merchandise arroz = MerchandiseBuilder.aMerchandise().withName("Arroz").build();
        store.addMerchandise(fideos);
        merchandiseRepository.save(arroz);
        merchandiseRepository.save(fideos);
        Long storeId = storeRepository.save(store).id();
        List<Merchandise> merchandiseList = Arrays.asList(fideos);

        Optional<List<Merchandise>> retrivedMerchandiseList = merchandiseRepository.getMerchandiseFromStore(storeId);
        assertThat(retrivedMerchandiseList).hasValue(merchandiseList);
        assertEquals(1, merchandiseList.size());
    }
}
