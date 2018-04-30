package com.example.steffen.rememo.Fragments;

import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.steffen.rememo.Logic.Appealing;
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

import java.util.ArrayList;
import java.util.List;


public class Fragment_Appealing extends Fragment {
    public static Fragment_Appealing newInstance() {
        Fragment_Appealing fragment = new Fragment_Appealing();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    List<Appealing> list;
    RecyclerView mRecyclerView;
    User currentUser;
    List<User> updatedList;
    List<String> emailList;
    private DatabaseReference mDatabase;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        container.removeAllViews();
        View fragmentView = inflater.inflate(R.layout.fragment_appealing, container, false);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseLogic.EncodeString(FirebaseAuth.getInstance().getCurrentUser().getEmail())).child("appealing");
        mDatabase.addChildEventListener(listener);
        DatabaseReference userpath = FirebaseDatabase.getInstance().getReference().child("users");
        userpath.addChildEventListener(listener);

        list = new ArrayList<Appealing>();

        mRecyclerView = (RecyclerView) fragmentView.findViewById(R.id.appealing_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setHasFixedSize(true);

        return fragmentView;

    }


    ChildEventListener listener = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot snapshot, String s) {
            Appealing steff = snapshot.getValue(Appealing.class);
            System.out.println(steff.getMail());
            list.add(steff);


            mRecyclerView.setAdapter(new Fragment_Appealing.RecyclerViewAdapter(list));


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
            String current = FirebaseLogic.EncodeString(mail.toLowerCase());
            String selected = FirebaseLogic.EncodeString(user.getEmail().toLowerCase());
            if(current.equals(selected)) {
                currentUser = user;
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
    @Override
    public void onResume() {
        super.onResume();

    }


    private class RecyclerViewHolder extends RecyclerView.ViewHolder {
        private CardView cw;
        private TextView tw_name;
        private TextView tw_workplace_role;
        private Button btn_contact;
        private ImageView iw_image;

        public RecyclerViewHolder(View item) {
            super(item);
        }

        public RecyclerViewHolder(LayoutInflater inflater, ViewGroup container) {
            super(inflater.inflate(R.layout.cardview_appealing, container, false));

            cw = itemView.findViewById(R.id.appealing_recyclerview);
            tw_name = itemView.findViewById(R.id.appealing_name);
            tw_workplace_role = itemView.findViewById(R.id.appealing_workplace_role);
            btn_contact = (Button) itemView.findViewById(R.id.appealing_request);
            iw_image = (ImageView) itemView.findViewById(R.id.appealing_picture);


        }
    }

    private class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {
        private List<Appealing> mlist;

        public RecyclerViewAdapter(List<Appealing> list) {
            this.mlist = list;

        }

        @Override
        public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            return new RecyclerViewHolder(inflater, parent);

        }

        @Override
        public void onBindViewHolder(RecyclerViewHolder holder, int position) {
            final Appealing temp = mlist.get(position);
            final Contact contact = new Contact();
            holder.tw_name.setText(temp.getName());
            String merge = temp.getRole() + " at " + temp.getWorkplace();
            holder.tw_workplace_role.setText(merge);
            Glide.with(getContext()).load(temp.getPhoto()).apply(RequestOptions.circleCropTransform()).into(holder.iw_image);

            holder.btn_contact.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getContext(), "Clicked Request Contact: " + temp.getName(),
                            Toast.LENGTH_LONG).show();
                    User temp_user = new User();
                    temp_user.setPhotoURL(temp.getPhoto());
                    temp_user.setPhone(temp.getPhone());
                    temp_user.setRole(temp.getRole());
                    temp_user.setBackground(temp.getBackground());
                    temp_user.setWorkplace(temp.getWorkplace());
                    temp_user.setName(temp.getName());
                    temp_user.setEmail(temp.getMail());
                    contact.requestContact(currentUser,temp_user);

                }
            });


        }

        @Override
        public int getItemCount() {
            return mlist.size();
        }
    }
}

