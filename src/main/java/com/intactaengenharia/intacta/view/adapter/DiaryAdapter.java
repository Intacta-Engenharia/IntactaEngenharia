package com.intactaengenharia.intacta.view.adapter;

import android.content.Context;
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

import com.intactaengenharia.intacta.service.model.Diary;
import com.intactaengenharia.intacta.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DiaryAdapter extends RecyclerView.Adapter<DiaryAdapter.MyViewHolder> {

    //ArrayList<String> servicelist, workerlist, photolist;
    private List<Diary> mList;

    public DiaryAdapter() {
        this.mList = new ArrayList<>();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.card_diary, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        if (position == 0) {
            holder.cbHide.setChecked(true);
            holder.llMore.setVisibility(View.VISIBLE);
        } else {
            holder.cbHide.setChecked(false);
            holder.llMore.setVisibility(View.GONE);
        }
        holder.bind(mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void notifyChanged(List<Diary> list) {
        if (list != null) {
            mList = list;
            notifyDataSetChanged();
        }
    }


    static class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView mTitle;
        private final TextView mFiscal;
        private final CheckBox mStartServices;
        private final CheckBox cbHide;
        private final CheckBox cbWorkers;
        private final CheckBox cbPhotos;
        private final RecyclerView rvServices;
        private final RecyclerView rvWorkers;
        private final RecyclerView rvPhotos;
        private final LinearLayout llMore;
        private final LinearLayout llShort;
        private final CardView mCard;
        private final Context mContext;
        //private ImageButton generatepdf;


        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            llShort = itemView.findViewById(R.id.ll_short);
            mCard = itemView.findViewById(R.id.card_diary);
            mFiscal = itemView.findViewById(R.id.fiscal);
            mTitle = itemView.findViewById(R.id.txt_day);
            mStartServices = itemView.findViewById(R.id.cb_services);
            rvServices = itemView.findViewById(R.id.rv_services);
            rvPhotos = itemView.findViewById(R.id.rv_photos);
            cbPhotos = itemView.findViewById(R.id.cb_photos);
            cbWorkers = itemView.findViewById(R.id.cb_workers);
            rvWorkers = itemView.findViewById(R.id.rv_workers);
            cbHide = itemView.findViewById(R.id.cb_hide);
            llMore = itemView.findViewById(R.id.ll_more);
            mContext = itemView.getContext();
            //generatepdf = itemView.findViewById(R.id.generatepdf);
        }

        public void bind(Diary diary) {
//
//            if (mList.get(position).getData().equals("Não existe diários de obra no momento.")) {
//                mTitle.setTextColor(Color.LTGRAY);
//                mTitle.setText(mList.get(position).getData());
//                llShort.setBackgroundColor(Color.WHITE);
//                cbHide.setVisibility(View.GONE);
//                mFiscal.setVisibility(View.GONE);
//
//                return;
//            }
            if (diary != null) {
                if (diary.getFiscal() != null && !diary.getFiscal().trim().isEmpty()) {
                    mFiscal.setText(diary.getFiscal());
                } else {
                    mFiscal.setText("Fiscal não informado");
                    mFiscal.setTextColor(Color.LTGRAY);
                }
                if (diary.getData() != null) {
                    Date d;
                    SimpleDateFormat showUser = new SimpleDateFormat("EEE, d MMM yyyy", new Locale("pt", "BR"));
                    SimpleDateFormat convertTodate = new SimpleDateFormat("dd/MM/yyyy", new Locale("pt", "BR"));
                    try {
                        d = convertTodate.parse(diary.getData().toUpperCase());
                        if (d != null) {
                            mTitle.setText(showUser.format(d));
                        }

                    } catch (ParseException e) {
                        e.printStackTrace();
                        mTitle.setText(diary.getData());
                    }
                } else {
                    mTitle.setText("Data não informada");
                    mTitle.setTextColor(Color.LTGRAY);
                }
                setServiceAdapter(this, diary.getServices());
                setWorkerAdapter(this, diary.getWorkers());
                setPhotoAdapter(this, diary.getPhotos());
                //getdata(holder, position);

                cbHide.setOnCheckedChangeListener((compoundButton, isChecked) -> {
                    if (!isChecked) {
                        llMore.setVisibility(View.GONE);
                    } else {
                        llMore.setVisibility(View.VISIBLE);
                    }
                });

                llShort.setOnClickListener(v -> {
                    cbHide.setChecked(!cbHide.isChecked());
                });

                cbWorkers.setOnCheckedChangeListener((buttonView, isChecked) -> {
                    if (!isChecked) {
                        rvWorkers.setVisibility(View.GONE);
                    } else {
                        rvWorkers.setVisibility(View.VISIBLE);
                    }
                });
                mStartServices.setOnCheckedChangeListener((buttonView, isChecked) -> {
                    if (!isChecked) {
                        rvServices.setVisibility(View.GONE);
                    } else {
                        rvServices.setVisibility(View.VISIBLE);
                    }
                });
                cbPhotos.setOnCheckedChangeListener((buttonView, isChecked) -> {
                    if (!isChecked) {
                        rvPhotos.setVisibility(View.GONE);
                    } else {
                        rvPhotos.setVisibility(View.VISIBLE);
                    }
                });


                if (diary.isOperavel()) {
                    llShort.setBackgroundColor(mContext.getResources().getColor(R.color.colorPrimaryDark));
                    mTitle.setTextColor(Color.WHITE);
                    mFiscal.setTextColor(Color.WHITE);
                    cbHide.setVisibility(View.VISIBLE);
                    llShort.setEnabled(true);
                } else {
                    int drkgray = Color.DKGRAY;
                    llShort.setBackgroundColor(Color.LTGRAY);
                    mTitle.setTextColor(drkgray);
                    mFiscal.setTextColor(drkgray);
                    mFiscal.setText("Dia não operável!");
                    mFiscal.setGravity(Gravity.CENTER);
                    mFiscal.setTypeface(mFiscal.getTypeface(), Typeface.BOLD);
                    cbHide.setVisibility(View.GONE);
                    llShort.setEnabled(false);
                    llMore.setVisibility(View.GONE);
                }


            }
            Animation in = AnimationUtils.loadAnimation(mContext, R.anim.fade_in);
            mCard.startAnimation(in);
        }

        private void setServiceAdapter(MyViewHolder holder, ArrayList<String> services) {
            if (services != null) {
                if (services.isEmpty()) {
                    services.add("Nenhum serviço iniciado");
                }
                ListAdapter adapter = new ListAdapter(services);
                holder.rvServices.setAdapter(adapter);
                GridLayoutManager llm = new GridLayoutManager(mContext, 1, RecyclerView.VERTICAL, false);
                holder.rvServices.setLayoutManager(llm);
            }
        }

        private void setWorkerAdapter(MyViewHolder holder, ArrayList<String> workers) {
            if (workers != null) {
                if (workers.isEmpty()) {
                    workers.add("Nenhum trabalhador informado");
                }
                ListAdapter adapter = new ListAdapter(workers);
                holder.rvWorkers.setAdapter(adapter);
                GridLayoutManager llm = new GridLayoutManager(mContext, 1, RecyclerView.VERTICAL, false);
                holder.rvWorkers.setLayoutManager(llm);
            }
        }

        private void setPhotoAdapter(MyViewHolder holder, ArrayList<String> photos) {
            if (photos != null) {
                if (!photos.isEmpty()) {
                    ImageAdapter adapter = new ImageAdapter(photos);
                    holder.rvPhotos.setAdapter(adapter);
                    GridLayoutManager llm = null;
                    if (photos.size() > 2) {
                        llm = new GridLayoutManager(mContext, 2, RecyclerView.HORIZONTAL, false);
                    } else {
                        llm = new GridLayoutManager(mContext, 1, RecyclerView.HORIZONTAL, false);

                    }
                    holder.rvPhotos.setLayoutManager(llm);
                }
            }
        }

    }
}
