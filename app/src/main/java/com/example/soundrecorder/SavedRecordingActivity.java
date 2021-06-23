package com.example.soundrecorder;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SavedRecordingActivity extends AppCompatActivity {
    ListView lstView;
    Button btnBack;
    private String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_recording);
        lstView = findViewById(R.id.lstItems);
        btnBack = findViewById(R.id.btnBack);
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
//                File filepath = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/MyRecord/"+mylst.get(position).getFile_name());
//                Intent in=new Intent();
//                in.setAction(android.content.Intent.ACTION_VIEW);
//                in.setDataAndType(Uri.fromFile(filepath), "audio/*");
//                startActivity(in);

//                String nm = mylst.get(position).getName();
                String desc = mylst.get(position).getFile_name();
                Toast.makeText(SavedRecordingActivity.this, "File Name is " + desc,Toast.LENGTH_LONG).show();
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
    }
}