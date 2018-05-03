package com.example.steffen.rememo.Logic;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseLogic {


    private User mUser;

    public FirebaseLogic() {

    }

    public void setUser(User user) {
        mUser = user;

    }

    public static String EncodeString(String string) {
        return string.replace(".", ",");
    }

}

