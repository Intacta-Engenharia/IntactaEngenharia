package com.intactaengenharia.intacta.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.intactaengenharia.intacta.R;
import java.util.List;

public class EndServiceAdapter extends RecyclerView.Adapter<EndServiceAdapter.MyViewHolder> {

    private final List<String> mList;

    public EndServiceAdapter(List<String> mList) {
        this.mList = mList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_endservice, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.bind(mList.get(position));
    }


    @Override
    public int getItemCount() {
        return mList.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView mTxtEndService;
        private final LinearLayout timeline;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mTxtEndService = itemView.findViewById(R.id.txt_endservice);
            timeline = itemView.findViewById(R.id.timeline);
        }

        void bind(String endService) {
            if (endService != null) {
                mTxtEndService.setVisibility(View.VISIBLE);
                timeline.setVisibility(View.VISIBLE);
                mTxtEndService.setText(endService);
            } else {
                timeline.setVisibility(View.GONE);
                mTxtEndService.setVisibility(View.GONE);
            }
        }
    }
}
