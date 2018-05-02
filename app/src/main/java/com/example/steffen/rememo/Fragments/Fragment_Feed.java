package com.example.steffen.rememo.Fragments;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationProvider;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
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
import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.GeoQuery;
import com.firebase.geofire.GeoQueryEventListener;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseError;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.TimeUnit;


public class Fragment_Feed extends Fragment {
    public static Fragment_Feed newInstance() {
        Fragment_Feed fragment = new Fragment_Feed();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());



    }

    private List<User> list;
    private DatabaseReference mDatabase;
    private DatabaseReference geodb;
    RecyclerView mRecyclerView;
    private User currentUser;
    private FusedLocationProviderClient mFusedLocationClient;
    private GeoFire geoFire;
    private List<String> mNearby;
    private GeoLocation glocation;
    private GeoQuery gQuery;
    private double mRange;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        container.removeAllViews();
        View fragmentView = inflater.inflate(R.layout.fragment_feed, container, false);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("users");
        geodb = FirebaseDatabase.getInstance().getReference().child("geofire");
        geoFire = new GeoFire(geodb);
        mNearby=new ArrayList<String>();

        SharedPreferences preferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        mRange = preferences.getInt("range",50);
        System.out.println("mRange : "+mRange);
        System.out.println("string:"+preferences.getString("range","empty"));
        System.out.println("int."+preferences.getInt("range",0));


        if (ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mFusedLocationClient.getLastLocation().addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    // Got last known location. In some rare situations this can be null.

                    if (location != null) {
                        glocation = new GeoLocation(location.getLatitude(), location.getLongitude());
                        geoFire.setLocation(FirebaseLogic.EncodeString(FirebaseAuth.getInstance().getCurrentUser().getEmail()), glocation, new GeoFire.CompletionListener() {
                            @Override
                            public void onComplete(String key, DatabaseError error) {
                                GeoQuery query = geoFire.queryAtLocation(glocation,10);
                                query.addGeoQueryEventListener(new GeoQueryEventListener() {
                                    public void onKeyEntered(String key, GeoLocation location) {

                                        System.out.println(String.format("%s entered at [%f, %f]", key, location.latitude, location.longitude));
                                        String nearby=key;
                                        mNearby.add(nearby);
                                    }

                                    @Override
                                    public void onKeyExited(String key) {
                                        System.out.println(String.format("%s exited", key));
                                    }

                                    @Override
                                    public void onKeyMoved(String key, GeoLocation location) {
                                        System.out.println(String.format("%s moved to [%f, %f]", key, location.latitude, location.longitude));
                                    }

                                    @Override
                                    public void onGeoQueryReady() {
                                        System.out.println("All initial key entered events have been fired!");
                                    }

                                    @Override
                                    public void onGeoQueryError(DatabaseError error){

                                    }


                                    // run for another 60 seconds
                                });
                                attachListener();
                            }
                        });
                    }
                }
            });
        }



 
        list = new ArrayList<User>();

        mRecyclerView = (RecyclerView) fragmentView.findViewById(R.id.feed_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(new RecyclerViewAdapter(list));
        return fragmentView;


    }


    ChildEventListener listener = new ChildEventListener() {
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
            for(String temp:mNearby){
                 if(selected.equals(FirebaseLogic.EncodeString(temp.toLowerCase()))){
                     if(!selected.equals(current)) {
                         list.add(user);
                     }
                }
            }
            mRecyclerView.setAdapter(new RecyclerViewAdapter(list));

        }


        @Override
        public void onCancelled(DatabaseError databaseError) {
            System.out.println("The read failed: " + databaseError.getCode());
        }

        public void onChildRemoved(DataSnapshot dataSnapshot) {

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
public void attachListener(){
    mDatabase.addChildEventListener(listener);

}

    private class RecyclerViewHolder extends RecyclerView.ViewHolder {
        private CardView cw;
        private TextView tw_name;
        private TextView tw_workplace_role;
        private Button btn_appealing;
        private Button btn_contact;
        private ImageView iw_picture;

        public RecyclerViewHolder(View item) {
            super(item);
        }

        public RecyclerViewHolder(LayoutInflater inflater, ViewGroup container) {
            super(inflater.inflate(R.layout.cardview_feed, container, false));

            cw = itemView.findViewById(R.id.feed_recyclerview);
            tw_name = itemView.findViewById(R.id.feed_name);
            tw_workplace_role = itemView.findViewById(R.id.feed_workplace_role);
            btn_appealing = (Button) itemView.findViewById(R.id.feed_appealing);
            btn_contact = (Button) itemView.findViewById(R.id.feed_contact);
            iw_picture = (ImageView) itemView.findViewById(R.id.feed_picture);



        }
    }

    private class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {
        private List<User> mlist;

        public RecyclerViewAdapter(List<User> list) {
            this.mlist = list;

        }

        @Override
        public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            return new RecyclerViewHolder(inflater, parent);

        }

        @Override
        public void onBindViewHolder(RecyclerViewHolder holder, int position) {
            final User temp = mlist.get(position);
            final Contact contact = new Contact();
            final Appealing appealing = new Appealing();
            holder.tw_name.setText(temp.getName());
            String merge = temp.getRole() + " at " + temp.getWorkplace();
            holder.tw_workplace_role.setText(merge);
            Glide.with(getContext()).load(temp.getPhotoURL()).apply(RequestOptions.circleCropTransform()).into(holder.iw_picture);

            holder.btn_appealing.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getContext(), "Clicked Appealing: " + temp.getName(),
                            Toast.LENGTH_LONG).show();
                    appealing.addAppealing(temp);
                }
            });

            holder.btn_contact.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getContext(), "Clicked Feed: " + temp.getName(),
                            Toast.LENGTH_LONG).show();
                    contact.requestContact(currentUser,temp);

                }
            });
        }

        @Override
        public int getItemCount() {
            return mlist.size();
        }
    }

}
