package com.example.steffen.rememo.Logic;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Contact {


    private String mail;

    private FirebaseDatabase mDatabase;
    private DatabaseReference mRef;


    public Contact() {
    }

    public Contact(String mail) {
        this.mail = mail;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void addContact(User user) {

        String mail = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        mail = FirebaseLogic.EncodeString(mail);

        mDatabase = FirebaseDatabase.getInstance();
        Contact contact = new Contact(user.getEmail());
        mRef = mDatabase.getReference().child("users").child(mail).child("contacts").child(FirebaseLogic.EncodeString(user.getEmail()));

        mRef.setValue(contact);
    }

}
