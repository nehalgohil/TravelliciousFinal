package com.dolphinwebsolution.travellcious.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.LayerDrawable;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dolphinwebsolution.travellcious.Activity.Package_Alldetail;
import com.dolphinwebsolution.travellcious.Activity.Package_name;
import com.dolphinwebsolution.travellcious.Activity.Review_details;
import com.dolphinwebsolution.travellcious.Activity.Sub_category;
import com.dolphinwebsolution.travellcious.Model.Bestselling_Package_model;
import com.dolphinwebsolution.travellcious.Model.Review_model;
import com.dolphinwebsolution.travellcious.R;

import java.util.List;
import java.util.Locale;

/**
 * Created by ap6 on 5/9/18.
 */

public class Review_adapter extends RecyclerView.Adapter<com.dolphinwebsolution.travellcious.Adapter.Review_adapter.MyViewHolder> {
    String id, cat_name;
    private List<Review_model> model;
    private Context context;
    LayoutInflater inflater;

    public Review_adapter(Context context, List<Review_model> model) {
        this.context = context;
        this.model = model;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public com.dolphinwebsolution.travellcious.Adapter.Review_adapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.review_row, parent, false);
        return new com.dolphinwebsolution.travellcious.Adapter.Review_adapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(com.dolphinwebsolution.travellcious.Adapter.Review_adapter.MyViewHolder holder, final int position) {

        final Review_model package_model = model.get(position);
        AssetManager am = context.getApplicationContext().getAssets();
        Typeface typeface = Typeface.createFromAsset(am,
                String.format(Locale.getDefault(),"Lato-Bold.ttf"));
        holder.txt_review_desc.setTypeface(typeface);

        holder.txt_review_desc.setText(package_model.getReview());
        holder.txt_review_nm.setText(package_model.getUsername());
        String rating=package_model.getStart();
        Log.e("rating...",""+rating);
        holder.ratingBar.setRating(Float.parseFloat(rating));

        LayerDrawable star=(LayerDrawable)holder.ratingBar.getProgressDrawable();
        star.getDrawable(2).setColorFilter(Color.parseColor("#ff8a65"), PorterDuff.Mode.SRC_ATOP);

        Glide.with(context).load(package_model.getUser_image()).into(holder.img_review);

        holder.cv_onclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        AppCompatActivity activity = (AppCompatActivity) view.getContext();
                        Intent i=new Intent(context, Review_details.class);
                        i.putExtra("review",model.get(position).getReview());
                        i.putExtra("start",model.get(position).getStart());
                        i.putExtra("user_img",model.get(position).getUser_image());
                        activity.startActivity(i);
                        activity.overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);

                    }
                }, 50);

            }
        });


    }


    @Override
    public int getItemCount() {
        return model.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView txt_review_nm, txt_review_desc, txt_city,txt_date;
        public ImageView img_review;
        LinearLayout lv_package;
        RatingBar ratingBar;
        CardView cv_onclick;

        public MyViewHolder(View itemView) {
            super(itemView);

            ratingBar = (RatingBar) itemView.findViewById(R.id.ratingBar);
            txt_review_nm = (TextView) itemView.findViewById(R.id.txt_review_nm);
            txt_review_desc = (TextView) itemView.findViewById(R.id.txt_review_desc);
            txt_city = (TextView) itemView.findViewById(R.id.txt_city);
            txt_date = (TextView) itemView.findViewById(R.id.txt_date);
            img_review = (ImageView) itemView.findViewById(R.id.img_review);
            cv_onclick=(CardView)itemView.findViewById(R.id.cv_onclick);
 /* ll_cat_click = (LinearLayout) itemView.findViewById(R.id.ll_cat_click);
 img_cat = (ImageView) itemView.findViewById(R.id.img_cat);
 */
        }
    }
}




