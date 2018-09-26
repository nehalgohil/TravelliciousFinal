package com.dolphinwebsolution.travellcious.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dolphinwebsolution.travellcious.Activity.Package_name;
import com.dolphinwebsolution.travellcious.Model.Destionation_model;
import com.dolphinwebsolution.travellcious.Model.FAQ_Model;
import com.dolphinwebsolution.travellcious.R;

import java.util.List;
import java.util.Locale;

/**
 * Created by ap6 on 24/9/18.
 */


public class FAQ_adapter extends RecyclerView.Adapter<com.dolphinwebsolution.travellcious.Adapter.FAQ_adapter.MyViewHolder> {
    String id;
    private List<FAQ_Model> faq_model;
    private Context context;
    LayoutInflater inflater;
    Boolean flag = true;


    public FAQ_adapter(Context context, List<FAQ_Model> faq_model) {
        this.context = context;
        this.faq_model = faq_model;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public com.dolphinwebsolution.travellcious.Adapter.FAQ_adapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.faquestion_row, parent, false);
        return new com.dolphinwebsolution.travellcious.Adapter.FAQ_adapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final com.dolphinwebsolution.travellcious.Adapter.FAQ_adapter.MyViewHolder holder, final int position) {

        final FAQ_Model model = faq_model.get(position);
        AssetManager am = context.getApplicationContext().getAssets();
        Typeface typeface = Typeface.createFromAsset(am,
                String.format(Locale.getDefault(), "Lato-Bold.ttf"));
        Typeface tf = Typeface.createFromAsset(am,
                String.format(Locale.getDefault(), "Lato-Black.ttf"));
        Typeface medium = Typeface.createFromAsset(am,
                String.format(Locale.getDefault(), "Lato-Medium.ttf"));

        holder.txt_ans.setTypeface(medium);
        holder.txt_faq.setTypeface(tf);

        holder.txt_ans.setText(model.getFAQs_containts());
        holder.txt_faq.setText(model.getFAQs_title());

        holder.ll_down_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity activity = (AppCompatActivity) view.getContext();

                if (flag == true && holder.ll_down_arrow.isClickable() == true) {
                    holder.lv_faq_ans.setVisibility(View.VISIBLE);
                    holder.img_down_arrow.setImageResource(R.drawable.ic_up);
                    activity.overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
                    flag = false;
                } else if (flag == false && holder.ll_down_arrow.isClickable()) {
                    holder.img_down_arrow.setImageResource(R.drawable.ic_down);
                    holder.lv_faq_ans.setVisibility(View.GONE);
                    activity.overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
                    flag = true;
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return faq_model.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView txt_ans, txt_faq, txtdesc;
       LinearLayout lv_faq_ans,lv_faq,ll_down_arrow;
       AppCompatImageView img_down_arrow;

        public MyViewHolder(View itemView) {
            super(itemView);
            txt_ans = (TextView) itemView.findViewById(R.id.txt_ans);
            txt_faq = (TextView) itemView.findViewById(R.id.txt_faq);
            lv_faq_ans = (LinearLayout) itemView.findViewById(R.id.lv_faq_ans);
            lv_faq = (LinearLayout) itemView.findViewById(R.id.lv_faq);
            ll_down_arrow = (LinearLayout) itemView.findViewById(R.id.ll_down_arrow);
            img_down_arrow = (AppCompatImageView) itemView.findViewById(R.id.img_down_faqq);

        }
    }
}







