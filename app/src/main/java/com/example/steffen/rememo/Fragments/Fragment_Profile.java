package com.example.steffen.rememo.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.steffen.rememo.R;

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

        txt_name.setText("Dummy navn");
        txt_workplace.setText("Dummy workplace");
        txt_role.setText("Dummy role");
        txt_background.setText("Dummy background");
        txt_email.setText("Dummy email");
        txt_phone.setText("Dummy phone");

        return fragmentView;
    }
}
