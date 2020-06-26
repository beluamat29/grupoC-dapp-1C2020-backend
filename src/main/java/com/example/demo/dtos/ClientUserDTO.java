package com.example.demo.dtos;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ClientUserDTO {
    @JsonProperty
    private String username;
    @JsonProperty
    private String password;
    @JsonProperty
    private Long id;

    @JsonCreator
    public ClientUserDTO(@JsonProperty("username") String username,
                         @JsonProperty("password") String password,
                         @JsonProperty("id") Long id) {
        this.username = username;
        this.password = password;
        this.id = id;
    }

    public String getUsername() {
        return this.username;
    }
    public String getPassword() { return this.password; }
    public Long getId() { return this.id; }

    public void setUsername(String username) {
        this.username = username;
    }
    public void setId(Long id) { this.id = id; }
    public void setPassword(String password) {
        this.password = password;
    }
}
