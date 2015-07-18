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

import java.util.Collections;
import java.util.List;


public class ReportDetailAdapter extends RecyclerView.Adapter<ReportDetailAdapter.ReportDetailViewHolder> {
    private LayoutInflater inflater;
    List<ReportDetailListItem> data = Collections.emptyList();

    public ReportDetailAdapter(Context context, List<ReportDetailListItem> data) {

        inflater = LayoutInflater.from(context);
        this.data = data;
    }

    @Override
    public ReportDetailViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.report_detail_list_item, parent, false);
        ReportDetailViewHolder holder = new ReportDetailViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ReportDetailViewHolder holder, int position) {

        ReportDetailListItem current = data.get(position);

        holder.information.setText(current.Description);
        holder.thumpnail.setImageResource(current.IconID);
    }

    @Override
    public int getItemCount() {

        return data.size();
    }

    class ReportDetailViewHolder extends RecyclerView.ViewHolder {

        TextView information;
        ImageView thumpnail;

        public ReportDetailViewHolder(View itemView) {
            super(itemView);
            information = (TextView) itemView.findViewById(R.id.headline_report);
            thumpnail = (ImageView) itemView.findViewById(R.id.image_report);
        }
    }
}
