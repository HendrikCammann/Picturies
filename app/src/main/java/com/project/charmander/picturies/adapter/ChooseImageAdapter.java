package com.project.charmander.picturies.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.project.charmander.picturies.listItems.ImageListItem;
import com.project.charmander.picturies.R;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ChooseImageAdapter extends RecyclerView.Adapter<ChooseImageAdapter.ChooseImageViewHolder> {
    public static final String TAG=ChooseImageAdapter.class.getSimpleName();

    private LayoutInflater inflater;
    List<ImageListItem> data = Collections.emptyList();

    public ArrayList<Drawable> SelectedImages = new ArrayList();
    public ArrayList<String> SelectedInformation = new ArrayList();
    public ArrayList<String> SelectedDescription = new ArrayList();

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
        TextView description;
        RelativeLayout view;
        boolean isSelcted = false;

        public ChooseImageViewHolder(View itemView) {
            super(itemView);
            information = (TextView) itemView.findViewById(R.id.image_information);
            thumpnail = (ImageView) itemView.findViewById(R.id.image_thumpnail);
            description = (TextView) itemView.findViewById(R.id.image_description);

            view = (RelativeLayout) itemView.findViewById(R.id.choose_image_item);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            if(!isSelcted) {

                view.setBackground(new ColorDrawable(Color.parseColor("#a31258")));
                information.setTextColor(Color.parseColor("#FFFFFF"));
                description.setTextColor(Color.parseColor("#FFFFFF"));
                Drawable thumpnailImage = thumpnail.getDrawable();
                String informationString = information.getText().toString();
                String descriptionString = description.getText().toString();
                SelectedImages.add(thumpnailImage);
                SelectedInformation.add(informationString);
                SelectedDescription.add(descriptionString);
                Toast.makeText(v.getContext(), "SELECTED", Toast.LENGTH_SHORT).show();
                isSelcted = true;
            } else {

                view.setBackground(new ColorDrawable(Color.parseColor("#FFFFFF")));
                information.setTextColor(Color.parseColor("#a31258"));
                description.setTextColor(Color.parseColor("#a31258"));
                Drawable thumpnailImage = thumpnail.getDrawable();
                String informationString = information.getText().toString();
                String descriptionString = description.getText().toString();

                if(SelectedImages.contains(thumpnailImage) && SelectedInformation.contains(informationString) && SelectedDescription.contains(descriptionString)) {
                    SelectedImages.remove(thumpnailImage);
                    SelectedInformation.remove(informationString);
                    SelectedDescription.remove(descriptionString);
                }

                Toast.makeText(v.getContext(), "DESELECTED", Toast.LENGTH_SHORT).show();
                isSelcted = false;
            }
        }
    }
}

