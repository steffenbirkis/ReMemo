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
        navigation.setSelectedItemId(R.id.navigation_feed);

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
                    Intent myIntent = new Intent(getApplicationContext(), FeedActivity.class);
                    startActivityForResult(myIntent, 0);
                    return true;
                case R.id.navigation_appealing:
                    Intent myIntent2 = new Intent(getApplicationContext(), AppealingActivity.class);
                    startActivityForResult(myIntent2, 0);
                    return true;
                case R.id.navigation_contacts:
                    Intent myIntent3 = new Intent(getApplicationContext(), ContactActivity.class);
                    startActivityForResult(myIntent3, 0);
                    return true;
                case R.id.navigation_profile:
                    Intent myIntent4 = new Intent(getApplicationContext(), ProfileActivity.class);
                    startActivityForResult(myIntent4, 0);
                    return true;
            }
            return false;
        }
    };
}