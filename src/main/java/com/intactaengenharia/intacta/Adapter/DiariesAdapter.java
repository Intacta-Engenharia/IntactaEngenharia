package com.intactaengenharia.intacta.Adapter;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.intactaengenharia.intacta.Beans.Diary;
import com.intactaengenharia.intacta.R;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DiariesAdapter extends RecyclerView.Adapter<DiariesAdapter.MyViewHolder>  {

    private Activity activity;
    ArrayList<String> servicelist, workerlist, photolist;
    ArrayList<Diary> diarylist;

    public DiariesAdapter(Activity mContext, ArrayList<Diary> diary) {
        this.activity = mContext;
        this.diarylist = diary;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater mInflater = LayoutInflater.from(activity);
        view = mInflater.inflate(R.layout.history,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        if (diarylist.get(position) != null) {
            if (diarylist.get(position).getFiscal() != null && diarylist.get(position).getLocal() != null) {
                holder.fiscal.setText(diarylist.get(position).getFiscal());
                holder.local.setText(diarylist.get(position).getLocal());
                setServiceAdapter(holder,diarylist.get(position).getServices());
                setWorkerAdapter(holder,diarylist.get(position).getWorkers());
                setPhotoAdapter(holder,diarylist.get(position).getPhotos());
            } else {
                holder.fiscal.setText("Fiscal não informado");
                holder.local.setText("Local não informado");
                holder.local.setTextColor(Color.LTGRAY);
                holder.fiscal.setTextColor(Color.LTGRAY);
            }
            Date date;
            SimpleDateFormat sdf = new SimpleDateFormat("EEE, d MMM yyyy");
            try {
                date = new SimpleDateFormat("dd/MM/yyyy").parse(diarylist.get(position).getData().toUpperCase());
                holder.title.setText(sdf.format(date));

            } catch (ParseException e) {
                e.printStackTrace();
                holder.title.setText(sdf.format(diarylist.get(position).getData()));
            }
            //getdata(holder, position);

            holder.maincheck.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (!isChecked) {
                    holder.maincontent.setVisibility(View.GONE);
                } else {
                    holder.maincontent.setVisibility(View.VISIBLE);
                }
            });

            holder.top.setOnClickListener(v -> {
                if (holder.maincheck.isChecked()) {
                    holder.maincheck.setChecked(false);
                } else {
                    holder.maincheck.setChecked(true);
                }
            });


            holder.workers.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (!isChecked) {
                    holder.rvWorkers.setVisibility(View.GONE);
                } else {
                    holder.rvWorkers.setVisibility(View.VISIBLE);
                }
            });
            holder.startservices.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (!isChecked) {
                    holder.rvServices.setVisibility(View.GONE);
                } else {
                    holder.rvServices.setVisibility(View.VISIBLE);
                }
            });
            holder.checkphotos.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (!isChecked) {
                    holder.rvPhotos.setVisibility(View.GONE);
                } else {
                    holder.rvPhotos.setVisibility(View.VISIBLE);
                }
            });

            if (position == 0) {
                holder.maincheck.setChecked(true);
            }


            if (!diarylist.get(position).isOperavel()) {
                int drkgray = Color.DKGRAY;
                holder.top.setBackgroundColor(Color.LTGRAY);
                holder.title.setTextColor(drkgray);
                holder.fiscal.setTextColor(drkgray);
                holder.fiscal.setText("Dia não operável!");
                holder.fiscal.setGravity(Gravity.CENTER);
                holder.fiscal.setTypeface(holder.fiscal.getTypeface(), Typeface.BOLD);
                holder.maincheck.setVisibility(View.GONE);
                holder.top.setEnabled(false);
                holder.maincontent.setVisibility(View.GONE);
                holder.local.setVisibility(View.GONE);

            } else {
                holder.top.setBackgroundColor(activity.getResources().getColor(R.color.colorPrimaryDark));
                holder.title.setTextColor(Color.WHITE);
                holder.fiscal.setTextColor(Color.WHITE);
            }


        } else {

            int drkgray = Color.RED;
            holder.top.setBackgroundColor(Color.DKGRAY);
            holder.title.setVisibility(View.GONE);
            holder.fiscal.setTextColor(drkgray);
            holder.fiscal.setText("Adicionar novo diário de obra");
            holder.fiscal.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            holder.fiscal.setPadding(10, 10, 10, 10);
            holder.fiscal.setGravity(Gravity.CENTER);
            holder.fiscal.setTypeface(holder.fiscal.getTypeface(), Typeface.BOLD);
            holder.maincheck.setVisibility(View.GONE);
            holder.maincontent.setVisibility(View.GONE);
            holder.local.setVisibility(View.GONE);
            holder.top.setOnClickListener(v -> {
                SlidingUpPanelLayout panelLayout = activity.findViewById(R.id.view);
                panelLayout.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
            });
            holder.fiscal.setOnClickListener(v -> {
                SlidingUpPanelLayout panelLayout = activity.findViewById(R.id.view);
                panelLayout.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
            });

        }
        Animation in = AnimationUtils.loadAnimation(activity, R.anim.fade_in);
        holder.card.startAnimation(in);
    }

    private void setServiceAdapter(MyViewHolder holder,ArrayList<String> services) {
        if(services != null) {
            ListAdapter adapter = new ListAdapter(activity, services, false);
            holder.rvServices.setAdapter(adapter);
            GridLayoutManager llm = new GridLayoutManager(activity, 1, RecyclerView.VERTICAL, false);
            holder.rvServices.setLayoutManager(llm);
        }
    }

    private void setWorkerAdapter(MyViewHolder holder,ArrayList<String> workers) {
        if(workers != null) {
            ListAdapter adapter = new ListAdapter(activity, workers, false);
            holder.rvWorkers.setAdapter(adapter);
            GridLayoutManager llm = new GridLayoutManager(activity, 1, RecyclerView.VERTICAL, false);
            holder.rvWorkers.setLayoutManager(llm);
        }
    }
    private void setPhotoAdapter(MyViewHolder holder,ArrayList<String> photos){
        if(photos != null) {
            if (!photos.isEmpty()) {
                ImageAdapter adapter = new ImageAdapter(photos, activity);
                holder.rvPhotos.setAdapter(adapter);
                GridLayoutManager llm = null;
                if (photos.size() > 2) {
                    llm = new GridLayoutManager(activity, 2, RecyclerView.HORIZONTAL, false);
                } else {
                    llm = new GridLayoutManager(activity, 1, RecyclerView.HORIZONTAL, false);

                }
                holder.rvPhotos.setLayoutManager(llm);
            }
        }
    }

    @Override
    public int getItemCount() {
        return diarylist.size();
    }



    static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView title,fiscal,local;
        private CheckBox startservices,maincheck, workers, checkphotos;
        private RecyclerView rvServices, rvWorkers, rvPhotos;
        private LinearLayout maincontent,top;
        private CardView card;
        //private ImageButton generatepdf;


        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            top = itemView.findViewById(R.id.top);
            card = itemView.findViewById(R.id.card);
            fiscal = itemView.findViewById(R.id.fiscal);
            local = itemView.findViewById(R.id.local);
            title = itemView.findViewById(R.id.title);
            startservices = itemView.findViewById(R.id.startservices);
            rvServices = itemView.findViewById(R.id.rv_services);
            rvPhotos = itemView.findViewById(R.id.rv_photos);
            checkphotos = itemView.findViewById(R.id.checkphotos);
            workers = itemView.findViewById(R.id.workers);
            rvWorkers = itemView.findViewById(R.id.rv_workers);
            maincheck = itemView.findViewById(R.id.hidehistory);
            maincontent = itemView.findViewById(R.id.maincontent);
            //generatepdf = itemView.findViewById(R.id.generatepdf);
        }
    }
}
