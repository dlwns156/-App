package com.example.mapapap;

import android.os.Bundle;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class framdata extends Fragment {

    private View view;
    RequestQueue requestQueue;

    Spinner spn_li;
    String set = "";
    String loset = "";
    TextView tv_sese;
    Integer sen = 0;
    TextView[] tvarray = new TextView[12];

    public static framdata newInstance(){
        framdata framdata = new framdata();
        return framdata;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if(view == null){
            try {
                view = inflater.inflate(R.layout.data,container,false);
            }catch (InflateException e){

            }
        }

        tv_sese = view.findViewById(R.id.tv_sese);

        if (sen == 0){
            tv_sese.setVisibility(View.VISIBLE);
        }else {
            tv_sese.setVisibility(View.INVISIBLE);
        }

        spn_li = view.findViewById(R.id.spn_li);

        for(int i = 0; i < tvarray.length; i++){
            int imgId = getResources().getIdentifier("tv"+(i+1),"id",getActivity().getPackageName());
            tvarray[i] = view.findViewById(imgId);
        }

        ArrayAdapter<CharSequence> lolist = ArrayAdapter.createFromResource(getActivity().getApplicationContext(),
                R.array.my_array, android.R.layout.simple_spinner_item);

        lolist.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spn_li.setAdapter(lolist);
        spn_li.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                set = adapterView.getItemAtPosition(i).toString();
                sen = i;

                loset = loequals.lolo(set);

                String url = "https://www.khoa.go.kr/api/oceangrid/buObsRecent/search.do?ServiceKey=GCNl65efEvJ9nEOSq67wag==&ObsCode="+loset+"&ResultType=json";

                if (requestQueue == null){
                    requestQueue = Volley.newRequestQueue(getContext());
                }

                StringRequest request = new StringRequest(Request.Method.POST,
                        url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                if (sen == 0){
                                    tv_sese.setVisibility(View.VISIBLE);
                                    for(int i = 0; i < tvarray.length; i++){
                                        tvarray[i].setText("");
                                    }

                                }else {

                                    tv_sese.setVisibility(View.INVISIBLE);

                                    try {

                                        JSONObject json = new JSONObject(response);
                                        Log.d("json", String.valueOf(json));

                                        if (json.getJSONObject("result").getJSONObject("data").get("record_time").equals("")){
                                            tvarray[0].setText("자료 없음");
                                        }else {
                                            tvarray[0].setText("측정시간"+System.getProperty("line.separator")+json.getJSONObject("result").getJSONObject("data").get("record_time").toString());
                                        }
                                        tvarray[1].setText("측정장소"+System.getProperty("line.separator")+set);
                                        tvarray[2].setText("풍향");
                                        Log.d("json",json.getJSONObject("result").getJSONObject("data").get("wind_dir")+"");
                                        if (json.getJSONObject("result").getJSONObject("data").get("wind_dir").equals("")){
                                            tvarray[3].setText("자료 없음");
                                        }else {
                                            tvarray[3].setText(json.getJSONObject("result").getJSONObject("data").get("wind_dir")+" deg");
                                        }
                                        tvarray[4].setText("풍속");
                                        if (json.getJSONObject("result").getJSONObject("data").get("wind_speed").equals("")){
                                            tvarray[5].setText("자료 없음");
                                        }else {
                                            tvarray[5].setText(json.getJSONObject("result").getJSONObject("data").get("wind_speed")+" m/s");
                                        }
                                        tvarray[6].setText("유향");
                                        if (json.getJSONObject("result").getJSONObject("data").get("current_dir").equals("")){
                                            tvarray[7].setText("자료 없음");
                                        }else {
                                            tvarray[7].setText(json.getJSONObject("result").getJSONObject("data").get("current_dir")+" deg");
                                        }
                                        tvarray[8].setText("유속");
                                        if (json.getJSONObject("result").getJSONObject("data").get("current_speed").equals("")){
                                            tvarray[9].setText("자료 없음");
                                        }else {
                                            tvarray[9].setText(json.getJSONObject("result").getJSONObject("data").get("current_speed")+" cm/s");
                                        }
                                        tvarray[10].setText("파고");
                                        if (json.getJSONObject("result").getJSONObject("data").get("wave_height").equals("")){
                                            tvarray[11].setText("자료 없음");
                                        }else {
                                            tvarray[11].setText(json.getJSONObject("result").getJSONObject("data").get("wave_height")+" m");
                                        }

                                    }catch (JSONException e) {
                                        e.printStackTrace();
                                    }

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
                        Map<String,String>params = new HashMap<>();

//                        params.put("go","123");

                        return params;
                    }
                };

                requestQueue.add(request);


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });




        return view;

    }
}