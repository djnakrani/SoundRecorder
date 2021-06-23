package com.example.soundrecorder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.widget.VideoView;

public class SplashScreenActivity extends AppCompatActivity {

    private static int SPLASH_TIME = 5000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        VideoView vView = (VideoView) findViewById(R.id.vView);
        String path =  "android.resource://" + getPackageName() + "/" + R.raw.splash;
        Uri video = Uri.parse(path);
        vView.setVideoURI(video);
        vView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
                vView.start();
            }
        });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(SplashScreenActivity.this,MainActivity.class);
                startActivity(i);
                finish();
            }
        },SPLASH_TIME);
    }
}