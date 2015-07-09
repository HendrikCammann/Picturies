package com.project.charmander.picturies.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.project.charmander.picturies.MainActivity;
import com.project.charmander.picturies.R;
import com.project.charmander.picturies.adapter.ChooseImageAdapter;
import com.project.charmander.picturies.listItems.ImageListItem;

import java.util.ArrayList;
import java.util.List;

public class CreateReportActivity extends Fragment {

    private RecyclerView imageList;
    private ChooseImageAdapter adapter;

    public CreateReportActivity() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_create_report, container, false);
        imageList = (RecyclerView) rootView.findViewById(R.id.image_list);
        adapter = new ChooseImageAdapter(getActivity(), getData());
        imageList.setAdapter(adapter);
        imageList.setLayoutManager(new LinearLayoutManager(getActivity()));
        Button saveButton = (Button) rootView.findViewById(R.id.save_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Bericht hinzugefügt", Toast.LENGTH_LONG).show();
                Intent i = new Intent(getActivity(), MainActivity.class);
                startActivity(i);
            }
        });

        Button cancelButton = (Button) rootView.findViewById(R.id.cancel_button);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Bericht verworfen", Toast.LENGTH_LONG).show();
                Intent i = new Intent(getActivity(), MainActivity.class);
                startActivity(i);
            }
        });

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
