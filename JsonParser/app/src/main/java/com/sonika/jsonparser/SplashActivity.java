package com.sonika.jsonparser;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 3000;
    SharedPreferences sm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        sm = getSharedPreferences("USER_LOGIN", 0);
        final SharedPreferences.Editor editor = sm.edit();


        new Handler().postDelayed(new Runnable() {


            @Override
            public void run() {

                if (sm.contains("email")&&sm.contains("password")){
                    Intent i = new Intent(SplashActivity.this, FooditemsActivity.class);
                    startActivity(i);
                }else {

                    // This method will be executed once the timer is over
                    // Start your app main activity
                    Intent i = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(i);
                }
                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}
