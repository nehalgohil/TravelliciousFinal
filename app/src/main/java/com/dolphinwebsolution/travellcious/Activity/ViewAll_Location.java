package com.dolphinwebsolution.travellcious.Activity;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.dolphinwebsolution.travellcious.Adapter.Vacation_adapter;
import com.dolphinwebsolution.travellcious.Adapter.ViewAll_Location_adapter;

import com.dolphinwebsolution.travellcious.Model.ViewAll_Location_model;
import com.dolphinwebsolution.travellcious.Model.sliderimage_model;
import com.dolphinwebsolution.travellcious.R;
import com.dolphinwebsolution.travellcious.utils.CheckNetwork;
import com.dolphinwebsolution.travellcious.utils.Main_Url;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ViewAll_Location extends AppCompatActivity {
    RecyclerView recyclerView_location;
    private List<ViewAll_Location_model> loc_model = new ArrayList<ViewAll_Location_model>();
    String URLGETLOCATION;
    ViewAll_Location_adapter adapter;
    CardView card_vall;
    TextView txt_message;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all__location);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Locations");
        //http://travel.demoproject.info/api/get_all_locations.php
        URLGETLOCATION= Main_Url.sortUrl+"get_all_locations.php";
        progressBar=(ProgressBar) findViewById(R.id.progressBar);
        card_vall=(CardView) findViewById(R.id.card_vall);
        txt_message=(TextView) findViewById(R.id.txt_message);

        recyclerView_location=(RecyclerView)findViewById(R.id.recyclerView_location);
        adapter = new ViewAll_Location_adapter(ViewAll_Location.this, loc_model);
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(ViewAll_Location.this, LinearLayoutManager.VERTICAL, true);
        layoutManager1.setReverseLayout(false);
        recyclerView_location.setLayoutManager(layoutManager1);
        recyclerView_location.setAdapter(adapter);


        if (CheckNetwork.isNetworkAvailable(ViewAll_Location.this)) {
            Call_GET_LOCATION_API();
        } else {
            Toast.makeText(ViewAll_Location.this, "Please Check your Internet Connection", Toast.LENGTH_SHORT).show();

        }


    }

    private void Call_GET_LOCATION_API() {

        progressBar.setVisibility(View.VISIBLE);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLGETLOCATION,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.e("response_Location", "" + response);
                        try {
                            loc_model.clear();
                            JSONObject jsonObject = new JSONObject(response);

                            Log.e("response_getLocation", "" + jsonObject);
                           progressBar.setVisibility(View.GONE);
                           recyclerView_location.setVisibility(View.VISIBLE);
                            String status = jsonObject.getString("status");
                            Log.e("status_location", "" + status);
                            String message = jsonObject.getString("message");

                            if (status.equals("Success") == true) {
                                Log.e("message", "" + message);
                                String data = jsonObject.getString("data");
                                Log.e("data", "" + data);

                                JSONObject data_obj = new JSONObject(data);
                                Log.e("data_obj", "" + data_obj);

                                String Get_all_locations = data_obj.getString("Get_all_locations");
                                Log.e("Get_all_locations", "" + Get_all_locations);


                                JSONArray location_arr = new JSONArray(Get_all_locations);

                                Log.e("location_arr", "" + location_arr);
                                if (location_arr != null &&  location_arr.isNull(1) !=true) {
                                    for (int i = 0; i < location_arr.length(); i++) {
                                        try {
                                            card_vall.setVisibility(View.GONE);
                                            JSONObject loc_object = location_arr.getJSONObject(i);
                                            Log.e("object", "" + loc_object);
                                            Log.e("location_id", "" + loc_object.getString("location_id"));
                                            Log.e("location_image", "" + loc_object.getString("location_image"));
                                            ViewAll_Location_model model = new ViewAll_Location_model(
                                                    loc_object.getString("location_id"),
                                                    loc_object.getString("location_image"),
                                                    loc_object.getString("location_title"),
                                                    loc_object.getString("location_description"),
                                                    loc_object.getString("budget"));
                                            loc_model.add(model);

                                        } catch (Exception e) {
                                            Log.e("Exception", "" + e);
                                        } finally {
                                            adapter.notifyItemChanged(i);

                                        }
                                    }
                                } else {
                                    AssetManager am =getApplicationContext().getAssets();
                                    Typeface medium = Typeface.createFromAsset(am,
                                            String.format(Locale.getDefault(),"Lato-Medium.ttf"));
                                    txt_message.setTypeface(medium);

                                    card_vall.setVisibility(View.VISIBLE);
                                    txt_message.setText("Location not avaible");

                                    Toast.makeText(ViewAll_Location.this, "array_null", Toast.LENGTH_SHORT).show();
                                }


                            }else{
                                Toast.makeText(ViewAll_Location.this, "message==" + message, Toast.LENGTH_SHORT).show();
                                Log.e("response_msggg_toast", "" + message);

                            }


                        } catch (JSONException e) {

                        }
                    }


                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ViewAll_Location.this, "not get Response"+error, Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.VISIBLE);
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(ViewAll_Location.this);
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
            Intent i = new Intent(ViewAll_Location.this, Request_callBack.class);
            startActivity(i);
            Toast.makeText(getApplicationContext(), "Request", Toast.LENGTH_LONG).show();
        } else if (item.getItemId() == R.id.send_qury) {
            Intent i = new Intent(ViewAll_Location.this, Send_query.class);
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
