package com.example.steffen.rememo.Logic;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


/**
 * Created by Steffen on 22.03.2018.
 */

public class User {
    private String name;
    private String workplace;
    private String role;

    public User(){}

    public String getWorkplace() {
        return workplace;
    }

    public void setWorkplace(String workplace) {
        this.workplace = workplace;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public User(String name, String workplace, String role){
        this.name=name;
        this.workplace=workplace;
        this.role=role;

    }

    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }













}
