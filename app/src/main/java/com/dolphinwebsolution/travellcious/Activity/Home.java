package com.dolphinwebsolution.travellcious.Activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import android.support.annotation.IdRes;
import android.support.v4.app.FragmentManager;
import android.view.Gravity;
import android.view.View;

import com.dolphinwebsolution.travellcious.Fragment.Home_frag;
import com.dolphinwebsolution.travellcious.Fragment.Menu_frag;
import com.dolphinwebsolution.travellcious.Fragment.Notification_frag;
import com.dolphinwebsolution.travellcious.Fragment.Search_frag;
import com.dolphinwebsolution.travellcious.Fragment.SignUp_frag;
import com.dolphinwebsolution.travellcious.R;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.BottomBarTab;
import com.roughike.bottombar.OnTabReselectListener;
import com.roughike.bottombar.OnTabSelectListener;

public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        getSupportActionBar().hide();
       /* getApplicationContext().setTheme(android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
*/
        BottomBar bottomBar = (BottomBar) findViewById(R.id.bottomBar);
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                if (tabId == R.id.home1) {
                    getSupportFragmentManager().beginTransaction()
                            .setCustomAnimations(R.anim.slide_from_right,
                                    0,0,R.anim.slide_from_right)
                            .replace(R.id.frame_layout, new Home_frag()
                            ).addToBackStack(null).commit();

                } else if (tabId == R.id.search) {
                    getSupportFragmentManager().beginTransaction()
                            .setCustomAnimations(R.anim.slide_from_right,
                                    0,0,R.anim.slide_from_right)
                            .replace(R.id.frame_layout, new Search_frag()
                            ).addToBackStack(null).commit();

                } else if (tabId == R.id.login) {
                /*    FragmentManager manager = getSupportFragmentManager();
                    manager.beginTransaction().replace(R.id.frame_layout, new SignUp_frag()).commit();
                */    getSupportFragmentManager().beginTransaction()
                            .setCustomAnimations(R.anim.slide_from_right,
                                    0,0,R.anim.slide_from_right)
                            .replace(R.id.frame_layout, new SignUp_frag()
                            ).addToBackStack(null).commit();

                } else if (tabId == R.id.notifi) {
                   /* FragmentManager manager = getSupportFragmentManager();
                    manager.beginTransaction().replace(R.id.frame_layout, new Notification_frag()).commit();
                   */ getSupportFragmentManager().beginTransaction()
                            .setCustomAnimations(R.anim.slide_from_right,
                                    0,0,R.anim.slide_from_right)
                            .replace(R.id.frame_layout, new Notification_frag()
                            ).addToBackStack(null).commit();

                } else if (tabId == R.id.menu) {
                  /*  FragmentManager manager = getSupportFragmentManager();
                    manager.beginTransaction().replace(R.id.frame_layout, new Menu_frag()).commit();
                  */  getSupportFragmentManager().beginTransaction()
                            .setCustomAnimations(R.anim.slide_from_right,
                                    0,0,R.anim.slide_from_right)
                            .replace(R.id.frame_layout, new Menu_frag()
                            ).addToBackStack(null).commit();

                }
            }
        });

        bottomBar.setOnTabReselectListener(new OnTabReselectListener() {
            @Override
            public void onTabReSelected(int tabId) {
                if (tabId == R.id.home1) {
                   /* FragmentManager manager = getSupportFragmentManager();
                    manager.beginTransaction().replace(R.id.frame_layout, new Home_frag()).commit();
                   */     getSupportFragmentManager().beginTransaction()
                            .setCustomAnimations(R.anim.slide_from_left,
                                    0,0,R.anim.slide_from_left)
                            .replace(R.id.frame_layout, new Home_frag()
                            ).addToBackStack(null).commit();
                } else if (tabId == R.id.search) {
                   /* FragmentManager manager = getSupportFragmentManager();
                    manager.beginTransaction().replace(R.id.frame_layout, new Search_frag()).commit();
                   */ getSupportFragmentManager().beginTransaction()
                            .setCustomAnimations(R.anim.slide_from_left,
                                    0,0,R.anim.slide_from_left)
                            .replace(R.id.frame_layout, new Search_frag()
                            ).addToBackStack(null).commit();

                } else if (tabId == R.id.login) {
                /*    FragmentManager manager = getSupportFragmentManager();
                    manager.beginTransaction().replace(R.id.frame_layout, new SignUp_frag()).commit();
                */    getSupportFragmentManager().beginTransaction()
                            .setCustomAnimations(R.anim.slide_from_left,
                                    0,0,R.anim.slide_from_left)
                            .replace(R.id.frame_layout, new SignUp_frag()
                            ).addToBackStack(null).commit();

                } else if (tabId == R.id.notifi) {
                  /*  FragmentManager manager = getSupportFragmentManager();
                    manager.beginTransaction().replace(R.id.frame_layout, new Notification_frag()).commit();
                  */  getSupportFragmentManager().beginTransaction()
                            .setCustomAnimations(R.anim.slide_from_left,
                                    0,0,R.anim.slide_from_left)
                            .replace(R.id.frame_layout, new Notification_frag()
                            ).addToBackStack(null).commit();

                } else if (tabId == R.id.menu) {
                 /*   FragmentManager manager = getSupportFragmentManager();
                    manager.beginTransaction().replace(R.id.frame_layout, new Menu_frag()).commit();
                 */   getSupportFragmentManager().beginTransaction()
                            .setCustomAnimations(R.anim.slide_from_left,
                                    0,0,R.anim.slide_from_left)
                            .replace(R.id.frame_layout, new Menu_frag()
                            ).addToBackStack(null).commit();

                }
            }
        });




        for (int i = 0; i < bottomBar.getTabCount(); i++) {
            BottomBarTab tab = bottomBar.getTabAtPosition(i);
            tab.setGravity(Gravity.CENTER);

            View icon = tab.findViewById(com.roughike.bottombar.R.id.bb_bottom_bar_icon);
            icon.setPadding(20, icon.getPaddingTop(), 20, icon.getPaddingTop());
            View title = tab.findViewById(com.roughike.bottombar.R.id.bb_bottom_bar_title);
            title.setVisibility(View.GONE);
        }

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }


  }
