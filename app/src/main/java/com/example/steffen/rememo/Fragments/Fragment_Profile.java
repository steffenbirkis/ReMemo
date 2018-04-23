package com.example.steffen.rememo.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
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
    private ImageView mImageView;
    private String TAG="1";
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


             final View fragmentView = inflater.inflate(R.layout.fragment_profile, container, false);
     final  TextView txt_name = (TextView) fragmentView.findViewById(R.id.txt_name);
     final  TextView txt_workplace = (TextView) fragmentView.findViewById(R.id.txt_workplace);
     final  TextView txt_role = (TextView) fragmentView.findViewById(R.id.txt_role);
     final  TextView txt_background = (TextView) fragmentView.findViewById(R.id.txt_background);
     final  TextView txt_email = (TextView) fragmentView.findViewById(R.id.txt_email);
     final  TextView txt_phone = (TextView) fragmentView.findViewById(R.id.txt_phone);
     mImageView  = (ImageView) fragmentView.findViewById(R.id.profile_image);




     DatabaseReference FirebaseRef=fbd.getReference().child("users");
     DatabaseReference ValueRef=fbd.getReference().child("users").child(FirebaseLogic.EncodeString(FirebaseAuth.getInstance().getCurrentUser().getEmail())).child("photoURL");



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
                txt_email.setText(user.getEmail());
                txt_phone.setText(user.getPhone());
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

        ValueEventListener postListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            String post = dataSnapshot.getValue(String.class);
            System.out.println(post);
            Glide.with(fragmentView.getContext()).load(post).apply(RequestOptions.circleCropTransform()).into(mImageView);

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            // Getting Post failed, log a message
            Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            // ...
        }
    };
ValueRef.addValueEventListener(postListener);

        return fragmentView;
    }
}
