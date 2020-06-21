package com.example.demo.repositories;

import com.example.demo.builders.ClientUserBuilder;
import com.example.demo.builders.StoreAdminBuilder;
import com.example.demo.model.user.ClientUser;
import com.example.demo.model.user.StoreAdminUser;
import com.example.demo.model.user.User;
import com.example.demo.repositories.threshold.MoneyThresholdRepository;
import com.example.demo.repositories.users.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MoneyThresholdRepository moneyThresholdRepository;

    @Test
    public void anUserIsRetrieved() throws Exception {
        ClientUser user = ClientUserBuilder.user().build();
        moneyThresholdRepository.save(user.moneyThreshold());
        userRepository.save(user);

        Optional<User> retrievedUser = userRepository.findByUsernameEquals(user.username());
        assertThat(retrievedUser).hasValue(user);
    }

    @Test
    public void aUserIsUpdated() {
        ClientUser user = ClientUserBuilder.user().withAddress("Alsina 123").withPassword("ABC789").build();
        User savedUser = userRepository.save(user);

        User retrievedUser = userRepository.findById(savedUser.id()).get();
        retrievedUser.setPassword("newPassword");
        retrievedUser.setAddress("Alvear 4512");

        userRepository.save(retrievedUser);
        retrievedUser = userRepository.findById(retrievedUser.id()).get();

        assertEquals(retrievedUser.password(), "newPassword");
        assertEquals(retrievedUser.address(), "Alvear 4512");
    }

    @Test
    public void aStoreAdminUserCanUpdateItsPassword() {
        StoreAdminUser storeAdminUser = StoreAdminBuilder.aStoreAdmin().withPassword("ABC789").build();

        User savedUser = userRepository.save(storeAdminUser);

        storeAdminUser.setPassword("newPassword");

        userRepository.save(storeAdminUser);

        User retrievedUser = userRepository.findById(savedUser.id()).get();

        assertEquals(retrievedUser.password(), "newPassword");
    }
}
