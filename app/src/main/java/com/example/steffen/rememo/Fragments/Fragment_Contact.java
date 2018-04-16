package com.example.steffen.rememo.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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


public class Fragment_Contact extends Fragment {

    public static Fragment_Contact newInstance() {
        Fragment_Contact fragment = new Fragment_Contact();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }
    private List<Contact> clist;
    private List<User> list;
    private DatabaseReference mDatabase;
    RecyclerView mRecyclerView;
    RecyclerView.Adapter mAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_contact, container, false);
        list = new ArrayList<User>();
        clist=new ArrayList<Contact>();

        mDatabase = FirebaseDatabase.getInstance().getReference().child("contacts").child(FirebaseLogic.EncodeString(FirebaseAuth.getInstance().getCurrentUser().getEmail()));
        mDatabase.addChildEventListener(contactlistener);



        mRecyclerView = (RecyclerView) fragmentView.findViewById(R.id.contact_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setHasFixedSize(true);




        mRecyclerView.setAdapter(new RecyclerViewAdapter(list));





        return fragmentView;




    }
    ChildEventListener contactlistener=new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot snapshot, String s) {

            Contact contact = snapshot.getValue(Contact.class);
            System.out.println(contact.getMail());
            clist.add(contact);

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

    ChildEventListener userList=new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot snapshot, String s) {

            User user = snapshot.getValue(User.class);
            for(int i=0;i<=clist.size();i++){
                if(clist.isEmpty()==false){
                if(clist.get(i).getMail().equals(user.getEmail())){
                    list.add(user);
                    mDatabase=FirebaseDatabase.getInstance().getReference().child("users");
                    mDatabase.addChildEventListener(userList);
                    }
            }}
            mRecyclerView.setAdapter(new Fragment_Contact.RecyclerViewAdapter(list));

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

    public class RecyclerViewHolder extends RecyclerView.ViewHolder{
        private CardView cw;
        private TextView tw_name;
        private TextView tw_workplace_role;

        public RecyclerViewHolder(View item){
            super(item);
        }
        public RecyclerViewHolder(LayoutInflater inflater, ViewGroup container){
            super(inflater.inflate(R.layout.cardview_contact, container, false));

            cw = itemView.findViewById(R.id.contact_recyclerview);
            tw_name = itemView.findViewById(R.id.contact_name);
            tw_workplace_role = itemView.findViewById(R.id.contact_workplace_role);

        }
    }
    private class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolder>{
        private List<User> mlist;
        public RecyclerViewAdapter(List<User> list){
            this.mlist = list;

        }
        @Override
        public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            return new RecyclerViewHolder(inflater, parent);

        }
        @Override
        public void onBindViewHolder(RecyclerViewHolder holder, int position){
            final User temp = mlist.get(position);
            final Contact contact = new Contact();
            final Appealing appealing = new Appealing();
            holder.tw_name.setText(temp.getName());
            String merge = temp.getRole() + " at " + temp.getWorkplace();
            holder.tw_workplace_role.setText(merge);
        }

        @Override
        public int getItemCount(){
            return mlist.size();
        }
    }

}