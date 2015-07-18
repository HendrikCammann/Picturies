package com.project.charmander.picturies.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.project.charmander.picturies.MainActivity;
import com.project.charmander.picturies.R;
import com.project.charmander.picturies.adapter.ChooseImageAdapter;
import com.project.charmander.picturies.listItems.ImageListItem;
import com.project.charmander.picturies.model.Picture;
import com.project.charmander.picturies.model.Roadtrip;
import com.project.charmander.picturies.model.User;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class CreateReportActivity extends Fragment {
    public static final String TAG = CreateReportActivity.class.getSimpleName();

    private RecyclerView imageList;
    private ChooseImageAdapter adapter;
    private EditText mTitleView;

    public CreateReportActivity() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_create_report, container, false);
        mTitleView= (EditText) rootView.findViewById(R.id.create_report_name);
        imageList = (RecyclerView) rootView.findViewById(R.id.image_list);
        adapter = new ChooseImageAdapter(getActivity(),  MainActivity.getCurrentUser().getPictures());
        imageList.setAdapter(adapter);
        imageList.setLayoutManager(new LinearLayoutManager(getActivity()));
        Button saveButton = (Button) rootView.findViewById(R.id.save_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Bericht hinzugefügt", Toast.LENGTH_LONG).show();
                ArrayList<Picture> selectedImage = adapter.getSelectedImages();
                String title = mTitleView.getText().toString();
                sendReportToDatabase(title, null, selectedImage);
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

        int[] thumpnails = {R.drawable.ruth, R.drawable.stefan, R.drawable.teacher, R.drawable.walter};
        String[] informations = {"Brooklyn", "Lady Liberty", "Lehrer", "Walter"};
        String[] descriptions = {"'Ein wundervoller Tag im Taxi - leider viel im Stau gestanden. Dafür aber eine tolles Gespräch mit dem Taxifahrer gehabt.'", "'Ein wundervoller Tag im Taxi - leider viel im Stau gestanden. Dafür aber eine tolles Gespräch mit dem Taxifahrer gehabt.'", "'Ein wundervoller Tag im Taxi - leider viel im Stau gestanden. Dafür aber eine tolles Gespräch mit dem Taxifahrer gehabt.'", "'Ein wundervoller Tag im Taxi - leider viel im Stau gestanden. Dafür aber eine tolles Gespräch mit dem Taxifahrer gehabt.'"};

        for(int i = 0; i<thumpnails.length && i<informations.length && i<descriptions.length; i++) {
            ImageListItem current = new ImageListItem();
            current.iconId = thumpnails[i];
            current.title = informations[i];
            current.description = descriptions[i];

            data.add(current);
        }

        return data;
    }


    public void sendReportToDatabase(String title, String description, ArrayList<Picture> pictures){


        //TODO: addRoadtriptoUser , roadtrip zu Picture hinzufügen
        //TODO: roadtrip to User

        UUID id = UUID.randomUUID();
        Calendar c = Calendar.getInstance();
        Date currentDate = c.getTime();
        final User currentUser = MainActivity.getCurrentUser();
        final Roadtrip uploadedRoadtrip = new Roadtrip(id, title,  pictures, currentUser, currentDate);

        String json = uploadedRoadtrip.generateJson();

        Log.d(TAG, json);

        final String databaseURL ="http://charmander.iriscouch.com/roadtrips/"+id.toString();
        final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

        RequestBody requestBody = RequestBody.create(JSON, json);

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(databaseURL)
                .header("Content-Type", "application/json")
                .put(requestBody)
                .build();

        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                Log.d(TAG, "Request gescheitert");
            }

            @Override
            public void onResponse(Response response) throws IOException {
                if(response.isSuccessful()){
                    Log.d(TAG,response.toString());
                    if(response.code() == 201){
                        Log.d(TAG, response.body().string());
                        currentUser.addRoadtripToList(uploadedRoadtrip);

                    }
                }
                else{
                    Log.d(TAG,response.toString() + "ResponseBody: " +response.body().string());
                }
            }
        });

    }
}
