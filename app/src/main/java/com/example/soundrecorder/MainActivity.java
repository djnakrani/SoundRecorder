package com.example.soundrecorder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private int PERMISSION_CODE = 1;
    Button recording,start;
    private boolean isRecording =false;
    private MediaRecorder media;
    private String filename="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recording = findViewById(R.id.btnRecordlist);
        start =findViewById(R.id.btnPlay);

        recording.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, SavedRecordingActivity.class);
                startActivity(i);
            }
        });

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isRecording)//Stop Recording
                {

                    stopRecord();
                    Toast.makeText(MainActivity.this,"Record Stop",Toast.LENGTH_SHORT).show();
                    isRecording=false;
                }
                else{//start Recording
                    Toast.makeText(MainActivity.this,"Work Else",Toast.LENGTH_SHORT).show();
                    if(checkPermission())
                    {
                        Toast.makeText(MainActivity.this,"Record work",Toast.LENGTH_SHORT).show();
                        startRecord();
                        isRecording=true;
                    }
                }
            }
        });

    }

    private void stopRecord() {
        media.stop();
        media.release();
        media= null;
    }

    private void startRecord() {
        String pathfile= Environment.getExternalStorageDirectory().getAbsolutePath();
        SimpleDateFormat Formater=new SimpleDateFormat("yyyy_MM_dd_hh_mm_ss", Locale.ENGLISH);
        Date now=new Date();
        filename = "Recording_"+Formater.format(now)+".mp3";
        media=new MediaRecorder();
        media.setAudioSource(MediaRecorder.AudioSource.MIC);
        media.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        media.setOutputFile(pathfile+"/Recording/"+filename);
        media.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            media.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }

        media.start();
    }

    private boolean checkPermission() {
//        if (ContextCompat.checkSelfPermission(this,
//                Manifest.permission.RECORD_AUDIO)
//                != PackageManager.PERMISSION_GRANTED) {
//
//                ActivityCompat.requestPermissions(this,
//                        new String[]{Manifest.permission.RECORD_AUDIO},
//                        PERMISSION_CODE);
//
//            }
//        else if (ContextCompat.checkSelfPermission(this,
//                Manifest.permission.RECORD_AUDIO)
//                == PackageManager.PERMISSION_GRANTED) {
//
//            //Go ahead with recording audio now
//            return true;
//        }
        return true;
    }
}