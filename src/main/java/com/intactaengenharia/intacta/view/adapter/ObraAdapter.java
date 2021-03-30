package com.intactaengenharia.intacta.view.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.intactaengenharia.intacta.service.listeners.OnItemClickListener;
import com.intactaengenharia.intacta.service.model.Obra;
import com.intactaengenharia.intacta.R;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import me.zhanghai.android.materialprogressbar.MaterialProgressBar;

public class ObraAdapter extends RecyclerView.Adapter<ObraAdapter.MyViewHolder> {

    private List<Obra> mList;
    private final OnItemClickListener<Obra> mListener;

    public ObraAdapter(OnItemClickListener<Obra> listener) {
        this.mList = new ArrayList<>();
        this.mListener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_obra, parent, false);
        return new MyViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.bind(mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void notifyChanged(List<Obra> list) {
        if (list != null) {
            mList = list;
            notifyDataSetChanged();
        }
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView mTitle;
        private final TextView mDate;
        private final TextView mEntry;
        private final TextView mLocal;
        private final MaterialProgressBar mProgress;
        private final CardView mCard;
        private final OnItemClickListener<Obra> mListener;

        MyViewHolder(@NonNull View itemView, OnItemClickListener<Obra> listener) {
            super(itemView);
            mCard = itemView.findViewById(R.id.card_obra);
            mTitle = itemView.findViewById(R.id.title_obra);
            mProgress = itemView.findViewById(R.id.progress);
            mLocal = itemView.findViewById(R.id.local);
            mEntry = itemView.findViewById(R.id.paid);
            mDate = itemView.findViewById(R.id.data);
            mListener = listener;
        }


        public void bind(Obra obra) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", new Locale("pt", "BR"));
            NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

            double entry = (double) Math.round(obra.getEntry()) / 100;
            Double pay = Math.round(obra.getValor()) * entry / 100;
            mEntry.setText(new StringBuilder().append(obra.getEntry()).append("% pago: ").append(formatter.format(pay)).toString());
            mTitle.setText(obra.getObra());
            mLocal.setText(obra.getLocal());
            mProgress.setProgress(obra.getProgress().intValue());
            try {
                Date d = dateFormat.parse(obra.getData());
                if (d != null) {
                    mDate.setText(dateFormat.format(d));
                }
            } catch (ParseException e) {
                e.printStackTrace();
                mDate.setText(obra.getData());
            }

            mCard.setOnClickListener(v -> {
                mListener.onItemClick(obra);
            });
            mCard.setOnLongClickListener(view -> {
                mListener.onLongClick(obra.getKey());
                return true;
            });
            if (obra.getProgress() == 100) {
                int white = Color.WHITE;
                mDate.setTextColor(white);
                mTitle.setTextColor(white);
                mEntry.setTextColor(white);
                mLocal.setTextColor(white);
            } else {
                int black = Color.BLACK;
                mDate.setTextColor(black);
                mTitle.setTextColor(black);
                mEntry.setTextColor(black);
                mLocal.setTextColor(black);
            }


        }

    }

}

