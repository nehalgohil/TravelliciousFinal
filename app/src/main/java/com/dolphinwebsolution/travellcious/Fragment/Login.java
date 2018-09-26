package com.dolphinwebsolution.travellcious.Fragment;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dolphinwebsolution.travellcious.Model.sliderimage_model;
import com.dolphinwebsolution.travellcious.Preference.Login_preference;
import com.dolphinwebsolution.travellcious.R;
import com.dolphinwebsolution.travellcious.utils.Main_Url;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class Login extends Fragment implements View.OnClickListener {

    EditText et_email, et_password;
    LinearLayout lv_login, lv_signup;
    View v;

    public Login() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_login, container, false);

        et_email = (EditText) v.findViewById(R.id.et_email);
        et_password = (EditText) v.findViewById(R.id.et_password);
        lv_login = (LinearLayout) v.findViewById(R.id.lv_login);
        /*btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Loginapi();

            }
        });*/

        lv_login.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View view) {


        if (view == lv_login) {
            if (et_email.getText().length() == 0) {
                Toast.makeText(getActivity(), "Please enter Email id", Toast.LENGTH_SHORT).show();
            } else if (isValidEmailAddress(et_email.getText().toString()) == false) {
                Toast.makeText(getActivity(), "Please enter valid email.", Toast.LENGTH_SHORT).show();
            } else if (et_password.getText().length() == 0) {
                Toast.makeText(getActivity(), "Please enter password", Toast.LENGTH_LONG).show();
            } else if (et_password.getText().toString().length() <= 5) {
                Toast.makeText(getActivity(), "Password must be at least 8 characters long.", Toast.LENGTH_LONG).show();
            } else {
                Loginapi();
            }
        }
    }

    private void Loginapi() {
        final String email, password;
        email = et_email.getText().toString();
        password = et_password.getText().toString();
        //  String URL = Main_Url.sortUrl + "user_login.php?email=" + email + "&password=" + password;
        final String URL = Main_Url.sortUrl + "user_login.php?";
        Log.e("trace", URL);
        final StringRequest request = new StringRequest(StringRequest.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("response", "" + response);
                try {
                    Log.e("trace_url", URL);
                    JSONObject jsonObject = new JSONObject(response);
                    String status = jsonObject.getString("status");
                    final String message = jsonObject.getString("message");
                    String data = jsonObject.getString("data");
                    Log.e("status", "" + status);
                    JSONObject data_obj = new JSONObject(data);
                    Log.e("data_obj", "" + data_obj);
                    if (status.equals("Success")==true) {
                         if (getActivity() != null) {
                            try {
                              //  Toast.makeText(getActivity(), ""+status, Toast.LENGTH_SHORT).show();

                                Log.e("data_obj", "" + data_obj);
                                Log.e("User_Id", "" + data_obj.getString("User_Id"));
                                Log.e("first_name", "" + data_obj.getString("first_name"));
                                Login_preference.setcustomer_id(getActivity(),data_obj.getString("User_Id"));
                                Login_preference.setloginflag(getActivity(),"isLogin");


                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        getFragmentManager().beginTransaction()
                                                .setCustomAnimations(R.anim.slide_from_right,
                                                        0,0,R.anim.slide_from_right)
                                                .replace(R.id.frame_layout, new Home_frag()
                                                ).addToBackStack(null).commit();

                                        Toast.makeText(getActivity(), ""+message, Toast.LENGTH_SHORT).show();

                     /*   FragmentManager manager = getActivity().getSupportFragmentManager();
                        manager.beginTransaction().replace(R.id.frame_layout, new Login()).commit();
*/
                                    }
                                }, 50);

                            } catch (Exception e) {
                                Log.e("Exception", "" + e);
                            } finally {
                            }


                        }
                    }else
                    {
                        Toast.makeText(getActivity(), "message :"+message, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("", "" + error);
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> KeyValuePair = new HashMap<String, String>();
                Log.e("email", "" + email);
                KeyValuePair.put("password", password);
                KeyValuePair.put("email", email);
                return KeyValuePair;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(request);
        requestQueue.add(request);
    }

    public boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }
}
