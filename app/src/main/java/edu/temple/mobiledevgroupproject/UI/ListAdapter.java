package edu.temple.mobiledevgroupproject.UI;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import edu.temple.mobiledevgroupproject.Objects.Job;
import edu.temple.mobiledevgroupproject.R;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

    private ArrayList<Job> jobList;
    private static RecyclerViewItemClicked itemClickedListener;

    public ListAdapter(ArrayList<Job> jobList, RecyclerViewItemClicked itemClickedListener) {
        this.jobList = jobList;
        this.itemClickedListener = itemClickedListener;
    }

    @NonNull
    @Override
    public ListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater
                .from(viewGroup.getContext())
                .inflate(R.layout.card_layout, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListAdapter.ViewHolder viewHolder, int i) {
        Job thisJob = jobList.get(viewHolder.getAdapterPosition());

        viewHolder.titleTextView.setText(thisJob.getJobTitle());
        viewHolder.descTextView.setText(thisJob.getJobDescription());
        viewHolder.userTextView.setText(thisJob.getUser().getUserName());

        String month = String.valueOf(thisJob.getDateOfJob().getMonth());
        String day = String.valueOf(thisJob.getDateOfJob().getDay());
        String year = String.valueOf(thisJob.getDateOfJob().getYear());

        viewHolder.dateOfJobTextView.setText(month + "/" + day + "/" + year);
    }

    @Override
    public int getItemCount() {
        return jobList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView titleTextView;
        TextView descTextView;
        TextView userTextView;
        TextView dateOfJobTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.title_tv);
            descTextView = itemView.findViewById(R.id.desc_tv);
            userTextView = itemView.findViewById(R.id.user_tv);
            dateOfJobTextView = itemView.findViewById(R.id.date_tv);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            itemClickedListener.userItemClick(v, this.getAdapterPosition());
        }
    }
}
