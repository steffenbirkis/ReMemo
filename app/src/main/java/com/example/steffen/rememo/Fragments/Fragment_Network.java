package com.example.steffen.rememo.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.steffen.rememo.R;

public class Fragment_Network extends Fragment {

    public static Fragment_Network newInstance() {
        Fragment_Network fragment = new Fragment_Network();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        container.removeAllViews();
        View fragmentView = inflater.inflate(R.layout.fragment_feed, container, false);

        Button btn_contacts = fragmentView.findViewById(R.id.view_contacts);
        btn_contacts.setOnClickListener(onViewContacts);

        return fragmentView;


    }


    private View.OnClickListener onViewContacts = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Fragment contact = Fragment_Contact.newInstance();
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.frame_layout, contact);
            transaction.commit();
        }
    };
}


