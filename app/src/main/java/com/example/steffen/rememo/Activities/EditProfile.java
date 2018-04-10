package com.example.steffen.rememo.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.steffen.rememo.Logic.User;
import com.example.steffen.rememo.R;

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
        // user = fra firebase
        save = (Button)findViewById(R.id.save_changes);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshData();
                /*
                user.setName(name.getText().toString());
                user.setWorkplace(workplace.getText().toString());
                user.setRole(role.getText().toString());
                user.setBackground(background.getText().toString());
                user.setEmail(email.getText().toString());
                user.setPhone(phone.getText().toString());
                */

            }
        });

    }
    public void refreshData(){
        name = (EditText) findViewById(R.id.edit_name);
        workplace = (EditText) findViewById(R.id.edit_workplace);
        role = (EditText) findViewById(R.id.edit_workplace);
        background = (EditText) findViewById(R.id.edit_background);
        email = (EditText) findViewById(R.id.edit_email);
        phone = (EditText) findViewById(R.id.edit_phone);

    }

}