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

import com.project.charmander.picturies.R;
import com.project.charmander.picturies.fragments.ReportDetailActivity;
import com.project.charmander.picturies.listItems.ReportListItem;

import java.util.Collections;
import java.util.List;


public class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.ReportViewHolder> {

    private LayoutInflater inflater;
    List<ReportListItem> data = Collections.emptyList();
    final Fragment detail = new ReportDetailActivity();
    FragmentTransaction ft;


    public ReportAdapter(Context context, List<ReportListItem> data) {

        ft = ((FragmentActivity)context).getSupportFragmentManager().beginTransaction();

        inflater = LayoutInflater.from(context);
        this.data = data;
    }

    @Override
    public ReportViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.report_list_item, parent, false);
        ReportViewHolder holder = new ReportViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ReportViewHolder holder, int position) {

        ReportListItem current = data.get(position);

        holder.headline.setText(current.title);
    }

    @Override
    public int getItemCount() {

        return data.size();
    }

    class ReportViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView headline;

        public ReportViewHolder(View itemView) {
            super(itemView);
            headline = (TextView) itemView.findViewById(R.id.report_name);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
//            Toast.makeText(v.getContext(), "Click", Toast.LENGTH_SHORT).show();
            ft.replace(R.id.main_fragment, detail).commit();
        }
    }
}

//http://charmander.iriscouch.com/pictures/_design/mypictures/_view/mypictures?key=%22org.couchdb.user%3Ahendrik%22
