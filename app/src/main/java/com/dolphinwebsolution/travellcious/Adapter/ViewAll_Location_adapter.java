package com.dolphinwebsolution.travellcious.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dolphinwebsolution.travellcious.Activity.Package_name;
import com.dolphinwebsolution.travellcious.Model.ViewAll_Location_model;
import com.dolphinwebsolution.travellcious.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.List;
import java.util.Locale;



public class ViewAll_Location_adapter extends RecyclerView.Adapter<com.dolphinwebsolution.travellcious.Adapter.ViewAll_Location_adapter.MyViewHolder> {
    String id;
    private List<ViewAll_Location_model> model;
    private Context context;
    LayoutInflater inflater;


    public ViewAll_Location_adapter(Context context, List<ViewAll_Location_model> model) {
        this.context = context;
        this.model = model;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public com.dolphinwebsolution.travellcious.Adapter.ViewAll_Location_adapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.perticular_category_row, parent, false);
        return new com.dolphinwebsolution.travellcious.Adapter.ViewAll_Location_adapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final com.dolphinwebsolution.travellcious.Adapter.ViewAll_Location_adapter.MyViewHolder holder, final int position) {

        final ViewAll_Location_model model_desti = model.get(position);
        AssetManager am = context.getApplicationContext().getAssets();
        Typeface typeface = Typeface.createFromAsset(am,
                String.format(Locale.getDefault(), "Lato-Bold.ttf"));
        Typeface tf = Typeface.createFromAsset(am,
                String.format(Locale.getDefault(), "Lato-Black.ttf"));
        Typeface medium = Typeface.createFromAsset(am,
                String.format(Locale.getDefault(), "Lato-Medium.ttf"));
        holder.txtstarting.setTypeface(medium);
        holder.txt_descc.setTypeface(medium);
        holder.txt_location_title.setTypeface(typeface);
        holder.txt_viewall_budget.setTypeface(tf);
        holder.txt_viewall_budget.setText(model_desti.getBudget());
        holder.txt_location_title.setText(model_desti.getLocation_title());
        holder.txt_descc.setText(model_desti.getLocation_description());

        Picasso.with(context)
                .load(model_desti.getLocation_image())
                .into(new Target() {
                    @Override
                    public void onBitmapLoaded(final Bitmap bitmap, Picasso.LoadedFrom from) {
                        BitmapDrawable background = new BitmapDrawable(bitmap);
                        holder.lv_img_viewall.setBackgroundDrawable(background);
                    }

                    @Override
                    public void onBitmapFailed(Drawable errorDrawable) {

                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {

                    }
                });

        holder.lv_layout_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                final AppCompatActivity activity = (AppCompatActivity) view.getContext();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        String  location_id;
                        location_id = model.get(position).getLocation_id();
                        Intent i = new Intent(context, Package_name.class);
                        i.putExtra("location_id", location_id);
                        i.putExtra("location_title", model.get(position).getLocation_title());
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
        public TextView txt_viewall_budget, txt_location_title, txt_descc,txtstarting;
        LinearLayout lv_img_viewall, lv_layout_click;
        /*  ImageView imgdesti;
          Button btn_desti_vmore;
  */
        public ImageView img_sub;

        public MyViewHolder(View itemView) {
            super(itemView);
            lv_img_viewall = (LinearLayout) itemView.findViewById(R.id.lv_img);
            lv_layout_click = (LinearLayout) itemView.findViewById(R.id.ll_sub_click);
            txt_viewall_budget = (TextView) itemView.findViewById(R.id.txt_cat_budget);
            txt_location_title = (TextView) itemView.findViewById(R.id.txt_category_title);
            txt_descc = (TextView) itemView.findViewById(R.id.txt_descc);
            txtstarting = (TextView) itemView.findViewById(R.id.txtbud);
           /* txtpacknm = (TextView) itemView.findViewById(R.id.txtpacknm);
            imgdesti = (ImageView) itemView.findViewById(R.id.imgdestinaton);
            btn_desti_vmore = (Button) itemView.findViewById(R.id.btn_desti_vmore);
      */
        }
    }
}


