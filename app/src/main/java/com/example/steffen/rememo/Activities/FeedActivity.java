package com.example.steffen.rememo.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.steffen.rememo.Logic.User;
import com.example.steffen.rememo.R;

import java.util.ArrayList;
import java.util.List;

public class FeedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        List<String> liste = new ArrayList<>();

        liste.add("Kevin");
        liste.add("Steph");

        User Steffen = new User("steffen Birkeland", "bilde", "arbeidsplass", "rolle", liste, "41046505");
        Steffen.pushUser(Steffen);

    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_feed:
                    setContentView(R.layout.activity_feed);
                    return true;
                case R.id.navigation_appealing:
                    setContentView(R.layout.activity_appealing);
                    return true;
                case R.id.navigation_contacts:
                    setContentView(R.layout.activity_contact);
                    return true;
                case R.id.navigation_profile:
                    setContentView(R.layout.activity_profile);
                    return true;
            }
            return false;
        }
    };
}