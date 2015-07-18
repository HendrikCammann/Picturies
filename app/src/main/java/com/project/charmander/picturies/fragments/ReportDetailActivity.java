package com.project.charmander.picturies.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.project.charmander.picturies.MainActivity;
import com.project.charmander.picturies.R;
import com.project.charmander.picturies.model.Picture;
import com.project.charmander.picturies.model.Roadtrip;

public class ReportDetailActivity extends Fragment {
    public static final String TAG = ReportDetailActivity.class.getSimpleName();

    public ReportDetailActivity() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_report_detail, container, false);

        Bundle args = this.getArguments();
        int position = args.getInt("position");

        Log.d(TAG, "Position:" + position);

        Roadtrip roadtrip = MainActivity.getCurrentUser().getRoadtrip(position);


        TextView titleView = (TextView) rootView.findViewById(R.id.report_detail_headline);

        titleView.setText(roadtrip.getName());

        return rootView;

    }
}
