package com.example.mapapap;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class frammap extends Fragment {

    private View view;
    fram1_1 fram1_1;
    fram1_2 fram1_2;


    public static frammap newInstance(){
        frammap frammap = new frammap();
        return frammap;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.map,container,false);

        fram1_1 = new fram1_1();
        fram1_2 = new fram1_2();

        getChildFragmentManager().beginTransaction().replace(R.id.fram_ch_fram,fram1_1).commit();

        Button btn1 = view.findViewById(R.id.btn_fram_btn1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getChildFragmentManager().beginTransaction().replace(R.id.fram_ch_fram,fram1_1).commit();
            }
        });
        Button btn2 = view.findViewById(R.id.btn_fram_btn2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getChildFragmentManager().beginTransaction().replace(R.id.fram_ch_fram,fram1_2).commit();
            }
        });


        return view;
    }
}
