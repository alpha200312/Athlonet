package com.project.athlo_app.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.athlo_app.DataModels.organization;
import com.project.athlo_app.HomeFragment;
import com.project.athlo_app.R;
import com.project.athlo_app.interfaces.Recycler_view_listener_home;

import java.util.ArrayList;

public class OrganizationAdapter extends RecyclerView.Adapter<OrganizationAdapter.MyViewHolder> {

    Context context;
    ArrayList <organization> arrayList;
    Recycler_view_listener_home recyclerViewListenerHome;


    public OrganizationAdapter(Context context, ArrayList<organization> arrayList, HomeFragment homeFragment) {
        this.context=context;
        this.arrayList=arrayList;
        this.recyclerViewListenerHome=homeFragment;


    }

    @NonNull
    @Override
    public OrganizationAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.organization_item,parent,false);
        final MyViewHolder myViewHolder=new MyViewHolder(view);

        return myViewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull OrganizationAdapter.MyViewHolder holder, int position) {
        final String nam=arrayList.get(position).getOrganization_name();
        holder.name.setText(nam);

        holder.follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(holder.follow.getText()=="follow"){

                    holder.follow.setText("followed");
                }else{
                    holder.follow.setText("follow");
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        Button follow;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.tvOrganizationName);
            follow=itemView.findViewById(R.id.btnFollow);


         itemView.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 recyclerViewListenerHome.get_events(getAdapterPosition());
             }
         });



        }


    }
}
