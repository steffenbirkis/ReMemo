package com.example.steffen.rememo.Logic;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

/**
 * Created by Steffen on 22.03.2018.
 */

public class User {
    private String Name;
    private String Picture;
    private String Workplace;
    private String Role;
    private List<String> Contacts;
    private String Mobil;

    public User(){}



    public User(String name, String picture, String workplace, String role, List<String> contacts,String mobil) {
        Name = name;

        Picture = picture;
        Workplace = workplace;
        Role = role;
        Contacts = contacts;
        Mobil=mobil;

    }
    public String getMobil() {
        return Mobil;
    }

    public void setMobil(String mobil) {
        Mobil = mobil;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPicture() {
        return Picture;
    }

    public void setPicture(String picture) {
        Picture = picture;
    }

    public String getWorkplace() {
        return Workplace;
    }

    public void setWorkplace(String workplace) {
        Workplace = workplace;
    }

    public String getRole() {
        return Role;
    }

    public void setRole(String role) {
        Role = role;
    }

    public List getContacts() {
        return Contacts;
    }

    public void setContacts(List<String> contacts) {
        Contacts = contacts;
    }

    public void ChangeUser(String name, String Picture, String Workplace, String Role, List <String> Contacts,String mobil){
        this.Name=name;
        this.Picture=Picture;
        this.Workplace=Workplace;
        this.Role=Role;
        this.Contacts=Contacts;
        this.Mobil=mobil;
    }
    public void pushUser(User user){
        FirebaseDatabase database=FirebaseDatabase.getInstance();
        DatabaseReference myRef=database.getReference();

        myRef.child("users").child(user.getMobil()).setValue(user);

    }







}
