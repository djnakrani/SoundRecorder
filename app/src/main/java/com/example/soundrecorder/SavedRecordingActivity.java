package com.example.soundrecorder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

public class SavedRecordingActivity extends AppCompatActivity {
    ListView lstView;
    Button btnBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_recording);
        lstView = findViewById(R.id.lstItems);
        btnBack = findViewById(R.id.btnBack);
        List<Items> lsItems = new ArrayList<>();
        lsItems.add(new Items("Recording1","12th may 2021","12:04"));
        lsItems.add(new Items("Recording2","12th may 2021","02:37"));
        lsItems.add(new Items("Recording3","12th may 2021","04:23"));
        lsItems.add(new Items("Recording4","12th may 2021","10:38"));
        lsItems.add(new Items("Recording5","12th may 2021","10:45"));
        lsItems.add(new Items("Recording6","12th may 2021","02:37"));
        lsItems.add(new Items("Recording7","12th may 2021","05:23"));
        lsItems.add(new Items("Recording8","12th may 2021","4:38"));
        lsItems.add(new Items("Recording9","12th may 2021","06:38"));

       CustomItemAdapter objadpt = new CustomItemAdapter(SavedRecordingActivity.this,lsItems);
        lstView.setAdapter(objadpt);
        lstView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String nm = lsItems.get(position).getName();
                String desc = lsItems.get(position).getDesc();
                Toast.makeText(SavedRecordingActivity.this, "Name is " + nm + "\nDescription is " + desc ,Toast.LENGTH_LONG).show();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SavedRecordingActivity.this, MainActivity.class);
                startActivity(i);
            }
        });
    }
}