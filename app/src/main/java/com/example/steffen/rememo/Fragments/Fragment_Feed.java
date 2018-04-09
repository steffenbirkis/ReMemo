package com.example.steffen.rememo.Fragments;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.steffen.rememo.Logic.DataObject;
import com.example.steffen.rememo.Logic.MyRecyclerViewAdapter;
import com.example.steffen.rememo.R;

import java.util.ArrayList;


public class Fragment_Feed extends Fragment {
    public static Fragment_Feed newInstance() {
        Fragment_Feed fragment = new Fragment_Feed();
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
        View fragmentView = inflater.inflate(R.layout.fragment_feed, container, false);

        RecyclerView mRecyclerView = (RecyclerView) fragmentView.findViewById(R.id.my_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setHasFixedSize(true);

        mAdapter = new MyRecyclerViewAdapter(getDataSet());


        DataObject obj1 = new DataObject("Ping", "Pong");
        DataObject obj2 = new DataObject("Ding", "Dong");
        DataObject obj3 = new DataObject("Zing", "Zong");
        DataObject obj4 = new DataObject("Bing", "Bong");


        ((MyRecyclerViewAdapter) mAdapter).addItem(obj1, 0);
        ((MyRecyclerViewAdapter) mAdapter).addItem(obj2, 1);
        ((MyRecyclerViewAdapter) mAdapter).addItem(obj3, 2);
        ((MyRecyclerViewAdapter) mAdapter).addItem(obj4, 3);
        mRecyclerView.setAdapter(mAdapter);
        return inflater.inflate(R.layout.fragment_feed, container, false);

    }
    @Override
    public void onResume() {
        super.onResume();
        ((MyRecyclerViewAdapter) mAdapter).setOnItemClickListener(new MyRecyclerViewAdapter
                .MyClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                Log.i("This", " Clicked on Item " + position);
            }
        });
    }

    private ArrayList<DataObject> getDataSet() {
        ArrayList results = new ArrayList<DataObject>();
        for (int index = 0; index < 20; index++) {
            DataObject obj = new DataObject("Some Primary Text " + index,
                    "Secondary " + index);
            results.add(index, obj);
        }
        return results;
    }
}
