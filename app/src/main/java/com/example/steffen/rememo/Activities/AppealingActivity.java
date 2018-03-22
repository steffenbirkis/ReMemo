package com.example.steffen.rememo.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.steffen.rememo.R;

public class AppealingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appealing);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
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


