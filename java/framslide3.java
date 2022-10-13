package com.example.mapapap;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class framslide3 extends Fragment {

    View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.framslide3, container, false);


        return view;
    }
    private void setInit(View view) {
        TextView textView = view.findViewById(R.id.fram_ch_text3); //여기에 각자 텍스트 뷰 참조 가능
    }
}
