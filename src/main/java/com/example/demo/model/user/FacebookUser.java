package com.example.demo.model.user;

import com.example.demo.serializers.FacebookUserJsonSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.persistence.*;

@Entity
@DiscriminatorValue("FACEBOOK_USER")
@JsonSerialize(using = FacebookUserJsonSerializer.class)
public class FacebookUser {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    private String username;

    public FacebookUser(String email){
        this.username = email;
    }

    public FacebookUser(){};

    public String getUsername() {  return username; }

    public void setUsername(String mail) { this.username = mail; }

    public Long id() { return this.id; }

    public void setId(Long id) { this.id = id; }
}
