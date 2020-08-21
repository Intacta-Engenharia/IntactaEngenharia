package com.intactaengenharia.intacta.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.intactaengenharia.intacta.R;

import java.util.ArrayList;

public class ViewPagerAdapter extends PagerAdapter {

    private ArrayList<String> uriArrayList;
    private Context context;

    public ViewPagerAdapter(ArrayList<String> uriArrayList, Context context) {
        this.uriArrayList = uriArrayList;
        this.context = context;
    }

    @Override
    public int getCount() {
        if (uriArrayList == null) {
            return 0;
        }else{
            return uriArrayList.size();
        }
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.pagerphotos_card, container, false);
        ImageView photo = view.findViewById(R.id.pictures);
        Glide.with(context).load(uriArrayList.get(position)).into(photo);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((RelativeLayout) (object));
    }
}
