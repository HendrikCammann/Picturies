package com.project.charmander.picturies.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.project.charmander.picturies.listItems.ImageListItem;
import com.project.charmander.picturies.R;

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

    class ChooseImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView information;
        ImageView thumpnail;
        LinearLayout view;

        public ChooseImageViewHolder(View itemView) {
            super(itemView);
            information = (TextView) itemView.findViewById(R.id.image_information);
            thumpnail = (ImageView) itemView.findViewById(R.id.image_thumpnail);
            view = (LinearLayout) itemView.findViewById(R.id.choose_image_item);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Drawable test = view.getBackground();
            Drawable selectedColor = new ColorDrawable(Color.parseColor("#a31258"));

            if(test != selectedColor) {
                view.setBackground(new ColorDrawable(Color.parseColor("#a31258")));
                Toast.makeText(v.getContext(), "SELECTED", Toast.LENGTH_LONG).show();
            } else {
                view.setBackground(new ColorDrawable(Color.parseColor("#FFFFFF")));
                Toast.makeText(v.getContext(), "DESELECTED", Toast.LENGTH_LONG).show();
            }
        }
    }
}

