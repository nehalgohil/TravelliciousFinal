package com.dolphinwebsolution.travellcious.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dolphinwebsolution.travellcious.Adapter.Viewall_bestselling_adapter;
import com.dolphinwebsolution.travellcious.Model.Bestselling_Package_model;
import com.dolphinwebsolution.travellcious.R;
import com.dolphinwebsolution.travellcious.utils.CheckNetwork;
import com.dolphinwebsolution.travellcious.utils.Main_Url;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class BestSelling_viewAll extends AppCompatActivity {
    RecyclerView recycler_bestselling_vall;
    private String URL,cat_id,location_id ;
    private List<Bestselling_Package_model> model = new ArrayList<Bestselling_Package_model>();
    Viewall_bestselling_adapter adapter;
    ProgressBar pd;
    String category="vacation";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_best_selling_view_all);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        cat_id=getIntent().getStringExtra("cat_id");
        location_id=getIntent().getStringExtra("location_id");
        Log.e("catid",""+cat_id);
        Log.e("location",""+location_id);

        URL = Main_Url.sortUrl + "get_package.php?best_selling="+cat_id +"&location_id="+location_id;

        pd=(ProgressBar)findViewById(R.id.progressBar);
        recycler_bestselling_vall=(RecyclerView)findViewById(R.id.recycler_bestselling_vall);
        adapter = new Viewall_bestselling_adapter(BestSelling_viewAll.this, model);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(BestSelling_viewAll.this, 1);
        recycler_bestselling_vall.setLayoutManager(mLayoutManager);
        recycler_bestselling_vall.setItemAnimator(new DefaultItemAnimator());
        recycler_bestselling_vall.setAdapter(adapter);

        // ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(ViewAll_destination.this, R.dimen.item_offset);
        //recycle_destination.addItemDecoration(itemDecoration);
        if (CheckNetwork.isNetworkAvailable(BestSelling_viewAll.this)){
            // productlist.clear();
            callgetPackages();
        }else{
        }

    }

    private void callgetPackages() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.e("response", "" + response);
                        model.clear();
                        //   pd.setVisibility(View.GONE);

                        JSONArray arr= null;
                        try {
                            arr = new JSONArray(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.e("arr", "" + arr);
                        for (int i = 0; i < arr.length(); i++) {
                            try {
                                JSONObject object = arr.getJSONObject(i);
                                Log.e("object", "" + object);
                                Log.e("object_packagetitle", "" +  object.getString("package_title"));
                                String days=null,night = null;
                                String duration =object.getString("Duration");
                                JSONArray day = new JSONArray(duration);
                                Log.e("day", "" + day);

                                for(int j=0;j<day.length();j++)
                                {
                                    JSONObject obj=day.getJSONObject(j);
                                    days=obj.getString("Days");
                                    Log.e("dayss", "" + days);

                                    night=obj.getString("Nights");

                                    // loop and add it to array or arraylist
                                }
                                String pack_img=object.getString("package_images");
                                String image=null;
                                JSONArray imgarr=new JSONArray(pack_img);
                                for (int k=0;k<imgarr.length();k++)
                                {
                                    JSONObject jsonObject=imgarr.getJSONObject(k);
                                    image=jsonObject.getString("image");
                                }
                          /*      Bestselling_Package_model best_model = new Bestselling_Package_model(
                                        object.getString("package_title"),
                                        object.getString("description"),days,
                                        object.getString("budget"),
                                        object.getString("best_time_to_visit"),night,
                                        object.getString("category_id"),
                                        object.getString("location_id"),
                                        object.getString("features"),
                                        object.getString("Category_Name"),
                                        object.getString("Location_Name"),
                                        image,object.getString("id"));
*/
                               // model.add(best_model);

                            } catch (Exception e) {
                                Log.e("Exception", "" + e);
                            } finally {
                                adapter.notifyItemChanged(i);

                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //  Toast.makeText(getActivity(), "not get Response" + error, Toast.LENGTH_SHORT).show();
                    }
                }) /*{
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> KeyValuePair = new HashMap<String, String>();
                Log.e("idddddddddd", "" + id);
                KeyValuePair.put("id", id);
                return KeyValuePair;
            }
        }*/;

        RequestQueue requestQueue = Volley.newRequestQueue(BestSelling_viewAll.this);
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
        }
        else if(item.getItemId()== R.id.request_callback)
        {
            Intent i=new Intent(BestSelling_viewAll.this,Request_callBack.class);
            startActivity(i);
            Toast.makeText(getApplicationContext(),"Request",Toast.LENGTH_LONG).show();
        }
        else if(item.getItemId() == R.id.send_qury)
        {
            Intent i=new Intent(BestSelling_viewAll.this,Send_query.class);
            startActivity(i);

            Toast.makeText(getApplicationContext(),"sendquery",Toast.LENGTH_LONG).show();
        }
        else if(item.getItemId() == R.id.help)
        {
            Toast.makeText(getApplicationContext(),"Item 3 Selected",Toast.LENGTH_LONG).show();
        }

        return super.onOptionsItemSelected(item);
    }

}
