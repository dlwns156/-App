package com.example.mapapap;

import android.content.Context;
import android.util.Log;
import android.widget.TextView;

import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Utils;

import java.util.ArrayList;

public class MyMarkerView extends MarkerView {

    private TextView tvContent;
    int cate;
    ArrayList<String> xvallist2 = new ArrayList<>();

    public MyMarkerView(Context context, int layoutResource, ArrayList<String> xVals, int cate) {
        super(context, layoutResource);
        this.cate = cate;
        xvallist2 = xVals;
        tvContent = (TextView)findViewById(R.id.tvContent);
    }

    // callbacks everytime the MarkerView is redrawn, can be used to update the
    // content (user-interface)
    @Override
    public void refreshContent(Entry e, Highlight highlight) {

        if (e instanceof CandleEntry) {

            CandleEntry ce = (CandleEntry) e;

            tvContent.setText("" + Utils.formatNumber(ce.getHigh(), 0, true));

        } else {


            int xValue = (int)e.getX();
//            String xValue = e.getData().toString();
            float yValue = (float)e.getY();

            Log.d("tafsde", xvallist2.get(xValue) +"");
            if (cate == 1) {
                tvContent.setText("수온 : "+yValue+"°C"+System.getProperty("line.separator")+"날짜 : "+xvallist2.get(xValue));
            }
            else {
                tvContent.setText("질산질소 : "+yValue+"μM"+System.getProperty("line.separator")+"날짜 : "+xvallist2.get(xValue));
            }


        }

        super.refreshContent(e, highlight);
    }

    @Override
    public MPPointF getOffset() {
        return new MPPointF(-(getWidth() / 2), -getHeight());
    }

}