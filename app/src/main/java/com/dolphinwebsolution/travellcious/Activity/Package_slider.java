package com.dolphinwebsolution.travellcious.Activity;

import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dolphinwebsolution.travellcious.Adapter.Package_img_grid_adapter;
import com.dolphinwebsolution.travellcious.Model.Package_images_grid_model;
import com.dolphinwebsolution.travellcious.Model.sliderimage_model;
import com.dolphinwebsolution.travellcious.R;
import com.dolphinwebsolution.travellcious.utils.CheckNetwork;
import com.dolphinwebsolution.travellcious.utils.Main_Url;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;

public class Package_slider extends AppCompatActivity implements ViewPager.OnPageChangeListener {
    int currentPage = 0;
    Timer timer;
    final long DELAY_MS = 500;//delay in milliseconds before task is to be executed
    final long PERIOD_MS = 3000; // time in milliseconds between successive task executions.
    private ViewPager mPager;
    String URL;
    private CircleIndicator indicator;
    private Sliding_Package_img_Adapter adapter;
    private List<sliderimage_model> sliderimage_models = new ArrayList<sliderimage_model>();
    String URLPACKAGEIMAGE,Package_id,images,imgid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_package_slider);
        URL = Main_Url.sortUrl + "get_package.php?id="+"1";

        getSupportActionBar().hide();
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Package_id=getIntent().getStringExtra("Package_id");
        images=getIntent().getStringExtra("images");
        imgid=getIntent().getStringExtra("image_id");
        Log.e("Packageid",""+Package_id);
        URLPACKAGEIMAGE = Main_Url.sortUrl + "get_package_details.php?package_id=" + Package_id;


        mPager = (ViewPager) findViewById(R.id.pager_img);
        indicator = (CircleIndicator)findViewById(R.id.indicator_img);
        mPager.addOnPageChangeListener(this);
        startAutoScrollViewPager();
        if (CheckNetwork.isNetworkAvailable(Package_slider.this))
        {
            CallSliderApi();
        }else {

        }

    }
    private void startAutoScrollViewPager() {

        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            @Override
            public void run() {
                if (currentPage == sliderimage_models.size() - 1) {
                    currentPage = 0;
                }
                mPager.setCurrentItem(currentPage++, true);
            }
        };

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, DELAY_MS, PERIOD_MS);
    }
    private void CallSliderApi() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLPACKAGEIMAGE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.e("response_banner", "" + response);
                        Log.e("Response__IMagessss", "" + response);

                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(response);

                            Log.e("URLLLLLL_details..", "" + URLPACKAGEIMAGE);
                            Log.e("Response_Package_img..", "" + jsonObject);
                            sliderimage_models.clear();

                            //   progress.setVisibility(View.GONE);
                            //   lv_package_view.setVisibility(View.VISIBLE);
                            String status = jsonObject.getString("status");
                            String message = jsonObject.getString("message");

                            if (status.equals("Success") == true) {
                                String data = jsonObject.getString("data");
                                JSONObject data_obj = new JSONObject(data);
                                //Image_title

                                String package_images = data_obj.getString("package_images");
                                Log.e("package_images", "" + data_obj.getString("package_images"));
                                JSONArray package_images_array = new JSONArray(package_images);
                                Log.e("pag_images_array_slider", "" + package_images_array);

                                if (package_images_array != null && package_images_array.isNull(0) != true) {
                                    for (int i = 0; i < package_images_array.length(); i++) {
                                        try {

                                            JSONObject img_obj = package_images_array.getJSONObject(i);
                                            Log.e("img_obje", "" + img_obj);
                                            sliderimage_model imageModel = new sliderimage_model(img_obj.getString("Image_id"),
                                                    img_obj.getString("Images"));
                                            sliderimage_models.add(imageModel);
                                            Log.e("schedule", "" + sliderimage_models.size());

                                            mPager.setAdapter(new Sliding_Package_img_Adapter(Package_slider.this, sliderimage_models));
                                            indicator.setViewPager(mPager);


                                        } catch (Exception e) {
                                        }
                                    }
                                } else {
                                    Toast.makeText(Package_slider.this, "null array", Toast.LENGTH_SHORT).show();
                                }

                            } else {
                                Toast.makeText(Package_slider.this, "" + message, Toast.LENGTH_SHORT).show();
                                Log.e("response_msggg_toast", "" + message);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }


                },  new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //   Toast.makeText(getActivity()), "not get Response", Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(Package_slider.this);
        requestQueue.add(stringRequest);
        requestQueue.add(stringRequest);

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

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
        }

        return super.onOptionsItemSelected(item);
    }


}

