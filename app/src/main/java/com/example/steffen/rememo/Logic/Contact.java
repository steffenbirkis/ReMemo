package com.example.steffen.rememo.Logic;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Contact {
    private String mail;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mRef;


    public Contact(){}
    public Contact(String mail){
        this.mail=mail;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void addContact(Contact contact,User user){

       String mail= FirebaseAuth.getInstance().getCurrentUser().getEmail();
      mail=FirebaseLogic.EncodeString(mail);
       
        mDatabase=FirebaseDatabase.getInstance();
        mRef=mDatabase.getReference().child("contacts").child(mail);
        contact=new Contact(user.getEmail());
        mRef.setValue(contact);
    }

}
