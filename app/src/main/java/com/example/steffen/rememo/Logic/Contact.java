package com.example.steffen.rememo.Logic;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Contact {


    private String mail;
    private boolean request;
    private boolean acknowledgement;
    private String name;
    private String workplace;
    private String role;
    private String background;
    private String phone;
    private String photo;
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

    public void requestContact(User currentuser, User targetuser) {

        mDatabase = FirebaseDatabase.getInstance();
        Contact cUserContact = new Contact(currentuser.getEmail(),true, false);
        cUserContact.setName(currentuser.getName());
        cUserContact.setWorkplace(currentuser.getWorkplace());
        cUserContact.setRole(currentuser.getRole());
        cUserContact.setBackground(currentuser.getBackground());
        cUserContact.setPhone(currentuser.getPhone());
        cUserContact.setPhoto(currentuser.getPhotoURL());

        Contact tUserContact = new Contact(targetuser.getEmail(),false,true);
        tUserContact.setName(targetuser.getName());
        tUserContact.setWorkplace(targetuser.getWorkplace());
        tUserContact.setRole(targetuser.getRole());
        tUserContact.setBackground(targetuser.getBackground());
        tUserContact.setPhone(targetuser.getPhone());
        tUserContact.setPhoto(targetuser.getPhotoURL());

        String cUserMail = FirebaseLogic.EncodeString(currentuser.getEmail());
        String tUserMail = FirebaseLogic.EncodeString(targetuser.getEmail());

        mRef = mDatabase.getReference().child("users");
        mRef.child(cUserMail).child("contacts").child(tUserMail).setValue(tUserContact);
        mRef.child(tUserMail).child("contacts").child(cUserMail).setValue(cUserContact);

    }

    public void ackContact(String currentmail, Contact target){
        mDatabase = FirebaseDatabase.getInstance();
        Contact cUserContact = new Contact(currentmail,true, true);
        target.setRequest(true);
        target.setAcknowledgement(true);

        String cUserMail = FirebaseLogic.EncodeString(currentmail);
        String tUserMail = FirebaseLogic.EncodeString(target.getMail());

        mRef = mDatabase.getReference().child("users");
        mRef.child(cUserMail).child("contacts").child(tUserMail).setValue(target);
        mRef.child(tUserMail).child("contacts").child(cUserMail).child("acknowledgement").setValue(true);
        mRef.child(tUserMail).child("contacts").child(cUserMail).child("request").setValue(true);

    }

    public void denyContact(String currentmail, Contact target){
        String cUserMail = FirebaseLogic.EncodeString(currentmail);
        String tUserMail = FirebaseLogic.EncodeString(target.getMail());

        mRef = mDatabase.getInstance().getReference().child("users");
        mRef.child(cUserMail).child("contacts").child(tUserMail).removeValue();
        mRef.child(tUserMail).child("contacts").child(cUserMail).removeValue();

    }
}
