package com.example.mapapap;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.LineChart;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class fram1_2 extends Fragment {

    View view;
    TextView edit_text;
    String chch1;
    RequestQueue requestQueue;
    LineChart line_chart, line_chart2;
    String go = "7";
    int limited = 14;
    ArrayList<String> xvallist = new ArrayList<>();
    ArrayList<Double> datelist = new ArrayList<>();
    ArrayList<Double> temlist = new ArrayList<>();
    ArrayList<Double> nolist = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fram1_2,container,false);

        edit_text = view.findViewById(R.id.fram_ch_text2);
        Bundle bundle = getArguments();

        if(bundle != null){
            chch1 = bundle.getString("marker");
            edit_text.setVisibility(View.INVISIBLE);
        }

        if (line_chart != null){
            line_chart.invalidate();
            line_chart.clear();
        }

        if (datelist != null){
            datelist.clear();
            temlist.clear();
        }

        Log.d("json","차트 진입");

        String url = "http://121.147.185.22:5000/predict";

        if (requestQueue == null){
            requestQueue = Volley.newRequestQueue(getContext());
        }

        StringRequest request = new StringRequest(Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("json","차트 받기");

                        line_chart = view.findViewById(R.id.line_chart);
                        line_chart2 = view.findViewById(R.id.line_char2);
                        try {

                            JSONObject json = new JSONObject(response);
                            Log.d("json", String.valueOf(json));

                            JSONArray xVals = json.getJSONArray("xval");
                            JSONArray date = json.getJSONArray("date");
                            JSONArray wtr_tmp = json.getJSONArray("wtr_tmp");
                            JSONArray no = json.getJSONArray("no");

                            for (int i=0; i<xVals.length(); i++) {
                                xvallist.add(xVals.getString(i));
                            }

                            for (int i = 0; i < date.length(); i++){
                                datelist.add(date.getDouble(i));
                                Log.d("test",datelist.toString());
                            }

                            for (int i = 0; i < wtr_tmp.length(); i++){
                                temlist.add(wtr_tmp.getDouble(i));
                                Log.d("test", temlist.toString());
                            }

                            for (int i = 0; i < wtr_tmp.length(); i++){
                                nolist.add(no.getDouble(i));
                            }

                            line_chart.invalidate();
                            line_chart.clear();
                            line_chart2.invalidate();
                            line_chart2.clear();

                            Line.line(line_chart, xvallist, datelist, temlist,go,limited, 1);
                            Line.line(line_chart2, xvallist, datelist, nolist,go,limited, 0);

                        }catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Log.d("json","차트 전송");
                Map<String,String>params = new HashMap<>();
                params.put("go",go);
                params.put("ro",chch1);

                return params;

            }
        };

        requestQueue.add(request);

        return view;

    }
}