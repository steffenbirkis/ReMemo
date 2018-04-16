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
    private String background;
    private String email;
    private String phone;
    private String photoURL;

    public User(){}

    public User(String name, String workplace, String role, String photoURL){
        this.name=name;
        this.workplace=workplace;
        this.role=role;
        this.photoURL = photoURL;

    }

    public User(String name, String workplace, String role, String background, String email, String phone, String photoURL){
        this.name=name;
        this.workplace=workplace;
        this.role=role;
        this.background=background;
        this.email=email;
        this.phone=phone;
        this.photoURL = photoURL;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

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

    public String getPhotoURL() { return photoURL; }

    public void setPhotoURL(String photoURL) { this.photoURL = photoURL; }


    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
