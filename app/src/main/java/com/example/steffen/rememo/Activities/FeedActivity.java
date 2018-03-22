package com.example.steffen.rememo.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.steffen.rememo.Activities.Logic.User;
import com.example.steffen.rememo.R;

import java.util.ArrayList;
import java.util.List;

public class FeedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
        List <String> liste=new ArrayList<>();

        liste.add("Kevin");
        liste.add("Steph");

        User Steffen=new User("steffen Birkeland","bilde","arbeidsplass","rolle",liste,"41046505");
        Steffen.pushUser(Steffen);
    }

    public void onHome(View view) {
        Intent myIntent = new Intent(view.getContext(), FeedActivity.class);
        startActivityForResult(myIntent, 0);
    }

    public void onAppealing(View view) {
        Intent myIntent = new Intent(view.getContext(), AppealingActivity.class);
        startActivityForResult(myIntent, 0);
    }

    public void onContact(View view) {
        Intent myIntent = new Intent(view.getContext(), ContactActivity.class);
        startActivityForResult(myIntent, 0);
    }

    public void onProfile(View view) {
        Intent myIntent = new Intent(view.getContext(), ProfileActivity.class);
        startActivityForResult(myIntent, 0);
    }
}
