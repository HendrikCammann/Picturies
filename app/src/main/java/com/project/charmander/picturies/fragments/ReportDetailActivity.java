package com.project.charmander.picturies.fragments;


import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.project.charmander.picturies.MainActivity;
import com.project.charmander.picturies.R;
import com.project.charmander.picturies.adapter.ReportDetailAdapter;
import com.project.charmander.picturies.listItems.ReportDetailListItem;
import com.project.charmander.picturies.model.Picture;
import com.project.charmander.picturies.model.Roadtrip;

import java.util.ArrayList;
import java.util.List;

public class ReportDetailActivity extends Fragment {
    public static final String TAG = ReportDetailActivity.class.getSimpleName();
    private static int pos;
    private RecyclerView reportList;
    private ReportDetailAdapter adapter;

    public ReportDetailActivity() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_report_detail, container, false);

        reportList = (RecyclerView) rootView.findViewById(R.id.report_detail_list);

        Bundle args = this.getArguments();
        int position = args.getInt("position");

        pos = position;

        Log.d(TAG, "Position:" + position);

        adapter = new ReportDetailAdapter(getActivity(), getData());
        reportList.setAdapter(adapter);
        reportList.setLayoutManager(new LinearLayoutManager(getActivity()));

        Roadtrip roadtrip = MainActivity.getCurrentUser().getRoadtrip(position);


        TextView titleView = (TextView) rootView.findViewById(R.id.report_detail_headline);

        titleView.setText(roadtrip.getName());

        return rootView;

    }

    public static List<ReportDetailListItem> getData() {
        List<ReportDetailListItem> data = new ArrayList<>();

        Roadtrip road = MainActivity.getCurrentUser().getRoadtrip(pos);

        int[] thumpnails = {R.drawable.john, R.drawable.ruth, R.drawable.stefan, R.drawable.teacher, R.drawable.walter};
        String[] informations = {"John", "Ruth", "Stefan", "Lehrer", "Walter"};

        for(int i = 0; i<thumpnails.length && i<informations.length; i++) {
            ReportDetailListItem current = new ReportDetailListItem();
            current.IconID = thumpnails[i];
            current.Description = informations[i];

            data.add(current);
        }

        return data;
    }
}
