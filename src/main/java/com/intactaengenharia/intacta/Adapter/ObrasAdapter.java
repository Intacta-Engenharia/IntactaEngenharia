package com.intactaengenharia.intacta.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.intactaengenharia.intacta.Beans.Obra;
import com.intactaengenharia.intacta.MainActivityv2;
import com.intactaengenharia.intacta.R;
import java.text.NumberFormat;
import java.util.ArrayList;
import me.zhanghai.android.materialprogressbar.MaterialProgressBar;

public class ObrasAdapter extends RecyclerView.Adapter<ObrasAdapter.MyViewHolder>   {

    ArrayList<Obra> obras;
    Activity activity;

    public ObrasAdapter(ArrayList<Obra> obras, Activity activity) {
        this.obras = obras;
        this.activity = activity;

    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater mInflater = LayoutInflater.from(activity.getBaseContext());
        view = mInflater.inflate(R.layout.obras_card,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final Obra obra = obras.get(position);
        NumberFormat formatter = NumberFormat.getCurrencyInstance();

        double entry = (double) Math.round(obra.getEntry()) / 100;
        System.out.println( entry + "% pago: " );
        Double pay = Math.round( obra.getValor()) * entry /100;
        System.out.println(pay);
        System.out.println("Valor de contrato " + obra.getValor() /100);
        System.out.println("Entrada " + pay);
        holder.entrada.setText(new StringBuilder().append(obra.getEntry()).append("% pago: ").append(formatter.format(pay)).toString());
        holder.obra.setText(obra.getObra());
        holder.data.setText(obra.getData());

        holder.progress.setProgress(obra.getProgress().intValue());
        holder.layout.setOnClickListener(v -> {
            //Toast.makeText(activity,"selecionou " +obra.getObra() + "key " + obra.getKey() , Toast.LENGTH_LONG).show();
            System.out.println("idofkey:" + obra.getKey());
            Intent i = new Intent(activity, MainActivityv2.class);
            i.putExtra("keyobra",obra.getKey());

            activity.startActivity(i);
        });
        if (obra.getProgress() == 100){
           int white = Color.WHITE;
            holder.data.setTextColor(white);
            holder.obra.setTextColor(white);
            holder.entrada.setTextColor(white);
         }
    }






    @Override
    public int getItemCount() {
        return obras.size();
    }
    static class MyViewHolder extends  RecyclerView.ViewHolder {
        TextView obra,data,entrada;
        MaterialProgressBar progress;
        CardView layout;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            entrada = itemView.findViewById(R.id.paid);
            obra = itemView.findViewById(R.id.obra);
            data = itemView.findViewById(R.id.data);
            layout = itemView.findViewById(R.id.card);
            progress = itemView.findViewById(R.id.progress);
        }
    }

}

