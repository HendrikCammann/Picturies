package com.project.charmander.picturies.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.project.charmander.picturies.R;

public class ReportDetailActivity extends Fragment {
    public ReportDetailActivity() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_report_detail, container, false);

        return rootView;

    }
}
