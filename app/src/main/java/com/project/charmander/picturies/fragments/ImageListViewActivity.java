package com.project.charmander.picturies.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.project.charmander.picturies.R;
import com.project.charmander.picturies.adapter.ImageAdapter;
import com.project.charmander.picturies.listItems.ImageListItem;

import java.util.ArrayList;
import java.util.List;

public class ImageListViewActivity extends Fragment {

    private RecyclerView imageList;
    private ImageAdapter adapter;

    public ImageListViewActivity() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.activity_image_list_view, container, false);

        imageList = (RecyclerView) rootView.findViewById(R.id.image_list);

        adapter = new ImageAdapter(getActivity(), getData());
        imageList.setAdapter(adapter);
        imageList.setLayoutManager(new LinearLayoutManager(getActivity()));
        return rootView;
    }

    public static List<ImageListItem> getData() {

        List<ImageListItem> data = new ArrayList<>();

        int[] thumpnails = {R.drawable.taxi, R.drawable.ruth, R.drawable.stefan, R.drawable.teacher, R.drawable.walter};
        String[] informations = {"John", "Ruth", "Stefan", "Lehrer", "Walter"};

        for(int i = 0; i<thumpnails.length && i<informations.length; i++) {
            ImageListItem current = new ImageListItem();
            current.iconId = thumpnails[i];
            current.title = informations[i];

            data.add(current);
        }

        return data;
    }
}
