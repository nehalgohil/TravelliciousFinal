package com.dolphinwebsolution.travellcious.Fragment;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.dolphinwebsolution.travellcious.Activity.Package_name;
import com.dolphinwebsolution.travellcious.Activity.ViewAll_Location;
import com.dolphinwebsolution.travellcious.Adapter.Category_adapter;
import com.dolphinwebsolution.travellcious.Adapter.Destination_adapter;
import com.dolphinwebsolution.travellcious.Adapter.Honeymoon_adapter;
import com.dolphinwebsolution.travellcious.Adapter.Vacation_adapter;
import com.dolphinwebsolution.travellcious.Model.Category_model;
import com.dolphinwebsolution.travellcious.Model.Destionation_model;
import com.dolphinwebsolution.travellcious.Model.Honeymoon_model;
import com.dolphinwebsolution.travellcious.Model.Vacation_model;
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
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;


public class Home_frag extends Fragment implements ViewPager.OnPageChangeListener {
    RecyclerView recycle_vac, honey_recycler, desti_recycler;
    private List<Destionation_model> models = new ArrayList<Destionation_model>();
    private List<Vacation_model> vac_model = new ArrayList<Vacation_model>();
    private List<Honeymoon_model> honey_model = new ArrayList<Honeymoon_model>();
    private List<Destionation_model> desti_model = new ArrayList<Destionation_model>();
    Vacation_adapter vac_adapter;
    Honeymoon_adapter honey_adapter;
    Destination_adapter destination_adapter;
    CardView cardview_honeymoon, cardview_vaction, cardview_destination;
    private static String type = "image_only", category = "suggestion";
    private SlidingImage_Adapter adapter;
    private List<sliderimage_model> sliderimage_models = new ArrayList<sliderimage_model>();
    private String URL_HOMEPAGE;
    View v;
    private List<Category_model> category_model = new ArrayList<Category_model>();
    private Category_adapter category_adapter;
    RecyclerView recycle_category;
    ProgressBar pd1, pd2;

    int currentPage = 0;
    Timer timer;
    final long DELAY_MS = 800;//delay in milliseconds before task is to be executed
    final long PERIOD_MS = 4000; // time in milliseconds between successive task executions.
    private ViewPager mPager;
    private CircleIndicator indicator;

    Button btn_desti_viewall, btn_honey_viewAll, btn_vac_viewAll, btn_vmore, btn_vmoretwo;
    TextView txtdesti;
    TextView txt_vac_budget, txt_vac_title;
    TextView txthoney, txtvac, txttravel, txt_text, txt_detailtwo, txt_know, txt_veri_agent,
            txt_detail, txt_know_more, txt_msg_honey, txt_msg_vaction, txt_msg_desti;
    ImageView img_vacation;
    LinearLayout lv_homepage;

    public Home_frag() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_home_frag, container, false);
        Allocatememory(v);
        //http://travel.demoproject.info/api/get_homepage_details.php
        URL_HOMEPAGE = Main_Url.sortUrl + "get_homepage_details.php";

        set_All_FontStyle();
        Attach_RecyclerView();
        Btn_Click_listner();
        //  getMainBannerImages();
        mPager.addOnPageChangeListener(this);
        startAutoScrollViewPager();
        if (CheckNetwork.isNetworkAvailable(getActivity())) {
            Call_Homepage_API();
        } else {

            Toast.makeText(getActivity(), "Please Check your Internet Connection", Toast.LENGTH_SHORT).show();
          /*  AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
            alertDialog.setTitle("Info");
            alertDialog.setMessage("Internet not available, Cross check your internet connectivity and try again");
            alertDialog.setIcon(android.R.drawable.ic_dialog_alert);
            alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    getActivity().finish();
                }
            });
            alertDialog.show();*/
        }


        return v;
    }

    private void Call_Homepage_API() {
        pd2.setVisibility(View.VISIBLE);
        AssetManager am = getActivity().getApplicationContext().getAssets();
        final Typeface medium = Typeface.createFromAsset(am,
                String.format(Locale.getDefault(), "Lato-Medium.ttf"));


        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_HOMEPAGE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        sliderimage_models.clear();
                        Log.e("response_banner", "" + response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            Log.e("response_homepage", "" + jsonObject);
                            pd2.setVisibility(View.GONE);
                            lv_homepage.setVisibility(View.VISIBLE);
                            String status = jsonObject.getString("status");
                            Log.e("status_home", "" + status);
                            String message = jsonObject.getString("message");

                            if (status.equals("Success") == true) {
                                Log.e("message", "" + message);
                                String data = jsonObject.getString("data");
                                Log.e("data", "" + data);

                                JSONObject data_obj = new JSONObject(data);
                                Log.e("data_obj", "" + data_obj);
                                sliderimage_models.clear();
                                category_model.clear();
                                vac_model.clear();
                                honey_model.clear();
                                desti_model.clear();
                                String banners = data_obj.getString("banners");
                                Log.e("banners", "" + banners);


                                JSONArray banners_arr = new JSONArray(banners);
                                pd2.setVisibility(View.GONE);

                                Log.e("banners", "" + banners_arr);
                                if (banners_arr != null && banners_arr.isNull(0) != true) {
                                    for (int i = 0; i < banners_arr.length(); i++) {
                                        try {
                                            JSONObject object = banners_arr.getJSONObject(i);
                                            sliderimage_model imageModel = new sliderimage_model(object.getString("location_id"), object.getString("location_image"));
                                            sliderimage_models.add(imageModel);
                                            Log.e("schedule", "" + sliderimage_models.size());

                                            mPager.setAdapter(new SlidingImage_Adapter(getActivity(), sliderimage_models));
                                            indicator.setViewPager(mPager);
                                        } catch (Exception e) {
                                            Log.e("Exception", "" + e);
                                        } finally {
                                        }
                                    }
                                } else {
                                    Toast.makeText(getActivity(), "array_null", Toast.LENGTH_SHORT).show();
                                }

                                String category_icon_title = data_obj.getString("category_icon_title");
                                Log.e("category_icon_title", "" + category_icon_title);

                                JSONArray category_arr = new JSONArray(category_icon_title);
                                Log.e("category_arr", "" + category_arr);
                                if (category_arr != null && category_arr.isNull(0) != true) {
                                    for (int i = 0; i < category_arr.length(); i++) {
                                        try {
                                            JSONObject cat_object = category_arr.getJSONObject(i);
                                            Log.e("object", "" + cat_object);
                                            Log.e("id", "" + cat_object.getString("category_id"));
                                            Log.e("url", "" + cat_object.getString("category_icon"));
                                            Category_model model = new Category_model(cat_object.getString("category_id"),
                                                    cat_object.getString("category_icon"),
                                                    cat_object.getString("category_name"));
                                            category_model.add(model);
                                        } catch (Exception e) {
                                            Log.e("Exception", "" + e);
                                        } finally {
                                            category_adapter.notifyItemChanged(i);

                                        }
                                    }
                                } else {
                                    Toast.makeText(getActivity(), "array_null", Toast.LENGTH_SHORT).show();
                                }
                                String Destination_for_you_title = data_obj.getString("Destination_for_you_title");
                                Log.e("Destination_title", "" + Destination_for_you_title);
                                txtdesti.setText(Destination_for_you_title);

                                String Destination_for_you = data_obj.getString("Destination_for_you");
                                Log.e("Destination_for_you", "" + Destination_for_you);
                                JSONArray desti_arr = new JSONArray(Destination_for_you);
                                Log.e("desti_arr", "" + desti_arr);
                                if (desti_arr != null && desti_arr.isNull(0) != true) {
                                    for (int i = 0; i < 2; i++) {
                                        try {
                                            cardview_destination.setVisibility(View.GONE);
                                            JSONObject desti_object = desti_arr.getJSONObject(i);
                                            Log.e("desti_object", "" + desti_object);
                                            Log.e("id", "" + desti_object.getString("location_id"));
                                            Log.e("url", "" + desti_object.getString("location_image"));
                                            Destionation_model dmodel = new Destionation_model(desti_object.getString("location_id"),
                                                    desti_object.getString("location_image"), desti_object.getString("location_title"),
                                                    desti_object.getString("budget"), desti_object.getString("location_description"));
                                            desti_model.add(dmodel);
                                        } catch (Exception e) {
                                            Log.e("Exception", "" + e);
                                        } finally {
                                            destination_adapter.notifyItemChanged(i);

                                        }
                                    }
                                } else {
                                    txt_msg_desti.setTypeface(medium);

                                    txt_msg_desti.setText("Package not Available");
                                    cardview_destination.setVisibility(View.VISIBLE);
                                    Toast.makeText(getActivity(), "Destination_array_null", Toast.LENGTH_SHORT).show();
                                }

                                String Vacation_Destination_title = data_obj.getString("Vacation_Destination_title");
                                Log.e("Vacation_Destination", "" + Vacation_Destination_title);
                                txtvac.setText(Vacation_Destination_title);
                                String Top_Vacation_Destination = data_obj.getString("Top_Vacation_Destination");
                                Log.e("Top_Vacation_Destin", "" + Top_Vacation_Destination);
                                JSONArray vacation_arr = new JSONArray(Top_Vacation_Destination);
                                Log.e("vacation_arr", "" + vacation_arr);
                                if (vacation_arr != null && vacation_arr.isNull(0) != true) {
                                    for (int i = 0; i < vacation_arr.length(); i++) {
                                        try {
                                            cardview_vaction.setVisibility(View.GONE);
                                            JSONObject vac_object = vacation_arr.getJSONObject(i);
                                            Log.e("object", "" + vac_object);
                                            Log.e("id", "" + vac_object.getString("location_id"));
                                            Log.e("url", "" + vac_object.getString("location_image"));
                                            Vacation_model vmodel = new Vacation_model(vac_object.getString("location_id"),
                                                    vac_object.getString("location_image"), vac_object.getString("location_title"),
                                                    vac_object.getString("budget"));


                                            vac_model.add(vmodel);
                                        } catch (Exception e) {
                                            Log.e("Exception", "" + e);
                                        } finally {
                                            vac_adapter.notifyItemChanged(i);

                                        }
                                    }
                                } else {
                                    txt_msg_vaction.setTypeface(medium);

                                    txt_msg_vaction.setText("Package not Available");
                                    cardview_vaction.setVisibility(View.VISIBLE);
                                    Toast.makeText(getActivity(), "Vacation_array_null", Toast.LENGTH_SHORT).show();
                                }


                                String Honeymoon_Destination_title = data_obj.getString("Honeymoon_Destination_title");
                                Log.e("Honeymoon_Dest_title", "" + Honeymoon_Destination_title);
                                txthoney.setText(Honeymoon_Destination_title);
                                String Top_Honeymoon_Destination = data_obj.getString("Top_Honeymoon_Destination");
                                Log.e("Top_Honeymoon_Dest", "" + Top_Honeymoon_Destination);

                                JSONArray honeymoon_arr = new JSONArray(Top_Honeymoon_Destination);
                                Log.e("honeymoon_arr", "" + honeymoon_arr);

                                if (honeymoon_arr != null && honeymoon_arr.isNull(0) != true) {
                                    for (int i = 0; i < honeymoon_arr.length(); i++) {
                                        try {
                                            cardview_honeymoon.setVisibility(View.GONE);

                                            JSONObject honey_object = vacation_arr.getJSONObject(i);
                                            Log.e("object", "" + honey_object);
                                            Log.e("id", "" + honey_object.getString("location_id"));
                                            Log.e("url", "" + honey_object.getString("location_image"));
                                            Honeymoon_model hmodel = new Honeymoon_model(honey_object.getString("location_id"),
                                                    honey_object.getString("location_image"), honey_object.getString("location_title"),
                                                    honey_object.getString("budget"));
                                            honey_model.add(hmodel);
                                        } catch (Exception e) {
                                            Log.e("Exception", "" + e);
                                        } finally {
                                            honey_adapter.notifyItemChanged(i);

                                        }
                                    }
                                } else {
                                    txt_msg_honey.setTypeface(medium);
                                    txt_msg_honey.setText("Package not available");
                                    cardview_honeymoon.setVisibility(View.VISIBLE);

                                    Toast.makeText(getActivity(), "array_null", Toast.LENGTH_SHORT).show();
                                }

                            }else{
                                Toast.makeText(getActivity(), "message==" + message, Toast.LENGTH_SHORT).show();
                                Log.e("response_msggg_toast", "" + message);

                            }


                        } catch (JSONException e) {

                        }
                    }


                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), "not get Response"+error, Toast.LENGTH_SHORT).show();
                            pd2.setVisibility(View.VISIBLE);
                    }

                });
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
        requestQueue.add(stringRequest);


    }

    private void Btn_Click_listner() {
        btn_desti_viewall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent i = new Intent(getActivity(), ViewAll_Location.class);
                        startActivity(i);
                        getActivity().overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);

                    }
                }, 50);

            }
        });
        btn_vac_viewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        Intent i = new Intent(getActivity(), ViewAll_Location.class);
                        startActivity(i);
                        getActivity().overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                    }
                }, 50);

            }
        });
        btn_honey_viewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        Intent i = new Intent(getActivity(), ViewAll_Location.class);
                        startActivity(i);
                        getActivity().overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                    }
                }, 50);


            }
        });

    }

    private void Attach_RecyclerView() {
        recycle_vac = (RecyclerView) v.findViewById(R.id.vac_recycler);
        honey_recycler = (RecyclerView) v.findViewById(R.id.honey_recycler);
        desti_recycler = (RecyclerView) v.findViewById(R.id.desti_recycler);

        vac_adapter = new Vacation_adapter(getContext(), vac_model);
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, true);
        layoutManager1.setReverseLayout(false);
        recycle_vac.setLayoutManager(layoutManager1);
        recycle_vac.setAdapter(vac_adapter);

        honey_adapter = new Honeymoon_adapter(getContext(), honey_model);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, true);
        layoutManager2.setReverseLayout(false);
        honey_recycler.setLayoutManager(layoutManager2);
        honey_recycler.setAdapter(honey_adapter);

        destination_adapter = new Destination_adapter(getContext(), desti_model);
        LinearLayoutManager layoutManager3 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, true);
        layoutManager3.setReverseLayout(false);
        desti_recycler.setLayoutManager(layoutManager3);
        desti_recycler.setAdapter(destination_adapter);

        category_adapter = new Category_adapter(getActivity(), category_model);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, true);
        layoutManager.setReverseLayout(false);
        recycle_category.setLayoutManager(layoutManager);
        recycle_category.setAdapter(category_adapter);

        mPager = (ViewPager) v.findViewById(R.id.pager);
        indicator = (CircleIndicator) v.findViewById(R.id.indicator);


    }

    private void set_All_FontStyle() {
        AssetManager am = getActivity().getApplicationContext().getAssets();
        Typeface typeface = Typeface.createFromAsset(am,
                String.format(Locale.getDefault(), "Lato-Bold.ttf"));
        Typeface tf = Typeface.createFromAsset(am,
                String.format(Locale.getDefault(), "Lato-Medium.ttf"));
        txt_msg_honey.setTypeface(typeface);
        txt_msg_vaction.setTypeface(typeface);
         txtdesti.setTypeface(typeface);
        txthoney.setTypeface(typeface);
        txttravel.setTypeface(typeface);
        txtvac.setTypeface(typeface);
        btn_desti_viewall.setTypeface(typeface);
        btn_honey_viewAll.setTypeface(typeface);
        btn_vac_viewAll.setTypeface(typeface);
        //btn_vmore.setTypeface(typeface);
        // btn_vmoretwo.setTypeface(typeface);
        txt_text.setTypeface(typeface);
        txt_veri_agent.setTypeface(typeface);
        txt_know.setTypeface(tf);
        txt_detail.setTypeface(tf);
        txt_detailtwo.setTypeface(tf);
        txt_know_more.setTypeface(tf);

    }

    private void Allocatememory(View v) {


        cardview_destination = (CardView) v.findViewById(R.id.cardview_destination);
        cardview_honeymoon = (CardView) v.findViewById(R.id.cardview_honeymoon);
        cardview_vaction = (CardView) v.findViewById(R.id.cardview_vaction);
        txt_msg_honey = (TextView) v.findViewById(R.id.txt_msg_honey);
        txt_msg_vaction = (TextView) v.findViewById(R.id.txt_msg_vaction);
        txt_msg_desti = (TextView) v.findViewById(R.id.txt_msg_desti);


        lv_homepage = (LinearLayout) v.findViewById(R.id.lv_homepage);
        recycle_category = (RecyclerView) v.findViewById(R.id.cat_recycler);
        pd2 = (ProgressBar) v.findViewById(R.id.pd2);
        btn_desti_viewall = (Button) v.findViewById(R.id.btn_desti_viewall);
        txthoney = (TextView) v.findViewById(R.id.txthoney);
        txtvac = (TextView) v.findViewById(R.id.txtvac);
        txttravel = (TextView) v.findViewById(R.id.txt_travel);
        txtdesti = (TextView) v.findViewById(R.id.txtdesti);
        btn_honey_viewAll = (Button) v.findViewById(R.id.btn_honey_viewAll);

        btn_vac_viewAll = (Button) v.findViewById(R.id.btn_vac_viewAll);
        img_vacation = (ImageView) v.findViewById(R.id.img_vacation);


        txt_vac_budget = (TextView) v.findViewById(R.id.txt_vac_budget);
        txt_vac_title = (TextView) v.findViewById(R.id.txt_vac_title);

        txt_text = (TextView) v.findViewById(R.id.txt_text);
        txt_detailtwo = (TextView) v.findViewById(R.id.txt_detailtwo);
        txt_know = (TextView) v.findViewById(R.id.txt_know);
        txt_veri_agent = (TextView) v.findViewById(R.id.txt_veri_agent);
        txt_detail = (TextView) v.findViewById(R.id.txt_detail);
        txt_know_more = (TextView) v.findViewById(R.id.txt_know_more);


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

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


}



