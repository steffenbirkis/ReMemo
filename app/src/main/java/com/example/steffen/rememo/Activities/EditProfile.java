package com.example.steffen.rememo.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.steffen.rememo.Logic.FirebaseLogic;
import com.example.steffen.rememo.Logic.User;
import com.example.steffen.rememo.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditProfile extends AppCompatActivity {


    private Button save;
    private EditText name;
    private EditText workplace;
    private EditText role;
    private EditText background;
    private EditText email;
    private EditText phone;
    User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_editprofile);

        save = (Button)findViewById(R.id.save_changes);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshData();
                FirebaseDatabase fbd=FirebaseDatabase.getInstance();
                String mail= FirebaseAuth.getInstance().getCurrentUser().getEmail();
                final String tempmail= FirebaseLogic.EncodeString(mail);

                DatabaseReference FirebaseRef=fbd.getReference().child("users").child(tempmail);
                User user=new User(name.getText().toString(),workplace.getText().toString(),role.getText().toString());
                FirebaseRef.setValue(user);
              //  FirebaseRef.child("name").setValue(name.getText().toString());
              //  FirebaseRef.child("workplace").setValue(workplace.getText().toString());
              //  FirebaseRef.child("role").setValue(role.getText().toString());
              //  FirebaseRef.child("background").setValue(background.getText().toString());
              //  FirebaseRef.child("email").setValue(email.getText().toString());
              //  FirebaseRef.child("phone").setValue(phone.getText().toString());
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
    public void refreshData(){
        name = (EditText) findViewById(R.id.edit_name);
        workplace = (EditText) findViewById(R.id.edit_workplace);
        role = (EditText) findViewById(R.id.edit_role);
        background = (EditText) findViewById(R.id.edit_background);
        email = (EditText) findViewById(R.id.edit_email);
        phone = (EditText) findViewById(R.id.edit_phone);

    }

}