package com.example.mapapap;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    framlist framlist;
    framdata framdata;
    frammap frammap;
    framhome framhome;
    BottomNavigationView bottomNavigationView;
    Toolbar toolbar;
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    ImageView imageView;
    GoogleMap googleMap;
    private int requestCode;
    private String[] permissions;
    private int[] grantResults;
    AlertDialog.Builder builder;
    AlertDialog alertDialog;

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        imageView = findViewById(R.id.img_navi);
        frammap = new frammap();
        framlist = new framlist();
        framdata = new framdata();
        framhome = new framhome();

        navigationView = findViewById(R.id.login_navigation_view);

        drawerLayout = findViewById(R.id.drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportFragmentManager().beginTransaction().replace(R.id.framLayout, framhome).commit();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.item_login:
                        if (LoginRequest.getUser(MainActivity.this).length()==0){
                            Log.d("로그인","login");
                            Intent intent1 = new Intent(MainActivity.this, login.class);
                            startActivity(intent1);

                            break;
                        }
                        builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setTitle("로그인").setMessage("로그인이 되어있습니다.");
                        alertDialog = builder.create();
                        alertDialog.show();
                        onBackPressed();
                        break;
                    case R.id.item_singin:
                        if (LoginRequest.getUser(MainActivity.this).length()==0){
                            Toast.makeText(MainActivity.this, "회원가입 클릭", Toast.LENGTH_SHORT).show();
                            Intent intent2 = new Intent(MainActivity.this, singin.class);
                            startActivity(intent2);
                            break;
                        }
                        builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setTitle("로그인").setMessage("로그인이 되어있습니다.");
                        alertDialog = builder.create();
                        alertDialog.show();
                        onBackPressed();
                        break;
                    case R.id.dron:
                        if (LoginRequest.getUser(MainActivity.this).length()==0){
                            Intent intent4 = new Intent(MainActivity.this, login.class);
                            startActivity(intent4);
                            break;
                        }else{
                            Intent intent3 = new Intent(MainActivity.this, reservation.class);
                            startActivity(intent3);
                            break;
                        }
                    case R.id.item_logput:
                        if (LoginRequest.getUser(MainActivity.this).length()!=0){
                            LoginRequest.clearUser(MainActivity.this);
                            Toast.makeText(MainActivity.this,"로그아웃 되었습니다.",Toast.LENGTH_SHORT).show();
                            onBackPressed();
                            break;
                        }
                        onBackPressed();
                        break;
                }
                return false;
            }
        });

        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.menu_item1:
                        getSupportFragmentManager().beginTransaction().replace(R.id.framLayout, framhome).commit();
                        return true;
                    case R.id.menu_item2:
                        getSupportFragmentManager().beginTransaction().replace(R.id.framLayout, frammap).commit();
                        return true;
                    case R.id.menu_item3:
                        getSupportFragmentManager().beginTransaction().replace(R.id.framLayout, framdata).commit();
                        return true;
                    case R.id.menu_item4:
                        getSupportFragmentManager().beginTransaction().replace(R.id.framLayout, framlist).commit();
                        return true;
                }
                return false;
            }
        });

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED){

        }else {
            checkLocationPermissionWithRationale();
        }

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
    public void onMapReady(@NonNull GoogleMap googleMap) {
        this.googleMap = googleMap;
    }

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    private void checkLocationPermissionWithRationale() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                new AlertDialog.Builder(this)
                        .setTitle("위치정보")
                        .setMessage("이 앱을 사용하기 위해서는 위치정보에 접근이 필요합니다. 위치정보 접근을 허용하여 주세요.")
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        }).create().show();
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        this.requestCode = requestCode;
        this.permissions = permissions;
        this.grantResults = grantResults;
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                    }
                } else {
                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }
}