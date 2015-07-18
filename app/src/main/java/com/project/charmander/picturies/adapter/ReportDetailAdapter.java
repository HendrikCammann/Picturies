package com.project.charmander.picturies.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.charmander.picturies.R;
import com.project.charmander.picturies.listItems.ReportDetailListItem;
import com.project.charmander.picturies.model.Picture;
import com.project.charmander.picturies.model.Roadtrip;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class ReportDetailAdapter extends RecyclerView.Adapter<ReportDetailAdapter.ReportDetailViewHolder> {
    private LayoutInflater inflater;
    List<ReportDetailListItem> data = Collections.emptyList();
    ArrayList<Picture> mPictures;

    public ReportDetailAdapter(Context context, ArrayList<Picture> pictures ) {

        inflater = LayoutInflater.from(context);
        mPictures = pictures;
    }

    @Override
    public ReportDetailViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.report_detail_list_item, parent, false);
        ReportDetailViewHolder holder = new ReportDetailViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ReportDetailViewHolder holder, int position) {
        holder.bindPicture(mPictures.get(position));
    }

    @Override
    public int getItemCount() {

        return mPictures.size();
    }

    class ReportDetailViewHolder extends RecyclerView.ViewHolder {

        TextView information;
        ImageView thumpnail;
        Picture mPicture;

        public void bindPicture(Picture picture){
            mPicture = picture;
            information.setText(picture.getName() + " - " + picture.getDescription());
            thumpnail.setImageBitmap(picture.getImage());
        }

        public ReportDetailViewHolder(View itemView) {
            super(itemView);
            information = (TextView) itemView.findViewById(R.id.headline_report);
            thumpnail = (ImageView) itemView.findViewById(R.id.image_report);
        }
    }
}
