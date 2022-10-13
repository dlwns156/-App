package com.example.mapapap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class login extends AppCompatActivity {

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;

    private Button btn_login;
    private EditText edit_id, edit_pw;
    RequestQueue requestQueue;
    String url;
    JSONArray jsonArray1,jsonArray2,jsonArray3,jsonArray4;
    ArrayList<String>arrayList = new ArrayList<>();
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        drawerLayout = findViewById(R.id.login_dra);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        navigationView = findViewById(R.id.login_navigation_view);
        
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.item_login:
                        Log.d("로그인","login");
                        Toast.makeText(login.this, "로그인 클릭", Toast.LENGTH_SHORT).show();
                        Intent intent1 = new Intent(login.this, login.class);
                        startActivity(intent1);
                        break;
                    case R.id.item_singin:
                        Toast.makeText(login.this, "회원가입 클릭", Toast.LENGTH_SHORT).show();
                        Intent intent2 = new Intent(login.this, singin.class);
                        startActivity(intent2);
                        break;
                    case R.id.dron:
                        Toast.makeText(login.this, "드론예약 클릭", Toast.LENGTH_SHORT).show();
                        Intent intent3 = new Intent(login.this, reservation.class);
                        startActivity(intent3);
                        break;
                    case R.id.item_logput:
                        if (LoginRequest.getUser(login.this).length()!=0){
                            LoginRequest.clearUser(login.this);
                            Toast.makeText(login.this,"로그아웃 되었습니다.",Toast.LENGTH_SHORT).show();
                            onBackPressed();
                            break;
                        }
                        onBackPressed();
                        break;
                }
                return false;
            }
        });

        btn_login = findViewById(R.id.login_btn_login);
        edit_id = findViewById(R.id.login_edit_id);
        edit_pw = findViewById(R.id.login_edit_pw);

        if (requestQueue == null){
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }


        btn_login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {


                String inputID = edit_id.getText().toString();
                String inputPW = edit_pw.getText().toString();
                Log.d("아이다",inputID);
                if (inputID.equals("admin")){
                    url = "http://119.206.175.239:5000/admin";
                    Log.d("url",url);
                    Log.d("inputid",inputID);
                }else{
                    url = "http://119.206.175.239:5000/login";
                    Log.d("url",url);
                }

                StringRequest request = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                try {
                                    Log.d("되나","되나");
                                    JSONObject json = new JSONObject(response);
                                    if (inputID.equals("admin")) {
                                        jsonArray1 = json.getJSONArray("date");
                                        jsonArray2 = json.getJSONArray("id");
                                        jsonArray3 = json.getJSONArray("loc");
                                        jsonArray4 = json.getJSONArray("time");
                                        Log.d("딕셔너리",json+"");
                                        Log.d("day",jsonArray1+"");
                                        Log.d("id",jsonArray2+"");
                                        Log.d("loc",jsonArray3+"");
                                        Log.d("time",jsonArray4+"");
                                    }

//                                    Log.d("list",jsonArray.getString(1)+"");
//                                    Log.d("json", jsonArray+"");
//
//                                    for (int i=0; i<jsonArray.length(); i++){
//                                        Log.d("반목문",jsonArray.getString(i)+"");
//                                        arrayList.add(jsonArray.getString(i));
//                                    }
                                    String id = json.getString("id");
                                    Log.d("id",id);
                                    if (inputID.equals("admin")){
                                        Intent intent = new Intent(getApplicationContext(),Admin.class);
                                        LoginRequest.setUserId(login.this,inputID);
                                        intent.putExtra("id",LoginRequest.getUser(getApplicationContext()));
                                        intent.putExtra("json",json+"");
                                        intent.putExtra("jsonArr1",jsonArray1+"");
                                        intent.putExtra("jsonArr2",jsonArray2+"");
                                        intent.putExtra("jsonArr3",jsonArray3+"");
                                        intent.putExtra("jsonArr4",jsonArray4+"");
                                        startActivity(intent);

                                    }else if (json.getString("result").equals("success")){
                                        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                                        LoginRequest.setUserId(login.this,inputID);
                                        intent.putExtra("id",id);
                                        Toast.makeText(getApplicationContext(), "로그인 되었습니다.",Toast.LENGTH_SHORT).show();
                                        Log.d("intentID",id);
                                        startActivity(intent);
                                    }



                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }


                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d("res","오류");
                            }
                        }
                ) {
                    @Nullable
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {

                        Map<String, String> params = new HashMap<>();

                        params.put("id", inputID);
                        params.put("pw", inputPW);

                        return params;
                    }
                };
                requestQueue.add(request);
            }
        });
    }
}

