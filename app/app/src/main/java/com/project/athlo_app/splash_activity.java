package com.project.athlo_app;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class splash_activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Thread thread=new Thread(){
            public void run(){
                try {
                    sleep(3000);
                    startActivity(new Intent(splash_activity.this,login_activity.class));
                }catch (Exception e){


                }

            }
        };
        thread.start();
    }
}