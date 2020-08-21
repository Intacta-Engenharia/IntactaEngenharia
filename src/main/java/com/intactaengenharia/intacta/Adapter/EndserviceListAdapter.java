package com.intactaengenharia.intacta.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.intactaengenharia.intacta.R;

import java.util.ArrayList;

public class EndserviceListAdapter extends RecyclerView.Adapter<EndserviceListAdapter.MyViewHolder> {

     private Context mContext;
    private boolean ended = false;
     ArrayList<String> endServiceList;

    public EndserviceListAdapter(Context mContext, ArrayList<String> endServiceList) {
        this.mContext = mContext;
        this.endServiceList = endServiceList;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.endservice_layout,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            holder.title.setText(endServiceList.get(position));
            if (position == 0){
                holder.timeline.setVisibility(View.GONE);
            }

            if (endServiceList.get(position).equals("Nenhum serviço finalizado")){
                holder.title.setTextColor(Color.LTGRAY);
                holder.title.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                holder.title.setPadding(15,15,15,15);
                holder.title.setCompoundDrawablesWithIntrinsicBounds(null,null,null,null);
            }
    }


    @Override
    public int getItemCount() {
        return endServiceList.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private LinearLayout timeline;




        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            timeline = itemView.findViewById(R.id.timeline);

        }
    }
}
