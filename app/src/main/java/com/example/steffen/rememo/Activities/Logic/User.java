package com.example.steffen.rememo.Activities.Logic;

/**
 * Created by Steffen on 22.03.2018.
 */

public class User {
    private String Name;
    private String Picture;
    private String Workplace;
    private String Role;
    private String [] Contacts;

    public User(String name, String picture, String workplace, String role, String[] contacts) {
        Name = name;
        Picture = picture;
        Workplace = workplace;
        Role = role;
        Contacts = contacts;
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

    public String[] getContacts() {
        return Contacts;
    }

    public void setContacts(String[] contacts) {
        Contacts = contacts;
    }

    public void ChangeUser(String name, String Picture, String Workplace, String Role, String [] Contacts){
        this.Name=name;
        this.Picture=Picture;
        this.Workplace=Workplace;
        this.Role=Role;
        this.Contacts=Contacts;}





}
