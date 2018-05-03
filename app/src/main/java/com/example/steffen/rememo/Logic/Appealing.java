package com.example.steffen.rememo.Logic;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Appealing {
    private String mail;
    private String name;
    private String workplace;
    private String role;
    private String background;
    private String phone;
    private String photo;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mRef;


    public Appealing() {
    }

    public Appealing(String mail) {
        this.mail = mail;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public void addAppealing(User user) {
        String mail = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        mail = FirebaseLogic.EncodeString(mail).toLowerCase();

        mDatabase = FirebaseDatabase.getInstance();
        mRef = mDatabase.getReference().child("users").child(mail).child("appealing").child(FirebaseLogic.EncodeString(user.getEmail()));
        Appealing appealing = new Appealing();
        appealing.setMail(user.getEmail());
        appealing.setBackground(user.getBackground());
        appealing.setName(user.getName());
        appealing.setPhone(user.getPhone());
        appealing.setPhoto(user.getPhotoURL());
        appealing.setRole(user.getRole());
        appealing.setWorkplace(user.getWorkplace());
        mRef.setValue(appealing);
    }
}
