package com.example.demo.services.users;

import com.example.demo.model.exceptions.ForbiddenAttributeUpdate;
import com.example.demo.model.exceptions.NotAvailableUserNameException;
import com.example.demo.model.exceptions.NotFoundUserException;
import com.example.demo.model.store.Store;
import com.example.demo.model.user.StoreAdminUser;
import com.example.demo.model.user.User;
import com.example.demo.repositories.storeSchedule.StoreScheduleRepository;
import com.example.demo.repositories.threshold.MoneyThresholdRepository;
import com.example.demo.repositories.users.UserRepository;
import com.example.demo.model.user.ClientUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MoneyThresholdRepository moneyThresholdRepository;

    public User authenticateUser(String username, String password) {
        return userRepository.findByUsernameAndPassword(username, password)
                .orElseThrow(NotFoundUserException::new);
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public Boolean canAddUser(String username) {
        if(userRepository.findByUsernameEquals(username).isPresent()) {
            throw new NotAvailableUserNameException();
        }
        else return true;
    }

    @Override
    public ClientUser addUser(String username, String password, String address) {
        if(canAddUser(username)) {
            ClientUser newClientUser = new ClientUser(username, password, address);
            moneyThresholdRepository.save(newClientUser.moneyThreshold());
            ClientUser savedUser = userRepository.save(newClientUser);
            return savedUser;
        }
        throw new NotAvailableUserNameException();
    }

    @Override
    public StoreAdminUser addStoreAdmin(StoreAdminUser storeAdminUser) {
        if(canAddUser(storeAdminUser.username())) {
            StoreAdminUser newClientUser = new StoreAdminUser(storeAdminUser.username(), storeAdminUser.password(), storeAdminUser.store());
            StoreAdminUser savedUser = userRepository.save(newClientUser);
            return savedUser;
        }
        throw new NotAvailableUserNameException();
    }

    @Override
    public User findUserById(Long id) {
        return userRepository.findById(id).orElseThrow(NotFoundUserException::new);
    }

    @Override
    public User updateUser(User userToUpdate) {
        User alreadySavedUser = this.findUserById(userToUpdate.id());
        if(alreadySavedUser.username() != userToUpdate.username()){
            throw new ForbiddenAttributeUpdate("a user cannot update its username");
        }
        alreadySavedUser.setPassword(userToUpdate.password());
        alreadySavedUser.setAddress(userToUpdate.address());
        return userRepository.save(alreadySavedUser);
    }
}
