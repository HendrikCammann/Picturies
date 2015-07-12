package com.project.charmander.picturies.fragments;

import android.graphics.Bitmap;
import android.location.Geocoder;
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

import com.project.charmander.picturies.MainActivity;
import com.project.charmander.picturies.R;
import com.project.charmander.picturies.model.Picture;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by hendrikcammann on 06.07.15.
 */
public class ImageDetailViewActivity extends Fragment {

    public static final String TAG = ImageDetailViewActivity.class.getSimpleName();

    public ImageDetailViewActivity() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_image_detail_view, container, false);

        Bundle args = this.getArguments();
        int position = args.getInt("position");

        Log.d(TAG, "Position:"+position);

        Picture picture = MainActivity.getCurrentUser().getPicture(position);

        TextView titleView = (TextView) rootView.findViewById(R.id.image_view_title);
        TextView berichtView = (TextView) rootView.findViewById(R.id.image_view_inreport);
        TextView dateView = (TextView) rootView.findViewById(R.id.image_view_date);
        TextView descriptionView = (TextView) rootView.findViewById(R.id.image_view_text);
        ImageView thumbnailView = (ImageView) rootView.findViewById(R.id.image_view_thumpnail);

        String title = picture.getName();
        if(title == "") title = "kein Titel angeben";
        String bericht = "in keinem Bericht verwendet";
        //TODO: Bericht dynamisch auslesen

        Date date = picture.getCreated();
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss z");
        String strDate = sdf.format(date);

        String description = picture.getDescription();
        if(description=="") description = "keine Beschreibung hinzugefügt";
        Bitmap image = picture.getImage();

        titleView.setText(title);
        berichtView.setText(bericht);
        dateView.setText(strDate);
        descriptionView.setText(description);
        thumbnailView.setImageBitmap(image);



        return rootView;

    }
}
