package com.example.steffen.rememo.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.steffen.rememo.Logic.FirebaseLogic;
import com.example.steffen.rememo.Logic.User;
import com.example.steffen.rememo.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Fragment_Profile extends Fragment {
    private User mCurrent;
    public static Fragment_Profile newInstance() {
        Fragment_Profile fragment = new Fragment_Profile();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FirebaseDatabase fbd=FirebaseDatabase.getInstance();


             View fragmentView = inflater.inflate(R.layout.fragment_profile, container, false);
     final  TextView txt_name = (TextView) fragmentView.findViewById(R.id.txt_name);
     final  TextView txt_workplace = (TextView) fragmentView.findViewById(R.id.txt_workplace);
     final  TextView txt_role = (TextView) fragmentView.findViewById(R.id.txt_role);
     final  TextView txt_background = (TextView) fragmentView.findViewById(R.id.txt_background);
     final  TextView txt_email = (TextView) fragmentView.findViewById(R.id.txt_email);
     final  TextView txt_phone = (TextView) fragmentView.findViewById(R.id.txt_phone);
     final  ImageView profile_img = (ImageView) fragmentView.findViewById(R.id.profile_image);




     DatabaseReference FirebaseRef=fbd.getReference().child("users");



        FirebaseRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot snapshot, String s) {
                FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
                String mail=firebaseAuth.getCurrentUser().getEmail();
                User user=snapshot.getValue(User.class);
                String robust1 = FirebaseLogic.EncodeString(mail.toLowerCase());
                String robust2 = FirebaseLogic.EncodeString(user.getEmail().toLowerCase());
               if(robust1.equals(robust2)){

                txt_name.setText(user.getName());
                txt_workplace.setText(user.getWorkplace());
                txt_role.setText(user.getRole());
                txt_background.setText(user.getBackground());
                txt_email.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());
                txt_phone.setText(user.getRole());
                profile_img.setImageResource(R.drawable.dummy_img);
            }}


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

        return fragmentView;
    }
}
