package com.example.mapapap;

import android.Manifest;
import android.app.FragmentManager;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.IndoorBuilding;
import com.google.android.gms.maps.model.IndoorLevel;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.ClusterManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class fram1_1 extends Fragment implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener{

    private FragmentManager fragmentManager;
    GoogleMap googleMap;
    RequestQueue requestQueue;
    View view;
    int M1,M2,M3,M4,M5,M6,M7,M8,M9,M10,M11,M12;
    int limited = 3;
    Button btn_check;
    fram1_2 fram1_2;
    int[] mama = {};

    ClusterManager<Mapitem> mClusterManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if(view == null){
            try {
                Log.d("te","??? ??????");
                view = inflater.inflate(R.layout.fram1_1,container,false);
                btn_check = view.findViewById(R.id.btn_check);
                btn_check.setVisibility(view.VISIBLE);
            }catch (InflateException e){

            }
        }

        if (view.findViewById(R.id.btn_check)==null){
            btn_check.setVisibility(view.VISIBLE);
        }

        btn_check = view.findViewById(R.id.btn_check);
        fram1_2 = new fram1_2();
        btn_check.setVisibility(view.INVISIBLE);
        fragmentManager = getActivity().getFragmentManager();
        MapFragment mapFragment = (MapFragment) fragmentManager.findFragmentById(R.id.map2);
        mapFragment.getMapAsync(this);

        String url = "http://211.223.106.184:5000/pmap";

        if (requestQueue == null){
            requestQueue = Volley.newRequestQueue(getContext());
        }

        StringRequest request = new StringRequest(Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            Log.d("json", "test");
                            JSONObject json = new JSONObject(response);
                            Log.d("json", String.valueOf(json));

                            M1 = (int) json.get("???????????????");
                            M2 = (int) json.get("????????????");
                            M3 = (int) json.get("???????????????");
                            M4 = (int) json.get("??????");
                            M5 = (int) json.get("??????");
                            M6 = (int) json.get("?????????");
                            M7 = (int) json.get("?????????");
                            M8 = (int) json.get("??????");
                            M9 = (int) json.get("???????????????");
                            M10 = (int) json.get("????????????");
                            M11 = (int) json.get("?????????");
                            M12 = (int) json.get("????????????");

                            onMapReady(googleMap);

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

                Map<String,String>params = new HashMap<>();
                params.put("go","123");

                return params;
            }
        };

        Log.d("test",request.toString());

        requestQueue.add(request);

        return view;
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        this.googleMap = googleMap;

        Log.d("te","????????? ??????");

        // ?????? ?????? ????????? ??? ??????
        LatLng latLng = new LatLng(35.907757,127.766922);
//        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 6));
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 6));

        double[] lats = {34.53410, 35.811300, 34.733900, 35.152067, 35.076267, 34.795406, 35.06155, 35.659367, 34.4633, 37.351433, 36.29775, 36.563233};
        double[] lngs = {127.48005, 126.348525, 128.544433, 129.153967, 129.102533, 128.330544, 128.36795, 126.265833, 126.2204, 126.337333, 126.26150, 126.193300};

        String[] titles = {"???????????????","????????????","???????????????","??????","??????","?????????","?????????","??????","???????????????","????????????","?????????","????????????"};
        String[] snippets = {"?????? 3???","????????????","?????? 3???","?????? 3???","?????? 3???","?????? 3???","?????? 3???","?????????","?????? 2???","?????? 2???","??????(?????????)","??????(?????????)"};

        mama = new int[]{M1, M2, M3, M4, M5, M6, M7, M8, M9, M10, M11, M12};


        LatLng[] latLngasd = new LatLng[lats.length]; // ????????? ??????

        for (int i = 0; i < lats.length; i++){
            latLngasd[i] = new LatLng(lats[i],lngs[i]);
        } // ?????? ?????? ?????? ??? lats.length ????????? ?????? ????????? ????????? ?????? ??? ?????? ??????

        MarkerOptions[] markers = new MarkerOptions[titles.length];

        MarkerOptions markerOptions1 = new MarkerOptions();
        LatLng latLng1 = new LatLng(35.907757,127.766922);
        markerOptions1.title("????????????");
        markerOptions1.snippet("??????");
        markerOptions1.position(latLng1);
        googleMap.addMarker(markerOptions1);

        for (int i=0; i<lats.length; i++) {
            MarkerOptions marker = new MarkerOptions();
            markers[i] = marker
                    .position(new LatLng(lats[i], lngs[i]))
                    .title(titles[i])
                    .snippet(snippets[i])
                    .alpha(0.7f);
//                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));

            googleMap.addMarker(markers[i]);
        }

        googleMap.setOnMarkerClickListener(fram1_1.this);

        CircleOptions[] circle15KM = new CircleOptions[latLngasd.length]; // ????????? ??????

        for (int i = 0; i < latLngasd.length; i++){
            circle15KM[i] = new CircleOptions().center(latLngasd[i])
                    .radius(15000) // m ??????
                    .strokeWidth(0f);
            Log.d("te",mama[i]+"");
            if (mama[i] >= limited) {
                    circle15KM[i].fillColor(Color.parseColor("#80ff0000"));
            }else {
                circle15KM[i].fillColor(Color.parseColor("#800000ff"));
            }
            googleMap.addCircle(circle15KM[i]); // ????????? ??????
        }

        // ?????? ?????? ??????
        googleMap.getUiSettings().setZoomControlsEnabled(true);

        // ????????? ?????? ????????????
        googleMap.getUiSettings().setMapToolbarEnabled(false);

        IndoorBuilding building = googleMap.getFocusedBuilding();
        if (building != null) {
            int activeLevelIndex = building.getActiveLevelIndex();
            IndoorLevel activeLevel = building.getLevels().get(activeLevelIndex);
        }

        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED){
            googleMap.setMyLocationEnabled(true);
        }else {
//            checkLocationPermissionWithRationale();
        }
    }


    @Override
    public boolean onMarkerClick(@NonNull Marker marker) {

//        LatLng latLng = new LatLng(35.907757,127.766922);

        Toast.makeText(getActivity(), marker.getTitle() +" ??? ????????? ????????? ????????? ????????? ?????????", Toast.LENGTH_LONG).show();
//        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(marker.getPosition(), 10));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(marker.getPosition(), 10));
        Bundle bundle = new Bundle();
        btn_check.setVisibility(view.VISIBLE);
        btn_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bundle.putString("marker", marker.getTitle());
                Log.d("te",marker.getTitle()+"");
                fram1_2.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.fram_ch_fram,fram1_2).commit();
            }
        });
        return false;
    }

}
