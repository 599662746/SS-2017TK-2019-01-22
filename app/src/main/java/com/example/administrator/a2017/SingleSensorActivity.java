package com.example.administrator.a2017;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;

public class SingleSensorActivity extends AppCompatActivity {

    private LineChart lineChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_sensor);
        lineChart = findViewById(R.id.lineChart);
        initView();
    }

    private void initView() {
        ArrayList<Entry> values1 = new ArrayList<>();
        ArrayList<Entry> values2 = new ArrayList<>();

        values1.add(new Entry(4,10));
        values1.add(new Entry(6,15));
        values1.add(new Entry(9,20));
        values1.add(new Entry(12,5));
        values1.add(new Entry(15,30));

        values2.add(new Entry(3,110));
        values2.add(new Entry(6,115));
        values2.add(new Entry(9,130));
        values2.add(new Entry(12,85));
        values2.add(new Entry(15,90));
        LineDataSet set1;
        LineDataSet set2;
        set1 = new LineDataSet(values1, "测试数据1");
        set2 = new LineDataSet(values2, "测试数据2");
        ArrayList<LineDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1); // add the datasets
        dataSets.add(set2);
        //创建LineData对象 属于LineChart折线图的数据集合
        LineData data = new LineData();
        data.addDataSet(set1);
        data.addDataSet(set2);
        // 添加到图表中
        lineChart.setData(data);


        XAxis xAxis=lineChart.getXAxis();
        xAxis.setTextColor(Color.BLACK);
        // 如果设置为true，则在绘制时会避免“剪掉”在x轴上的图表或屏幕边缘的第一个和最后一个坐标轴标签项。
        xAxis.setAvoidFirstLastClipping(true);
        // 几个x坐标轴之间才绘制？
//        xAxis.setSpaceBetweenLabels(5);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setEnabled(true);

        //绘制图表
        lineChart.invalidate();
    }

}
