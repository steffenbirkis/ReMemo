package com.example.steffen.rememo.Activities;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.steffen.rememo.Logic.Contact;
import com.example.steffen.rememo.Logic.FirebaseLogic;
import com.example.steffen.rememo.Logic.User;
import com.example.steffen.rememo.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;

public class EditProfile extends AppCompatActivity {


    private Button save;
    private EditText name;
    private EditText workplace;
    private EditText role;
    private EditText background;
    private EditText phone;
    private User currentUser;
    private Button mSelectImage;
    private StorageReference mStorage;
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
        currentUser = new User();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseLogic.EncodeString(FirebaseAuth.getInstance().getCurrentUser().getEmail())).child("contacts");
        mDatabase.addChildEventListener(contactlistener);
        DatabaseReference userpath = FirebaseDatabase.getInstance().getReference().child("users");
        userpath.addChildEventListener(userlistener);
        mStorage = FirebaseStorage.getInstance().getReference();
        mSelectImage = (Button) findViewById(R.id.edit_picture);
        mImageView = (ImageView) findViewById(R.id.editprofile_image);

        cList = new ArrayList<Contact>();
        mSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/jpeg");
                intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                startActivityForResult(Intent.createChooser(intent, "Complete action using"), GALLERY_INTENT);
            }
        });

        save = (Button) findViewById(R.id.save_changes);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshData();
                updateUser();
                FirebaseDatabase fbd = FirebaseDatabase.getInstance();
                String mail = FirebaseAuth.getInstance().getCurrentUser().getEmail();
                final String tempmail = FirebaseLogic.EncodeString(mail);
                if(currentUser.getName()!=null&&currentUser.getWorkplace()!=null&&currentUser.getBackground()!=null&&currentUser.getPhone()!=null&&currentUser.getRole()!=null&&image!=null){
                FirebaseRef = fbd.getReference().child("users").child(tempmail);
                FirebaseRef.child("email").setValue(mail);
                FirebaseRef.child("name").setValue(currentUser.getName());
                FirebaseRef.child("workplace").setValue(currentUser.getWorkplace());
                FirebaseRef.child("background").setValue(currentUser.getBackground());
                FirebaseRef.child("role").setValue(currentUser.getRole());
                FirebaseRef.child("phone").setValue(currentUser.getPhone());

                FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseLogic.EncodeString(FirebaseAuth.getInstance().getCurrentUser().getEmail())).child("photoURL").setValue(image.toString());

                    updateData();

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
                }
                else if(image==null){
                    Toast.makeText(getApplicationContext(),"Please add a picture",Toast.LENGTH_LONG).show();
                }


                else{
                    Toast.makeText(getApplicationContext(),"None of the fields can be empty",Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    public void refreshData() {
        name = (EditText) findViewById(R.id.edit_name);
        workplace = (EditText) findViewById(R.id.edit_workplace);
        role = (EditText) findViewById(R.id.edit_role);
        background = (EditText) findViewById(R.id.edit_background);
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
                            updateImage(image);
                            currentUser.setPhotoURL(image.toString());
                            // Set the download URL to the message box, so that the user can send it to the database
                        }


                    });
        }
    }

    public void updateData() {
        DatabaseReference temp_path = FirebaseDatabase.getInstance().getReference().child("users");
        List<String> emails = new ArrayList<String>();
        String current = FirebaseLogic.EncodeString(FirebaseAuth.getInstance().getCurrentUser().getEmail());
        for (Contact c : cList) {
            emails.add(FirebaseLogic.EncodeString(c.getMail()));
        }
        for (String s : emails) {
            temp_path.child(s).child("contacts").child(current).child("name").setValue(currentUser.getName());
            temp_path.child(s).child("contacts").child(current).child("workplace").setValue(currentUser.getWorkplace());
            temp_path.child(s).child("contacts").child(current).child("role").setValue(currentUser.getRole());
            temp_path.child(s).child("contacts").child(current).child("background").setValue(currentUser.getBackground());
            temp_path.child(s).child("contacts").child(current).child("phone").setValue(currentUser.getPhone());
            temp_path.child(s).child("contacts").child(current).child("photo").setValue(currentUser.getPhotoURL());
        }
    }

    public void updateImage(Uri pic) {
        Glide.with(this).load(pic).into(mImageView);
    }

    public void updateUser() {
        currentUser.setName(name.getText().toString());
        currentUser.setWorkplace(workplace.getText().toString());
        currentUser.setRole(role.getText().toString());
        currentUser.setBackground(background.getText().toString());
        currentUser.setPhone(phone.getText().toString());
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
            FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
            String mail = firebaseAuth.getCurrentUser().getEmail();
            User user = snapshot.getValue(User.class);
            String robust2 = null;
            if (user.getEmail() != null) {
                robust2 = FirebaseLogic.EncodeString(user.getEmail().toLowerCase());
            }
            String robust1 = FirebaseLogic.EncodeString(mail.toLowerCase());
            if (robust1.equals(robust2)) {
                currentUser = user;
                Glide.with(getApplicationContext()).load(currentUser.getPhotoURL()).apply(RequestOptions.circleCropTransform()).into(mImageView);
                name.setText(currentUser.getName());
                workplace.setText(currentUser.getWorkplace());
                role.setText(currentUser.getRole());
                background.setText(currentUser.getBackground());
                phone.setText(currentUser.getPhone());
                if(currentUser.getPhotoURL()==null){
                    Toast.makeText(getApplicationContext(),"Please add a picture",Toast.LENGTH_LONG).show();
                }
                else{
                    image=Uri.parse(currentUser.getPhotoURL());

                }
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