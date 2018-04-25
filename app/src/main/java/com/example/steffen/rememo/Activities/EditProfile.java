package com.example.steffen.rememo.Activities;

import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.steffen.rememo.Fragments.Fragment_Contact;
import com.example.steffen.rememo.Fragments.Fragment_Feed;
import com.example.steffen.rememo.Logic.Contact;
import com.example.steffen.rememo.Logic.FirebaseLogic;
import com.example.steffen.rememo.Logic.User;
import com.example.steffen.rememo.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;

public class EditProfile extends AppCompatActivity {


    private Button save;
    private EditText name;
    private EditText workplace;
    private EditText role;
    private EditText background;
    private EditText email;
    private EditText phone;
    private User currentUser;

    private Button mSelectImage;
    private StorageReference mStorage;
    private FirebaseStorage mFirebaseStorage;
    private static final int GALLERY_INTENT = 2;
    private DatabaseReference FirebaseRef;
    private ImageView mImageView;
    private Uri image;
    private DatabaseReference mDatabase;
    private List<Contact> cList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_editprofile);
        refreshData();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseLogic.EncodeString(FirebaseAuth.getInstance().getCurrentUser().getEmail())).child("contacts");
        mDatabase.addChildEventListener(contactlistener);
        mDatabase.addChildEventListener(userlistener);
        mStorage = FirebaseStorage.getInstance().getReference();
        mSelectImage = (Button)findViewById(R.id.edit_picture);
        mImageView=(ImageView)findViewById(R.id.editprofile_image);

        cList = new ArrayList<Contact>();
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

                updateUser();

                FirebaseRef=fbd.getReference().child("users").child(tempmail);
                FirebaseRef.child("name").setValue(currentUser.getName());
                FirebaseRef.child("workplace").setValue(currentUser.getWorkplace());
                FirebaseRef.child("email").setValue(mail);
                FirebaseRef.child("background").setValue(currentUser.getBackground());
                FirebaseRef.child("role").setValue(currentUser.getRole());
                FirebaseRef.child("phone").setValue(currentUser.getPhone());
                FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseLogic.EncodeString(FirebaseAuth.getInstance().getCurrentUser().getEmail())).child("photoURL").setValue(image.toString());


                updateData();

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

    public void updateData(){
        DatabaseReference temp_path = FirebaseDatabase.getInstance().getReference().child("users");
        List<String> emails = new ArrayList<String>();
        String current = FirebaseLogic.EncodeString(FirebaseAuth.getInstance().getCurrentUser().getEmail());
        for(Contact c:cList){
            emails.add(FirebaseLogic.EncodeString(c.getMail()));
        }
        for(String s:emails){
            temp_path.child(s).child("contacts").child(current).child("name").setValue(currentUser.getName());
            temp_path.child(s).child("contacts").child(current).child("workplace").setValue(currentUser.getWorkplace());
            temp_path.child(s).child("contacts").child(current).child("role").setValue(currentUser.getRole());
            temp_path.child(s).child("contacts").child(current).child("background").setValue(currentUser.getBackground());
            temp_path.child(s).child("contacts").child(current).child("phone").setValue(currentUser.getPhone());
            temp_path.child(s).child("contacts").child(current).child("photo").setValue(currentUser.getPhotoURL());

        }
    }

    public void updateImage(Uri pic){
        Glide.with(this).load(pic).into(mImageView);
    }

    public void updateUser(){
        currentUser.setName(name.getText().toString());
        currentUser.setWorkplace(workplace.getText().toString());
        currentUser.setRole(role.getText().toString());
        currentUser.setBackground(background.getText().toString());
        currentUser.setPhone(phone.getText().toString());
        currentUser.setPhotoURL(mImageView.toString());

    }


    ChildEventListener contactlistener = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot snapshot, String s) {

            Contact contact = snapshot.getValue(Contact.class);
            cList.add(contact);
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            System.out.println("The read failed: " + databaseError.getCode());
        }

        public void onChildRemoved(DataSnapshot dataSnapshot) {
            return;
        }

        public void onChildChanged(DataSnapshot dataSnapshot, String s) {
        }

        public void onChildMoved(DataSnapshot dataSnapshot, String s) {
        }

    };
    ChildEventListener userlistener = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot snapshot, String s) {
            FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
            String mail=firebaseAuth.getCurrentUser().getEmail();
            User user = snapshot.getValue(User.class);
            String robust1 = FirebaseLogic.EncodeString(mail.toLowerCase());
            String robust2 = FirebaseLogic.EncodeString(user.getEmail().toLowerCase());
            if(robust1.equals(robust2)) {
                currentUser = user;
                Glide.with(getApplicationContext()).load(currentUser.getPhotoURL()).apply(RequestOptions.circleCropTransform()).into(mImageView);
                name.setText(currentUser.getName());
                workplace.setText(currentUser.getWorkplace());
                role.setText(currentUser.getRole());
                background.setText(currentUser.getBackground());
                phone.setText(currentUser.getPhone());
            }

        }


        @Override
        public void onCancelled(DatabaseError databaseError) {
            System.out.println("The read failed: " + databaseError.getCode());
        }

        public void onChildRemoved(DataSnapshot dataSnapshot) {
            return;
        }

        public void onChildChanged(DataSnapshot dataSnapshot, String s) {
        }

        public void onChildMoved(DataSnapshot dataSnapshot, String s) {
        }

    };

}