package com.example.steffen.rememo.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.steffen.rememo.Logic.Contact;
import com.example.steffen.rememo.Logic.StringLogic;
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
    private DatabaseReference mDatabase;
    RecyclerView mRecyclerView;
    LayoutInflater inflater;
    ViewGroup container;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        container.removeAllViews();
        View fragmentView = inflater.inflate(R.layout.fragment_contact, container, false);
        this.inflater = inflater;
        this.container = container;
        clist = new ArrayList<Contact>();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("users").child(StringLogic.EncodeString(FirebaseAuth.getInstance().getCurrentUser().getEmail())).child("contacts");
        mDatabase.addChildEventListener(contactlistener);
        mRecyclerView = (RecyclerView) fragmentView.findViewById(R.id.contact_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(new RecyclerViewAdapter(clist));

        return fragmentView;
    }

    private View.OnClickListener onViewReq = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            clickViewRequests(v);
        }
    };

    ChildEventListener contactlistener = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot snapshot, String s) {

            Contact contact = snapshot.getValue(Contact.class);
            if (contact.isAcknowledgement() && contact.isRequest()) {
                clist.add(contact);
            }
            mRecyclerView.setAdapter(new Fragment_Contact.RecyclerViewAdapter(clist));

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


    public void clickViewRequests(View v) {
        Fragment requests = Fragment_Requests.newInstance();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, requests);
        transaction.commit();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        private CardView cw;
        private TextView tw_name;
        private TextView tw_workplace;
        private TextView tw_role;
        private ImageView imageView;

        public RecyclerViewHolder(View item) {
            super(item);
        }

        public RecyclerViewHolder(LayoutInflater inflater, ViewGroup container) {
            super(inflater.inflate(R.layout.cardview_contact, container, false));

            cw = itemView.findViewById(R.id.contact_card_view);
            tw_name = itemView.findViewById(R.id.contact_name);
            tw_workplace = itemView.findViewById(R.id.contact_workplace);
            tw_role = itemView.findViewById(R.id.contact_role);
            imageView = itemView.findViewById(R.id.contact_picture);
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
            return new RecyclerViewHolder(inflater, parent);
        }

        @Override
        public void onBindViewHolder(RecyclerViewHolder holder, int position) {
            final Contact temp = mlist.get(position);
            holder.tw_name.setText(temp.getName());
            holder.tw_workplace.setText(temp.getWorkplace());
            holder.tw_role.setText(temp.getRole());
            Glide.with(getContext()).load(temp.getPhoto()).apply(RequestOptions.circleCropTransform()).into(holder.imageView);
            if ((position % 2) == 0) {
                holder.cw.setCardBackgroundColor(ContextCompat.getColor(getActivity(), R.color.primaryBackground));
            } else {
                holder.cw.setCardBackgroundColor(ContextCompat.getColor(getActivity(), R.color.white));

            }
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    viewProfile(temp);
                }
            });
        }

        @Override
        public int getItemCount() {
            return mlist.size();
        }
    }

    public void viewProfile(Contact contact) {
        View fragment = inflater.inflate(R.layout.fragment_profile, container, false);
        TextView txt_email = (TextView) fragment.findViewById(R.id.txt_email);
        TextView txt_name = (TextView) fragment.findViewById(R.id.txt_name);
        TextView txt_workplace = (TextView) fragment.findViewById(R.id.txt_workplace);
        TextView txt_role = (TextView) fragment.findViewById(R.id.txt_role);
        TextView txt_background = (TextView) fragment.findViewById(R.id.txt_background);
        TextView txt_phone = (TextView) fragment.findViewById(R.id.txt_phone);
        ImageView mImageView = (ImageView) fragment.findViewById(R.id.profile_image);

        txt_email.setText(contact.getMail());
        txt_name.setText(contact.getName());
        txt_workplace.setText(contact.getWorkplace());
        txt_role.setText(contact.getRole());
        txt_background.setText(contact.getBackground());
        txt_phone.setText(contact.getPhone());
        Glide.with(getContext()).load(contact.getPhoto()).apply(RequestOptions.circleCropTransform()).into(mImageView);

        container.removeAllViews();
        container.addView(fragment);
    }
}