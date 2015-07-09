package com.project.charmander.picturies.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.project.charmander.picturies.R;
import com.project.charmander.picturies.adapter.FriendsAdapter;
import com.project.charmander.picturies.listItems.FriendsListItem;

import java.util.ArrayList;
import java.util.List;


public class FriendsActivity extends Fragment {

    private RecyclerView friendsList;
    private FriendsAdapter adapter;

    public FriendsActivity() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.activity_friends, container, false);

        friendsList = (RecyclerView) rootView.findViewById(R.id.friends_list);

        adapter = new FriendsAdapter(getActivity(), getData());
        friendsList.setAdapter(adapter);
        friendsList.setLayoutManager(new LinearLayoutManager(getActivity()));
        return rootView;
    }

    public static List<FriendsListItem> getData() {

        List<FriendsListItem> data = new ArrayList<>();

        int[] thumpnails = {R.drawable.john, R.drawable.ruth, R.drawable.stefan, R.drawable.teacher, R.drawable.walter};
        String[] informations = {"John", "Ruth", "Stefan", "Lehrer", "Walter"};

        for(int i = 0; i<thumpnails.length && i<informations.length; i++) {
            FriendsListItem current = new FriendsListItem();
            current.iconId = thumpnails[i];
            current.title = informations[i];

            data.add(current);
        }

        return data;
    }

}
