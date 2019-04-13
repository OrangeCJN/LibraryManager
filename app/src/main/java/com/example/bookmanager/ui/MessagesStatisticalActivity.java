package com.example.bookmanager.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.bookmanager.Constant;
import com.example.bookmanager.R;
import com.example.bookmanager.callback.FirebaseQueryResult;
import com.example.bookmanager.model.Book;
import com.example.bookmanager.model.ChartBean;
import com.example.bookmanager.model.Comment;
import com.example.bookmanager.ui.base.BaseActivity;
import com.example.bookmanager.util.ChartUtil;
import com.example.bookmanager.util.FilterUtil;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

// work 3.2
// use MPAndroidChart https://github.com/PhilJay/MPAndroidChart
public class MessagesStatisticalActivity extends BaseActivity {

    private PieChart picChart;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Messages Statistical");
        picChart = findViewById(R.id.pic_chart);
        Description description = new Description();
        description.setText("");
        picChart.setDescription(description);
        initChartDatas();
    }

    @Override
    protected int getContentId() {
        return R.layout.activity_chart_message;
    }

    /**
     * 初始化统计数据
     */
    private void initChartDatas() {
        mIDataFetcher.getAllComment(new FirebaseQueryResult() {
            @Override
            public void success(DataSnapshot dataSnapshot) {
                List<Comment> comments = FilterUtil.filterDataList(dataSnapshot, Comment.class);
                if (comments == null || comments.size() == 0) {
                    return;
                }
                int anonymousCount = 0;
                for (Comment c : comments) {
                    if (c.getName().equals(Constant.ANONYMOUS_TAG)) {
                        anonymousCount++;
                    }
                }
                List<PieEntry> strings = new ArrayList<>();
                strings.add(new PieEntry(comments.size() - anonymousCount, "Real-name"));
                strings.add(new PieEntry(anonymousCount, "Anonymous"));

                PieDataSet dataSet = new PieDataSet(strings, "");

                ArrayList<Integer> colors = new ArrayList<Integer>();
                colors.add(getResources().getColor(R.color.have_color));
                colors.add(getResources().getColor(R.color.borrow_color));
                dataSet.setColors(colors);

                PieData pieData = new PieData(dataSet);
                pieData.setDrawValues(true);

                picChart.setData(pieData);
                picChart.invalidate();
            }
        });
    }

}
