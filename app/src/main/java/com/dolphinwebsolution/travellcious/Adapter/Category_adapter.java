package com.dolphinwebsolution.travellcious.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.graphics.drawable.PictureDrawable;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/*
import com.ahmadrosid.svgloader.SvgLoader;
*/
import com.ahmadrosid.svgloader.SvgLoader;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.StreamEncoder;
import com.dolphinwebsolution.travellcious.Activity.Package_Alldetail;
import com.dolphinwebsolution.travellcious.Activity.Sub_category;
import com.dolphinwebsolution.travellcious.Model.Category_model;
import com.dolphinwebsolution.travellcious.R;

import java.io.InputStream;
import java.util.List;
import java.util.Locale;


public class Category_adapter extends RecyclerView.Adapter<Category_adapter.MyViewHolder> {
    String cat_id,cat_name;
    private List<Category_model> category_model;
    private Context context;
    LayoutInflater inflater;

    public Category_adapter(Context context, List<Category_model> category_model) {
        this.context = context;
        this.category_model = category_model;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.category_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {


        final Category_model category = category_model.get(position);

        AssetManager am = context.getApplicationContext().getAssets();
          Typeface medium = Typeface.createFromAsset(am,
                String.format(Locale.getDefault(),"Lato-Medium.ttf"));


        holder.txtcatnm.setTypeface(medium);
        holder.txtcatnm.setText(category.getTxtcatnm());


        SvgLoader.pluck()
                .with((Activity) context)
                .load(category.getImg_cat(),holder.img_cat);


        holder.ll_cat_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        AppCompatActivity activity = (AppCompatActivity) view.getContext();

                        cat_id=category_model.get(position).getCategory_id();
                        Log.e("id",""+cat_id);
                        cat_name=category_model.get(position).getTxtcatnm();
                        Log.e("catname",""+cat_name);
                        Intent i=new Intent(context, Sub_category.class);
                        i.putExtra("cat_id",cat_id);
                        i.putExtra("catname",cat_name);
                        activity.startActivity(i);
                        activity.overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);

                    }
                }, 50);



            }
        });
    }



    @Override
    public int getItemCount() {
        return category_model.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView txtcatnm;
        public ImageView img_cat;
        LinearLayout ll_cat_click;
        public MyViewHolder(View itemView) {
            super(itemView);

            txtcatnm = (TextView) itemView.findViewById(R.id.txtcatnm);
            ll_cat_click = (LinearLayout) itemView.findViewById(R.id.ll_cat_click);
            img_cat = (ImageView) itemView.findViewById(R.id.img_cat);
        }
    }
}
