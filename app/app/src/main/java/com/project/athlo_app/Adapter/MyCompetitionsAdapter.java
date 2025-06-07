package com.project.athlo_app.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.athlo_app.DataModels.event;
import com.project.athlo_app.R;

import java.util.List;

public class MyCompetitionsAdapter extends RecyclerView.Adapter<MyCompetitionsAdapter.MyViewHolder> {
    private List<event> eventList;

    public MyCompetitionsAdapter(List<event> eventList){
        this.eventList=eventList;

    }
    @NonNull
    @Override
    public MyCompetitionsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.mycompetitions_layout,parent,false);

        return  new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyCompetitionsAdapter.MyViewHolder holder, int position) {
        event event=eventList.get(position);

        holder.tvEventName.setText(event.getName());
        holder.tvSport.setText("Sport: " + event.getSport());
        holder.tvOrganizer.setText("Organizer: " + event.getOrganizerId());
        holder.tvDate.setText("Date: " + event.getStartDate() + " - " + event.getEndDate());
        holder.tvStatus.setText(event.getStatus());

    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvEventName, tvSport, tvOrganizer, tvDate, tvStatus;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvEventName = itemView.findViewById(R.id.tvEventName);
            tvSport = itemView.findViewById(R.id.tvSport);
            tvOrganizer = itemView.findViewById(R.id.tvOrganizer);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvStatus = itemView.findViewById(R.id.tvStatus);

        }
    }
}
