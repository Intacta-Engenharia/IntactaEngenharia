package com.intactaengenharia.intacta.view.adapter;

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
import java.util.List;

public class ViewPagerAdapter extends PagerAdapter {

    private final List<String> images;

    public ViewPagerAdapter(List<String> images) {
        this.images = images;
    }

    @Override
    public int getCount() {
        if (images == null) {
            return 0;
        }else{
            return images.size();
        }
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater layoutInflater = (LayoutInflater) container.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.pagerphotos_card, container, false);

        ImageView photo = view.findViewById(R.id.pictures);
        Glide.with(container.getContext()).load(images.get(position)).into(photo);
        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((RelativeLayout) (object));
    }
}
