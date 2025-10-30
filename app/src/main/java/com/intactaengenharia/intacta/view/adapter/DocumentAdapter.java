package com.intactaengenharia.intacta.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.intactaengenharia.intacta.R;
import com.intactaengenharia.intacta.service.model.Document;
import com.intactaengenharia.intacta.service.utils.Alerts;

import java.util.ArrayList;
import java.util.List;

public class DocumentAdapter extends RecyclerView.Adapter<DocumentAdapter.MyViewHolder> {

    private List<Document> mList;

    public DocumentAdapter() {
        this.mList = new ArrayList<>();
    }

    @NonNull
    @Override
    public DocumentAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_document, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DocumentAdapter.MyViewHolder holder, int position) {
        holder.bind(mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void notifyChanged(List<Document> list) {
        if (list != null) {
            mList = list;
            notifyDataSetChanged();
        }
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView mTextDocument;
        private final CardView mCard;
        private final Context context;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mCard = itemView.findViewById(R.id.card_document);
            mTextDocument = itemView.findViewById(R.id.txt_document);
            context = itemView.getContext();
        }

        void bind(Document document) {
            mCard.setEnabled(!document.getTitle().isEmpty());
            mTextDocument.setText(document.getTitle());
            mCard.setOnClickListener(v -> Alerts.dialogPdf(context, document.getUrl()));
        }
    }


}
