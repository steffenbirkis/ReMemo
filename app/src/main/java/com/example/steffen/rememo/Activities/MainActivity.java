package com.example.steffen.rememo.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.steffen.rememo.Fragments.Fragment_Appealing;
import com.example.steffen.rememo.Fragments.Fragment_Feed;
import com.example.steffen.rememo.Fragments.Fragment_Network;
import com.example.steffen.rememo.Fragments.Fragment_Requests;
import com.example.steffen.rememo.R;

public class MainActivity extends AppCompatActivity {

    private Toolbar mTopToolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mTopToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mTopToolbar);

        final BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener
                (new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        Fragment selectedFragment = null;
                        switch (item.getItemId()) {
                            case R.id.action_feed:
                                selectedFragment = Fragment_Feed.newInstance();
                                bottomNavigationView.getMenu().getItem(0).setChecked(true);

                                break;
                            case R.id.action_appealing:
                                selectedFragment = Fragment_Appealing.newInstance();
                                bottomNavigationView.getMenu().getItem(1).setChecked(true);

                                break;
                            case R.id.action_request:
                                selectedFragment = Fragment_Requests.newInstance();
                                bottomNavigationView.getMenu().getItem(2).setChecked(true);

                                break;
                            case R.id.action_network:
                                selectedFragment = Fragment_Network.newInstance();
                                bottomNavigationView.getMenu().getItem(3).setChecked(true);
                                break;
                        }
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.frame_layout, selectedFragment);
                        transaction.commit();
                        return true;
                    }
                });

        //Manually displaying the first fragment - one time only
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, Fragment_Feed.newInstance());
        transaction.addToBackStack("test");
        transaction.commit();
        //Used to select an item programmatically
        //bottomNavigationView.getMenu().getItem(2).setChecked(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, UserPreferences.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

