package com.dolphinwebsolution.travellcious.Fragment;


import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dolphinwebsolution.travellcious.Activity.Contact_Us;
import com.dolphinwebsolution.travellcious.Activity.Customize_package_activity;
import com.dolphinwebsolution.travellcious.Activity.Customize_quote_second;
import com.dolphinwebsolution.travellcious.Activity.Home;
import com.dolphinwebsolution.travellcious.Activity.MyAccount;
import com.dolphinwebsolution.travellcious.Activity.My_Wishlist;
import com.dolphinwebsolution.travellcious.Activity.Request_callBack;
import com.dolphinwebsolution.travellcious.R;


public class Menu_frag extends Fragment {

    View v;
    LinearLayout ll_login, ll_signup;
    private static int TIME_OUT = 50;
    LinearLayout ll_myaccount,lv_customize_search, ll_contactus, ll_need_help, ll_rateus, ll_wishlist, ll_request_callback;

    public Menu_frag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_menu_frag, container, false);

        ll_login = (LinearLayout) v.findViewById(R.id.ll_login);
        ll_signup = (LinearLayout) v.findViewById(R.id.ll_signup);

        ll_myaccount = (LinearLayout) v.findViewById(R.id.my_account);
        ll_wishlist = (LinearLayout) v.findViewById(R.id.my_wishlist);

        ll_rateus = (LinearLayout) v.findViewById(R.id.rateus);
        ll_need_help = (LinearLayout) v.findViewById(R.id.nhcallus);
        ll_contactus = (LinearLayout) v.findViewById(R.id.contactus);
        ll_request_callback = (LinearLayout) v.findViewById(R.id.request_callback);
        lv_customize_search = (LinearLayout) v.findViewById(R.id.lv_customize_search);

        lv_customize_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent i = new Intent(getActivity(), Customize_package_activity.class);
                        startActivity(i);
                        getActivity().overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);

                    }
                }, TIME_OUT);
            }
        });
        ll_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getFragmentManager().beginTransaction()
                                .setCustomAnimations(R.anim.slide_from_right,
                                        0,0,R.anim.slide_from_right)
                                .replace(R.id.frame_layout, new Login()
                                ).addToBackStack(null).commit();
                    }
                }, TIME_OUT);
            }
        });

        ll_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getFragmentManager().beginTransaction()
                                .setCustomAnimations(R.anim.slide_from_right,
                                        0,0,R.anim.slide_from_right)
                                .replace(R.id.frame_layout, new SignUp_frag()
                                ).addToBackStack(null).commit();
                    }
                }, TIME_OUT);
            }

        });

        ll_myaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent i = new Intent(getActivity(), MyAccount.class);
                        startActivity(i);
                      getActivity().overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);

                    }
                }, TIME_OUT);
            }
        });


        ll_need_help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        Intent i = new Intent(Intent.ACTION_DIAL);
                        i.setData(Uri.parse("tel:1234567890"));
                        startActivity(i);
                        getActivity().overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);

                    }
                }, TIME_OUT);
            }
        });

        ll_rateus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext())
                        .setTitle("Rate this application")
                        .setMessage("If you love our app, please take a moment to rate it.")
                        .setPositiveButton("RATE", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (getContext() != null) {
                                    ////////////////////////////////
                                    Uri uri = Uri.parse("market://details?id=" + getContext().getPackageName());
                                    Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                                    // To count with Play market backstack, After pressing back button,
                                    // to taken back to our application, we need to add following flags to intent.
                                    goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                                            Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET |
                                            Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                                    try {
                                        getContext().startActivity(goToMarket);
                                    } catch (ActivityNotFoundException e) {
                                        getContext().startActivity(new Intent(Intent.ACTION_VIEW,
                                                Uri.parse("http://play.google.com/store/apps/details?id=" + getContext().getPackageName())));
                                    }
                                }
                            }
                        })
                        .setNegativeButton("CANCEL", null);
                builder.show();
            }
        });

        ll_contactus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent i = new Intent(getContext(), Contact_Us.class);
                        startActivity(i);
                        getActivity().overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);

                    }
                }, TIME_OUT);
            }
        });

        ll_wishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent i = new Intent(getContext(), My_Wishlist.class);
                        startActivity(i);
                        getActivity().overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);

                    }
                }, TIME_OUT);

            }
        });

        ll_request_callback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent i = new Intent(getContext(), Request_callBack.class);
                        startActivity(i);
                        getActivity().overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);

                    }
                }, TIME_OUT);
                      }
        });

        lv_customize_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent i = new Intent(getContext(), Customize_package_activity.class);
                        startActivity(i);
                        getActivity().overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);

                    }
                }, TIME_OUT);
            }
        });

        return v;
    }

}
