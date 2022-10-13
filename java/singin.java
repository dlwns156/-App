package com.example.mapapap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
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
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class singin extends AppCompatActivity {

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    Button btn_singin;
    EditText edit_id, edit_pw, edit_name, edit_call;
    RequestQueue requestQueue;
    NavigationView navigationView;


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
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singin);
        drawerLayout = findViewById(R.id.singin_dra);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        navigationView = findViewById(R.id.singin_navigation_view);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.item_login:
                        if (LoginRequest.getUser(singin.this).length() == 0) {
                            Log.d("로그인", "login");
                            Intent intent1 = new Intent(singin.this, login.class);
                            startActivity(intent1);

                            break;
                        }
                        onBackPressed();
                        break;
                    case R.id.item_singin:
                        if (LoginRequest.getUser(singin.this).length() == 0) {
                            Toast.makeText(singin.this, "회원가입 클릭", Toast.LENGTH_SHORT).show();
                            Intent intent2 = new Intent(singin.this, singin.class);
                            startActivity(intent2);
                            break;
                        }
                        onBackPressed();
                        break;
                    case R.id.dron:
                        if (LoginRequest.getUser(singin.this).length() == 0) {
                            Intent intent4 = new Intent(singin.this, login.class);
                            startActivity(intent4);
                            break;
                        } else {
                            Intent intent3 = new Intent(singin.this, reservation.class);
                            startActivity(intent3);
                            break;
                        }
                    case R.id.item_logput:
                        if (LoginRequest.getUser(singin.this).length() != 0) {
                            LoginRequest.clearUser(singin.this);
                            Toast.makeText(singin.this, "로그아웃 되었습니다.", Toast.LENGTH_SHORT).show();
                            onBackPressed();
                            break;
                        }
                        onBackPressed();
                        break;
                    }
                    return false;
                }
            });

                btn_singin = findViewById(R.id.singin_btn_singin);
                edit_id = findViewById(R.id.singin_edit_id);
                edit_pw = findViewById(R.id.singin_edit_pw);
                edit_call = findViewById(R.id.singin_edit_call);
                edit_name = findViewById(R.id.singin_edit_name);

                if (requestQueue == null) {
                    requestQueue = Volley.newRequestQueue(singin.this);
                }

                String url = "http://119.206.175.239:5000/signin";


                btn_singin.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {

                        String inputID = edit_id.getText().toString();
                        String inputPW = edit_pw.getText().toString();
                        String inputCALL = edit_call.getText().toString();
                        String inputNAME = edit_name.getText().toString();
                        final String[] idpw = new String[1];

                        StringRequest request = new StringRequest(Request.Method.POST, url,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {

                                        try {
                                            JSONObject json = new JSONObject(response);

                                            Log.d("json", String.valueOf(json));

                                            JSONObject json1 = json.getJSONObject(("id"));
                                            Log.d("json1", String.valueOf(json1));
                                            idpw[0] = json1.getString("id");
                                            Log.d("id", idpw[0]);

                                            JSONObject json2 = json.getJSONObject(("pw"));
                                            Log.d("json2", String.valueOf(json2));
                                            idpw[1] = json1.getString("pw");
                                            Log.d("pw", idpw[1]);

                                            JSONObject json3 = json.getJSONObject(("call"));
                                            Log.d("json3", String.valueOf(json3));
                                            idpw[2] = json1.getString("phone");
                                            Log.d("phone", idpw[2]);

                                            JSONObject json4 = json.getJSONObject(("name"));
                                            Log.d("json4", String.valueOf(json4));
                                            idpw[3] = json1.getString("name");
                                            Log.d("name", idpw[3]);

                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                        intent.putExtra("res", idpw[0]);
                                        Toast.makeText(getApplicationContext(), "회원가입 되었습니다.",Toast.LENGTH_SHORT).show();
                                        startActivity(intent);
                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Log.d("회원가입 볼리", "오류");
                                    }
                                }
                        ) {
                            @Nullable
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {

                                Map<String, String> params = new HashMap<>();

                                params.put("id", inputID);
                                params.put("pw", inputPW);
                                params.put("phone", inputCALL);
                                params.put("name", inputNAME);

                                return params;
                            }
                        };
                        requestQueue.add(request);
                    }
                });
            }
        }
