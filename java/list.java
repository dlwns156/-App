package com.example.mapapap;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class list extends AppCompatActivity {

    ListView listView;
    Button list_btn;
    String userid;


    // 리스트뷰에 사용할 제목 배열
    ArrayList<String> titleList = new ArrayList<>();
    // 클릭했을 때 어떤 게시물을 클랙했는지 번호를 담기 위한 배열
    ArrayList<String> seqList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);


        userid = getIntent().getStringExtra("id");
        Log.d("id",userid+"");
        listView = findViewById(R.id.list_listView);
        list_btn = findViewById(R.id.list_btn);
        list_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(list.this, register.class);
                intent.putExtra("id",userid);
                startActivity(intent);
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 어떤 값을 클릭했는지
                Toast.makeText(list.this, parent.getItemAtPosition(position)+"클릭",Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(list.this, MainActivity.class);
                startActivity(intent);
            }
        });


    }
};