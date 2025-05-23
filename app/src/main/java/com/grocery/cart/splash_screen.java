package com.grocery.cart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.widget.VideoView;

public class splash_screen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        VideoView videoView=findViewById(R.id.videoView);
        String vPath="android.resource://"+getPackageName()+"/raw/animatedlogo";
        Uri videoURI=Uri.parse(vPath);
        videoView.setVideoURI(videoURI);
        videoView.start();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences pref=getSharedPreferences("login",MODE_PRIVATE);
                Boolean check=pref.getBoolean("flag", false);
                Intent iNext;
                if (check){
                    iNext=new Intent(splash_screen.this, MainActivity.class);
                }
                else {
                    iNext=new Intent(  splash_screen.this, MainActivity.class) ;
                }
                startActivity(iNext);
                finish();

            }
        }, 4000);

    }
}