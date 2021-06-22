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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class MainActivity extends AppCompatActivity {

    private static final int PERMISSION_CODE = 1;
    Button recording,start,save;
//    private boolean isRecording =false;
    private static String filename = null;
    MediaRecorder media;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recording = findViewById(R.id.btnRecordlist);
        start =findViewById(R.id.btnPlay);
        save =findViewById(R.id.btnSave);
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
                if(checkPermission()) {
                    String filePath = Environment.getExternalStorageDirectory().getAbsolutePath()+ "/AudioRecording.3gp";
                    SimpleDateFormat Formater = new SimpleDateFormat("yyyy_MM_dd_hh_mm_ss", Locale.ENGLISH);
                    Date now = new Date();
                    filename = "Recording_" + Formater.format(now) + ".mp3";
                    media = new MediaRecorder();
                    media.setAudioSource(MediaRecorder.AudioSource.MIC);
                    media.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
                    media.setOutputFile(filename);
                    media.setOutputFile(filePath + "/" + filename);
                    media.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
                    try {
                        media.prepare();
                    } catch (IOException e) {
                        Log.e("Prepare", "prepare() failed");
                    }
                    media.start();
                }
                else {
                    RequestPermissions();
                }
//                if(isRecording)//Stop Recording
//                {
//                    stopRecord();
//                    Toast.makeText(MainActivity.this,"Record Stop",Toast.LENGTH_SHORT).show();
//                    isRecording=false;
//                }
//                else{//start Recording
//                    Toast.makeText(MainActivity.this,"Work Else",Toast.LENGTH_SHORT).show();
//                    if(checkPermission())
//                    {
//                        Toast.makeText(MainActivity.this,"Record work",Toast.LENGTH_SHORT).show();
//                          startRecord();
//                        isRecording=true;
//                    }
//                }
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                media.stop();
                media.release();
                media= null;
                Toast.makeText(MainActivity.this,"Record Stop",Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_CODE:
                if (grantResults.length > 0) {
                    boolean permissionToRecord = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean permissionToStore = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    if (permissionToRecord && permissionToStore) {
                        Toast.makeText(getApplicationContext(), "Permission Granted", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Permission Denied", Toast.LENGTH_LONG).show();
                    }
                }
                break;
        }
    }
    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);
        int result1 = ContextCompat.checkSelfPermission(getApplicationContext(), RECORD_AUDIO);
        return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED;
    }
    private void RequestPermissions() {
        ActivityCompat.requestPermissions(MainActivity.this, new String[]{RECORD_AUDIO, WRITE_EXTERNAL_STORAGE}, PERMISSION_CODE);
    }
}