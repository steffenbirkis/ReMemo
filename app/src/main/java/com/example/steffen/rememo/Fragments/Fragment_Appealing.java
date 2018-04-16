package com.example.steffen.rememo.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.steffen.rememo.Logic.Appealing;
import com.example.steffen.rememo.Logic.Contact;
import com.example.steffen.rememo.Logic.User;
import com.example.steffen.rememo.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;
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
    RecyclerView.Adapter mAdapter;
    List<User> list;
    List<String> list_emails;
    RecyclerView mRecyclerView;
    List<User> updatedList;
    private DatabaseReference mDatabase;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_appealing, container, false);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("users").addChildEventListener(listener);
        mDatabase.child("appealing").addChildEventListener(listener_mails);

        list = new ArrayList<User>();
        list_emails = new ArrayList<String>();

        mRecyclerView = (RecyclerView) fragmentView.findViewById(R.id.appealing_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setHasFixedSize(true);
        getUsers();

        return fragmentView;

    }

    ChildEventListener listener_mails=new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot snapshot, String s) {

            String temp = snapshot.getValue(String.class);
            list_emails.add(temp);
            mRecyclerView.setAdapter(new RecyclerViewAdapter(updatedList));

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
    ChildEventListener listener=new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot snapshot, String s) {

            User user = snapshot.getValue(User.class);
            list.add(user);

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

    private void getUsers(){
        updatedList = new ArrayList<User>();
        Iterator<String> iterator = list_emails.iterator();
        while(iterator.hasNext()){
            Iterator<User> userIterator = list.iterator();
            User temp = userIterator.next();
            if(temp.getEmail().equals(iterator.next())){
                updatedList.add(temp);
            }
        }
        mRecyclerView.setAdapter(new RecyclerViewAdapter(updatedList));
    }

    @Override
    public void onResume() {
        super.onResume();

    }


    private class RecyclerViewHolder extends RecyclerView.ViewHolder{
        private CardView cw;
        private TextView tw_name;
        private TextView tw_workplace_role;
        private Button btn_contact;

        public RecyclerViewHolder(View item){
            super(item);
        }
        public RecyclerViewHolder(LayoutInflater inflater, ViewGroup container){
            super(inflater.inflate(R.layout.cardview_appealing, container, false));

            cw = itemView.findViewById(R.id.appealing_recyclerview);
            tw_name = itemView.findViewById(R.id.appealing_name);
            tw_workplace_role = itemView.findViewById(R.id.appealing_workplace_role);
            btn_contact = (Button) itemView.findViewById(R.id.appealing_request);


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
            holder.tw_name.setText(temp.getName());
            String merge = temp.getRole() + " at " + temp.getWorkplace();
            holder.tw_workplace_role.setText(merge);

            holder.btn_contact.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    Toast.makeText(getContext(), "Clicked on contact @Appealing: "+temp.getName(),
                            Toast.LENGTH_LONG).show();
                    contact.addContact(temp);

                }
            });
        }

        @Override
        public int getItemCount(){
            return mlist.size();
        }
    }
}

