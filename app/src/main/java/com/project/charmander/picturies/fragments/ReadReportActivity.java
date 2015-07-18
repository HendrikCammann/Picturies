package com.project.charmander.picturies.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.project.charmander.picturies.MainActivity;
import com.project.charmander.picturies.R;
import com.project.charmander.picturies.adapter.ReportAdapter;
import com.project.charmander.picturies.listItems.ReportListItem;

import java.util.ArrayList;
import java.util.List;

public class ReadReportActivity extends Fragment {

    private RecyclerView reportList;
    private ReportAdapter adapter;

    public ReadReportActivity() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.activity_read_report, container, false);

        reportList = (RecyclerView) rootView.findViewById(R.id.report_list);

        adapter = new ReportAdapter(getActivity(), MainActivity.getCurrentUser().getRoadtrips());
        reportList.setAdapter(adapter);
        reportList.setLayoutManager(new LinearLayoutManager(getActivity()));
        return rootView;
    }

    public static List<ReportListItem> getData() {

        List<ReportListItem> data = new ArrayList<>();

        String[] informations = {"New York 2014", "Rio mit Familie 2015", "Berlin Mai 15", "New York 2014", "Rio mit Familie 2015", "Berlin Mai 15", "New York 2014", "Rio mit Familie 2015", "Berlin Mai 15"};

        for(int i = 0; i<informations.length; i++) {
            ReportListItem current = new ReportListItem();
            current.title = informations[i];

            data.add(current);
        }

        return data;
    }
}
