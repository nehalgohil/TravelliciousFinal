package com.dolphinwebsolution.travellcious.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dolphinwebsolution.travellcious.Activity.Package_name;
import com.dolphinwebsolution.travellcious.Model.Destionation_model;
import com.dolphinwebsolution.travellcious.R;

import java.util.List;
import java.util.Locale;

/**
 * Created by ap6 on 17/9/18.
 */


public class Destination_adapter extends RecyclerView.Adapter<com.dolphinwebsolution.travellcious.Adapter.Destination_adapter.MyViewHolder> {
    String id;
    private List<Destionation_model> dmodel;
    private Context context;
    LayoutInflater inflater;


    public Destination_adapter(Context context, List<Destionation_model> model) {
        this.context = context;
        this.dmodel = model;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public com.dolphinwebsolution.travellcious.Adapter.Destination_adapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.destination_row, parent, false);
        return new com.dolphinwebsolution.travellcious.Adapter.Destination_adapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(com.dolphinwebsolution.travellcious.Adapter.Destination_adapter.MyViewHolder holder, final int position) {

        final Destionation_model model = dmodel.get(position);
        AssetManager am = context.getApplicationContext().getAssets();
        Typeface typeface = Typeface.createFromAsset(am,
                String.format(Locale.getDefault(), "Lato-Bold.ttf"));
        Typeface tf = Typeface.createFromAsset(am,
                String.format(Locale.getDefault(), "Lato-Black.ttf"));
        Typeface medium = Typeface.createFromAsset(am,
                String.format(Locale.getDefault(), "Lato-Medium.ttf"));

        holder.txt_budget.setTypeface(tf);
        holder.txtdesc.setTypeface(medium);
        holder.btn_desti_vmore.setTypeface(typeface);
        holder.txt_location_nm.setTypeface(typeface);
        holder.txt_budget.setText(model.getBudget());
        holder.txt_location_nm.setText(model.getLocation_title());
        holder.txtdesc.setText(model.getLocation_description());
        // holder.txtdesc.setText(model_desti.getDescription());
        Glide.with(context).load(model.getLocation_image()).into(holder.img_destinaton);

        holder.btn_desti_vmore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity activity = (AppCompatActivity) view.getContext();

                String  location_id;
                location_id = dmodel.get(position).getLocation_id();
                Intent i = new Intent(context, Package_name.class);
                i.putExtra("location_id", location_id);
                Log.e("Location_id_desti",location_id);
                i.putExtra("location_title", dmodel.get(position).getLocation_title());

                activity.startActivity(i);
                activity.overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);

            }
        });
    }


    @Override
    public int getItemCount() {
        return dmodel.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView txt_location_nm, txt_budget, txtdesc;
        ImageView img_destinaton;
        Button btn_desti_vmore;


        public MyViewHolder(View itemView) {
            super(itemView);
            txt_budget = (TextView) itemView.findViewById(R.id.txt_budget);
            txtdesc = (TextView) itemView.findViewById(R.id.txt_desc);
            txt_budget = (TextView) itemView.findViewById(R.id.txt_budget);
            txt_location_nm = (TextView) itemView.findViewById(R.id.txt_location_nm);
            img_destinaton = (ImageView) itemView.findViewById(R.id.img_destinaton);
            btn_desti_vmore = (Button) itemView.findViewById(R.id.btn_desti_vmore);
        }
    }
}





