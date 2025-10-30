package com.intactaengenharia.intacta.view.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.intactaengenharia.intacta.R;
import com.intactaengenharia.intacta.service.utils.Alerts;


import java.util.List;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.MyViewHolder> {

    private final List<String> imgs;
    PhotoAdapter(List<String> imgs) {
        this.imgs = imgs;
    }

    @NonNull
    @Override
    public PhotoAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_photos, parent, false);
        return new PhotoAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoAdapter.MyViewHolder holder, int position) {
        Context context = holder.itemView.getContext();
        Glide.with(context).load(imgs.get(position))
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        holder.error.setText(e.getMessage());
                        holder.error.setVisibility(View.VISIBLE);
                        holder.pgPhotos.setVisibility(View.GONE);
                        holder.mImgPreview.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        Animation in = AnimationUtils.loadAnimation(context, R.anim.fade_in);
                        holder.mImgPreview.setVisibility(View.VISIBLE);
                        holder.mImgPreview.startAnimation(in);
                        holder.pgPhotos.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(holder.mImgPreview);


        holder.mImgPreview.setOnClickListener(v -> {
            Alerts alerts = new Alerts(context);
            alerts.dialogPhoto(imgs, position);
        });


    }


    @Override
    public int getItemCount() {
        return imgs.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        private final ImageView mImgPreview;
        private final TextView error;
        private final ProgressBar pgPhotos;
        private final CardView mCard;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mImgPreview = itemView.findViewById(R.id.pictures);
            mCard = itemView.findViewById(R.id.card_photos);
            pgPhotos = itemView.findViewById(R.id.pg_photo);
            error = itemView.findViewById(R.id.error);
        }
    }

}


