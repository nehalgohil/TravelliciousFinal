package com.dolphinwebsolution.travellcious.Fragment;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dolphinwebsolution.travellcious.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignUp_frag extends Fragment {
    TextView txt_privacy,txtlogin;
    View v;
    EditText et_fname, et_lname, et_mobile, et_email, et_pass;
    LinearLayout lv_signup;

    public SignUp_frag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v= inflater.inflate(R.layout.fragment_sign_up_frag, container, false);
        txt_privacy=(TextView)v.findViewById(R.id.txt_privacy);
        txtlogin=(TextView)v.findViewById(R.id.txtlogin);
        String first = "by signing up you agree to the ";
        String next = "<font color='#03A9F4'>T & C</font>";
        String n = " and ";
        String last = "<font color='#03A9F4'>Privacy policy</font>";
        txt_privacy.setText(Html.fromHtml(first + next + n + last));

        String login = " Already have an account ? ";
        String nn = "<font color='#03A9F4'>Login here</font>";
        txtlogin.setText(Html.fromHtml(login + nn));

        et_fname = (EditText) v.findViewById(R.id.et_fname);
        et_lname = (EditText) v.findViewById(R.id.et_lname);
        et_mobile = (EditText) v.findViewById(R.id.et_mobile);
        et_email = (EditText) v.findViewById(R.id.et_email);
        et_pass = (EditText) v.findViewById(R.id.et_pass);
        lv_signup = (LinearLayout) v.findViewById(R.id.lv_signup);

        lv_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                callSignUpApi();

            }
        });

        return v;
    }

    private void callSignUpApi() {

        String fname = et_fname.getText().toString();
        String lname = et_lname.getText().toString();
         String mobile = et_mobile.getText().toString();
        String emailid = et_email.getText().toString();
        String password = et_pass.getText().toString();

        if (CheckInput() == false) {
            String url = "http://travel.demoproject.info/api/user_ragistration.php";
            Log.e("urllll_signup", "" + url);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.e("response_reg", "" + response);

                            try {

                                JSONObject jsonObject = new JSONObject(response);
                                String status = jsonObject.getString("status");
                                String message = jsonObject.getString("message");
                                String data = jsonObject.getString("data");
                                Log.e("status", "" + status);
                                Log.e("message", "" + message);
                                Log.e("status", "" + status);
                                JSONObject data_obj = new JSONObject(data);

                                if (status.equals("Success")==true) {

                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            getFragmentManager().beginTransaction()
                                                    .setCustomAnimations(R.anim.slide_from_right,
                                                            0,0,R.anim.slide_from_right)
                                                    .replace(R.id.frame_layout, new Login()
                                                    ).addToBackStack(null).commit();
                                        }
                                    }, 50);


                                  /*  FragmentManager manager = getActivity().getSupportFragmentManager();
                                    manager.beginTransaction().replace(R.id.frame_layout, new Login()).commit();
*/
                                    Toast.makeText(getActivity(), ""+message, Toast.LENGTH_SHORT).show();
                                }else {
                                    Toast.makeText(getActivity(), ""+message, Toast.LENGTH_SHORT).show();
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    HashMap<String, String> KeyValuePair = new HashMap<String, String>();
                    KeyValuePair.put("firstname", et_fname.getText().toString());
                    KeyValuePair.put("lastname", et_lname.getText().toString());
                    KeyValuePair.put("mobile", et_mobile.getText().toString());
                    KeyValuePair.put("email", et_email.getText().toString());
                    KeyValuePair.put("password", et_pass.getText().toString());

                    return KeyValuePair;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
            requestQueue.add(stringRequest);
        }

    }

    private boolean CheckInput() {
        boolean ErrorStatus = false;
        if (et_pass.length() != 6) {
            et_pass.setError("Invalid Password");
            ErrorStatus = true;
        }

        if (!et_pass.equals(et_pass)) {
            et_pass.setError("Both Password Must Be Same");
        }

        String em = et_email.getText().toString().trim();
        String emailPattern = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        if (em.matches(emailPattern)) {
           /* Toast.makeText(getContext(), "Valid email address",
                    Toast.LENGTH_SHORT).show();*/
        } else {
            Toast.makeText(getActivity(), "Invalid email address",
                    Toast.LENGTH_SHORT).show();
        }
        return ErrorStatus;
    }

}
