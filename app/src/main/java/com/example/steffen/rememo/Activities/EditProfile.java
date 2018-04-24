package com.example.steffen.rememo.Activities;

import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.example.steffen.rememo.Logic.FirebaseLogic;
import com.example.steffen.rememo.Logic.User;
import com.example.steffen.rememo.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.UploadTask;

public class EditProfile extends AppCompatActivity {


    private Button save;
    private EditText name;
    private EditText workplace;
    private EditText role;
    private EditText background;
    private EditText email;
    private EditText phone;
    User user;

    private Button mSelectImage;
    private StorageReference mStorage;
    private FirebaseStorage mFirebaseStorage;
    private static final int GALLERY_INTENT = 2;
    private DatabaseReference FirebaseRef;
    private ImageView mImageView;
    private Uri image;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_editprofile);

        mStorage = FirebaseStorage.getInstance().getReference();
        mSelectImage = (Button)findViewById(R.id.edit_picture);
        mImageView=(ImageView)findViewById(R.id.editprofile_image);

        mSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/jpeg");
                intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                startActivityForResult(Intent.createChooser(intent, "Complete action using"), GALLERY_INTENT);
                }});

        save = (Button)findViewById(R.id.save_changes);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshData();
                FirebaseDatabase fbd=FirebaseDatabase.getInstance();
                String mail= FirebaseAuth.getInstance().getCurrentUser().getEmail();
                final String tempmail= FirebaseLogic.EncodeString(mail);

                FirebaseRef=fbd.getReference().child("users").child(tempmail);
                User user=new User(name.getText().toString(),workplace.getText().toString(),mail);
                FirebaseRef.setValue(user);
                FirebaseRef.child("background").setValue(background.getText().toString());
                FirebaseRef.child("role").setValue(role.getText().toString());
                FirebaseRef.child("phone").setValue(phone.getText().toString());
                FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseLogic.EncodeString(FirebaseAuth.getInstance().getCurrentUser().getEmail())).child("photoURL").setValue(image.toString());

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
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_INTENT && resultCode == RESULT_OK) {

            Uri selectedImageUri = data.getData();

            // Get a reference to store file at chat_photos/<FILENAME>
            StorageReference photoRef = mStorage.child(selectedImageUri.getLastPathSegment());

            // Upload file to Firebase Storage
            photoRef.putFile(selectedImageUri)
                    .addOnSuccessListener(this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(EditProfile.this, "Upload done", Toast.LENGTH_LONG).show();
                            // When the image has successfully uploaded, we get its download URL
                            image = taskSnapshot.getDownloadUrl();
                            System.out.println(image.toString());
                            updateImage(image);

                            // Set the download URL to the message box, so that the user can send it to the database

                        }


                    });
        }


    }

    public void updateImage(Uri pic){
        Glide.with(this).load(pic).into(mImageView);
    }

}