package com.example.demo.model.user;

import com.example.demo.builders.StoreAdminBuilder;
import com.example.demo.builders.StoreBuilder;
import com.example.demo.builders.ClientUserBuilder;
import com.example.demo.model.exceptions.InvalidMailException;
import com.example.demo.model.exceptions.InvalidUsernameOrPasswordException;
import com.example.demo.model.store.Store;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.*;

public class StoreAdminUserTest {

    @Test
    public void aStoreAdminKnowsItsUsername(){
        String aUsername = "Coto@gmail.com";
        StoreAdminUser storeAdminUser = StoreAdminBuilder.aStoreAdmin().withUsername(aUsername).build();
        assertEquals(storeAdminUser.username(), aUsername);
    }

    @Test
    public void aStoreAdminKnowsItsPassword(){
        String aPassword = "APassword";
        StoreAdminUser storeAdminUser = StoreAdminBuilder.aStoreAdmin().withPassword(aPassword).build();
        assertEquals(storeAdminUser.password(), aPassword);
    }

    @Test
    public void storeAdminCannotHaveAnEmptyUsername(){
        assertThrows(InvalidUsernameOrPasswordException.class , ()->  StoreAdminBuilder.aStoreAdmin().withUsername("").build() );
    }

    @Test
    public void storeAdminCannotHaveAnEmptyPassword(){
        assertThrows(InvalidUsernameOrPasswordException.class , ()->  StoreAdminBuilder.aStoreAdmin().withPassword("").build() );
    }

    @Test
    public void storeAdminEmailCannotHaveAInvalidFormat(){
        assertThrows(InvalidMailException.class , ()->  StoreAdminBuilder.aStoreAdmin().withUsername("coto").build() );
    }

    @Test
    public void aStoreAdminUserKnowsItsStore() {
        Store store = StoreBuilder.aStore().build();
        StoreAdminUser storeAdminUser = ClientUserBuilder.user().adminOfStore(store);
        assertEquals(storeAdminUser.store(), store);
    }

    @Test
    public void aStoreAdminUserIsAStoreAdmin() {
        Store store = StoreBuilder.aStore().build();
        StoreAdminUser storeAdminUser = ClientUserBuilder.user().adminOfStore(store);
        assertTrue(storeAdminUser.isAdminOfStore());
    }

}
