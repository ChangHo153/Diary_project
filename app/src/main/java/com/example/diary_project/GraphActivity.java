package com.example.diary_project;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class GraphActivity extends AppCompatActivity {
    private PieChart pieChart;
    private BottomNavigationView bottomNavigationView; //바텀 네비게이션 뷰
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        bottomNavigationView = findViewById(R.id.bottomNavi);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.action_calendar:
                        startActivity(new Intent(GraphActivity.this, MainActivity.class));
                        finish();
                        return true;
                    case R.id.action_view:
                        startActivity(new Intent(GraphActivity.this, ViewActivity.class));
                        finish();
                        return true;
                    case R.id.action_graph:
                        //overridePendingTransition(0, 0); 애니메이션 효과 추가
                        break;
                }
                return false;
            }
        });

        pieChart = findViewById(R.id.ch_piechart);
        setupPieChart();
        loadPieChartData();
    }

    // 차트를 만들기전 호출할 설정 메서드
    private void setupPieChart(){
        pieChart.setDrawHoleEnabled(true); //도넛형사용 메서드
        pieChart.setUsePercentValues(true); // 백분율사용 메서드
        pieChart.setEntryLabelTextSize(12);
        pieChart.setEntryLabelColor(Color.BLACK);
        pieChart.setCenterText("Spending by Category"); //가운데 들어갓 텍스트
        pieChart.setCenterTextSize(24);
        pieChart.getDescription().setEnabled(false);  //차트 설명 비활성화


        Legend l = pieChart.getLegend();   //범례추가
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);  //범례 수직정렬 상단 위치
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);  //수평 정렬 오른쪽 위치
        l.setOrientation(Legend.LegendOrientation.VERTICAL);  //표시될 레이블 방향

        //수직으로 보이기
        l.setDrawInside(false);
        l.setEnabled(true);
    }

    private void loadPieChartData(){

        //원형 차트 들어갈 값들
        ArrayList<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(0.2f, "Food & Dining"));
        entries.add(new PieEntry(0.15f, "Medical"));
        entries.add(new PieEntry(0.10f, "Entertainment"));
        entries.add(new PieEntry(0.25f, "Electricity and Gas"));
        entries.add(new PieEntry(0.3f, "Housing"));


        // 원형 차트에  쓸 차트 색
        ArrayList<Integer> colors = new ArrayList<>();
        for (int color: ColorTemplate.MATERIAL_COLORS) {
            colors.add(color);
        }

        for (int color: ColorTemplate.VORDIPLOM_COLORS) {
            colors.add(color);
        }

        //차트에 색 입히기
        PieDataSet piedataSet = new PieDataSet(entries, "Expense Category");
        piedataSet.setColors(colors);

        //차트에 값 넣기 및 그리기
        PieData pieData = new PieData(piedataSet);
        pieData.setDrawValues(true);
        pieData.setValueFormatter(new PercentFormatter(pieChart));
        pieData.setValueTextSize(12f);
        pieData.setValueTextColor(Color.BLACK);


        pieChart.setData(pieData);
        pieChart.invalidate();

        //pieChart.animateY(1400, Easing.EaseInOutQuad);  //애니효과

    }
}
