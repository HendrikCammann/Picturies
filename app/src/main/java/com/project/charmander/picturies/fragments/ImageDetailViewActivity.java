package com.project.charmander.picturies.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.project.charmander.picturies.R;

/**
 * Created by hendrikcammann on 06.07.15.
 */
public class ImageDetailViewActivity extends Fragment {

    public ImageDetailViewActivity() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_image_detail_view, container, false);

        return rootView;

    }
}
