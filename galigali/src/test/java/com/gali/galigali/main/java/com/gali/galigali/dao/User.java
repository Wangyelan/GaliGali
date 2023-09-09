package com.gali.galigali.dao;

import lombok.Data;

@Data
public class User {
    String username;
    String password;
    String role;
    String state;
    public User(String u,String p){
        this.username = u;
        this.password = p;
        this.role = "user";
        this.state = "normal";
    }
}
