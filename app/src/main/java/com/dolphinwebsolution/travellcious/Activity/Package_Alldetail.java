package com.dolphinwebsolution.travellcious.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.dolphinwebsolution.travellcious.Adapter.FAQ_adapter;
import com.dolphinwebsolution.travellcious.Adapter.PackDetail_hotel_adapter;
import com.dolphinwebsolution.travellcious.Adapter.SightSeen_adapter;
import com.dolphinwebsolution.travellcious.Model.FAQ_Model;
import com.dolphinwebsolution.travellcious.Model.Facilities_model;
import com.dolphinwebsolution.travellcious.Model.Hotel_model;
import com.dolphinwebsolution.travellcious.Model.Sightseen_model;
import com.dolphinwebsolution.travellcious.R;
import com.dolphinwebsolution.travellcious.utils.CheckNetwork;
import com.dolphinwebsolution.travellcious.utils.Main_Url;
import com.koushikdutta.ion.Ion;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class Package_Alldetail extends AppCompatActivity {
    RecyclerView hotel_recycler, faq_recycler,sightseen_recycler;
    public static String LOCATION_TITLE;
    private List<Hotel_model> hotel_model = new ArrayList<Hotel_model>();
    private List<FAQ_Model> faq_model = new ArrayList<FAQ_Model>();
    private List<Sightseen_model> sightseen_model = new ArrayList<Sightseen_model>();
    PackDetail_hotel_adapter hotel_adapter;
    SightSeen_adapter sightSeen_adapter;
    FAQ_adapter faq_adapter;
    String URL, Package_id, URLPACKAGEDETAIL;
    ImageView img_banner, imgone, imgtwo, imgthree, imgfour;
    TextView txt_inclusion, txt_exclusion, txt_location_title, txt_pdetail_budget;
    LinearLayout lv_inclusion,lv_exclusion,lv_allpkgdetail_show;
    CardView cardview_faq, cardview_hotel,cardview_sightseen;
    ProgressBar progress_pkgdetail;

    ArrayList<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_package_alldetail);
        setTitle("");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //http://travel.demoproject.info/api/get_package.php?id=1
        Package_id = getIntent().getStringExtra("Package_id");
        Log.e("Package_id", "" + Package_id);
        allocatememory();

        //http://travel.demoproject.info/api/get_package_details.php?package_id=1
        URLPACKAGEDETAIL = Main_Url.sortUrl + "get_package_details.php?package_id=" + Package_id;
        Attech_recyclerview();


        if (CheckNetwork.isNetworkAvailable(Package_Alldetail.this)) {
            Call_Package_Detail_API();
        } else {
            Toast.makeText(Package_Alldetail.this, "Please Check your Internet Connection", Toast.LENGTH_SHORT).show();

        }
        imgfour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Package_Alldetail.this, Package_image_gridview.class);
                i.putExtra("Package_id", Package_id);
                startActivity(i);
            }
        });

    }

    private void Attech_recyclerview() {

        hotel_adapter = new PackDetail_hotel_adapter(Package_Alldetail.this, hotel_model);
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(Package_Alldetail.this, LinearLayoutManager.VERTICAL, true);
        layoutManager1.setReverseLayout(false);
        hotel_recycler.setLayoutManager(layoutManager1);
        hotel_recycler.setAdapter(hotel_adapter);


        faq_adapter = new FAQ_adapter(Package_Alldetail.this, faq_model);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(Package_Alldetail.this, LinearLayoutManager.VERTICAL, true);
        layoutManager2.setReverseLayout(false);
        faq_recycler.setLayoutManager(layoutManager2);
        faq_recycler.setAdapter(faq_adapter);

        sightSeen_adapter = new SightSeen_adapter(Package_Alldetail.this, sightseen_model);
        LinearLayoutManager layoutManager3 = new LinearLayoutManager(Package_Alldetail.this, LinearLayoutManager.VERTICAL, true);
        layoutManager3.setReverseLayout(false);
        sightseen_recycler.setLayoutManager(layoutManager3);
        sightseen_recycler.setAdapter(sightSeen_adapter);


    }

    private void allocatememory() {


        progress_pkgdetail = (ProgressBar) findViewById(R.id.progress_pkgdetail);
        lv_allpkgdetail_show = (LinearLayout) findViewById(R.id.lv_allpkgdetail_show);
        lv_exclusion = (LinearLayout) findViewById(R.id.lv_exclusion);
        lv_inclusion = (LinearLayout) findViewById(R.id.lv_inclusion);
        faq_recycler = (RecyclerView) findViewById(R.id.faq_recycler);
        hotel_recycler = (RecyclerView) findViewById(R.id.hotel_recycler);
        sightseen_recycler = (RecyclerView) findViewById(R.id.sightseen_recycler);
        imgone = (ImageView) findViewById(R.id.imgone);
        imgtwo = (ImageView) findViewById(R.id.imgtwo);
        img_banner = (ImageView) findViewById(R.id.img_banner);
        imgthree = (ImageView) findViewById(R.id.imgthree);
        imgfour = (ImageView) findViewById(R.id.imgfour);
       // txt_inclusion = (TextView) findViewById(R.id.txt_inclusion);
        txt_exclusion = (TextView) findViewById(R.id.txt_exclusion);
        txt_location_title = (TextView) findViewById(R.id.txt_location_title);
        txt_pdetail_budget = (TextView) findViewById(R.id.txt_pdetail_budget);

        cardview_faq = (CardView) findViewById(R.id.cardview_faq);
        cardview_hotel = (CardView) findViewById(R.id.cardview_hotel);
        cardview_sightseen = (CardView) findViewById(R.id.cardview_sightseen);

    }

    private void Call_Package_Detail_API() {

        progress_pkgdetail.setVisibility(View.VISIBLE);
        lv_allpkgdetail_show.setVisibility(View.GONE);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLPACKAGEDETAIL, new Response.Listener<String>() {
            @SuppressLint("LongLogTag")
            @Override
            public void onResponse(String response) {
                try {
                    Log.e("Response_Package_detils", "" + response);

                    JSONObject jsonObject = new JSONObject(response);
                    Log.e("URLLLLLL_details..", "" + URLPACKAGEDETAIL);
                    Log.e("Response_Package_detils..", "" + jsonObject);

                       progress_pkgdetail.setVisibility(View.GONE);
                       lv_allpkgdetail_show.setVisibility(View.VISIBLE);
                    String status = jsonObject.getString("status");
                    String message = jsonObject.getString("message");

                    if (status.equals("Success") == true) {
                        hotel_model.clear();
                        faq_model.clear();
                        String data = jsonObject.getString("data");
                        JSONObject data_obj = new JSONObject(data);
                        //Image_title
                        String package_title_image = data_obj.getString("package_title_image");
                        Log.e("package_title_image", "" + data_obj.getString("package_title_image"));

                        JSONObject package_image_obj = new JSONObject(package_title_image);
                        Log.e("package_image_obj", "" + package_image_obj);

                        String package_image = package_image_obj.getString("package_image");
                        String location_title = package_image_obj.getString("location_title");
                        LOCATION_TITLE = package_image_obj.getString("location_title");

                        String budget = package_image_obj.getString("budget");
                        Log.e("package_image", "" + package_image);
                        Log.e("location_title", "" + LOCATION_TITLE);
                        Log.e("budget", "" + budget);
                        txt_location_title.setText(location_title);
                        txt_pdetail_budget.setText(budget);
                        Bitmap bmw = null;

                        try {
                            bmw = Ion.with(Package_Alldetail.this).load(package_image).asBitmap().get();
                            Log.e("bmw_img", "" + bmw);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }
                        img_banner.setImageBitmap(bmw);

                        String package_images = data_obj.getString("package_images");
                        Log.e("package_images", "" + data_obj.getString("package_images"));
                        JSONArray package_images_array = new JSONArray(package_images);
                        Log.e("package_images_array", "" + package_images_array);

                        if (package_images_array != null && package_images_array.isNull(0) != true) {
                            for (int i = 0; i < package_images_array.length(); i++) {
                                try {

                                    Bitmap imageone = null, imagetwo = null, imagethree, imagefour, imagefive;
                                    // cardview_review.setVisibility(View.GONE);
                                    JSONObject img_obje_one = package_images_array.getJSONObject(0);
                                    Log.e("img_obje", "" + img_obje_one);
                                    String img_one = img_obje_one.getString("Images");
                                    Log.e("img_one", "" + img_one);

                                    Glide.with(Package_Alldetail.this).load(img_one).into(imgone);

                                    JSONObject img_obje_two = package_images_array.getJSONObject(1);
                                    Log.e("img_objetwo", "" + img_obje_two);
                                    String img_two = img_obje_two.getString("Images");
                                    Log.e("img_two", "" + img_two);

                                    Glide.with(Package_Alldetail.this).load(img_two).into(imgtwo);
                                    Glide.with(Package_Alldetail.this).load(img_two).into(imgthree);
                                    Glide.with(Package_Alldetail.this).load(img_two).into(imgfour);

                                } catch (Exception e) {
                                }
                            }
                        } else {
                            Toast.makeText(Package_Alldetail.this, "Package Images Not Available", Toast.LENGTH_SHORT).show();
                        }

                        //Inclision
                        String Inclusion = data_obj.getString("Inclusion");
                        Log.e("Inclusion", "" + data_obj.getString("Inclusion"));

                        JSONArray Inclusion_aaray = new JSONArray(Inclusion);
                        Log.e("Inclusion_aaray", "" + Inclusion_aaray);
                        if (Inclusion_aaray != null && Inclusion_aaray.isNull(0) != true) {

                            final TextView[] myTextViews = new TextView[Inclusion_aaray.length()]; // create an empty array;
                            lv_inclusion.removeAllViews();
                            for (int j = 0; j < Inclusion_aaray.length(); j++) {
                                try {
                                    JSONObject inclusion_obj = Inclusion_aaray.getJSONObject(j);
                                    Log.e("Inclusions", "" + inclusion_obj.getString("Inclusions"));

                                    String inclusion = inclusion_obj.getString("Inclusions");
                                    Log.e("inclusion", "" +inclusion);
                                    TextView rowTextView = new TextView(Package_Alldetail.this);
                                    String circle = "\u25CF";
                                    rowTextView.setText(circle + " "+ inclusion);
                                    lv_inclusion.addView(rowTextView);
                                    myTextViews[j] = rowTextView;

                                } catch (Exception e) {

                                } finally {
                                }
                            }
                        } else {
                            txt_inclusion.setVisibility(View.VISIBLE);
                            // Toast.makeText(Package_name.this, "Selling Array Null", Toast.LENGTH_SHORT).show();
                        }

                        //Exclusion

                        String Exclusions = data_obj.getString("Exclusions");
                        Log.e("Exclusions", "" + data_obj.getString("Exclusions"));

                        JSONArray Exclusions_aaray = new JSONArray(Exclusions);
                        Log.e("Exclusions_aaray", "" + Exclusions_aaray);
                        if (Exclusions_aaray != null && Exclusions_aaray.isNull(0) != true) {

                            final TextView[] myTextViews = new TextView[Exclusions_aaray.length()]; // create an empty array;
                            lv_exclusion.removeAllViews();
                            for (int j = 0; j < Exclusions_aaray.length(); j++) {
                                try {
                                    JSONObject exclusion_obj = Exclusions_aaray.getJSONObject(j);
                                    Log.e("Exclusions", "" + exclusion_obj.getString("Exclusions"));

                                    String exclusion = exclusion_obj.getString("Exclusions");
                                    Log.e("exclusion", "" +exclusion);

                                    TextView rowTextView = new TextView(Package_Alldetail.this);
                                    String circle = "\u25CF";
                                    rowTextView.setText(circle + " "+ exclusion);
                                    lv_exclusion.addView(rowTextView);
                                    myTextViews[j] = rowTextView;

                                } catch (Exception e) {

                                } finally {
                                }
                            }
                        } else {
                            txt_exclusion.setVisibility(View.VISIBLE);
                            // Toast.makeText(Package_name.this, "Selling Array Null", Toast.LENGTH_SHORT).show();
                        }

                        // Hotel Details.......
                        String hotels_details = data_obj.getString("hotels_details");
                        Log.e("hotels_details", "" + data_obj.getString("hotels_details"));

                        JSONArray hotels_details_array = new JSONArray(hotels_details);
                        Log.e("hotels_details_array", "" + hotels_details_array);
                        if (hotels_details_array != null && hotels_details_array.isNull(0) != true) {
                            for (int j = 0; j < hotels_details_array.length(); j++) {
                                try {
                                    cardview_hotel.setVisibility(View.GONE);
                                    hotel_recycler.setVisibility(View.VISIBLE);
                                    JSONObject hotel_obj = hotels_details_array.getJSONObject(j);
                                    Log.e("hotel_title", "" + hotel_obj.getString("hotel_title"));
                                    Log.e("description", "" + hotel_obj.getString("description"));

                                    String facilities = hotel_obj.getString("facilities");
                                    Log.e("facilities", "" + hotel_obj.getString("facilities"));
                                    JSONArray facilities_array = new JSONArray(facilities);
                                    Log.e("facilities_array", "" + facilities_array);

                                    for (int k = 0; k < facilities_array.length(); k++) {

                                        JSONObject  facilities_obj = facilities_array.getJSONObject(k);
                                        Log.e("facilities_obj", "" + facilities_obj);
                                        String facilitiesss    = facilities_obj.getString("facilities");
                                      //  list.add(facilitiesss);
                                        Log.e("facilities_obj", "" + facilitiesss);

                                    }

                                    Hotel_model hmodel = new Hotel_model(hotel_obj.getString("hotel_title"),
                                            hotel_obj.getString("description"),
                                            hotel_obj.getString("star"), hotel_obj.getString("hotel_image"),
                                            hotel_obj.getString("check_in"), hotel_obj.getString("check_out"),
                                            hotel_obj.getString("room_type"), hotel_obj.getString("meals"), hotel_obj.getString("Nights"),
                                            hotel_obj.getString("Days"), hotel_obj.getString("Nights"));

                                    hotel_model.add(hmodel);

                                } catch (Exception e) {

                                } finally {
                                    hotel_adapter.notifyItemChanged(j);
                                }
                            }
                        } else {
                            cardview_hotel.setVisibility(View.VISIBLE);
                            hotel_recycler.setVisibility(View.GONE);
                            // Toast.makeText(Package_name.this, "Selling Array Null", Toast.LENGTH_SHORT).show();
                        }


                        //Sightseen data
                        String Sightseen = data_obj.getString("Sightseen");
                        Log.e("Sightseen", "" + data_obj.getString("Sightseen"));

                        JSONArray Sightseen_array = new JSONArray(Sightseen);
                        Log.e("Sightseen_array", "" + Sightseen_array);
                        if (Sightseen_array != null && Sightseen_array.isNull(0) != true) {
                            for (int z = 0; z < 4; z++) {
                                try {
                                    cardview_sightseen.setVisibility(View.GONE);
                                    sightseen_recycler.setVisibility(View.VISIBLE);
                                    JSONObject sightseen_obj = Sightseen_array.getJSONObject(z);
                                    Log.e("days", "" + sightseen_obj.getString("days"));
                                    Log.e("locations", "" + sightseen_obj.getString("locations"));

                                    Sightseen_model model = new Sightseen_model(
                                            sightseen_obj.getString("days"),
                                            sightseen_obj.getString("locations"),
                                            sightseen_obj.getString("sightseen_title"),
                                            sightseen_obj.getString("description"));

                                    sightseen_model.add(model);

                                } catch (Exception e) {

                                } finally {
                                    sightSeen_adapter.notifyItemChanged(z);
                                }
                            }
                        } else {
                            cardview_sightseen.setVisibility(View.VISIBLE);
                            sightseen_recycler.setVisibility(View.GONE);
                            // Toast.makeText(Package_name.this, "Selling Array Null", Toast.LENGTH_SHORT).show();
                        }

                        //FAQ data
                        String FAQs = data_obj.getString("FAQs");
                        Log.e("FAQs", "" + data_obj.getString("FAQs"));

                        JSONArray FAQs_array = new JSONArray(FAQs);
                        Log.e("FAQs_array", "" + FAQs_array);
                        if (FAQs_array != null && FAQs_array.isNull(0) != true) {
                            for (int z = 0; z < FAQs_array.length(); z++) {
                                try {
                                    cardview_faq.setVisibility(View.GONE);
                                    faq_recycler.setVisibility(View.VISIBLE);
                                    JSONObject FAQ_obj = FAQs_array.getJSONObject(z);
                                    Log.e("FAQs_title", "" + FAQ_obj.getString("FAQs_title"));
                                    Log.e("FAQs_containts", "" + FAQ_obj.getString("FAQs_containts"));

                                    FAQ_Model model = new FAQ_Model
                                            (FAQ_obj.getString("FAQs_title"),
                                                    FAQ_obj.getString("FAQs_containts"));

                                    faq_model.add(model);

                                } catch (Exception e) {

                                } finally {
                                    faq_adapter.notifyItemChanged(z);
                                }
                            }
                        } else {
                            cardview_faq.setVisibility(View.VISIBLE);
                            faq_recycler.setVisibility(View.GONE);
                            // Toast.makeText(Package_name.this, "Selling Array Null", Toast.LENGTH_SHORT).show();
                        }


                    } else {
                        Toast.makeText(Package_Alldetail.this, "" + message, Toast.LENGTH_SHORT).show();
                        Log.e("response_msggg_toast", "" + message);
                    }



                } catch (Exception e) {
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Package_Alldetail.this, ""+error , Toast.LENGTH_SHORT).show();
                progress_pkgdetail.setVisibility(View.VISIBLE);
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(Package_Alldetail.this);
        requestQueue.add(stringRequest);
        requestQueue.add(stringRequest);


    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        } else if (item.getItemId() == R.id.request_callback) {
            Intent i = new Intent(Package_Alldetail.this, Request_callBack.class);
            startActivity(i);
            Toast.makeText(getApplicationContext(), "Request", Toast.LENGTH_LONG).show();
        } else if (item.getItemId() == R.id.send_qury) {
            Intent i = new Intent(Package_Alldetail.this, Send_query.class);
            startActivity(i);

            Toast.makeText(getApplicationContext(), "sendquery", Toast.LENGTH_LONG).show();
        } else if (item.getItemId() == R.id.help) {
            Toast.makeText(getApplicationContext(), "Item 3 Selected", Toast.LENGTH_LONG).show();
        }

        return super.onOptionsItemSelected(item);
    }


}
