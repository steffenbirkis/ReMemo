package com.example.steffen.rememo.Activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.steffen.rememo.R;

public class Fragment_Appealing extends Fragment {
    public static Fragment_Appealing newInstance() {
        Fragment_Appealing fragment = new Fragment_Appealing();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_appealing, container, false);
    }
}
