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
import com.example.steffen.rememo.R;
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
    RecyclerView.Adapter mAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_contact, container, false);

        List<String> list = new ArrayList<String>();
        list.add("One");
        list.add("Two");
        list.add("Three");
        list.add("Four");
        list.add("Five");
        list.add("Three");
        list.add("Four");
        list.add("Five");
        list.add("Three");
        list.add("Four");
        list.add("Five");


        RecyclerView mRecyclerView = (RecyclerView) fragmentView.findViewById(R.id.contact_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setHasFixedSize(true);

        mRecyclerView.setAdapter(new RecyclerViewAdapter(list));
        return fragmentView;

    }
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
        private List<String> mlist;
        public RecyclerViewAdapter(List<String> list){
            this.mlist = list;

        }
        @Override
        public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            return new RecyclerViewHolder(inflater, parent);

        }
        @Override
        public void onBindViewHolder(RecyclerViewHolder holder, int position){
            holder.tw_name.setText(mlist.get(position));
            holder.tw_workplace_role.setText(mlist.get(position));
        }

        @Override
        public int getItemCount(){
            return mlist.size();
        }
    }}