package com.example.steffen.rememo.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.steffen.rememo.R;


import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

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
    RecyclerView.Adapter mAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_appealing, container, false);

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


        RecyclerView mRecyclerView = (RecyclerView) fragmentView.findViewById(R.id.recycler_view);
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
        private TextView tw;
        private TextView tw2;

        public RecyclerViewHolder(View item){
            super(item);
        }
        public RecyclerViewHolder(LayoutInflater inflater, ViewGroup container){
            super(inflater.inflate(R.layout.cardview_feed, container, false));

            cw = itemView.findViewById(R.id.recycler_view);
            tw = itemView.findViewById(R.id.textView);
            tw2 = itemView.findViewById(R.id.textView2);

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
            holder.tw.setText(mlist.get(position));
            holder.tw2.setText(mlist.get(position));
        }

        @Override
        public int getItemCount(){
            return mlist.size();
        }
    }
}

