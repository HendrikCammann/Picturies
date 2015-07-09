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


public class FriendsAdapter extends RecyclerView.Adapter<FriendsAdapter.FriendsViewHolder> {

    private LayoutInflater inflater;
    List<FriendsListItem> data = Collections.emptyList();

    public FriendsAdapter(Context context, List<FriendsListItem> data) {

        inflater = LayoutInflater.from(context);
        this.data = data;
    }

    @Override
    public FriendsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.friends_list_item, parent, false);
        FriendsViewHolder holder = new FriendsViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(FriendsViewHolder holder, int position) {

        FriendsListItem current = data.get(position);

        holder.information.setText(current.title);
        holder.thumpnail.setImageResource(current.iconId);
    }

    @Override
    public int getItemCount() {

        return data.size();
    }

    class FriendsViewHolder extends RecyclerView.ViewHolder {

        TextView information;
        ImageView thumpnail;

        public FriendsViewHolder(View itemView) {
            super(itemView);
            information = (TextView) itemView.findViewById(R.id.friend_information);
            thumpnail = (ImageView) itemView.findViewById(R.id.friend_thumpnail);
        }
    }
}
