package com.example.mapapap;

import android.graphics.Color;
import android.util.Log;
import android.widget.EditText;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.List;

public class Line {
   private static float threshold;
   //     static void line(LineChart lineChart, ArrayList<String> xVals, dArrayList<Integer> timelist, ArrayList<Double> temlist, EditText edit, int limited){
   static void line(LineChart lineChart, ArrayList<String> xVals, ArrayList<Double> timelist, ArrayList<Double> temlist, String edit, int limited, int cate){

      Log.d("check", "확인");

      Log.d("check2", timelist.size()+"");

//         if (timelist.size() != 0){
//             lineChart.invalidate();
//             //lineChart.clear();
//             Log.d("go","초기화");
//         }

      List<Entry> entries = new ArrayList<>();
      List<Entry> entries2 = new ArrayList<>();
      if (cate == 1) {
         threshold = 10f;
      }
      else {
         threshold = 0.5f;
      }

//        for (int i = 0; i < temlist.size() - Integer.valueOf(String.valueOf(edit.getText())); i++){
      for (int i = 0; i < temlist.size(); i++){

         entries.add(new Entry(Float.parseFloat(String.valueOf(timelist.get(i))),Float.parseFloat(String.valueOf(temlist.get(i)))));

      }

      for (int i = 0; i < temlist.size()-limited; i++){

         entries2.add(new Entry(Float.parseFloat(String.valueOf(timelist.get(i+limited))),Float.parseFloat(String.valueOf(temlist.get(i+limited)))));

      }

      LineDataSet linedataSet = new LineDataSet(entries, "과거");
      LineDataSet linedataSet2 = new LineDataSet(entries2, "예측");



      lineChart.setBackgroundColor(Color.rgb(255,255,255)); // 배경색
      lineChart.getDescription().setEnabled(true); // 그리드 파선 ??
//      lineChart.setPinchZoom(true);
      lineChart.setTouchEnabled(true); // 터치기능?

      // set listeners
//        lineChart.setOnChartValueSelectedListener(this);
      lineChart.setDrawGridBackground(true);

      //값이 선택될 때 상자를 표시할 마커 만들기
      //MyMarkerView mv = new MyMarkerView(getActivity(), R.layout.custom_marker_view);
      //차트에 마커 설정
      //mv.setChartView(lineChart);
      //lineChart.setMarker(mv);

      linedataSet.setLineWidth(3); //라인 두께
      linedataSet.setCircleRadius(6); // 점 크기
      linedataSet.setDrawCircleHole(true); // 원의 겉 부분 칠할거?
      linedataSet.setDrawCircles(true);
      linedataSet.setCircleColor(Color.rgb(255, 155, 155)); // 점 색깔
      linedataSet.setColor(Color.rgb(0, 0, 255)); // 라인 색깔
      linedataSet.setDrawHorizontalHighlightIndicator(false);
      linedataSet.setDrawHighlightIndicators(false);
      linedataSet.setDrawValues(false);
// ----------------------------------------------------------------------------------------------
      linedataSet2.setLineWidth(3); //라인 두께
      linedataSet2.setCircleRadius(6); // 점 크기
      linedataSet2.setDrawCircleHole(true); // 원의 겉 부분 칠할거?
      linedataSet2.setDrawCircles(true);
      linedataSet2.setCircleColor(Color.rgb(255, 155, 155)); // 점 색깔
      linedataSet2.setColor(Color.rgb(255, 0, 0)); // 라인 색깔
      linedataSet2.setDrawHorizontalHighlightIndicator(false);
      linedataSet2.setDrawHighlightIndicators(false);
      linedataSet2.setDrawValues(false);

      ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
      dataSets.add(linedataSet);
      dataSets.add(linedataSet2);
      // ----------------------------------------------------------------------------------------
      // 안내선

      // y 축
      //Typeface tf = Typeface.createFromAsset(getActivity().getAssets(),"OpenSans-Light.ttf"); // 글씨체
      LimitLine limY = new LimitLine(threshold, "위험선");
      limY.setLineWidth(5f);
      limY.enableDashedLine(0.5f, 0f, 0f);
      limY.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
      limY.setTextSize(20f);
//        limY.setTypeface(mTf); // 글씨체
//        limY.setLineColor(lineChart.getContext().getResources().getColor(R.color.teal_200));

      // x축
//      LimitLine limX = new LimitLine(limited, "예측");
      LimitLine limX = new LimitLine(limited, "");
      limX.setLineWidth(5f);
      limX.enableDashedLine(0.5f, 0f, 0f);
      limX.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
      limX.setTextSize(100f);
//        limX.setTypeface(mTf); // 글씨체
      limX.setLineColor(lineChart.getContext().getResources().getColor(R.color.teal_200));

      // y 축 안내선
      YAxis leftAxis = lineChart.getAxisLeft();
      leftAxis.removeAllLimitLines(); // reset all limit lines to avoid overlapping lines
      leftAxis.addLimitLine(limY);
      leftAxis.setAxisMaximum(threshold * 1.5f );
      // x 축 안내선
      XAxis rAxis = lineChart.getXAxis(); // x축 설정
      rAxis.removeAllLimitLines();
      rAxis.addLimitLine(limX);


//        LimitLine upper_limit = new LimitLine(5, "Max Value");
//        upper_limit.setLineWidth(500f);
//        upper_limit.enableDashedLine(1f, 1f, 0f);
//        upper_limit.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
//        upper_limit.setTextSize(500f);
//
//
//        LimitLine lower_limit = new LimitLine(3, "Min Value");
//        lower_limit.setLineWidth(500f);
//        lower_limit.enableDashedLine(1f, 1f, 0f);
//        lower_limit.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
//        lower_limit.setTextSize(500f);
//        dataSets.add(LimitLine(upper_limit));
//        dataSets.addLimitLine(upper_limit);
//        dataSets.addLimitLine(lower_limit);
//        dataSets.setDrawLimitLinesBehindData(true);

      //--------------------------------------------------------------------------------
      LineData lineData = new LineData(dataSets);
      lineData.setValueTextSize(15); //no working
      lineChart.setData(lineData);

//        lineChart.setVisibleXRangeMaximum(5); //가로 스크롤 생김 + 스크롤 넘어가기전 표출되는 데이터 값

      // 크기 조정 및 드래그 활성화
      lineChart.setDragEnabled(true);
      lineChart.setScaleEnabled(true);
      lineChart.setScaleXEnabled(true);
      lineChart.setScaleYEnabled(true);
      lineChart.setPinchZoom(true); //zoom 기능
//        lineChart.moveViewToX(1);
      lineChart.setScrollContainer(true);
      //-----------------------------------------------------------------------------------------------


// ---------------------------------------------------------------------------------------------------------

      XAxis xAxis = lineChart.getXAxis(); // x축 설정
      xAxis.setPosition(XAxis.XAxisPosition.BOTTOM); // x축 데이터 표시 위치
      xAxis.setLabelRotationAngle(-85.0f);
      xAxis.setTextSize(10); // 레이블 텍스트 사이즈
      xAxis.setLabelCount(timelist.size()); //x축의 데이터를 최대 몇 개 까지 나타낼지에 대한 설정
      xAxis.setTextColor(Color.RED); // x 축 색
//        xAxis.enableGridDashedLine(0, 0, 0); // 수직 격자선
//        xAxis.setSpaceMax(1f); // 오른쪽으로 얼마나 남았는가
      xAxis.setValueFormatter(new IndexAxisValueFormatter(xVals));
//        xAxis.enableGridDashedLine(10, 24, 0); //수직 격자선

      //Grid, AxisLine 표시 설정
      xAxis.setDrawGridLines(true);
      xAxis.setDrawAxisLine(true);

      xAxis.setGranularity(1f); // x축 확대 시 늘어남ㄱ밧??

      YAxis yaxis = lineChart.getAxisLeft(); // Y축 설정
      yaxis.setTextSize(10);
      yaxis.setLabelCount(temlist.size()); //y축의 데이터를 최대 몇 개 까지 나타낼지에 대한 설정
      yaxis.setTextColor(Color.BLUE); // Y축 색
      yaxis.setGranularity(0.01f); // y축 확대 시 늘어남ㄱ밧??
      yaxis.setAxisMinimum(0f);
      yaxis.setDrawLabels(true);
      yaxis.setDrawAxisLine(true);
      yaxis.setDrawGridLines(true);

      YAxis RyAxis = lineChart.getAxisRight();
      RyAxis.setDrawLabels(false);
      RyAxis.setDrawAxisLine(false);
      RyAxis.setDrawGridLines(false);

//        lineChart.highlightValue
//        isDragEnabled : 드레그 true/false
//        isScaleYEnabled : y축으로 늘리기 true/false
//        isScaleXEnabled : x축으로 늘리기 true/false
//
//        xAxis.isEnabled : x축 노출 true/false
//        axisLeft.isEnabled : y축 왼쪽 노출 true/ false
//        axisLeft.setDrawAxisLine : y축 왼쪽 라인 노출 true/ false
//        axisLeft.setDrawGridLines : y축 왼쪽 라인 gride 노출 true/ false
//        axisLeft.setDrawLabels : y축 왼쪽 라인에 라벨 노출 true/ false
//        axisRight.setDrawAxisLine : y 축 오른쪽 라인 노출 true/ fasle
//        axisRight.setDrawGridLines : y축 오른쪽 라인 gride 노출 true/ false
//        axisRight.isEnabled : y축 오른쪽 라인 사용 true/ false
//        xAxis.setDrawAxisLine : x축 노출 true/ false
//        xAxis.setDrawGridLines : x축 gride 노출 true/ false
//
//        yAxis = axisLeft : y축 왼쪽 획득yAxis.axisMaximum : y축 최고값 셋팅yAxis.axisMinimum : y 축 최저값 셋팅
//        highlightValue : 그래프 선택시 그 수치를 기준으로 나타나는 십자가 선
//        setMode : 라인 차트일 경우 그려지는 종류를 선택할 수 있습니다. (LINEAR/STEPPED/CUBIC_BEZIER/HORIZONTAL_BEZIER)
//        LimitLine : 제한 선으로 그래프 위에 따로 그려집니다.
//        그리고 라인 색과, 하이 라이트 색을 선택 셋팅 할 수 있으며( 셋팅은 R G B 값을 Hex 값으로 입력)
//        setDrawFilled ; 그래프 하단으로 색상 채우기
//        IFillFormatter : 색상을 어느 수치까지 채울지 선택
//        fillDrawable : 채워질 색상 선택 (그라데이션으로도 가능)
//        setDrawCircles : 각 수치를 원으로 표시
//        animateX : 그래프 그릴 시 x축 애니메이션으로
//        animateY : 그래프 그릴 시 Y축 애니메이션으로
//        invalidate : 다시 그리기
//        legend.isEnabled : 그래프 설명 사용 여부 true/false
//        MarkerView : 그래프에서 수치를 선택 했을 떄 수치와 관련된 정보를 노출할 수 있는 Marker



//        MyMarkerView mv1 = new MyMarkerView(getContext(), R.layout.cusom_marker_view);
//        mv1.setChartView(lineChart);
//        lineChart.setMarker(mv1);

      // 마커 붙이기
      MyMarkerView marker = new MyMarkerView(lineChart.getContext(), R.layout.markerviewtext, xVals, cate);
      marker.setChartView(lineChart);
      lineChart.setMarker(marker);

      Description description = new Description();
      description.setText("");
//--------------------------------------------------------------------------------------------------------


      lineChart.setDoubleTapToZoomEnabled(false);
      lineChart.setDrawGridBackground(false);
      lineChart.setDescription(description);
//        lineChart.animateY(2000, Easing.EaseInCubic);




      lineChart.setData(lineData);



   }
}