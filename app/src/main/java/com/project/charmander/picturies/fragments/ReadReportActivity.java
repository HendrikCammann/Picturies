package com.project.charmander.picturies.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.project.charmander.picturies.R;

/**
 * Created by hendrikcammann on 05.07.15.
 */
public class ReadReportActivity extends Fragment {
    public ReadReportActivity() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_read_report, container, false);

        return rootView;

    }
}
