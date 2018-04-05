package com.example.steffen.rememo.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.steffen.rememo.Logic.FirebaseLogic;
import com.example.steffen.rememo.Logic.User;
import com.example.steffen.rememo.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

import static com.example.steffen.rememo.Logic.FirebaseLogic.pushUser;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "EmailPassword";

    private FirebaseAuth mAuth;
    private EditText name;
    private EditText email;
    private EditText phone;
    private EditText workplace;
    private EditText role;
    private EditText password;
    private EditText retypePassword;
    private User mUser;
    private FirebaseLogic fLogic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        name = (EditText) findViewById(R.id.et_name);
        email = (EditText) findViewById(R.id.et_email);
        phone = (EditText) findViewById(R.id.et_phone);
        workplace = (EditText) findViewById(R.id.et_workplace);
        role = (EditText) findViewById(R.id.et_role);
        password = (EditText) findViewById(R.id.et_password);
        retypePassword = (EditText) findViewById(R.id.et_passwordcheck);
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    public void signIn(String emails, String password) {

        mAuth.signInWithEmailAndPassword(emails, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        // ...
                    }
                });
        mUser=fLogic.getDBUser(emails);

    }

    public void updateUI(FirebaseUser user) {

        if (user == null) {
            setContentView(R.layout.activity_login);
        } else {
            Intent intent=new Intent(this,MainActivity.class);
            startActivity(intent);
        }


    }

    public void createUser(View v) {
        refreshData();
        createFireUser();



    }

    private void createFireUser(){
        String temp_email = email.getText().toString();
        String temp_password = password.getText().toString();
        mAuth.createUserWithEmailAndPassword(temp_email, temp_password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            List<String> liste = new ArrayList<>();
                            liste.add("empty");
                            mUser = new User(name.getText().toString(), "bilde", workplace.getText().toString(), role.getText().toString(), liste,User.EncodeString( email.getText().toString()));
                            pushUser(mUser);
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }


                    }
                });
    }

    private void refreshData(){
        name = (EditText) findViewById(R.id.et_name);
        email = (EditText) findViewById(R.id.et_email);
        phone = (EditText) findViewById(R.id.et_phone);
        workplace = (EditText) findViewById(R.id.et_workplace);
        role = (EditText) findViewById(R.id.et_role);
        password = (EditText) findViewById(R.id.et_password);
        retypePassword = (EditText) findViewById(R.id.et_passwordcheck);
    }
    public User getmUser(){
        return mUser;
    }

}
