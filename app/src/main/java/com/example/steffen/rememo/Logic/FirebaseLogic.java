package com.example.steffen.rememo.Logic;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FirebaseLogic {
    private FirebaseDatabase mFirebase;
    private DatabaseReference myRef;
    private User mUser;

    public static void pushUser(User user){
        FirebaseDatabase database=FirebaseDatabase.getInstance();
        DatabaseReference myRef=database.getReference();

        myRef.child("users").child(user.getMail()).setValue(user);

    }

   public User getDBUser(String email){
// Attach a listener to read the data at our posts reference
        myRef.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            mUser = dataSnapshot.getValue(User.class);
        }
        @Override
        public void onCancelled(DatabaseError databaseError) {
            System.out.println("The read failed: " + databaseError.getCode());
        }
    });
        return mUser;
}
}

