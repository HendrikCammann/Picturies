package com.project.charmander.picturies.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.project.charmander.picturies.MainActivity;
import com.project.charmander.picturies.R;
import com.project.charmander.picturies.adapter.ImageAdapter;
import com.project.charmander.picturies.listItems.ImageListItem;
import com.project.charmander.picturies.model.Roadtrip;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
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

//
        adapter = new ImageAdapter(getActivity(), MainActivity.getCurrentUser().getPictures());
        imageList.setAdapter(adapter);
        imageList.setLayoutManager(new LinearLayoutManager(getActivity()));
        return rootView;
    }

    public static List<ImageListItem> getData() {

        List<ImageListItem> data = new ArrayList<>();

        int[] thumpnails = {R.drawable.ruth, R.drawable.stefan, R.drawable.teacher, R.drawable.walter};
        String[] informations = {"Ruth", "Stefan", "Lehrer", "Walter"};

        for(int i = 0; i<thumpnails.length && i<informations.length; i++) {
            ImageListItem current = new ImageListItem();
            current.iconId = thumpnails[i];
            current.title = informations[i];

            data.add(current);
        }

        return data;
    }

}
