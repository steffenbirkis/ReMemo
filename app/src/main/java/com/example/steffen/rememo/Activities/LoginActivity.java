package com.example.steffen.rememo.Activities;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.steffen.rememo.Logic.User;
import com.example.steffen.rememo.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "EmailPassword";

    private FirebaseAuth mAuth;
    private EditText Name;
    private EditText Email;
    private EditText Phone;
    private EditText Workplace;
    private EditText Role;
    private EditText Password;
    private EditText RetypePassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth=FirebaseAuth.getInstance();

        Name=(EditText)findViewById(R.id.et_name);
        Email=(EditText)findViewById(R.id.et_email);
        Phone=(EditText)findViewById(R.id.et_phone);
        Workplace=(EditText)findViewById(R.id.et_workplace);
        Role=(EditText)findViewById(R.id.et_role);
        Password=(EditText)findViewById(R.id.et_password);
        RetypePassword=(EditText)findViewById(R.id.et_passwordcheck);
    }
    public void createUser(){
        String email=Email.getText().toString();
        String password=Password.getText().toString();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
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
        // [END create_user_with_email]



    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    public void signIn(String email, String password){

        mAuth.signInWithEmailAndPassword(email, password)
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
    }
    public void updateUI(FirebaseUser user){
        if(user==null){
            setContentView(R.layout.activity_login);
        }else{
            setContentView(R.layout.activity_feed);
        }


    }

    public void onClick(View v){
        int i=v.getId();
        System.out.println(Email.getText().toString());
        if(i==R.id.Create){
            Log.d(TAG,Email.getText().toString());
            createUser();
            List<String> liste = new ArrayList<>();


            liste.add("empty");
            User user=new User(Name.getText().toString(),"bilde",Workplace.getText().toString(),Role.getText().toString(),liste,Phone.getText().toString());
            user.pushUser(user);
        }


    }
}
