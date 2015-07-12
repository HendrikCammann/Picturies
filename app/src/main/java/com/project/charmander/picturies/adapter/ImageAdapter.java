package com.project.charmander.picturies.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.charmander.picturies.fragments.ImageDetailViewActivity;
import com.project.charmander.picturies.R;
import com.project.charmander.picturies.model.Picture;

import java.util.ArrayList;


public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {

    private LayoutInflater inflater;
    final Fragment detail = new ImageDetailViewActivity();
    FragmentTransaction ft;
    private ArrayList<Picture> mPictures;


    public ImageAdapter(Context context, ArrayList<Picture> pictures) {

        ft = ((FragmentActivity)context).getSupportFragmentManager().beginTransaction();
        inflater = LayoutInflater.from(context);
        mPictures = pictures;
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.image_list_item, parent, false);
        ImageViewHolder holder = new ImageViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {
        holder.bindPicture( mPictures.get(position));
    }

    @Override
    public int getItemCount() {
        return mPictures.size();
    }

    class ImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView mInformation;
        ImageView mThumpnail;
        public Picture mPicture;

        public ImageViewHolder(View itemView) {
            super(itemView);
            mInformation = (TextView) itemView.findViewById(R.id.image_information);
            mThumpnail = (ImageView) itemView.findViewById(R.id.image_thumpnail);
            itemView.setOnClickListener(this);
        }

        public void bindPicture(Picture picture){
            mPicture = picture;
            mInformation.setText(picture.getName());
            mThumpnail.setImageBitmap(picture.getImage());

        }

        @Override
        public void onClick(View v) {
//            Toast.makeText(v.getContext(), "Click", Toast.LENGTH_SHORT).show();
            Bundle args = new Bundle();
            args.putInt("position", getPosition());
            detail.setArguments(args);
            ft.replace(R.id.main_fragment, detail).commit();
        }
    }
}
