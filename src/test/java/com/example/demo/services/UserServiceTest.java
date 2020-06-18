package com.example.demo.services;

import com.example.demo.builders.ClientUserBuilder;
import com.example.demo.model.exceptions.NotAvailableUserNameException;
import com.example.demo.model.exceptions.NotFoundUserException;
import com.example.demo.repositories.users.UserRepository;
import com.example.demo.services.users.IUserService;
import com.example.demo.model.user.ClientUser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Random;

import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class UserServiceTest {

    @MockBean
    UserRepository userRepositoryMock; //Si esto esta definido hay que usarlo si o si en cada test

    @Autowired
    IUserService userService;

    @Test
    public void aUserIsValidIfItExistsAndItsUsernameMatchesItsPassword() {
        ClientUser clientUser = ClientUserBuilder.user().build();
        when(userRepositoryMock.findByUsernameAndPassword(any(), any())).thenReturn(java.util.Optional.ofNullable(clientUser));

        assertEquals(userService.authenticateUser(clientUser.username(), clientUser.password()).username(), clientUser.username());
    }

    @Test
    public void aUserIsNotValidIfItsNotRegistered() {
        ClientUser clientUser = ClientUserBuilder.user().build();
        when(userRepositoryMock.findByUsernameAndPassword(any(), any())).thenReturn(java.util.Optional.ofNullable(null));

        assertThrows(NotFoundUserException.class, () -> userService.authenticateUser(clientUser.username(), clientUser.password()));

    }

    @Test
    public void aUserCanBeAddedIfTheresNotAnotherRegisteredUSerWithThatUsername() {
        when(userRepositoryMock.findByUsernameEquals(any())).thenReturn(java.util.Optional.ofNullable(null));
        assertTrue(userService.canAddUser("aNewUser"));
    }

    @Test
    public void aUserCannotBeAddedIfTheresAnotherRegisteredUSerWithThatUsername() {
        ClientUser clientUser = ClientUserBuilder.user().build();
        when(userRepositoryMock.findByUsernameEquals(any())).thenReturn(java.util.Optional.ofNullable(clientUser));
        assertThrows(NotAvailableUserNameException.class, () -> userService.addUser(clientUser.username(), clientUser.password(), clientUser.address()));
    }

    @Test
    public void whenAUserIsAddedTheServiceReturnsTheUser(){
        ClientUser clientUser = ClientUserBuilder.user().build();
        when(userRepositoryMock.findByUsernameEquals(any())).thenReturn(java.util.Optional.ofNullable(null));
        when(userRepositoryMock.save(any())).thenReturn(addIdToClientUser(clientUser));

        ClientUser createdUser = userService.addUser(clientUser.username(), clientUser.password(), clientUser.address());
        assertEquals(createdUser.username(), clientUser.username());
        assertEquals(createdUser.password(), clientUser.password());
    }

    @Test
    public void whenTryingToAddUserWithOccupiedUsernameNotAvailableNameExceptionIsThrown(){
        ClientUser clientUser = ClientUserBuilder.user().build();
        when(userRepositoryMock.findByUsernameEquals(any())).thenReturn(java.util.Optional.ofNullable(clientUser));

        assertThrows(NotAvailableUserNameException.class, () -> userService.addUser("aNewUser", "password", "address"));
    }

    private ClientUser addIdToClientUser(ClientUser aUser){
        aUser.setId(new Random().nextLong());
        return aUser;
    }
}
