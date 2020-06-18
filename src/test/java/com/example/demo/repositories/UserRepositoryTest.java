package com.example.demo.repositories;

import com.example.demo.builders.ClientUserBuilder;
import com.example.demo.model.user.ClientUser;
import com.example.demo.model.user.User;
import com.example.demo.repositories.threshold.MoneyThresholdRepository;
import com.example.demo.repositories.users.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

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

}
