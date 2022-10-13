package com.example.mapapap;

import android.app.AlertDialog;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;

public class Barchart {

    static void barchart(View view, JSONObject json) {

        String[] mmlo = {"서울","부산","대구","광주","대전"};

        Log.d("test","바차트 함수 진입");

        HorizontalBarChart mChart = view.findViewById(R.id.bar_chart);
        ArrayList<String> labels = new ArrayList<>();
        ArrayList<Integer> datda0 = new ArrayList<>();
        ArrayList<Integer> datda1 = new ArrayList<>();
        ArrayList<Integer> datda7 = new ArrayList<>();
        ArrayList<Integer> datda30 = new ArrayList<>();
        ArrayList<Integer> datda365 = new ArrayList<>();

        try {

            JSONArray jarray1 = json.getJSONArray("구분");
            JSONArray jarray2 = json.getJSONArray("당일");
            JSONArray jarray3 = json.getJSONArray("1일전");
            JSONArray jarray4 = json.getJSONArray("7일전");
            JSONArray jarray5 = json.getJSONArray("1개월전");
            JSONArray jarray6 = json.getJSONArray("1년전");


            for (int i = 0; i < jarray1.length(); i++){
                labels.add(jarray1.getString(i));
                Log.d("test",labels.toString());
            }

            for (int i = 0; i < jarray2.length(); i++){
                datda0.add(jarray2.getInt(i));
                Log.d("test당일",datda0.toString());
            }

            for (int i = 0; i < jarray3.length(); i++){
                datda1.add(jarray3.getInt(i));
                Log.d("test",datda1.toString());
            }

            for (int i = 0; i < jarray4.length(); i++){
                datda7.add(jarray4.getInt(i));
                Log.d("test",datda7.toString());
            }

            for (int i = 0; i < jarray5.length(); i++){
                datda30.add(jarray5.getInt(i));
                Log.d("test",datda30.toString());
            }

            for (int i = 0; i < jarray6.length(); i++){
                datda365.add(jarray5.getInt(i));
                Log.d("test",datda365.toString());
            }

            // 리스트 역순으로
            Collections.reverse(labels);
            Collections.reverse(datda0);
            Collections.reverse(datda1);
            Collections.reverse(datda7);
            Collections.reverse(datda30);
            Collections.reverse(datda365);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        mChart.setDrawBarShadow(false);
        mChart.getDescription().setEnabled(false);
        mChart.setDragEnabled(true);
        mChart.setPinchZoom(true); // 확대 줌
        mChart.setDrawGridBackground(true);
//        mChart.setDrawValueAboveBar(true);
        mChart.setTouchEnabled(true); // 터치기능?

        XAxis xAxis = mChart.getXAxis();
        xAxis.setLabelCount(5);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
//        xAxis.setCenterAxisLabels(true);
        xAxis.setGranularity(0f);
        xAxis.setGranularityEnabled(false);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(false);
        xAxis.setTextColor(Color.BLACK);
        xAxis.setTextSize(14f);
        xAxis.setAxisLineColor(Color.BLACK);

//        xAxis.setGranularity(10);
//        xAxis.setYOffset(100);

//        xAxis.setAxisMinimum(0f);
//        xAxis.setAxisMaximum(5f);

//        xAxis.setLabelCount(labels.size(), true);

        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setGranularity(10f);
        leftAxis.setTextColor(Color.BLACK);
        leftAxis.setTextSize(14f);
        leftAxis.setAxisLineColor(Color.BLACK);
        leftAxis.setDrawGridLines(true);
//        leftAxis.setLabelCount(10, true);
//        leftAxis.setAxisMinimum(4000f);
//        leftAxis.setAxisMaximum(8000f);
        leftAxis.setGranularityEnabled(true);
//        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
//        leftAxis.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);


        mChart.getAxisRight().setEnabled(false);

        ArrayList<BarEntry> day0 = new ArrayList<>();
        ArrayList<BarEntry> day1 = new ArrayList<>();
        ArrayList<BarEntry> day7 = new ArrayList<>();
        ArrayList<BarEntry> day30 = new ArrayList<>();
        ArrayList<BarEntry> day365 = new ArrayList<>();

        for (int i = 0; i < datda1.size(); i++) {
            day0.add(new BarEntry(i, datda0.get(i)));
            day1.add(new BarEntry(i, datda1.get(i)));
            day7.add(new BarEntry(i, datda7.get(i)));
            day30.add(new BarEntry(i, datda30.get(i)));
            day365.add(new BarEntry(i, datda365.get(i)));
        }

        BarDataSet set1 = new BarDataSet(day0, "당일");
        set1.setValueTextSize(20);
        set1.setDrawValues(true);
        set1.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return (String.valueOf((int) value)) + "원";
            }
        });
        set1.setColor(Color.parseColor("#D5E1DF"));

        BarDataSet set2 = new BarDataSet(day1, "1일전");
        set2.setDrawValues(true);
        set2.setValueTextSize(20);
        set2.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return (String.valueOf((int) value)) + "원";
            }
        });
        set2.setColor(Color.parseColor("#EACACB"));

        BarDataSet set3 = new BarDataSet(day7, "7일전");
        set3.setDrawValues(true);
        set3.setValueTextSize(20);
        set3.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return (String.valueOf((int) value)) + "원";
            }
        });
        set3.setColor(Color.parseColor("#E2B3A3"));

        BarDataSet set4 = new BarDataSet(day30, "한달전");

        set4.setDrawValues(true);
        set4.setValueTextSize(20);
        set4.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return (String.valueOf((int) value)) + "원";
            }
        });
        set4.setColor(Color.parseColor("#A3B6C5"));

        BarDataSet set5 = new BarDataSet(day365, "1년전");
        set5.setDrawValues(true);
        set5.setValueTextSize(20);
        set5.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return (String.valueOf((int) value)) + "원";
            }
        });
        set5.setColor(Color.parseColor("#B1D3C5"));


//        set1.setHighlightEnabled(true);
//        set2.setHighlightEnabled(true);
//        set3.setHighlightEnabled(true);
//        set4.setHighlightEnabled(true);
//        set5.setHighlightEnabled(true);


        ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
        dataSets.add(set5);
        dataSets.add(set4);
        dataSets.add(set3);
        dataSets.add(set2);
        dataSets.add(set1);

        Legend legend = mChart.getLegend();
        LegendEntry l1=new LegendEntry(set1.getLabel(), Legend.LegendForm.DEFAULT,10f,2f,null, set1.getColor());
        LegendEntry l2=new LegendEntry(set2.getLabel(), Legend.LegendForm.DEFAULT,10f,2f,null, set2.getColor());
        LegendEntry l3=new LegendEntry(set3.getLabel(), Legend.LegendForm.DEFAULT,10f,2f,null, set3.getColor());
        LegendEntry l4=new LegendEntry(set4.getLabel(), Legend.LegendForm.DEFAULT,10f,2f,null, set4.getColor());
        LegendEntry l5=new LegendEntry(set5.getLabel(), Legend.LegendForm.DEFAULT,10f,2f,null, set5.getColor());

        legend.setCustom(new LegendEntry[]{l1,l2,l3,l4,l5});

        legend.setTextSize(15);
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        legend.setYOffset(30f);
        legend.setDrawInside(true);
        legend.setOrientation(Legend.LegendOrientation.VERTICAL);
        legend.setEnabled(true);

        BarData data = new BarData(dataSets);

        mChart.setBackgroundColor(Color.parseColor("#ffffff"));
        mChart.setDrawGridBackground(false);
//        mChart.setBackgroundColor(Color.WHITE);
//        data.setDrawValues(true);

        float groupSpace = 0.25f;
        float barSpace = 0f;
        float barWidth = 0.15f;

        data.setBarWidth(barWidth);
//        data.setHighlightEnabled(true);

//        xAxis.setAxisMaximum(labels.length - 1.1f);

        mChart.setData(data);
//        mChart.setScaleEnabled(true);
//        mChart.setVisibleXRangeMaximum(6f);
        mChart.groupBars(-0.47f, groupSpace, barSpace);
        mChart.setDrawValueAboveBar(true);


        mChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
//                int x = mChart.getBarData().getDataSetForEntry(e).getEntryIndex((BarEntry)e);

                Log.d("좌표",mChart.getBarData().getEntryForHighlight(h).getX()+""); // 시간
                Log.d("가격",mChart.getBarData().getEntryForHighlight(h).getY()+""); // 원

                String mlo = mChart.getBarData().getDataSetForEntry(e).getLabel();
                Log.d("일시",mlo+""); // 라벨

                float whk = mChart.getBarData().getEntryForHighlight(h).getX();
                Log.d("좌표",whk+""); // 좌표
                String roro = "";

                if (whk >= 3.73){
                    roro = mmlo[0];
                }else if (whk < 3.73 && whk >= 2.73){
                    roro = mmlo[1];
                }else  if (whk < 2.73 && whk >= 1.73){
                    roro = mmlo[2];

                }else  if (whk < 1.73 && whk >= 0.73){
                    roro = mmlo[3];

                }else  if (whk < 0.73){
                    roro = mmlo[4];

                }

                Log.d("44",roro);

                int rkr = (int) mChart.getBarData().getEntryForHighlight(h).getY();
                Log.d("가격",rkr+""); // 가격

                View nview = LayoutInflater.from(view.getContext()).inflate(R.layout.barmarker,null);

                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                AlertDialog alertDialog;
                TextView t1text = nview.findViewById(R.id.barmar1);
                TextView t2text = nview.findViewById(R.id.barmar2);
                TextView t3text = nview.findViewById(R.id.barmar3);
                t1text.setText(roro);
                t2text.setText(mlo);
                t3text.setText(rkr+"원");
                builder.setView(nview);
                alertDialog = builder.create();
                alertDialog.show();
            }

            @Override
            public void onNothingSelected() {

            }
        });
        mChart.invalidate();
        mChart.animateY(1000); // 밑에서부터 올라오는 애니매이션 적용
    }
}