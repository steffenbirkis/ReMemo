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

public class Fragment_Profile extends Fragment {
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

        View fragmentView = inflater.inflate(R.layout.fragment_profile, container, false);
        TextView txt_name = (TextView) fragmentView.findViewById(R.id.txt_name);
        TextView txt_workplace = (TextView) fragmentView.findViewById(R.id.txt_workplace);
        TextView txt_role = (TextView) fragmentView.findViewById(R.id.txt_role);
        TextView txt_background = (TextView) fragmentView.findViewById(R.id.txt_background);
        TextView txt_email = (TextView) fragmentView.findViewById(R.id.txt_email);
        TextView txt_phone = (TextView) fragmentView.findViewById(R.id.txt_phone);
        ImageView profile_img = (ImageView) fragmentView.findViewById(R.id.profile_image);

        FirebaseLogic fbl=new FirebaseLogic();
        fbl.listenOnMail(FirebaseAuth.getInstance().getCurrentUser().getEmail());
        User current=fbl.getMuser();
        System.out.println(current.getMail());


        txt_name.setText(current.getName());
        txt_workplace.setText("workplace");
        txt_role.setText("role");
        txt_background.setText("pending");
        txt_email.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());
        txt_phone.setText("Dummy phone");
        profile_img.setImageResource(R.drawable.dummy_img);

        return fragmentView;
    }
}
