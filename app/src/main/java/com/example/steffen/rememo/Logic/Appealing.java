package com.example.steffen.rememo.Logic;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Appealing {
    private String mail;
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

    public void addAppealing(User user) {

        String mail = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        mail = FirebaseLogic.EncodeString(mail);

        mDatabase = FirebaseDatabase.getInstance();
        mRef = mDatabase.getReference().child("users").child(mail).child("appealing").child(FirebaseLogic.EncodeString(user.getEmail()));
        Contact contact = new Contact(user.getEmail());
        mRef.setValue(contact);
    }

}
