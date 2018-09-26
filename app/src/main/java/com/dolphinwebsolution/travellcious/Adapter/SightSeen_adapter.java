package com.dolphinwebsolution.travellcious.Adapter;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dolphinwebsolution.travellcious.Activity.Package_Alldetail;
import com.dolphinwebsolution.travellcious.Model.Hotel_model;
import com.dolphinwebsolution.travellcious.Model.Sightseen_model;
import com.dolphinwebsolution.travellcious.R;

import java.util.List;
import java.util.Locale;

/**
 * Created by ap6 on 25/9/18.
 */


public class SightSeen_adapter extends RecyclerView.Adapter<com.dolphinwebsolution.travellcious.Adapter.SightSeen_adapter.MyViewHolder> {
    String id, cat_name;
    private List<Sightseen_model> model;
    private Context context;
    LayoutInflater inflater;
    Sightseen_model sight_model;
    Boolean flag = true;

    public SightSeen_adapter(Context context, List<Sightseen_model> model) {
        this.context = context;
        this.model = model;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public com.dolphinwebsolution.travellcious.Adapter.SightSeen_adapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.sightseen_row, parent, false);
        return new com.dolphinwebsolution.travellcious.Adapter.SightSeen_adapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final com.dolphinwebsolution.travellcious.Adapter.SightSeen_adapter.MyViewHolder holder, final int position) {

        sight_model = model.get(position);
        Log.e("hotelllll", "" + sight_model.getDescription());

        //holder.txt_hotel_desc.setText(category.getHotel_description());
        Log.e("hotelllll", "" + sight_model.getDescription());
        AssetManager am = context.getApplicationContext().getAssets();
        Typeface typeface = Typeface.createFromAsset(am,
                String.format(Locale.getDefault(), "Lato-Bold.ttf"));
        holder.txt_sightseen.setTypeface(typeface);



        holder.txt_sightseen.setText(sight_model.getDays()+" "+sight_model.getLocations()+"  :  "+sight_model.getSightseen_title());





       /* holder.lv_down_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity activity = (AppCompatActivity) view.getContext();

                if (flag == true && holder.lv_down_arrow.isClickable() == true) {
                    holder.lv_click.setVisibility(View.VISIBLE);
                    holder.imgarrow.setImageResource(R.drawable.ic_up);
                    activity.overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
                    flag = false;
                } else if (flag == false && holder.lv_down_arrow.isClickable()) {
                    holder.imgarrow.setImageResource(R.drawable.ic_down);
                    holder.lv_click.setVisibility(View.GONE);
                    activity.overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);

                    flag = true;
                }
            }
        });*/


    }


    @Override
    public int getItemCount() {
        return model.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView txt_sightseen;


        public MyViewHolder(View itemView) {
            super(itemView);

            txt_sightseen = (TextView) itemView.findViewById(R.id.txt_sightseen);
        }
    }
}


