package com.dolphinwebsolution.travellcious.Activity;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.dolphinwebsolution.travellcious.Model.sliderimage_model;
import com.dolphinwebsolution.travellcious.R;

import java.util.List;


class Sliding_Package_img_Adapter extends PagerAdapter {
    Context context;
    private List<sliderimage_model> sliderimage_models;
    private LayoutInflater inflater;
    String screen;

    public Sliding_Package_img_Adapter(Context context, List<sliderimage_model> sliderimage_models) {

        this.context = context;
        this.sliderimage_models = sliderimage_models;
        inflater = LayoutInflater.from(context);

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }

    @Override
    public int getCount() {
        return sliderimage_models.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        final ImageView image;

        View view = null;
        inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.package_sliding_row, container, false);
        final sliderimage_model sm = sliderimage_models.get(position);

        image = (ImageView) view.findViewById(R.id.imageview);
        Glide.with(context).load(sm.getLocation_image()).into(image);
        // Picasso.with(context).load(imageModel.getUrl()).fit().into(image);
        ((ViewPager) container).addView(view);

        return view;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view == (LinearLayout) object);
    }
}