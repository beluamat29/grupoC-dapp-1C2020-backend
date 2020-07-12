package com.example.demo.services.users;

import com.example.demo.model.exceptions.ForbiddenAttributeUpdate;
import com.example.demo.model.exceptions.NotAvailableUserNameException;
import com.example.demo.model.exceptions.NotFoundUserException;
import com.example.demo.model.user.ClientUser;
import com.example.demo.model.user.StoreAdminUser;
import com.example.demo.model.user.User;
import com.example.demo.repositories.threshold.MoneyThresholdRepository;
import com.example.demo.repositories.users.UserRepository;
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
    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(NotFoundUserException::new);
    }

    @Override
    public User updateUser(Long userId, User user) {
        User retrievedUser = this.getUserById(userId);
        if(!retrievedUser.username().equals(user.username())) {throw new ForbiddenAttributeUpdate("updating user's username is forbidden");}
        retrievedUser.setAddress(user.address());
        retrievedUser.setPassword(user.password());
        if(user.isAdminOfStore()) {
            updateStoreData(user, retrievedUser);
        }
        return userRepository.save(retrievedUser);
    }

    @Override
    public ClientUser addFacebookUser(String username, String password, String address) {
        ClientUser facebookUser = ClientUser.createFacebookUser(username, password, address);
        return userRepository.save(facebookUser);
    }

    private void updateStoreData(User user, User retrievedUser) {
        retrievedUser.store().setName(user.store().name());
        retrievedUser.store().setAddress(user.store().address());
        retrievedUser.store().setDeliveryDistance(user.store().deliveryDistanceInKm());
        retrievedUser.store().storeSchedule().setDays(user.store().storeSchedule().days());
        retrievedUser.store().storeSchedule().setOpeningTime(user.store().storeSchedule().openingTime());
        retrievedUser.store().storeSchedule().setClosingTime(user.store().storeSchedule().closingTime());
        retrievedUser.store().setImageUrl(user.store().imageURL());
        retrievedUser.store().setCategories(user.store().storeCategories());
        retrievedUser.store().setPaymentMethods(user.store().availablePaymentMethods());
    }


}
