package com.example.steffen.rememo.Logic;

import android.provider.ContactsContract;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FirebaseLogic {
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private User mUser;
    private ChildEventListener mChildEventlistener;

    public static void pushUser(User user){
        FirebaseDatabase database=FirebaseDatabase.getInstance();
        DatabaseReference myRef=database.getReference();

        myRef.child("users").child(user.getMail()).setValue(user);

    }

   public void listenOnMail(String email) {                        //Kan hente 1 vilkårlig bruker etter mail
// Attach a listener to read the data at our posts reference
     final String tempmail = User.EncodeString(email);
        mUser=new User();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference().child("users");
       myRef.addChildEventListener(mChildEventlistener=new ChildEventListener() {
           @Override
           public void onChildAdded(DataSnapshot dataSnapshot, String s) {
               User hei;
               hei = dataSnapshot.getValue(User.class);
               if (hei.getMail().equals(tempmail)) {
                   setUser(hei);



               }
           }

           @Override
           public void onCancelled(DatabaseError databaseError) {
               System.out.println("The read failed: " + databaseError.getCode());
           }

           public void onChildRemoved(DataSnapshot dataSnapshot) {
               return;
           }

           public void onChildChanged(DataSnapshot dataSnapshot, String s) {
           }

           public void onChildMoved(DataSnapshot dataSnapshot, String s) {
           }


       });


   }
   public User getMuser(){
       return mUser;
   }
   public void detachFBListener(){
        myRef.removeEventListener(mChildEventlistener);
   }
   public void setUser(User user){
        mUser=user;

   }
}

