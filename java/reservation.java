package com.example.mapapap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class reservation extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    Chronometer character;
    Button btnStart, btnEnd;
    RadioButton radioButton, rBtnTime;
    CalendarView calView;
    TimePicker tPicker;
    RadioGroup radioGroup;
    int selectYear, selectMonth, selectDay;
    final CharSequence[] date = {null};
    final CharSequence[] Time = {null};
    TextView res_text;
    RequestQueue requestQueue;
    Spinner spi;
    EditText editText;
    String[] options;
    String pplo = "";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);
        btnEnd = findViewById(R.id.btnEnd);
        radioButton = findViewById(R.id.rBtnCalender);
        rBtnTime = findViewById(R.id.btnTime);
        tPicker = findViewById(R.id.timePicker);
        calView = findViewById(R.id.calenderView);
        radioGroup = findViewById(R.id.radio);
        res_text = findViewById(R.id.reservation_text);

        spi = findViewById(R.id.spi);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.my_array2,android.R.layout.simple_spinner_item);
        spi.setAdapter(adapter);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spi.setAdapter(adapter);
        spi.setOnItemSelectedListener(this);

        options = this.getResources().getStringArray(R.array.my_array2);

        radioButton.setChecked(true);
        tPicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                Time[0] = hourOfDay+"시"+minute+"분";
                Log.d("시간",Time[0]+"");
            }
        });

        calView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                date[0] = year +"/"+(month+1)+"/"+dayOfMonth;
                Log.d("날짜",date[0]+"");
                res_text.setText(date[0]+"날짜를 선택했습니다");
                res_text.setTextSize(0,40);
                tPicker.setVisibility(View.VISIBLE);
                calView.setVisibility(View.INVISIBLE);
                rBtnTime.setChecked(true);
            }
        });


        tPicker.setVisibility(View.INVISIBLE);

        radioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tPicker.setVisibility(View.INVISIBLE);
                calView.setVisibility(View.VISIBLE);
            }
        });
        rBtnTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tPicker.setVisibility(View.VISIBLE);
                calView.setVisibility(View.INVISIBLE);
            }
        });

        btnEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(reservation.this, MainActivity.class);
                String url = "http://119.206.175.239:5000/drone";
                Log.d("네이마르",date[0]+"");
                Log.d("메시",Time[0]+"");
                intent.putExtra("Loc",editText+"");
                intent.putExtra("Date",date[0]);
                intent.putExtra("Time",Time[0]);
                Toast.makeText(getApplicationContext(),"예약이 완료되었습니다.", Toast.LENGTH_LONG).show();
                startActivity(intent);

                if (requestQueue == null){
                    requestQueue = Volley.newRequestQueue(reservation.this);
                }

                StringRequest request = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                try {
                                    JSONObject json = new JSONObject(response);


                                    if (LoginRequest.getUser(reservation.this).length()!=0){
                                        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                                        Toast.makeText(getApplicationContext(), "예약이 완료되었습니다.",Toast.LENGTH_SHORT).show();
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

                        Log.d("보낸는지 확인",date[0]+"");
                        params.put("date", date[0]+"");
                        params.put("id",LoginRequest.getUser(reservation.this));
                        Log.d("id",LoginRequest.getUser(reservation.this));

                        params.put("loc",pplo);
                        Log.d("loc",pplo);

                        params.put("time",Time[0]+"");
                        Log.d("time",Time[0]+"");

                        return params;
                    }
                };
                requestQueue.add(request);
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        pplo = parent.getItemAtPosition(position).toString();
        Log.d("ttjtj",pplo);

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {


    }
}