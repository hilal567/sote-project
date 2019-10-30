package com.example.sote;


import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;

/**
 * A blood donation application by Fatma Hilali, Raymond Sagini and Sianwa Atemi
 */

public class MainActivity extends AppCompatActivity {
    ActionBar actionBar;

    private static int SPLASH_TIME_OUT = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        /* this is the code to change the color of the action/top bar */
        actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#c7251a")));
        // code to change action bar ends here


        new Handler().postDelayed(new Runnable() {

            @Override
            public void run(){

                Intent homeIntent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(homeIntent);
                finish();
            }
        },SPLASH_TIME_OUT);
    }
}

