package com.project.charmander.picturies.fragments;


import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.project.charmander.picturies.MainActivity;
import com.project.charmander.picturies.R;
import com.project.charmander.picturies.adapter.ReportDetailAdapter;
import com.project.charmander.picturies.listItems.ReportDetailListItem;
import com.project.charmander.picturies.model.Picture;
import com.project.charmander.picturies.model.Roadtrip;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ReportDetailActivity extends Fragment {
    public static final String TAG = ReportDetailActivity.class.getSimpleName();
    private static int pos;
    private RecyclerView reportList;
    private ReportDetailAdapter adapter;
    public ArrayList<Picture> picturesFromThatRoadtrip;

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


        Roadtrip roadtrip = MainActivity.getCurrentUser().getRoadtrip(position);
        ArrayList<String> pictureIds = roadtrip.getPictureIds();

        ArrayList<Picture> allPictures = getPictureInfos(pictureIds);

        adapter = new ReportDetailAdapter(getActivity(), allPictures);
        reportList.setAdapter(adapter);
        reportList.setLayoutManager(new LinearLayoutManager(getActivity()));

        TextView titleView = (TextView) rootView.findViewById(R.id.report_detail_headline);
        titleView.setText(roadtrip.getName());

        ImageView headImage = (ImageView) rootView.findViewById(R.id.report_detail_headerimage);
        if(allPictures.get(0) != null) {
        headImage.setImageBitmap(allPictures.get(0).getImage()); }

        return rootView;

    }

    private ArrayList<Picture> getPictureInfos(ArrayList<String> pictureIds) {

        picturesFromThatRoadtrip = new ArrayList<Picture>();
        ArrayList<Picture> allPictures = MainActivity.getCurrentUser().getPictures();

        for(int i=0;i < pictureIds.size();i++){
            Picture picture;
            String pictureID =  pictureIds.get(i);

            for(int j=0; j < allPictures.size(); j++){
                if(allPictures.get(j).getImageId().toString().equals(pictureID)) {
                    picture = allPictures.get(j);
                    picturesFromThatRoadtrip.add(picture);
                    break;
                }
            }
        }

       return picturesFromThatRoadtrip;
    }

       /*

  if(response.isSuccessful()){
                    Picture picture = new Picture(id,title, creator, created, latitude, longitude, bitmap, description);
                    mCurrentUser.addPictureToList(picture);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            Bitmap resizedImage = Bitmap.createScaledBitmap(bitmap, 175, 175, false);
                            mMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)).title(title).snippet(mCurrentUser.getPictures().size() - 1 + "").icon(BitmapDescriptorFactory.fromBitmap(resizedImage)));

                            anzahlPictures = (TextView) findViewById(R.id.profile_upload_pictures);
                            anzahlPictures.setText(mCurrentUser.getPictures().size()+ " Bilder");
                        }
                    });

                }
            }
        }); */

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
