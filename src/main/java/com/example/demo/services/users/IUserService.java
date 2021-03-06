package com.example.demo.services.users;

import com.example.demo.model.user.ClientUser;
import com.example.demo.model.user.StoreAdminUser;
import com.example.demo.model.user.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface IUserService {

    User authenticateUser(String username, String password);

    List<User> getUsers();

    Boolean canAddUser(String username);

    ClientUser addUser(String username, String password, String address);

    StoreAdminUser addStoreAdmin(StoreAdminUser storeAdminUser);

    User getUserById(Long id);

    User updateUser(Long id, User user);

    ClientUser addFacebookUser(String username, String password, String address);

    Optional<User> getUserByUsername(String username);

    StoreAdminUser findStoreAdmin(Long storeId);
}
