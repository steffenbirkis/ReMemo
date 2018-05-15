package com.example.steffen.rememo.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
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
import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.GeoQuery;
import com.firebase.geofire.GeoQueryEventListener;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
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
    private RecyclerView mRecyclerView;
    private User currentUser;
    private FusedLocationProviderClient mFusedLocationClient;
    private GeoFire geoFire;
    private List<String> mNearby;
    private GeoLocation glocation;
    private double mRange;
    private String current;
    private ViewGroup container;
    private LayoutInflater inflater;
    private SwipeRefreshLayout refreshLayout;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        container.removeAllViews();
        View fragmentView = inflater.inflate(R.layout.fragment_feed, container, false);
        this.container = container;
        this.inflater = inflater;
        mDatabase = FirebaseDatabase.getInstance().getReference().child("users");
        geodb = FirebaseDatabase.getInstance().getReference().child("geofire");
        geoFire = new GeoFire(geodb);
        mNearby = new ArrayList<String>();
        list = new ArrayList<User>();


        SharedPreferences preferences = getActivity().getSharedPreferences("userprefs", Context.MODE_PRIVATE);
        mRange = Double.parseDouble(preferences.getString("range", "50")) / 1000;
        update();
        ScheduledExecutorService scheduler =
                Executors.newSingleThreadScheduledExecutor();

        scheduler.scheduleAtFixedRate
                (new Runnable() {
                    public void run() {
                        if (ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                                || ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                            mFusedLocationClient.getLastLocation().addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
                                @Override
                                public void onSuccess(Location location) {
                                    // Got last known location. In some rare situations this can be null.

                                    if (location != null) {
                                        glocation = new GeoLocation(location.getLatitude(), location.getLongitude());
                                        geoFire.setLocation(StringLogic.EncodeString(FirebaseAuth.getInstance().getCurrentUser().getEmail()), glocation, new GeoFire.CompletionListener() {
                                            @Override
                                            public void onComplete(String key, DatabaseError error) {
                                                System.out.println("Location updated");
                                            }
                                        });
                                    }
                                }
                            });
                        }

                    }
                    }, 0, 1, TimeUnit.MINUTES);





        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        String mail = firebaseAuth.getCurrentUser().getEmail();
        current = StringLogic.EncodeString(mail.toLowerCase());
        mRecyclerView = (RecyclerView) fragmentView.findViewById(R.id.feed_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setHasFixedSize(true);
        refreshLayout=(SwipeRefreshLayout) fragmentView.findViewById(R.id.swiperefresh);
        mRecyclerView.setAdapter(new RecyclerViewAdapter(list));
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                update();
                refreshLayout.setRefreshing(false);
            }
        });
        return fragmentView;


    }


    ChildEventListener listener = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot snapshot, String s) {

            User user = snapshot.getValue(User.class);

            String selected = StringLogic.EncodeString(user.getEmail().toLowerCase());
            if (current.equals(selected)) {
                currentUser = user;
            }
            for (String temp : mNearby) {
                if (selected.equals(StringLogic.EncodeString(temp.toLowerCase()))) {
                    if (!selected.equals(current)) {
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

    public void attachListener() {
        mDatabase.addChildEventListener(listener);

    }

    private class RecyclerViewHolder extends RecyclerView.ViewHolder {
        private CardView cw;
        private TextView tw_name;
        private TextView tw_workplace;
        private TextView tw_role;
        private ImageButton btn_appealing;
        private ImageButton btn_contact;
        private ImageView iw_picture;

        public RecyclerViewHolder(View item) {
            super(item);
        }

        public RecyclerViewHolder(LayoutInflater inflater, ViewGroup container) {
            super(inflater.inflate(R.layout.cardview_feed, container, false));

            cw = itemView.findViewById(R.id.feed_card_view);
            tw_name = itemView.findViewById(R.id.feed_name);
            tw_role = itemView.findViewById(R.id.feed_role);
            tw_workplace = itemView.findViewById(R.id.feed_workplace);
            btn_appealing = itemView.findViewById(R.id.feed_appealing);
            btn_contact = itemView.findViewById(R.id.feed_contact);
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
            holder.tw_workplace.setText(temp.getWorkplace());
            holder.tw_role.setText(temp.getRole());
            Glide.with(getContext()).load(temp.getPhotoURL()).apply(RequestOptions.circleCropTransform()).into(holder.iw_picture);
            if((position%2)==0){
                holder.cw.setCardBackgroundColor(ContextCompat.getColor(getActivity(), R.color.primaryBackground));
                holder.btn_appealing.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.primaryBackground));
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
            holder.btn_appealing.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    appealing.addAppealing(temp);
                }
            });

            holder.btn_contact.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    contact.requestContact(currentUser, temp);
                    Toast.makeText(getActivity(), "Request sent", Toast.LENGTH_SHORT).show();


                }
            });
        }

        @Override
        public int getItemCount() {
            return mlist.size();
        }
    }


    public void viewProfile(User user) {
        View fragment = inflater.inflate(R.layout.fragment_lesser_profile, container, false);

        TextView lp_name = (TextView) fragment.findViewById(R.id.lp_name);
        TextView lp_workplace = (TextView) fragment.findViewById(R.id.lp_workplace);
        TextView lp_role = (TextView) fragment.findViewById(R.id.lp_role);
        TextView lp_background = (TextView) fragment.findViewById(R.id.lp_background);
        ImageView mImageView = (ImageView) fragment.findViewById(R.id.lp_image);

        lp_name.setText(user.getName());
        lp_workplace.setText(user.getWorkplace());
        lp_role.setText(user.getRole());
        lp_background.setText(user.getBackground());
        Glide.with(getContext()).load(user.getPhotoURL()).apply(RequestOptions.circleCropTransform()).into(mImageView);

        container.removeAllViews();
        container.addView(fragment);
    }
    public void update(){
        mNearby.clear();
        list.clear();

        if (ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mFusedLocationClient.getLastLocation().addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    // Got last known location. In some rare situations this can be null.

                    if (location != null) {
                        glocation = new GeoLocation(location.getLatitude(), location.getLongitude());
                        geoFire.setLocation(StringLogic.EncodeString(FirebaseAuth.getInstance().getCurrentUser().getEmail()), glocation, new GeoFire.CompletionListener() {
                            @Override
                            public void onComplete(String key, DatabaseError error) {
                                GeoQuery query = geoFire.queryAtLocation(glocation, mRange);
                                query.addGeoQueryEventListener(new GeoQueryEventListener() {
                                    public void onKeyEntered(String key, GeoLocation location) {
                                        String nearby = key;

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
                                    public void onGeoQueryError(DatabaseError error) {

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

    }
}
