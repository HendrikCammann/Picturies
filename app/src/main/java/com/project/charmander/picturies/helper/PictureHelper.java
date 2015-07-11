package com.project.charmander.picturies.helper;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.project.charmander.picturies.model.User;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.UUID;

/**
 * Created by Jenny on 11.07.2015.
 */
public class PictureHelper {
    public static final String TAG = PictureHelper.class.getSimpleName();

    public Bitmap mBitmap;

    public Bitmap getMarkerPictures(UUID id) {

        //setzt Bilder

        String imageUrl = "http://charmander.iriscouch.com/pictures/"+id.toString()+"/"+id.toString()+".png";

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(imageUrl)
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                Log.d(TAG, "Request gescheitert");
            }

            @Override
            public void onResponse(Response response) throws IOException {

                InputStream inputStream = response.body().byteStream();
                final Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

                if(response.isSuccessful()){
                   setBitmap(bitmap);
                }
            }
        });

        return getBitmap();
    }


    public void setBitmap(Bitmap bitmap) {
        mBitmap = bitmap;
    }

    public Bitmap getBitmap(){
        return mBitmap;
    }
}
