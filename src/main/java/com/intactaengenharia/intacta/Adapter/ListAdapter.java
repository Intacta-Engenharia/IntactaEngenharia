package com.intactaengenharia.intacta.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.intactaengenharia.intacta.R;

import java.util.ArrayList;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.MyViewHolder> {

     private Context mContext;
    private boolean ended = false;
     ArrayList<String> data;

    public ListAdapter(Context mContext, ArrayList<String> data,boolean ended ) {
         this.mContext = mContext;
        this.data = data;
        this.ended = ended;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.custom_list,parent,false);


        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            holder.title.setText(data.get(position));
        if (ended) {
            holder.title.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_check_circle_black_24dp,0);
        }else if(data.get(position).equals("Nenhum serviço finalizado")){
             holder.title.setTextColor(Color.LTGRAY);
        }
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView title;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);


        }
    }
}
