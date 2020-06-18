package com.example.demo.model.user;

import com.example.demo.deserializers.UserJsonDeserializer;
import com.example.demo.model.exceptions.InvalidMailException;
import com.example.demo.model.exceptions.InvalidUsernameOrPasswordException;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import javax.persistence.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="user_type",
        discriminatorType = DiscriminatorType.STRING)
@JsonDeserialize(using = UserJsonDeserializer.class)
public abstract class User {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    private String username;
    private String password;

    public User(String username, String password) {
        if(username.isEmpty() || password.isEmpty()){
            throw new InvalidUsernameOrPasswordException();
        }
        validateMail(username);
        this.username = username;
        this.password = password;
    }

    protected void validateMail(String username){
        Pattern pattern = Pattern
                .compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");

        Matcher mather = pattern.matcher(username);
        if(!mather.find()){
            throw new InvalidMailException();
        }
    }

    public User(){};

    public String username() { return this.username; }

    public String password() { return this.password; }

    public abstract Boolean isAdminOfStore();

    public void setUsername(String username) {
        this.username = username;
    };

    public void setPassword(String password) {
        this.password = password;
    }

    public Long id() {
        return this.id;
    }

    public void setId(Long id){
        this.id = id;
    }
}
