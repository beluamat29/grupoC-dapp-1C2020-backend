package com.example.demo.services.users;

import com.example.demo.model.user.ClientUser;
import com.example.demo.model.store.Store;
import com.example.demo.model.user.StoreAdminUser;
import com.example.demo.model.user.User;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface IUserService {

    User authenticateUser(String username, String password);

    List<User> getUsers();

    Boolean canAddUser(String username);

    ClientUser addUser(String username, String password, String address);

    StoreAdminUser addStoreAdmin(StoreAdminUser storeAdminUser);
}
