package com.example.steffen.rememo.Logic;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Contact {


    private String mail;
    private boolean request;
    private boolean acknowledgement;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mRef;


    public Contact() {
    }

    public Contact(String mail, boolean request, boolean acknowledgement) {
        this.mail = mail;
        this.request = request;
        this.acknowledgement = acknowledgement;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public boolean isRequest() {
        return request;
    }

    public void setRequest(boolean request) {
        this.request = request;
    }

    public boolean isAcknowledgement() {
        return acknowledgement;
    }

    public void setAcknowledgement(boolean acknowledgement) {
        this.acknowledgement = acknowledgement;
    }

    public void addContact(User currentuser, User targetuser) {


        mDatabase = FirebaseDatabase.getInstance();
        Contact cUserContact = new Contact(currentuser.getEmail(),true, false);
        Contact tUserContact = new Contact(targetuser.getEmail(),false,true);

        String cUserMail = FirebaseLogic.EncodeString(currentuser.getEmail());
        String tUserMail = FirebaseLogic.EncodeString(targetuser.getEmail());

        mRef = mDatabase.getReference().child("users");
        mRef.child(cUserMail).child("contacts").child(tUserMail).setValue(tUserContact);
        mRef.child(tUserMail).child("contacts").child(cUserMail).setValue(cUserContact);

    }

}
