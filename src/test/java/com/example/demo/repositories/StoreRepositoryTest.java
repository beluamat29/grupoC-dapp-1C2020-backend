package com.example.demo.repositories;

import com.example.demo.builders.StoreBuilder;
import com.example.demo.model.store.Store;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest

public class StoreRepositoryTest {

    @Autowired
    private StoreRepository storeRepository;

    @Test
    public void aStoreIsRetrieved() {
        Store store = StoreBuilder.aStore().buildWithNoId();
        storeRepository.save(store);

        Optional<Store> retrievedStore = storeRepository.findById(store.id());
        assertThat(retrievedStore).hasValue(store);
    }
}
