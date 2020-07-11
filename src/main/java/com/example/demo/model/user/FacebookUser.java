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
    private String mail;

    public FacebookUser(String email){
        this.mail = email;
    }

    public FacebookUser(){};

    public String getMail() {  return mail; }

    public void setMail(String mail) { this.mail = mail; }

    public Long id() { return this.id; }

    public void setId(Long id) { this.id = id; }
}
