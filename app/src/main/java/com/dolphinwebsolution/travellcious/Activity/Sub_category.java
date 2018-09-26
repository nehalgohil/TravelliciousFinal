package com.dolphinwebsolution.travellcious.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorRes;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dolphinwebsolution.travellcious.Adapter.Sub_cat_adapter;
import com.dolphinwebsolution.travellcious.Model.Sub_cat_model;
import com.dolphinwebsolution.travellcious.Model.ViewAll_Location_model;
import com.dolphinwebsolution.travellcious.R;
import com.dolphinwebsolution.travellcious.utils.CheckNetwork;
import com.dolphinwebsolution.travellcious.utils.Main_Url;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Sub_category extends AppCompatActivity {
    TextView txtperson;
   public static String cat_id,catname;
    private String URL;
    private List<Sub_cat_model> model = new ArrayList<Sub_cat_model>();
    private Sub_cat_adapter sub_cat_adapter;
    RecyclerView recycle_subcat;
    ProgressBar progressBar;
    CardView card_sub;
    TextView txt_message_sub;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_category);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);


        cat_id=getIntent().getStringExtra("cat_id");
        catname=getIntent().getStringExtra("catname");
        Log.e("catnm",""+catname);
        Log.e("cat_iddddddddddd",""+cat_id);

        setTitle(catname);
//http://travel.demoproject.info/api/get_locations_by_category.php?category_id=9
        URL = Main_Url.sortUrl + "get_locations_by_category.php?category_id=" + cat_id;

        progressBar = (ProgressBar) findViewById(R.id.pd);
        txt_message_sub = (TextView) findViewById(R.id.txt_message_sub);
        card_sub = (CardView) findViewById(R.id.card_sub);

        recycle_subcat = (RecyclerView) findViewById(R.id.recycle_subcat);
        sub_cat_adapter = new Sub_cat_adapter(Sub_category.this, model);
        LinearLayoutManager layoutManager = new LinearLayoutManager(Sub_category.this, LinearLayoutManager.VERTICAL, true);
        layoutManager.setReverseLayout(false);
        recycle_subcat.setLayoutManager(layoutManager);
        recycle_subcat.setAdapter(sub_cat_adapter);


        if (CheckNetwork.isNetworkAvailable(Sub_category.this)) {
            get_Location_By_Category();
        } else {
            Toast.makeText(Sub_category.this, "Please Check your Internet Connection", Toast.LENGTH_SHORT).show();

        }

    }
    private void get_Location_By_Category() {
        progressBar.setVisibility(View.VISIBLE);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("URL..SubLocation",""+URL);

                        Log.e("response_Location_sub", "" + response);
                        try {
                            model.clear();
                            JSONObject jsonObject = new JSONObject(response);

                            Log.e("response_getLoc_sub", "" + jsonObject);
                            progressBar.setVisibility(View.GONE);
                            recycle_subcat.setVisibility(View.VISIBLE);
                            String status = jsonObject.getString("status");
                            Log.e("status_location_sub", "" + status);
                            String message = jsonObject.getString("message");

                            if (status.equals("Success") == true) {
                                Log.e("message", "" + message);
                                String data = jsonObject.getString("data");
                                Log.e("data", "" + data);

                                JSONObject data_obj = new JSONObject(data);
                                Log.e("data_obj", "" + data_obj);

                                String Locations_by_category = data_obj.getString("Locations_by_category");
                                Log.e("Locations_by_category", "" + Locations_by_category);


                                JSONArray location_arr = new JSONArray(Locations_by_category);

                                Log.e("location_arr", "" + location_arr);
                                if (location_arr != null && location_arr.isNull(0) !=true ) {
                                    for (int i = 0; i < location_arr.length(); i++) {
                                        try {
                                            card_sub.setVisibility(View.GONE);
                                            JSONObject loc_object = location_arr.getJSONObject(i);
                                            Log.e("object", "" + loc_object);
                                            Log.e("location_id", "" + loc_object.getString("location_id"));
                                            Log.e("location_image", "" + loc_object.getString("location_image"));
                                            Sub_cat_model sub_model = new Sub_cat_model(
                                                    loc_object.getString("location_id"),
                                                    loc_object.getString("location_title"),
                                                    loc_object.getString("description"),
                                                    loc_object.getString("location_image"),
                                                    loc_object.getString("budget"));
                                            model.add(sub_model);

                                        } catch (Exception e) {
                                            Log.e("Exception", "" + e);
                                        } finally {
                                            sub_cat_adapter.notifyItemChanged(i);

                                        }
                                    }
                                } else  {

                                    AssetManager am =getApplicationContext().getAssets();
                                    Typeface medium = Typeface.createFromAsset(am,
                                            String.format(Locale.getDefault(),"Lato-Medium.ttf"));
                                    txt_message_sub.setTypeface(medium);
                                    card_sub.setVisibility(View.VISIBLE);
                                    txt_message_sub.setText("Location not avaible");
                                    Toast.makeText(Sub_category.this, "Locations not Available", Toast.LENGTH_SHORT).show();
                                }


                            }else{
                                Toast.makeText(Sub_category.this, "message==" + message, Toast.LENGTH_SHORT).show();
                                Log.e("response_msggg_toast", "" + message);

                            }


                        } catch (JSONException e) {

                        }
                    }


                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                           Toast.makeText(Sub_category.this, "not get Response=="+error, Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.VISIBLE);
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(Sub_category.this);
        requestQueue.add(stringRequest);
        requestQueue.add(stringRequest);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_wishlist, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        } else if (item.getItemId() == R.id.request_callback) {
            Intent i = new Intent(Sub_category.this, Request_callBack.class);
            startActivity(i);
            Toast.makeText(getApplicationContext(), "Request", Toast.LENGTH_LONG).show();
        } else if (item.getItemId() == R.id.send_qury) {
            Intent i = new Intent(Sub_category.this, Send_query.class);
            startActivity(i);

            Toast.makeText(getApplicationContext(), "sendquery", Toast.LENGTH_LONG).show();
        } else if (item.getItemId() == R.id.help) {
            Toast.makeText(getApplicationContext(), "Item 3 Selected", Toast.LENGTH_LONG).show();
        }



        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }


}
