package com.dolphinwebsolution.travellcious.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
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
import com.dolphinwebsolution.travellcious.R;

import java.util.List;
import java.util.Locale;

/**
 * Created by ap6 on 30/8/18.
 */

public class PackDetail_hotel_adapter extends RecyclerView.Adapter<PackDetail_hotel_adapter.MyViewHolder> {
    String id, cat_name;
    private List<Hotel_model> model;
    private Context context;
    LayoutInflater inflater;
    Hotel_model category;
    Boolean flag = true;

    public PackDetail_hotel_adapter(Context context, List<Hotel_model> model) {
        this.context = context;
        this.model = model;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.hote_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        category = model.get(position);
        Log.e("hotelllll", "" + category.getDescription());

        //holder.txt_hotel_desc.setText(category.getHotel_description());
        Log.e("hotelllll", "" + category.getDescription());
        AssetManager am = context.getApplicationContext().getAssets();
        Typeface typeface = Typeface.createFromAsset(am,
                String.format(Locale.getDefault(),"Lato-Bold.ttf"));
        holder.txt_hotel_title.setTypeface(typeface);
        holder.txt_meals.setTypeface(typeface);
        holder.txt_rooom_type.setTypeface(typeface);
        holder.txt_checkin.setTypeface(typeface);
        holder.txt_checkout.setTypeface(typeface);
        holder.txt_location_day.setTypeface(typeface);


        holder.txt_location_day.setText(Package_Alldetail.LOCATION_TITLE+" - "+category.getDays()+" Days "+" / " +category.getNights()+" Nights ");
        holder.txt_hotel_title.setText(category.getHotel_title());
        holder.txt_meals.setText(category.getMeals());
        holder.txt_rooom_type.setText(category.getRoom_type());
        holder.txt_checkin.setText(category.getCheck_in());
        Log.e("checkinnn", "" + category.getCheck_in());
        holder.txt_checkout.setText(category.getCheck_out());

        String circle = "\u2605";
        holder.txt_star.setText(category.getStar()+""+circle);

        Log.e("LOCATION_TITLE..", "" + Package_Alldetail.LOCATION_TITLE);
        //Log.e("listtttt", "" +category.getList());


       /* final int N = 6; // total number of textviews to add
        TextView rowTextView = null;
        final TextView[] myTextViews = new TextView[category.getList().size()]; // create an empty array;

        for (int i = 0; i < category.getList().size(); i++) {
            Log.e("categoryyyyyy",""+i);
             rowTextView = new TextView(context);
            rowTextView.setText("." +i);
            holder.ll_fecilities.addView(rowTextView);
            myTextViews[i] = rowTextView;
        }
*/
        Glide.with(context).load(category.getHotel_image()).into(holder.img_hotel);

        holder.lv_down_arrow.setOnClickListener(new View.OnClickListener() {
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
        });


    }


    @Override
    public int getItemCount() {
        return model.size();
    }

    public  class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView txt_star, txt_hotel_title, txt_meals, txt_rooom_type,txt_checkin,txt_checkout,txt_location_day;
        public ImageView img_hotel,imgarrow;
        LinearLayout lv_down_arrow;
        LinearLayout lv_click;
        LinearLayout ll_fecilities;

        public MyViewHolder(View itemView) {
            super(itemView);

            ll_fecilities = (LinearLayout) itemView.findViewById(R.id.ll_fecilities);
            lv_click = (LinearLayout) itemView.findViewById(R.id.lv_click);
            txt_star = (TextView) itemView.findViewById(R.id.txt_star);
            txt_meals = (TextView) itemView.findViewById(R.id.txt_meals);
            txt_checkout = (TextView) itemView.findViewById(R.id.txt_checkout);
            txt_location_day = (TextView) itemView.findViewById(R.id.txt_location_day);
            txt_checkin = (TextView) itemView.findViewById(R.id.txt_checkin);
            txt_rooom_type = (TextView) itemView.findViewById(R.id.txt_rooom_type);
          //  txt_hotel_desc = (TextView) itemView.findViewById(R.id.txt_hotel_desc);
            txt_hotel_title = (TextView) itemView.findViewById(R.id.txt_hotel_title);
            lv_down_arrow = (LinearLayout) itemView.findViewById(R.id.lv_down_arrow);
            img_hotel = (ImageView) itemView.findViewById(R.id.img_hotel);
            imgarrow = (ImageView) itemView.findViewById(R.id.imgarrow);
        }
    }
}
