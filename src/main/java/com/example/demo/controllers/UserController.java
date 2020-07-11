package com.example.demo.controllers;

import com.example.demo.dtos.ValidationUserDTO;
import com.example.demo.model.user.ClientUser;
import com.example.demo.model.user.FacebookUser;
import com.example.demo.model.user.StoreAdminUser;
import com.example.demo.model.user.User;
import com.example.demo.services.IStoreService;
import com.example.demo.services.users.IUserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Transactional
public class UserController {

    @Autowired
    private IUserService userService;

    @Autowired
    private IStoreService storeService;

    @GetMapping("/users")
    public List<User> getUsers() throws JsonProcessingException {
        return userService.getUsers();
    }

    @RequestMapping("/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") String id){
        Long userId = Long.parseLong(id);
        return new ResponseEntity<>(userService.getUserById(userId), HttpStatus.OK);
    }

    @PostMapping(path = "/validateUser")
    public ResponseEntity<User> validateUser(@RequestBody ValidationUserDTO validationUserDTO)
    {
        return new ResponseEntity<>(userService.authenticateUser(validationUserDTO.getUsername(), validationUserDTO.getPassword()), HttpStatus.OK);
    }

    @PostMapping(path = "/users", consumes = "application/json", produces = "application/json")
    public ResponseEntity<ClientUser> createUser(@RequestBody ClientUser clientUser) {
        ClientUser savedUser = userService.addUser(clientUser.username(), clientUser.password(), clientUser.address());
        return new ResponseEntity<>(savedUser, HttpStatus.OK);
    }

    @PutMapping(path = "/users/{id}")
    public ResponseEntity<User> updateUser(@RequestBody User clientUser, @PathVariable("id") String id) {
        Long userId = Long.parseLong(id);
        User savedUser = userService.updateUser(userId, clientUser);
        return new ResponseEntity<>(savedUser, HttpStatus.OK);
    }

    @PostMapping(path = "/storeAdmin", consumes = "application/json", produces = "application/json")
    public ResponseEntity<StoreAdminUser> createNewStore(@RequestBody StoreAdminUser storeAdminUser)
    {
        StoreAdminUser savedStoreAdmin = userService.addStoreAdmin(storeAdminUser);
        return new ResponseEntity<>(savedStoreAdmin, HttpStatus.OK);
    }

    @PostMapping(path="/facebookUser",  consumes = "application/json", produces = "application/json")
    public ResponseEntity<FacebookUser> createFacebookUser(@RequestBody FacebookUser facebookUser){
        FacebookUser user = userService.getFacebookUserByUsername(facebookUser.getUsername());
        if(user != null ){
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        FacebookUser savedUser = userService.addFacebookUser(facebookUser.getUsername());
        return new ResponseEntity<>(savedUser, HttpStatus.OK);
    }
}
