package com.example.steffen.rememo.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.steffen.rememo.Logic.Appealing;
import com.example.steffen.rememo.Logic.Contact;
import com.example.steffen.rememo.Logic.StringLogic;
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
    private DatabaseReference mDatabase;
    private String mail;
    private LayoutInflater inflater;
    private ViewGroup container;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        container.removeAllViews();
        this.inflater = inflater;
        this.container = container;
        View fragmentView = inflater.inflate(R.layout.fragment_appealing, container, false);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("users").child(StringLogic.EncodeString(FirebaseAuth.getInstance().getCurrentUser().getEmail())).child("appealing");
        mDatabase.addChildEventListener(listener);
        DatabaseReference userpath = FirebaseDatabase.getInstance().getReference().child("users");
        userpath.addChildEventListener(userlistener);
        list = new ArrayList<Appealing>();
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        mail = firebaseAuth.getCurrentUser().getEmail();
        mail = StringLogic.EncodeString(mail.toLowerCase());
        mRecyclerView = (RecyclerView) fragmentView.findViewById(R.id.appealing_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setHasFixedSize(true);

        return fragmentView;
    }

    ChildEventListener listener = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot snapshot, String s) {
            Appealing appealing = snapshot.getValue(Appealing.class);
            list.add(appealing);
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
            User user = snapshot.getValue(User.class);
            String selected = StringLogic.EncodeString(user.getEmail().toLowerCase());
            if (mail.equals(selected)) {
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
        private TextView tw_workplace;
        private TextView tw_role;
        private ImageButton btn_contact;
        private ImageView iw_image;
        private ImageButton btn_unappeal;

        public RecyclerViewHolder(View item) {
            super(item);
        }

        public RecyclerViewHolder(LayoutInflater inflater, ViewGroup container) {
            super(inflater.inflate(R.layout.cardview_appealing, container, false));

            cw = itemView.findViewById(R.id.appealing_card_view);
            tw_name = itemView.findViewById(R.id.appealing_name);
            tw_role = itemView.findViewById(R.id.appealing_role);
            tw_workplace = itemView.findViewById(R.id.appealing_workplace);
            btn_contact = itemView.findViewById(R.id.appealing_request);
            iw_image = (ImageView) itemView.findViewById(R.id.appealing_picture);
            btn_unappeal = itemView.findViewById(R.id.appealing_unappeal);


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
        public void onBindViewHolder(RecyclerViewHolder holder, final int position) {
            final Appealing temp = mlist.get(position);
            final Contact contact = new Contact();
            holder.tw_name.setText(temp.getName());
            holder.tw_workplace.setText(temp.getWorkplace());
            holder.tw_role.setText(temp.getRole());
            Glide.with(getContext()).load(temp.getPhoto()).apply(RequestOptions.circleCropTransform()).into(holder.iw_image);
            if((position%2)==0){
                holder.cw.setCardBackgroundColor(ContextCompat.getColor(getActivity(), R.color.primaryBackground));
                holder.btn_unappeal.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.primaryBackground));
                holder.btn_contact.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.primaryBackground));
            }else{
                holder.cw.setCardBackgroundColor(ContextCompat.getColor(getActivity(), R.color.white));

            }
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    viewProfile(temp);
                }
            });

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
                    contact.requestContact(currentUser, temp_user);
                    Toast.makeText(getActivity(),"Request sent",Toast.LENGTH_SHORT).show();

                }
            });

            holder.btn_unappeal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String mail = StringLogic.EncodeString(temp.getMail());
                    FirebaseDatabase.getInstance().getReference().child("users").child(StringLogic.EncodeString(currentUser.getEmail())).child("appealing").child(mail).removeValue();
                    System.out.println(mlist.size());
                    Toast.makeText(getActivity(), "Removed", Toast.LENGTH_SHORT).show();
                    mlist.remove(position);
                    mRecyclerView.setAdapter(new Fragment_Appealing.RecyclerViewAdapter(mlist));
                }
            });
        }

        @Override
        public int getItemCount() {
            return mlist.size();
        }
    }

    public void viewProfile(Appealing appealing){
        View fragment = inflater.inflate(R.layout.fragment_lesser_profile, container, false);

        TextView lp_name = (TextView) fragment.findViewById(R.id.lp_name);
        TextView lp_workplace = (TextView) fragment.findViewById(R.id.lp_workplace);
        TextView lp_role = (TextView) fragment.findViewById(R.id.lp_role);
        TextView lp_background = (TextView) fragment.findViewById(R.id.lp_background);
        ImageView mImageView = (ImageView) fragment.findViewById(R.id.lp_image);

        lp_name.setText(appealing.getName());
        lp_workplace.setText(appealing.getWorkplace());
        lp_role.setText(appealing.getRole());
        lp_background.setText(appealing.getBackground());
        Glide.with(getContext()).load(appealing.getPhoto()).apply(RequestOptions.circleCropTransform()).into(mImageView);

        container.removeAllViews();
        container.addView(fragment);
    }
}

