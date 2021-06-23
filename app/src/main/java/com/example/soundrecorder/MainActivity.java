package com.example.soundrecorder;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class MainActivity extends AppCompatActivity {

    private static final int PERMISSION_CODE = 1;
    Button recording,start,save;
    ImageView imageinfo;
    private boolean isRecording =false;
    private static String filename = null;
    TextView timerview;
    MediaRecorder media;
    private boolean isPause=false;
    Timer timer;
    TimerTask timerTask;
    double time=0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recording = findViewById(R.id.btnRecordlist);
        imageinfo=findViewById(R.id.imageView3);
        timerview=findViewById(R.id.textTimer);
        start =findViewById(R.id.btnPlay);
        save =findViewById(R.id.btnSave);
        timer=new Timer();
        save.setEnabled(false);
        recording.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, SavedRecordingActivity.class);
                startActivity(i);
            }
        });
        start.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                if(isRecording){
                    if(isPause){
                        Toast.makeText(MainActivity.this,"Record pause...",Toast.LENGTH_SHORT).show();
                        imageinfo.setBackground(getResources().getDrawable(R.drawable.microphone_white));
                        start.setBackground(getResources().getDrawable(R.drawable.play200));
                        media.pause();
                        timerTask.cancel();
                        isPause=false;
                    }
                    else{
                        Toast.makeText(MainActivity.this,"Record Resume...",Toast.LENGTH_SHORT).show();
                        imageinfo.setBackground(getResources().getDrawable(R.drawable.audio_wave));
                        start.setBackground(getResources().getDrawable(R.drawable.pause_white));
                        media.resume();
                        startTimer();
                        isPause=true;

                    }
                }
                else{
//                    Toast.makeText(MainActivity.this,"Start",Toast.LENGTH_SHORT).show();
                    if(checkPermission()){
                        Toast.makeText(MainActivity.this,"Recording Start...",Toast.LENGTH_SHORT).show();
                        startRecord();
                        startTimer();
                        imageinfo.setBackground(getResources().getDrawable(R.drawable.audio_wave));
                        start.setBackground(getResources().getDrawable(R.drawable.pause_white));
                        isRecording=true;
                        save.setEnabled(true);
                        isPause=true;
                    }
                    else{ RequestPermissions(); }
                }
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopRecord();
                imageinfo.setBackground(getResources().getDrawable(R.drawable.microphone_white));
                start.setBackground(getResources().getDrawable(R.drawable.play200));
                Toast.makeText(MainActivity.this,"Your Recording Saved Successfully....",Toast.LENGTH_SHORT).show();
                isRecording=false;
                save.setEnabled(false);
            }
        });
    }

    private void startTimer() {
        timerTask=new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        time++;
                        timerview.setText(getTextTimer());
                    }
                });
            }
        };
        timer.scheduleAtFixedRate(timerTask,0,1000);
    }

    private String getTextTimer() {
        int rounder=(int) Math.round(time);
        int sec=((rounder % 86400) % 3600) % 60;
        int min=((rounder % 86400) % 3600) / 60;
        int hr=((rounder % 86400) / 3600);
        String formattime=String.format("%02d",hr)+" : "+String.format("%02d",min)+" : "+String.format("%02d",sec);
        return formattime;

    }

    private void startRecord() {
        try {
            String filepath = Environment.getExternalStorageDirectory().getPath();
            File file = new File(filepath, "MyRecord");
            if (!file.exists())
                file.mkdirs();
            String filepath2 =file.getAbsolutePath();
            SimpleDateFormat Formater = new SimpleDateFormat("yyyy_MM_dd_hh_mm_ss", Locale.ENGLISH);
            Date now = new Date();
            filename = "Recording_" + Formater.format(now) + ".mp3";
            media=new MediaRecorder();
            media.reset();
            media.setAudioSource(MediaRecorder.AudioSource.MIC);
            media.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
            media.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            media.setOutputFile(filepath2+"/"+filename);
            media.prepare();
            media.start();
        } catch (Exception e) {
            Log.i("Start", String.valueOf(e));
        }
    }

    private void stopRecord() {
        try {
            media.stop();
            media.release();
            media = null;
        } catch (Exception e) {
            Log.i("Stop", String.valueOf(e));
        }
        if(timerTask != null) {
            timerTask.cancel();
            time=0.0;
            timerview.setText("00 : 00 : 00");
        }
    }

//    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        switch (requestCode) {
//            case PERMISSION_CODE:
//                if (grantResults.length > 0) {
//                    boolean permissionToRecord = grantResults[0] == PackageManager.PERMISSION_GRANTED;
//                    boolean permissionToStore = grantResults[1] == PackageManager.PERMISSION_GRANTED;
//                    if (permissionToRecord && permissionToStore) {
//                        Toast.makeText(MainActivity.this, "Permission Granted", Toast.LENGTH_LONG).show();
//                    } else {
//                        Toast.makeText(MainActivity.this, "Permission Denied", Toast.LENGTH_LONG).show();
//                    }
//                }
//                break;
//        }
//    }
    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(MainActivity.this, WRITE_EXTERNAL_STORAGE);
        int result1 = ContextCompat.checkSelfPermission(MainActivity.this, RECORD_AUDIO);
        return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED;
    }
    private void RequestPermissions() {
        ActivityCompat.requestPermissions(MainActivity.this, new String[]{RECORD_AUDIO, WRITE_EXTERNAL_STORAGE,READ_EXTERNAL_STORAGE}, PERMISSION_CODE);
    }
}