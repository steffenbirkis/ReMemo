package com.example.steffen.rememo.Activities;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import com.example.steffen.rememo.R;
import com.google.firebase.auth.FirebaseAuth;



/**
 * Created by kevin on 04-Apr-18.
 */

public class UserPreferences extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(android.R.id.content, new UserPrefsFragment());
        transaction.commit();
    }

    public static class UserPrefsFragment extends PreferenceFragment {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            PreferenceManager manager = getPreferenceManager();
            manager.setSharedPreferencesName("userprefs");
            addPreferencesFromResource(R.xml.userprefs);
            Preference signout = findPreference("signout");
            signout.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                public boolean onPreferenceClick(Preference preference) {
                    FirebaseAuth mAuth = FirebaseAuth.getInstance();
                    mAuth.signOut();
                    Intent intent = new Intent(preference.getContext(), LoginActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                    return true;
                }
            });
            Preference editprofile = findPreference("edit_profile");
            editprofile.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                public boolean onPreferenceClick(Preference preference) {
                    Intent intent = new Intent(preference.getContext(), EditProfile.class);
                    startActivity(intent);
                    getActivity().finish();
                    return true;
                }
            });

        }
    }
}