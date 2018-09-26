package com.dolphinwebsolution.travellcious.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.dolphinwebsolution.travellcious.Adapter.Package_img_grid_adapter;
import com.dolphinwebsolution.travellcious.Model.Hotel_model;
import com.dolphinwebsolution.travellcious.Model.Package_images_grid_model;
import com.dolphinwebsolution.travellcious.R;
import com.dolphinwebsolution.travellcious.utils.CheckNetwork;
import com.dolphinwebsolution.travellcious.utils.Main_Url;
import com.koushikdutta.ion.Ion;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class Package_image_gridview extends AppCompatActivity {
    ArrayList<Package_images_grid_model> grid_model = new ArrayList<Package_images_grid_model>();
    GridView gridview;
    String URLPACKAGEIMAGE;
    public static String  Package_id;
   
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_package_image_gridview);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        allocatememory();
        Package_id = getIntent().getStringExtra("Package_id");
        Log.e("Package_id", "" + Package_id);

        URLPACKAGEIMAGE = Main_Url.sortUrl + "get_package_details.php?package_id=" + Package_id;


        if (CheckNetwork.isNetworkAvailable(Package_image_gridview.this)) {
         CALL_PACKAGE_IMAGES_API();
        } else {
            Toast.makeText(Package_image_gridview.this, "Please Check your Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }

    private void CALL_PACKAGE_IMAGES_API() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLPACKAGEIMAGE, new Response.Listener<String>() {
            @SuppressLint("LongLogTag")
            @Override
            public void onResponse(String response) {
                try {
                    Log.e("Response_Package_IMagessss", "" + response);

                    JSONObject jsonObject = new JSONObject(response);
                    Log.e("URLLLLLL_details..", "" + URLPACKAGEIMAGE);
                    Log.e("Response_Package_img..", "" + jsonObject);

                    //   progress.setVisibility(View.GONE);
                    //   lv_package_view.setVisibility(View.VISIBLE);
                    String status = jsonObject.getString("status");
                    String message = jsonObject.getString("message");

                    if (status.equals("Success") == true) {
                        String data = jsonObject.getString("data");
                        JSONObject data_obj = new JSONObject(data);
                        //Image_title
                        grid_model.clear();
                        String package_images = data_obj.getString("package_images");
                        Log.e("package_images", "" + data_obj.getString("package_images"));
                        JSONArray package_images_array = new JSONArray(package_images);
                        Log.e("package_images_array", "" + package_images_array);

                        if (package_images_array != null && package_images_array.isNull(0) != true) {
                            for (int i = 0; i < package_images_array.length(); i++) {
                                try {

                                     JSONObject img_obj = package_images_array.getJSONObject(i);
                                    Log.e("img_obje", "" + img_obj);
                                   Package_images_grid_model gmodel = new Package_images_grid_model(
                                           img_obj.getString("Image_id"),img_obj.getString("Images"));
                                    grid_model.add(gmodel);

                                } catch (Exception e) {
                                }
                            }
                            Package_img_grid_adapter adapter = new Package_img_grid_adapter(Package_image_gridview.this,grid_model);
                            gridview.setAdapter(adapter);
                        } else {
                             Toast.makeText(Package_image_gridview.this, "null array", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(Package_image_gridview.this, "" + message, Toast.LENGTH_SHORT).show();
                        Log.e("response_msggg_toast", "" + message);
                    }
                } catch (Exception e) {
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(Package_image_gridview.this);
        requestQueue.add(stringRequest);
        requestQueue.add(stringRequest);




    }

    private void allocatememory() {
        gridview = (GridView) findViewById(R.id.gridview);
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
