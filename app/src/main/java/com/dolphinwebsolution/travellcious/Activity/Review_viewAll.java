package com.dolphinwebsolution.travellcious.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dolphinwebsolution.travellcious.Adapter.Review_adapter;
import com.dolphinwebsolution.travellcious.Adapter.Tranding_adapter;
import com.dolphinwebsolution.travellcious.Model.Bestselling_Package_model;
import com.dolphinwebsolution.travellcious.Model.Review_model;
import com.dolphinwebsolution.travellcious.Model.Tranding_model;
import com.dolphinwebsolution.travellcious.Model.sliderimage_model;
import com.dolphinwebsolution.travellcious.R;
import com.dolphinwebsolution.travellcious.utils.CheckNetwork;
import com.dolphinwebsolution.travellcious.utils.Main_Url;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Review_viewAll extends AppCompatActivity {
    String location_id;
    String review_api;
    private List<Review_model> rmodel = new ArrayList<Review_model>();
    RecyclerView recycler_review;
    private Review_adapter review_adapter;
    LinearLayout lv_rvall;
    ProgressBar progress_reviewvall;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_view_all);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Reviews");
        location_id = getIntent().getStringExtra("location_id");

        progress_reviewvall = (ProgressBar) findViewById(R.id.progress_reviewvall);
        lv_rvall = (LinearLayout) findViewById(R.id.lv_rvall);
        recycler_review = (RecyclerView) findViewById(R.id.recycler_review);
       /* review_adapter = new Review_adapter(Review_viewAll.this, rmodel);
        recycler_review.setAdapter(review_adapter);
*/

        review_adapter = new Review_adapter(Review_viewAll.this, rmodel);
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(Review_viewAll.this, LinearLayoutManager.VERTICAL, true);
        layoutManager1.setReverseLayout(false);
        recycler_review.setLayoutManager(layoutManager1);
        recycler_review.setAdapter(review_adapter);

        review_api= Main_Url.sortUrl + "get_location_details.php?location_id=" + location_id;

        if (CheckNetwork.isNetworkAvailable(Review_viewAll.this)) {
            Review_api();
        } else {
            Toast.makeText(Review_viewAll.this, "No internet connection", Toast.LENGTH_SHORT).show();
        }

    }
    private void Review_api() {
        progress_reviewvall.setVisibility(View.VISIBLE);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, review_api, new Response.Listener<String>() {
            @SuppressLint("LongLogTag")
            @Override
            public void onResponse(String response) {
                Log.e("Response_location_detils", "" + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String status = jsonObject.getString("status");
                    String message = jsonObject.getString("message");
                    progress_reviewvall.setVisibility(View.GONE);
                    lv_rvall.setVisibility(View.VISIBLE);

                    if (status.equals("Success"))
                   {
                        String data = jsonObject.getString("data");
                        JSONObject obj=new JSONObject(data);
                        rmodel.clear();
                        //Review
                        String review=obj.getString("review");
                        Log.e("reviewwww",""+obj.getString("review"));
                        JSONArray aray=new JSONArray(review);
                        if (aray != null){
                            for (int i=0; i<aray.length();i++){
                                try{
                                    JSONObject obje=aray.getJSONObject(i);
                                    //cardview_review.setVisibility(View.GONE);
                                    Review_model review_model = new Review_model(obje.getString("Stars"),
                                            obje.getString("Review"), obje.getString("User_Id"),
                                            obje.getString("Package_id"), obje.getString("User_image"),obje.getString("Username"));

                                    rmodel.add(review_model);

                                }catch (Exception e){
                                }
                                finally {
                                    review_adapter.notifyItemChanged(i);
                                }
                            }
                        }else{
                            Toast.makeText(Review_viewAll.this, "Data Not found", Toast.LENGTH_SHORT).show();
                        }



                    }else{
                        Toast.makeText(Review_viewAll.this, "message==" + message, Toast.LENGTH_SHORT).show();
                        Log.e("response_msggg_toast", "" + message);
                        // txt_msg_best.setText("Package not available");
                        // cardview_bestsell.setVisibility(View.VISIBLE);
                    }
                }catch (Exception e){
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Review_viewAll.this, "" + error, Toast.LENGTH_SHORT).show();
                progress_reviewvall.setVisibility(View.VISIBLE);
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(Review_viewAll.this);
        requestQueue.add(stringRequest);
        requestQueue.add(stringRequest);
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
            Intent i = new Intent(Review_viewAll.this, Request_callBack.class);
            startActivity(i);
            Toast.makeText(getApplicationContext(), "Request", Toast.LENGTH_LONG).show();
        } else if (item.getItemId() == R.id.send_qury) {
            Intent i = new Intent(Review_viewAll.this, Send_query.class);
            startActivity(i);

            Toast.makeText(getApplicationContext(), "sendquery", Toast.LENGTH_LONG).show();
        } else if (item.getItemId() == R.id.help) {
            Toast.makeText(getApplicationContext(), "Item 3 Selected", Toast.LENGTH_LONG).show();
        }

        return super.onOptionsItemSelected(item);
    }

}
