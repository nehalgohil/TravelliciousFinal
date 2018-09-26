package com.dolphinwebsolution.travellcious.Activity;

/**
 * Created by ap6 on 18/9/18.
 */

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.dolphinwebsolution.travellcious.Model.SliderPackage_name_model;
import com.dolphinwebsolution.travellcious.Model.sliderimage_model;
import com.dolphinwebsolution.travellcious.R;

import java.util.List;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.dolphinwebsolution.travellcious.Model.sliderimage_model;
import com.dolphinwebsolution.travellcious.R;


import java.util.List;

import static java.lang.System.in;


class SliderPackage_name_adapter extends PagerAdapter {
    Context context;
    private List<SliderPackage_name_model> sliderimage_models;
    private LayoutInflater inflater;
    String screen;

    public SliderPackage_name_adapter(Context context, List<SliderPackage_name_model> sliderimage_models) {

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
        view = inflater.inflate(R.layout.sliding_row, container, false);
        final SliderPackage_name_model sm = sliderimage_models.get(position);

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