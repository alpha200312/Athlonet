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

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.MyViewHolder> {
     private List<event> eventList;

    public EventAdapter(List<event> eventList) {
        this.eventList=eventList;
    }


    @NonNull
    @Override
    public EventAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.events_card,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventAdapter.MyViewHolder holder, int position) {
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
