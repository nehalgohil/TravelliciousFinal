package com.dolphinwebsolution.travellcious.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.android.volley.toolbox.ImageLoader;
import com.bumptech.glide.Glide;
import com.dolphinwebsolution.travellcious.Activity.Package_image_gridview;
import com.dolphinwebsolution.travellcious.Activity.Package_name;
import com.dolphinwebsolution.travellcious.Activity.Package_slider;
import com.dolphinwebsolution.travellcious.Model.Package_images_grid_model;
import com.dolphinwebsolution.travellcious.R;

import java.util.ArrayList;

/**
 * Created by ap6 on 24/9/18.
 */

    public class Package_img_grid_adapter extends BaseAdapter
    {

        private Context ctx;
        private ArrayList<Package_images_grid_model> gridmodel;
        LayoutInflater inflater ;
        public Package_img_grid_adapter(Context ctx,ArrayList<Package_images_grid_model> gridmodel)
        {
            this.ctx = ctx;
            this.gridmodel = gridmodel;
            inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        @Override
        public int getCount()
        {
            return gridmodel.size();
        }

        @Override
        public Object getItem(int i)
        {
            return gridmodel.get(i);
        }

        @Override
        public long getItemId(int i)
        {
            return i;
        }

        @Override
        public View getView(final int position, View view, ViewGroup viewGroup)
        {
            View CurrentGridView;
            CurrentGridView = view;
            MyViewHolder holder;
            if(CurrentGridView==null)
            {
                holder = new MyViewHolder();
                CurrentGridView = inflater.inflate(R.layout.grid_row,null);
                holder.imageview = (ImageView) CurrentGridView.findViewById(R.id.imageview);
                holder.lv_img_click = (LinearLayout) CurrentGridView.findViewById(R.id.lv_img_click);
                CurrentGridView.setTag(holder);
            }
            else
            {
                holder = (MyViewHolder)CurrentGridView.getTag();
            }
            final Package_images_grid_model model = gridmodel.get(position);


          holder.lv_img_click.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view)
                {
                    AppCompatActivity activity = (AppCompatActivity) view.getContext();
                    String images=gridmodel.get(position).getImages();
                    String image_id=gridmodel.get(position).getImg_id();
                    Intent i = new Intent(ctx, Package_slider.class);
                    i.putExtra("images", images);
                    Log.e("image_id",image_id);
                    Log.e("images",images);
                    i.putExtra("image_id",image_id);
                    i.putExtra("Package_id", Package_image_gridview.Package_id);

                    activity.startActivity(i);
                    activity.overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);


                }
            });

            Glide.with(ctx).load(model.getImages()).into(holder.imageview);

            return CurrentGridView;
        }
        class MyViewHolder {
            public ImageView imageview;
            LinearLayout lv_img_click;
        }
    }


