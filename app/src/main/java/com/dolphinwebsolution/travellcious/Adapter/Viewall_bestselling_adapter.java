package com.dolphinwebsolution.travellcious.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dolphinwebsolution.travellcious.Activity.Package_name;
import com.dolphinwebsolution.travellcious.Model.Bestselling_Package_model;
import com.dolphinwebsolution.travellcious.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.List;

/**
 * Created by ap6 on 10/9/18.
 */


public class Viewall_bestselling_adapter extends RecyclerView.Adapter<com.dolphinwebsolution.travellcious.Adapter.Viewall_bestselling_adapter.MyViewHolder> {
    String id;
    private List<Bestselling_Package_model> vmodel;
    private Context context;
    LayoutInflater inflater;


    public Viewall_bestselling_adapter(Context context, List<Bestselling_Package_model> model) {
        this.context = context;
        this.vmodel = model;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public com.dolphinwebsolution.travellcious.Adapter.Viewall_bestselling_adapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.viewall_location_row, parent, false);
        return new com.dolphinwebsolution.travellcious.Adapter.Viewall_bestselling_adapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final com.dolphinwebsolution.travellcious.Adapter.Viewall_bestselling_adapter.MyViewHolder holder, final int position) {

        final Bestselling_Package_model model_desti = vmodel.get(position);

        holder.txt_viewall_budget.setText(model_desti.getBudget());
       // holder.txt_location_title.setText(model_desti.getLocation_Name());
        // holder.txtdesc.setText(model_desti.getDescription());
        //  Glide.with(context).load(model_desti.getPackage_images()).into(holder.imgdesti);

      /*  Picasso.with(context)
                .load(model_desti.getPackage_images())
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
*/
        holder.lv_layout_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity activity = (AppCompatActivity) view.getContext();

             /*   String cat_id, location_id;
                cat_id = vmodel.get(position).getCategory_id();
                location_id = vmodel.get(position).getLocation_id();
                Intent i = new Intent(context, Package_name.class);
                i.putExtra("cat_id", cat_id);
                i.putExtra("location_id", location_id);
                i.putExtra("location_img",vmodel.get(position).getPackage_images());

         */      // activity.startActivity(i);
                activity.overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);

            }
        });


    }


    @Override
    public int getItemCount() {
        return vmodel.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView txt_viewall_budget, txt_location_title, txtdesc;
        LinearLayout lv_img_viewall, lv_layout_click;
        public ImageView img_sub;

        public MyViewHolder(View itemView) {
            super(itemView);
            /*lv_img_viewall = (LinearLayout) itemView.findViewById(R.id.lv_img_viewall);
            lv_layout_click = (LinearLayout) itemView.findViewById(R.id.ll_sub_click);
            txt_viewall_budget = (TextView) itemView.findViewById(R.id.txt_viewall_budget);
            txt_location_title = (TextView) itemView.findViewById(R.id.txt_location_title);
*/
        }
    }
}





