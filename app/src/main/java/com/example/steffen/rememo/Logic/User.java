package com.example.steffen.rememo.Logic;

import java.util.List;


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


    public User() {
    }

    public User(String name, String workplace, String email) {
        this.name = name;
        this.workplace = workplace;
        this.email = email;

    }

    public User(String name, String workplace, String role, String background, String email, String phone, String photoURL, List<Appealing> applist, List<Contact> contact) {
        this.name = name;
        this.workplace = workplace;
        this.role = role;
        this.background = background;
        this.email = email;
        this.phone = phone;
        this.photoURL = photoURL;


    }

    public User(String name, String workplace, String role, String background, String email, String phone, String photoURL) {
        this.name = name;
        this.workplace = workplace;
        this.role = role;
        this.background = background;
        this.email = email;
        this.phone = phone;
        this.photoURL = photoURL;
    }

  /*  public List<String> getAppealingStringList(){
        List<String> list = new ArrayList<>();
        Iterator<Appealing> iterator = appealing.iterator();
        while(iterator.hasNext()){
            list.add(iterator.next().getMail());
        }

        return list;
    }*/

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

    public String getPhotoURL() {
        return photoURL;
    }

    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }


    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
  /*  public List<Appealing> getAppealing() {
        return appealing;
    }

    public void setAppealing(List<Appealing> appealing) {
        this.appealing = appealing;
    }

    public List<Contact> getContacts() {
        return contacts;
    }

    public void setContactList(List<Contact> contactList) {
        this.contacts = contactList;
    }*/

}
