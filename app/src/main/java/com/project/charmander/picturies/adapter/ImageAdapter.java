package com.project.charmander.picturies.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.project.charmander.picturies.fragments.ImageDetailViewActivity;
import com.project.charmander.picturies.fragments.ReportDetailActivity;
import com.project.charmander.picturies.listItems.ImageListItem;
import com.project.charmander.picturies.R;

import java.util.Collections;
import java.util.List;


public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {

    private LayoutInflater inflater;
    List<ImageListItem> data = Collections.emptyList();
    final Fragment detail = new ImageDetailViewActivity();
    FragmentTransaction ft;


    public ImageAdapter(Context context, List<ImageListItem> data) {

        ft = ((FragmentActivity)context).getSupportFragmentManager().beginTransaction();
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

    class ImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView information;
        ImageView thumpnail;

        public ImageViewHolder(View itemView) {
            super(itemView);
            information = (TextView) itemView.findViewById(R.id.image_information);
            thumpnail = (ImageView) itemView.findViewById(R.id.image_thumpnail);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
//            Toast.makeText(v.getContext(), "Click", Toast.LENGTH_SHORT).show();
            ft.replace(R.id.main_fragment, detail).commit();
        }
    }
}
