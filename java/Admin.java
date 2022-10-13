package com.example.mapapap;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Admin extends AppCompatActivity {

    ListView listView;

    private static final String TAG = "Admin";
    private Context mContext=Admin.this;
    private ArrayAdapter arrayAdapter;
    String day, id, loc, time;
    List<String> items;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        listView = findViewById(R.id.listView);
        Intent intent = getIntent();
        day = intent.getStringExtra("jsonArr1");
        id = intent.getStringExtra("jsonArr2");
        loc = intent.getStringExtra("jsonArr3");
        time = intent.getStringExtra("jsonArr4");

        items = Arrays.asList(id,day,time,loc);

        arrayAdapter=new ArrayAdapter(mContext, android.R.layout.simple_list_item_1, items);
        listView.setAdapter(arrayAdapter);
        Log.d("AdminJson1",day);
        Log.d("AdminJson2",id);
        Log.d("AdminJson3",loc);
        Log.d("AdminJson4",time);


    }
}