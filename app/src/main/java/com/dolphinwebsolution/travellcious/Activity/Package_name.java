package com.dolphinwebsolution.travellcious.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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
import com.dolphinwebsolution.travellcious.Adapter.Bestselling_adapter;
import com.dolphinwebsolution.travellcious.Adapter.Review_adapter;
import com.dolphinwebsolution.travellcious.Adapter.Tranding_adapter;
import com.dolphinwebsolution.travellcious.Model.Bestselling_Package_model;
import com.dolphinwebsolution.travellcious.Model.Review_model;
import com.dolphinwebsolution.travellcious.Model.SliderPackage_name_model;
import com.dolphinwebsolution.travellcious.Model.Tranding_model;
import com.dolphinwebsolution.travellcious.Model.sliderimage_model;
import com.dolphinwebsolution.travellcious.R;
import com.dolphinwebsolution.travellcious.utils.CheckNetwork;
import com.dolphinwebsolution.travellcious.utils.Main_Url;
import com.koushikdutta.ion.Ion;
import com.rbrooks.indefinitepagerindicator.IndefinitePagerIndicator;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;

import me.relex.circleindicator.CircleIndicator;


public class Package_name extends AppCompatActivity {

    CardView cardview_trading, cardview_bestsell, cardview_review;
    TextView tv_review, tv_reviewmore, tv_more, txt_msg, txt_msg_best, txt_msg_review, tv_tranding, tv_bestsell, tv_overview, tv_review_text;
    LinearLayout lv_package_view;
    Button btn_bestsell_viewall, btn_trading_vall, btn_customize_package, btn_review_viewall;
    RecyclerView recycler_bestselling, recycler_trading, recycler_review;
    String cat_id, location_id, location_title, ovreview, getlocation_details;
    private List<Bestselling_Package_model> model = new ArrayList<Bestselling_Package_model>();
    private List<Tranding_model> trading_model = new ArrayList<Tranding_model>();
    private Bestselling_adapter adapter;
    Tranding_adapter trade_adapter;
    ProgressBar progress;
    private ViewPager mPager;
    private CircleIndicator indicator;

    private List<Review_model> rmodel = new ArrayList<Review_model>();
    private Review_adapter review_adapter;
    IndefinitePagerIndicator indicates, indicates1;
    private List<SliderPackage_name_model> sliderimage_models = new ArrayList<SliderPackage_name_model>();
    int currentPage = 0;
    Timer timer;
    final long DELAY_MS = 800;//delay in milliseconds before task is to be executed
    final long PERIOD_MS = 4000; // time in milliseconds between successive task executions.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_package);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Allocatememory();

        location_id = getIntent().getStringExtra("location_id");
        location_title = getIntent().getStringExtra("location_title");
         Log.e("location_idd", "" + location_id);
         Log.e("location_title", "" + location_title);
        setTitle(location_title);
        tv_review.setVisibility(View.VISIBLE);
        getlocation_details = Main_Url.sortUrl + "get_location_details.php?location_id=" + location_id;
        Log.e("URLLLLLL_details", "" + getlocation_details);

        AttachAll_Recyclerview();
        Btn_all_clicklistener();
        Set_all_fontstyle();
        startAutoScrollViewPager();

        if (CheckNetwork.isNetworkAvailable(Package_name.this)) {
            APigetlocation_details();
        } else {
            Toast.makeText(Package_name.this, "Please Check your Internet Connection", Toast.LENGTH_SHORT).show();
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

    private void APigetlocation_details() {
        progress.setVisibility(View.VISIBLE);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, getlocation_details, new Response.Listener<String>() {
            @SuppressLint("LongLogTag")
            @Override
            public void onResponse(String response) {
                Log.e("Response_location_detils", "" + response);
                try {
                    Log.e("URLLLLLL_details..", "" + getlocation_details);
                    JSONObject jsonObject = new JSONObject(response);

                    progress.setVisibility(View.GONE);
                    lv_package_view.setVisibility(View.VISIBLE);
                    String status = jsonObject.getString("status");
                    String message = jsonObject.getString("message");
                    if (status.equals("Success")==true) {
                        String data = jsonObject.getString("data");
                        JSONObject data_obj = new JSONObject(data);
                        //Image_title
                        String images_and_title = data_obj.getString("images_and_title");
                        Log.e("images_and_title", "" + data_obj.getString("images_and_title"));
                        JSONArray images_title_arr = new JSONArray(images_and_title);
                        sliderimage_models.clear();
                        rmodel.clear();
                        trading_model.clear();
                        model.clear();
                        if (images_title_arr != null && images_title_arr.isNull(0) != true) {
                            for (int i = 0; i < images_title_arr.length(); i++) {
                                try {
                                    JSONObject obje = images_title_arr.getJSONObject(i);
                                    Log.e("images_URL", "" + obje.getString("location_image"));
                                    SliderPackage_name_model imageModel = new SliderPackage_name_model(obje.getString("location_image"), obje.getString("location_title"));
                                    sliderimage_models.add(imageModel);

                                    mPager.setAdapter(new SliderPackage_name_adapter(Package_name.this, sliderimage_models));
                                    indicator.setViewPager(mPager);

                                } catch (Exception e) {
                                } finally {
                                    //review_adapter.notifyItemChanged(i);
                                }
                            }
                        } else {
                            Toast.makeText(Package_name.this, "Location Images not Available", Toast.LENGTH_SHORT).show();
                        }
                        //overview
                        ovreview = data_obj.getString("overview_details");
                        if(ovreview.equals("") !=true && ovreview.isEmpty()!=true) {
                            Log.e("overviewwww", "" + data_obj.getString("overview_details"));
                            AssetManager am = getApplicationContext().getAssets();
                            Typeface typeface = Typeface.createFromAsset(am,
                                    String.format(Locale.getDefault(), "Lato-Bold.ttf"));
                            Typeface tf = Typeface.createFromAsset(am,
                                    String.format(Locale.getDefault(), "Lato-Medium.ttf"));
                            Log.e("ovvovovovovovovo", "" + ovreview);
                            tv_review.setTypeface(tf);
                            tv_reviewmore.setTypeface(tf);
                            tv_more.setTypeface(typeface);
                            tv_review.setText(ovreview);
                            tv_reviewmore.setText(ovreview);
                        }else {
                            Toast.makeText(Package_name.this, "Data not Available", Toast.LENGTH_SHORT).show();
                        }
                        //Review
                        String review = data_obj.getString("review");
                        Log.e("reviewwww", "" + data_obj.getString("review"));
                        JSONArray review_array = new JSONArray(review);
                        if (review_array != null && review_array.isNull(0) != true) {
                            for (int i = 0; i < review_array.length(); i++) {
                                try {
                                    cardview_review.setVisibility(View.GONE);
                                    JSONObject obje = review_array.getJSONObject(i);

                                    Review_model review_model = new Review_model(obje.getString("Stars"),
                                            obje.getString("Review"), obje.getString("User_Id"),
                                            obje.getString("Package_id"), obje.getString("User_image"),
                                            obje.getString("Username"));

                                    rmodel.add(review_model);

                                } catch (Exception e) {
                                } finally {
                                    review_adapter.notifyItemChanged(i);
                                }
                            }
                        } else {
                            btn_review_viewall.setVisibility(View.GONE);
                            cardview_review.setVisibility(View.VISIBLE);
                            txt_msg_review.setText("Review Not Available");
                           // Toast.makeText(Package_name.this, "Data Not found", Toast.LENGTH_SHORT).show();
                        }

                        //Selling_package
                        String selling_package = data_obj.getString("selling_package");
                        Log.e("selling_package", "" + data_obj.getString("selling_package"));

                        JSONArray selling_pkg_array = new JSONArray(selling_package);
                        if (selling_pkg_array != null && selling_pkg_array.isNull(0) != true) {
                            for (int j = 0; j < selling_pkg_array.length(); j++) {
                                try {
                                    cardview_bestsell.setVisibility(View.GONE);
                                    JSONObject obje = selling_pkg_array.getJSONObject(j);
                                    Log.e("Location_title_package", "" + obje.getString("Location_title"));
                                    Log.e("features", "" + obje.getString("features"));
                                    String features = obje.getString("features");
                                    JSONObject object = new JSONObject(features);
                                    Bestselling_Package_model bestselling = new Bestselling_Package_model(obje.getString("Location_title"),
                                            obje.getString("duration"),
                                            obje.getString("budget"),
                                            obje.getString("PackageId"),
                                            object.getString("Flights"),
                                            object.getString("Upto 5 start"),
                                            object.getString("Meals"),
                                            object.getString("Stay Included"),
                                            object.getString("Airport Transfer"));

                                    model.add(bestselling);

                                } catch (Exception e) {

                                } finally {
                                    adapter.notifyItemChanged(j);
                                }
                            }
                        } else {
                            btn_bestsell_viewall.setVisibility(View.GONE);
                            cardview_bestsell.setVisibility(View.VISIBLE);
                            txt_msg_best.setText("Package not Available");
                           // Toast.makeText(Package_name.this, "Selling Array Null", Toast.LENGTH_SHORT).show();
                        }

                        //Treding_package
                        String treding_package = data_obj.getString("treding_package");

                        Log.e("treding_package", "" + data_obj.getString("treding_package"));
                        JSONArray treding_pkg_array = new JSONArray(treding_package);
                        if (treding_pkg_array != null && treding_pkg_array.isNull(0) != true) {

                            for (int k = 0; k < treding_pkg_array.length(); k++) {
                                try {
                                    cardview_trading.setVisibility(View.GONE);
                                    JSONObject objee = treding_pkg_array.getJSONObject(k);
                                    Log.e("Location_title_package", "" + objee.getString("Location_title"));
                                    Log.e("features", "" + objee.getString("features"));
                                    String features = objee.getString("features");
                                    JSONObject object = new JSONObject(features);
                                    Tranding_model tranding = new Tranding_model(objee.getString("Location_title"),
                                            objee.getString("duration"),
                                            objee.getString("budget"),
                                            objee.getString("PackageId"),
                                            object.getString("Flights"),
                                            object.getString("Upto 5 start"),
                                            object.getString("Meals"),
                                            object.getString("Stay Included"),
                                            object.getString("Airport Transfer"));

                                    trading_model.add(tranding);

                                } catch (Exception e) {
                                    Log.e("exception", "" + e);
                                } finally {
                                    trade_adapter.notifyItemChanged(k);
                                }
                            }
                        }else {
                            btn_trading_vall.setVisibility(View.GONE);
                            cardview_trading.setVisibility(View.VISIBLE);
                            txt_msg.setText("Package not Available");
                          //  Toast.makeText(Package_name.this, "Tranding array null", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(Package_name.this, "message==" + message, Toast.LENGTH_SHORT).show();
                        Log.e("response_msggg_toast", "" + message);
                    }
                } catch (Exception e) {
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Package_name.this, "" + error, Toast.LENGTH_SHORT).show();
                progress.setVisibility(View.VISIBLE);
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(Package_name.this);
        requestQueue.add(stringRequest);
        requestQueue.add(stringRequest);
    }

    private void Set_all_fontstyle() {
        AssetManager am = getApplicationContext().getAssets();
        Typeface typeface = Typeface.createFromAsset(am,
                String.format(Locale.getDefault(), "Lato-Bold.ttf"));
        Typeface tf = Typeface.createFromAsset(am,
                String.format(Locale.getDefault(), "Lato-Medium.ttf"));
        btn_bestsell_viewall.setTypeface(typeface);
        btn_trading_vall.setTypeface(typeface);
        tv_tranding.setTypeface(typeface);
        tv_bestsell.setTypeface(typeface);
        tv_overview.setTypeface(typeface);
        tv_review_text.setTypeface(typeface);
        btn_customize_package.setTypeface(typeface);

    }

    private void AttachAll_Recyclerview() {
        recycler_bestselling = (RecyclerView) findViewById(R.id.recycler_bestselling);
        recycler_review = (RecyclerView) findViewById(R.id.recycler_review);
        recycler_trading = (RecyclerView) findViewById(R.id.recycler_trading);
        review_adapter = new Review_adapter(Package_name.this, rmodel);
        recycler_bestselling.setVisibility(View.VISIBLE);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(Package_name.this, LinearLayoutManager.HORIZONTAL, true);
        layoutManager2.setReverseLayout(false);
        recycler_review.setLayoutManager(layoutManager2);
        recycler_review.setAdapter(review_adapter);

        indicates1.attachToRecyclerView(recycler_review);

        adapter = new Bestselling_adapter(Package_name.this, model);
        LinearLayoutManager layoutManager = new LinearLayoutManager(Package_name.this, LinearLayoutManager.HORIZONTAL, true);
        layoutManager.setReverseLayout(false);
        recycler_bestselling.setLayoutManager(layoutManager);
        recycler_bestselling.setAdapter(adapter);
        indicates.attachToRecyclerView(recycler_bestselling);


        trade_adapter = new Tranding_adapter(Package_name.this, trading_model);
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(Package_name.this, LinearLayoutManager.HORIZONTAL, true);
        layoutManager1.setReverseLayout(false);
        recycler_trading.setLayoutManager(layoutManager1);
        recycler_trading.setAdapter(trade_adapter);

    }

    private void Allocatememory() {
        indicates = findViewById(R.id.recyclerview_pager_indicator);
        indicates1 = findViewById(R.id.recyclerview_pager_indicator1);

        progress = (ProgressBar) findViewById(R.id.progress);
        cardview_review = (CardView) findViewById(R.id.cardview_review);
        cardview_bestsell = (CardView) findViewById(R.id.cardview_bestsell);
        cardview_trading = (CardView) findViewById(R.id.cardview_trading);
        //  img_location = (ImageView) findViewById(R.id.img_location);
        lv_package_view = (LinearLayout) findViewById(R.id.lv_package_view);
        btn_bestsell_viewall = (Button) findViewById(R.id.btn_bestsell_viewall);
        btn_trading_vall = (Button) findViewById(R.id.btn_trading_vall);
        btn_customize_package = (Button) findViewById(R.id.btn_customize_package);
        btn_review_viewall = (Button) findViewById(R.id.btn_review_viewall);

        tv_review = (TextView) findViewById(R.id.tv_review);
        tv_reviewmore = (TextView) findViewById(R.id.tv_reviewmore);
        tv_more = (TextView) findViewById(R.id.tv_more);

        txt_msg = (TextView) findViewById(R.id.txt_msg);
        txt_msg_best = (TextView) findViewById(R.id.txt_msg_best);
        txt_msg_review = (TextView) findViewById(R.id.txt_msg_review);
        tv_review_text = (TextView) findViewById(R.id.tv_review_text);
        tv_overview = (TextView) findViewById(R.id.tv_overview);
        tv_bestsell = (TextView) findViewById(R.id.tv_bestsell);
        tv_tranding = (TextView) findViewById(R.id.tv_tranding);

        mPager = (ViewPager) findViewById(R.id.pager_package);
        indicator = (CircleIndicator) findViewById(R.id.indicator_pack);

        btn_review_viewall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(Package_name.this, Review_viewAll.class);
                        intent.putExtra("location_id", "" + location_id);
                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);

                    }
                }, 50);


            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }

    private void Btn_all_clicklistener() {


        btn_bestsell_viewall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("viewalllllllllll", "");

                Intent i = new Intent(Package_name.this, BestSelling_viewAll.class);
                i.putExtra("cat_id", cat_id);
                i.putExtra("location_id", location_id);
                startActivity(i);
            }
        });
        tv_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tv_reviewmore.getVisibility() == View.GONE) {
                    tv_reviewmore.setVisibility(View.VISIBLE);
                    tv_review.setVisibility(View.GONE);
                    tv_more.setText("READ LESS");
                } else {
                    tv_reviewmore.setVisibility(View.GONE);
                    tv_review.setVisibility(View.VISIBLE);
                    tv_more.setText("READ MORE");
                }
                // tv_reviewmore.setVisibility(View.VISIBLE);
                //tv_review.setVisibility(View.GONE);
            }
        });

        btn_trading_vall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent i = new Intent(Package_name.this, BestSelling_viewAll.class);
                        i.putExtra("location_id", location_id);
                        startActivity(i);
                        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);

                    }
                }, 50);


            }
        });
        btn_customize_package.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent i = new Intent(Package_name.this, Customize_package_activity.class);
                        startActivity(i);
                        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);

                    }
                }, 50);

            }
        });
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
            Intent i = new Intent(Package_name.this, Request_callBack.class);
            startActivity(i);
            Toast.makeText(getApplicationContext(), "Request", Toast.LENGTH_LONG).show();
        } else if (item.getItemId() == R.id.send_qury) {
            Intent i = new Intent(Package_name.this, Send_query.class);
            startActivity(i);

            Toast.makeText(getApplicationContext(), "sendquery", Toast.LENGTH_LONG).show();
        } else if (item.getItemId() == R.id.help) {
            Toast.makeText(getApplicationContext(), "Item 3 Selected", Toast.LENGTH_LONG).show();
        }

        return super.onOptionsItemSelected(item);
    }


}
