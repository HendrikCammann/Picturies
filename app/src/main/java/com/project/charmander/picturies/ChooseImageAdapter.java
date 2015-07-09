package com.project.charmander.picturies;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;


public class ChooseImageAdapter extends RecyclerView.Adapter<ChooseImageAdapter.ChooseImageViewHolder> {

    private LayoutInflater inflater;
    List<ImageListItem> data = Collections.emptyList();

    public ChooseImageAdapter(Context context, List<ImageListItem> data) {

        inflater = LayoutInflater.from(context);
        this.data = data;
    }

    @Override
    public ChooseImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.image_list_item_small, parent, false);
        ChooseImageViewHolder holder = new ChooseImageViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ChooseImageViewHolder holder, int position) {

        ImageListItem current = data.get(position);

        holder.information.setText(current.title);
        holder.thumpnail.setImageResource(current.iconId);
    }

    @Override
    public int getItemCount() {

        return data.size();
    }

    class ChooseImageViewHolder extends RecyclerView.ViewHolder {

        TextView information;
        ImageView thumpnail;

        public ChooseImageViewHolder(View itemView) {
            super(itemView);
            information = (TextView) itemView.findViewById(R.id.image_information);
            thumpnail = (ImageView) itemView.findViewById(R.id.image_thumpnail);
        }
    }
}

