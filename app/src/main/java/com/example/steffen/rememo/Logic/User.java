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

    public User(){}

    public User(String name, String workplace, String role, String background, String email, String phone){
        this.name = name;
        this.workplace = workplace;
        this.role = role;
        this.background = background;
        this.email = email;
        this.phone = phone;

    }

    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public static String EncodeString(String string) {
        return string.replace(".", ",");
    }

    public static String DecodeString(String string) {
        return string.replace(",", ".");
    }
    public User getCurrent(){
        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        String email=user.getEmail();
        FirebaseLogic fetch=new FirebaseLogic();
        fetch.listenOnMail(email);
        User current=fetch.getMuser();
        return current;
    }











}
