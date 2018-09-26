package com.dolphinwebsolution.travellcious.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dolphinwebsolution.travellcious.R;

public class Review_details extends AppCompatActivity {
    public TextView txt_review_nm, txt_review_desc, txt_city,txt_date;
    public ImageView img_review;
    LinearLayout lv_package,lv_review_detail;
    RatingBar ratingBar;
    String review,start,user_img;
    ProgressBar progress_review;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_details);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Review");

        lv_review_detail = (LinearLayout) findViewById(R.id.lv_review_detail);
        progress_review = (ProgressBar) findViewById(R.id.progress_review);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        txt_review_nm = (TextView) findViewById(R.id.txt_review_nm);
        txt_review_desc = (TextView) findViewById(R.id.txt_review_desc);
        txt_city = (TextView) findViewById(R.id.txt_city);
        txt_date = (TextView) findViewById(R.id.txt_date);
        img_review = (ImageView)findViewById(R.id.img_review);

        review = getIntent().getStringExtra("review");
        start = getIntent().getStringExtra("start");
        user_img = getIntent().getStringExtra("user_img");

        Log.e("rescriptionnnnn",""+review);
        progress_review.setVisibility(View.VISIBLE);
        if(review.equals("") !=true || review != null || review.equals("null")!= true
                || start.equals("")!=true || start!=null || start.equals("null")!=true
                || user_img.equals("")!=true || user_img!=null || user_img.equals("null")!=true) {
            progress_review.setVisibility(View.GONE);
            lv_review_detail.setVisibility(View.VISIBLE);
            txt_review_desc.setText(review);
            ratingBar.setRating(Float.parseFloat(start));
            LayerDrawable star = (LayerDrawable) ratingBar.getProgressDrawable();
            star.getDrawable(2).setColorFilter(Color.parseColor("#ff8a65"), PorterDuff.Mode.SRC_ATOP);
            Glide.with(Review_details.this).load(user_img).into(img_review);
        }
        else
        {
            Toast.makeText(Review_details.this, "null review", Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        } else if (item.getItemId() == R.id.request_callback) {
            Intent i = new Intent(Review_details.this, Request_callBack.class);
            startActivity(i);
            Toast.makeText(getApplicationContext(), "Request", Toast.LENGTH_LONG).show();
        } else if (item.getItemId() == R.id.send_qury) {
            Intent i = new Intent(Review_details.this, Send_query.class);
            startActivity(i);

            Toast.makeText(getApplicationContext(), "sendquery", Toast.LENGTH_LONG).show();
        } else if (item.getItemId() == R.id.help) {
            Toast.makeText(getApplicationContext(), "Item 3 Selected", Toast.LENGTH_LONG).show();
        }

        return super.onOptionsItemSelected(item);
    }

}
