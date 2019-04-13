package com.example.bookmanager.util;

import android.graphics.Color;
import android.support.annotation.ColorRes;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

// work 3.2
//// use MPAndroidChart https://github.com/PhilJay/MPAndroidChart
public class ChartUtil {

    private BarChart barChart;
    private YAxis leftAxis;
    private YAxis rightAxis;
    private XAxis xAxis;
    private Legend legend;

    public ChartUtil(BarChart barChart) {
        this.barChart = barChart;
    }

    public void initBarChart(BarChart barChart) {
        barChart.setBackgroundColor(Color.WHITE);
        barChart.setDrawGridBackground(false);
        barChart.setDrawBarShadow(false);
        barChart.setHighlightFullBarEnabled(false);
        barChart.setDrawBorders(true);
        barChart.animateY(1000, Easing.getEasingFunctionFromOption(Easing.EasingOption.Linear));
        barChart.animateX(1000, Easing.getEasingFunctionFromOption(Easing.EasingOption.Linear));

        xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setAxisMinimum(0f);
        xAxis.setGranularity(1f);

        leftAxis = barChart.getAxisLeft();
        rightAxis = barChart.getAxisRight();
        leftAxis.setAxisMinimum(0f);
        rightAxis.setAxisMinimum(0f);

        legend = barChart.getLegend();
        legend.setForm(Legend.LegendForm.LINE);
        legend.setTextSize(11f);
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        legend.setDrawInside(false);

    }

    public void showBarChart(final List<String> xValues, LinkedHashMap<String, List<Integer>> dataLists,
                             @ColorRes List<Integer> colors) {
        List<IBarDataSet> dataSets = new ArrayList<>();
        int currentPosition = 0;

        for (LinkedHashMap.Entry<String, List<Integer>> entry : dataLists.entrySet()) {
            String name = entry.getKey();
            List<Integer> yValueList = entry.getValue();

            List<BarEntry> entries = new ArrayList<>();

            for (int i = 0; i < yValueList.size(); i++) {
                entries.add(new BarEntry(i, yValueList.get(i)));
            }
            BarDataSet barDataSet = new BarDataSet(entries, name);
            initBarDataSet(barDataSet, colors.get(currentPosition));
            dataSets.add(barDataSet);

            currentPosition++;
        }

        xAxis.setValueFormatter(new IndexAxisValueFormatter(xValues));

        BarData data = new BarData(dataSets);
        barChart.setScaleY(1);
        barChart.setData(data);
        int barAmount = dataLists.size();
        float groupSpace = 0.3f;
        float barWidth = (1f - groupSpace) / barAmount;
        float barSpace = 0f;
        data.setBarWidth(barWidth);
        data.groupBars(0f, groupSpace, barSpace);
        xAxis.setAxisMinimum(0f);
        xAxis.setAxisMaximum(xValues.size());
        xAxis.setCenterAxisLabels(true);
    }

    public void initBarDataSet(BarDataSet barDataSet, int color) {
        barDataSet.setColor(color);
        barDataSet.setFormLineWidth(1f);
        barDataSet.setFormSize(15.f);
        barDataSet.setDrawValues(false);
    }
}
