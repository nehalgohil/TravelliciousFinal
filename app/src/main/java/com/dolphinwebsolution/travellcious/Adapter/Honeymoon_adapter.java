package com.dolphinwebsolution.travellcious.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dolphinwebsolution.travellcious.Activity.Package_name;
import com.dolphinwebsolution.travellcious.Model.Honeymoon_model;
import com.dolphinwebsolution.travellcious.R;

import java.util.List;
import java.util.Locale;



public class Honeymoon_adapter extends RecyclerView.Adapter<com.dolphinwebsolution.travellcious.Adapter.Honeymoon_adapter.MyViewHolder> {
    String id;
    private List<Honeymoon_model> hmodel;
    private Context context;
    LayoutInflater inflater;


    public Honeymoon_adapter(Context context, List<Honeymoon_model> model) {
        this.context = context;
        this.hmodel = model;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public com.dolphinwebsolution.travellcious.Adapter.Honeymoon_adapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.vacation_row, parent, false);
        return new com.dolphinwebsolution.travellcious.Adapter.Honeymoon_adapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(com.dolphinwebsolution.travellcious.Adapter.Honeymoon_adapter.MyViewHolder holder, final int position) {

        final Honeymoon_model model_honey = hmodel.get(position);
        AssetManager am = context.getApplicationContext().getAssets();
        Typeface typeface = Typeface.createFromAsset(am,
                String.format(Locale.getDefault(), "Lato-Bold.ttf"));
        Typeface tf = Typeface.createFromAsset(am,
                String.format(Locale.getDefault(), "Lato-Black.ttf"));
        Typeface medium = Typeface.createFromAsset(am,
                String.format(Locale.getDefault(), "Lato-Medium.ttf"));

        holder.txt_vac_budget.setTypeface(tf);
        holder.txt_starting_budget.setTypeface(medium);
        holder.btn_vac_vmore.setTypeface(typeface);
        holder.txt_vac_title.setTypeface(typeface);
        holder.txt_vac_budget.setText(model_honey.getBudget());
        holder.txt_vac_title.setText(model_honey.getLocation_title());
        // holder.txtdesc.setText(model_desti.getDescription());
        Glide.with(context).load(model_honey.getLocation_image()).into(holder.img_vacation);

        holder.btn_vac_vmore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity activity = (AppCompatActivity) view.getContext();

                String location_id;
                location_id=hmodel.get(position).getLocation_id();
                Intent i=new Intent(context,Package_name.class);
                i.putExtra("location_id",location_id);
                i.putExtra("location_title", hmodel.get(position).getLocation_title());

                activity.startActivity(i);
                activity.overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);

            }
        });
    }


    @Override
    public int getItemCount() {
        return hmodel.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView txt_vac_budget, txt_vac_title, txtdesc, txt_starting_budget;
        ImageView img_vacation;
        Button btn_vac_vmore;
        public ImageView img_sub;

        public MyViewHolder(View itemView) {
            super(itemView);
            txt_vac_budget = (TextView) itemView.findViewById(R.id.txt_vac_budget);
            txt_starting_budget = (TextView) itemView.findViewById(R.id.txt_starting_budget);
            txt_vac_title = (TextView) itemView.findViewById(R.id.txt_vac_title);
            img_vacation = (ImageView) itemView.findViewById(R.id.img_vacation);
            btn_vac_vmore = (Button) itemView.findViewById(R.id.btn_vac_vmore);
        }
    }
}




