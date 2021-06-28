package com.example.soundrecorder;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SavedRecordingActivity extends AppCompatActivity {
    ListView lstView;
    Button btnBack,play_pause,btnprev,btnnext;
    private String path;
    MediaPlayer mediaPlayer;
    private boolean isbtnplayer=false;
    TextView txtFilename,txtruntime,txtfinaltime;
    private int finalTime=0;
    private int startTime=0;
    SeekBar seekBar;
    private int forwardTime = 5000;
    private int backwardTime = 5000;
    private Handler myHandler=new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_recording);
        play_pause=findViewById(R.id.btnplay_pause);
        lstView = findViewById(R.id.lstItems);
        btnBack = findViewById(R.id.btnBack);
        txtFilename=findViewById(R.id.txtFileName);
        txtfinaltime=findViewById(R.id.txtFinalTime);
        txtruntime=findViewById(R.id.txtTime);
        seekBar=findViewById(R.id.seekbar1);
        btnprev=findViewById(R.id.btnprev);
        btnnext=findViewById(R.id.btnnext);

        seekBar.setEnabled(false);
        List<Items> mylst = new ArrayList<>();
        path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/MyRecord/";
        File directory = new File(path);
        File[] files = directory.listFiles();
        for (int i = 0; i < files.length; i++)
        {

            String file_name = files[i].getName();
            String dd=file_name.substring(18,20);
            String mm=file_name.substring(15,17);
            String yy=file_name.substring(12,14);
            String name="My"+file_name.substring(0,10) +"_"+ i;
            String time=dd+"/"+mm+"/"+yy;
            String Duration=String.valueOf(files[i].length()/1024)+" KB";
            // you can store name to arraylist and use it later
            mylst.add(new Items(file_name,name,time,Duration));
        }


       CustomItemAdapter objadpt = new CustomItemAdapter(SavedRecordingActivity.this,mylst);
        lstView.setAdapter(objadpt);
        lstView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String Name=mylst.get(position).getName();
                String desc = mylst.get(position).getFile_name();
                txtFilename.setText(Name);
                seekBar.setEnabled(true);
                startPlay(desc);

            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SavedRecordingActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });

        play_pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isbtnplayer){
                    mediaPlayer.pause();
                    play_pause.setBackground(getResources().getDrawable(R.drawable.play200));
                    isbtnplayer=false;
                }
                else {
                    mediaPlayer.start();
                    play_pause.setBackground(getResources().getDrawable(R.drawable.pause_white));
                    isbtnplayer=true;
                }
            }
        });

        btnnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int temp = (int)startTime;

                if((temp+forwardTime)<=finalTime){
                    startTime = startTime + forwardTime;
                    mediaPlayer.seekTo((int) startTime);
                }
            }
        });

        btnprev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int temp = (int)startTime;

                if((temp-backwardTime)>0){
                    startTime = startTime - backwardTime;
                    mediaPlayer.seekTo((int) startTime);
                }
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int current_pos = seekBar.getProgress();
                mediaPlayer.seekTo(current_pos);
            }
        });
    }

    private void startPlay(String desc) {
        String Path= Environment.getExternalStorageDirectory().getAbsolutePath() + "/MyRecord/"+desc;
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.reset();
            mediaPlayer.release();
        }
        mediaPlayer=MediaPlayer.create(this, Uri.parse(Path));
        mediaPlayer.start();
        play_pause.setBackground(getResources().getDrawable(R.drawable.pause_white));
        isbtnplayer=true;
        finalTime = mediaPlayer.getDuration();
        startTime = mediaPlayer.getCurrentPosition();
        seekBar.setMax(mediaPlayer.getDuration());
        txtfinaltime.setText(createTimeLabel(finalTime));
        txtruntime.setText(createTimeLabel(startTime));
        seekBar.setProgress(startTime);
        myHandler.postDelayed(UpdateSongTime,100);

    }

    private String createTimeLabel(int duration) {
        String timeLabel = "";
        int min = duration / 1000 / 60;
        int sec = duration / 1000 % 60;
        timeLabel += min + ":";
        if (sec < 10) timeLabel += "0";
        timeLabel += sec;
        return timeLabel;
    }

    private Runnable UpdateSongTime = new Runnable() {
        public void run() {
            startTime = mediaPlayer.getCurrentPosition();
            txtruntime.setText(createTimeLabel(startTime));
            seekBar.setProgress(startTime);
            myHandler.postDelayed(this, 100);
        }
    };

}