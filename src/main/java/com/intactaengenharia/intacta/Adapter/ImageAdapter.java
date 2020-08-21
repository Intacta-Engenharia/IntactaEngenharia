package com.intactaengenharia.intacta.Adapter;

import android.app.Activity;
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
import com.intactaengenharia.intacta.Utilities.Alerts;


import java.util.ArrayList;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.MyViewHolder> {

    private ArrayList<String> imgs;
    private Activity activity;
    private  int position;
    ImageAdapter(ArrayList<String> imgs, Activity activity) {
        this.imgs = imgs;
        this.activity = activity;

    }


    @NonNull
    @Override
    public ImageAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater mInflater = LayoutInflater.from(activity.getBaseContext());
        view = mInflater.inflate(R.layout.photos_card,parent,false);

        return new ImageAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageAdapter.MyViewHolder holder, int position) {

            Glide.with(activity).load(imgs.get(position))
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            holder.error.setText(e.getMessage());
                            holder.error.setVisibility(View.VISIBLE);
                            holder.loading.setVisibility(View.GONE);
                            holder.imgPreview.setVisibility(View.GONE);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {

                            Animation in = AnimationUtils.loadAnimation(activity,R.anim.fade_in);
                            holder.imgPreview.setVisibility(View.VISIBLE);
                            holder.imgPreview.startAnimation(in);
                            holder.loading.setVisibility(View.GONE);
                            return false;
                        }
                    })
                    .into(holder.imgPreview);


                holder.imgPreview.setOnClickListener(v -> {
                    Alerts alerts = new Alerts(activity);
                    alerts.dialogPhoto(imgs,position);
                });


    }


    @Override
    public int getItemCount() {
        return imgs.size();
    }
    static class MyViewHolder extends  RecyclerView.ViewHolder {
        ImageView imgPreview;
        TextView error;
        ProgressBar loading;
        CardView layout;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPreview = itemView.findViewById(R.id.pictures);
            layout = itemView.findViewById(R.id.card);
            loading = itemView.findViewById(R.id.loading);
            error = itemView.findViewById(R.id.error);
        }
    }

}


