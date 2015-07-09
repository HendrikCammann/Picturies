package com.project.charmander.picturies.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.charmander.picturies.listItems.ImageListItem;
import com.project.charmander.picturies.R;

import java.util.Collections;
import java.util.List;


public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {

    private LayoutInflater inflater;
    List<ImageListItem> data = Collections.emptyList();

    public ImageAdapter(Context context, List<ImageListItem> data) {

        inflater = LayoutInflater.from(context);
        this.data = data;
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.image_list_item, parent, false);
        ImageViewHolder holder = new ImageViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {

        ImageListItem current = data.get(position);

        holder.information.setText(current.title);
        holder.thumpnail.setImageResource(current.iconId);
    }

    @Override
    public int getItemCount() {

        return data.size();
    }

    class ImageViewHolder extends RecyclerView.ViewHolder {

        TextView information;
        ImageView thumpnail;

        public ImageViewHolder(View itemView) {
            super(itemView);
            information = (TextView) itemView.findViewById(R.id.image_information);
            thumpnail = (ImageView) itemView.findViewById(R.id.image_thumpnail);
        }
    }
}
