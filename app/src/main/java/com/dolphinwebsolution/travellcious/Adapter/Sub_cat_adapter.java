package com.dolphinwebsolution.travellcious.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dolphinwebsolution.travellcious.Activity.Package_name;
import com.dolphinwebsolution.travellcious.Model.Sub_cat_model;
import com.dolphinwebsolution.travellcious.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.List;
import java.util.Locale;


public class Sub_cat_adapter extends RecyclerView.Adapter<Sub_cat_adapter.MyViewHolder> {
    String id;
    private List<Sub_cat_model> model;
    private Context context;
    LayoutInflater inflater;

    public Sub_cat_adapter(Context context, List<Sub_cat_model> model) {
        this.context = context;
        this.model = model;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.perticular_category_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        final Sub_cat_model category = model.get(position);
        AssetManager am = context.getApplicationContext().getAssets();
        Typeface typeface = Typeface.createFromAsset(am,
                String.format(Locale.getDefault(),"Lato-Bold.ttf"));
        Typeface tf = Typeface.createFromAsset(am,
                String.format(Locale.getDefault(),"Lato-Black.ttf"));
        Typeface medium = Typeface.createFromAsset(am,
                String.format(Locale.getDefault(),"Lato-Medium.ttf"));

        holder.txt_location_nm.setTypeface(typeface);
        holder.txt_budget.setTypeface(tf);
        holder.txtbud.setTypeface(medium);
        holder.txt_descc.setTypeface(medium);

        holder.txt_location_nm.setText(category.getLocation_title());
        holder.txt_budget.setText(category.getBudget());
        holder.txt_descc.setText(category.getDescription());
            AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        Picasso.with(context)
                .load(category.getLocation_image())
                .into(new Target() {
                    @Override
                    public void onBitmapLoaded (final Bitmap bitmap, Picasso.LoadedFrom from){
                        BitmapDrawable background = new BitmapDrawable(bitmap);
                        holder.lv_img.setBackgroundDrawable(background);
                    }

                    @Override
                    public void onBitmapFailed(Drawable errorDrawable) {

                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {

                    }
                });
       holder.ll_sub_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity activity = (AppCompatActivity) view.getContext();

                Intent i=new Intent(context,Package_name.class);
                i.putExtra("location_id",model.get(position).getLocation_id());
                Log.e("location_id_sub",""+model.get(position).getLocation_id());
                i.putExtra("location_title",model.get(position).getLocation_title());
                activity.startActivity(i);
               activity.overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
            }
        });


    }
    @Override
    public int getItemCount() {
        return model.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView txt_location_nm,txtperson,txt_budget,txtbud,txt_descc;
        LinearLayout ll_sub_click,lv_img;
        ImageView img;

        public ImageView img_sub;
        public MyViewHolder(View itemView) {
            super(itemView);
            txt_descc = (TextView)itemView. findViewById(R.id.txt_descc);
            txtbud = (TextView)itemView. findViewById(R.id.txtbud);
            txt_location_nm = (TextView)itemView. findViewById(R.id.txt_category_title);
            txt_budget = (TextView)itemView. findViewById(R.id.txt_cat_budget);
            txtperson = (TextView)itemView. findViewById(R.id.txtperson);
             ll_sub_click = (LinearLayout) itemView.findViewById(R.id.ll_sub_click);
            lv_img = (LinearLayout) itemView.findViewById(R.id.lv_img);
        }
    }
}
