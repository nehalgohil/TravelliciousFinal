package com.dolphinwebsolution.travellcious.Activity;

import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.dolphinwebsolution.travellcious.R;

public class MyAccount extends AppCompatActivity {

    LinearLayout ll_my_profile,ll_change_password,ll_logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);
        setTitle("My Account");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ll_my_profile = (LinearLayout)findViewById(R.id.my_profile);
        ll_change_password = (LinearLayout)findViewById(R.id.changepassword);
        ll_logout = (LinearLayout)findViewById(R.id.logout);

        ll_my_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent i = new Intent(MyAccount.this,MyProfile.class);
                        startActivity(i);
                        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);

                    }
                }, 50);
            }
        });

        ll_change_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent i = new Intent(MyAccount.this,ChangePassword.class);
                        startActivity(i);
                        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);

                    }
                }, 50);
            }
        });

        ll_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Snackbar snackbar = Snackbar.make(ll_logout, "Logout", Snackbar.LENGTH_LONG);
                snackbar.show();

            }
        });
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
