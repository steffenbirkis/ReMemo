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

import com.example.steffen.rememo.Logic.Contact;
import com.example.steffen.rememo.Logic.FirebaseLogic;
import com.example.steffen.rememo.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class Fragment_Requests extends Fragment {

    public static Fragment_Requests newInstance() {
        Fragment_Requests fragment = new Fragment_Requests();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    List<Contact> list;
    RecyclerView mRecyclerView;
    private DatabaseReference mDatabase;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_request, container, false);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseLogic.EncodeString(FirebaseAuth.getInstance().getCurrentUser().getEmail())).child("appealing");
        mDatabase.addChildEventListener(listener);

        list = new ArrayList<Contact>();

        mRecyclerView = (RecyclerView) fragmentView.findViewById(R.id.request_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setHasFixedSize(true);

        return fragmentView;
    }


    ChildEventListener listener = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot snapshot, String s) {

            Contact contact = snapshot.getValue(Contact.class);
            if (!contact.isAcknowledgement() && contact.isRequest()) {
                list.add(contact);
            }
            System.out.println(contact.getMail());

            mRecyclerView.setAdapter(new Fragment_Requests.RecyclerViewAdapter(list));

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

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        private CardView cw;
        private TextView tw_name;
        private TextView tw_workplace_role;

        public RecyclerViewHolder(View item) {
            super(item);
        }

        public RecyclerViewHolder(LayoutInflater inflater, ViewGroup container) {
            super(inflater.inflate(R.layout.cardview_request, container, false));

            cw = itemView.findViewById(R.id.request_recyclerview);
            tw_name = itemView.findViewById(R.id.request_name);
            tw_workplace_role = itemView.findViewById(R.id.request_workplace_role);

        }
    }


    private class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {
        private List<Contact> mlist;

        public RecyclerViewAdapter(List<Contact> list) {
            this.mlist = list;

        }

        @Override
        public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            return new Fragment_Requests.RecyclerViewHolder(inflater, parent);

        }

        @Override
        public void onBindViewHolder(Fragment_Requests.RecyclerViewHolder holder, int position) {
            final Contact temp = mlist.get(position);
            holder.tw_name.setText(temp.getMail());
        }

        @Override
        public int getItemCount() {
            return mlist.size();
        }
    }
}