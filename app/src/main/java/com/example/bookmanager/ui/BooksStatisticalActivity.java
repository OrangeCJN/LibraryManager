package com.example.bookmanager.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.example.bookmanager.R;
import com.example.bookmanager.callback.FirebaseQueryResult;
import com.example.bookmanager.model.Book;
import com.example.bookmanager.model.ChartBean;
import com.example.bookmanager.ui.base.BaseActivity;
import com.example.bookmanager.util.ChartUtil;
import com.example.bookmanager.util.FilterUtil;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

// work 3.2
// use MPAndroidChart https://github.com/PhilJay/MPAndroidChart
public class BooksStatisticalActivity extends BaseActivity {

    private BarChart barChart;
    private ChartUtil mChartUtil;
    private List<ChartBean> mChartBeans;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Books Statistical");
        initChartBeans();
        barChart = findViewById(R.id.bar_chart);
        Description description = new Description();
        description.setText("");
        barChart.setDescription(description);
        mChartUtil = new ChartUtil(barChart);
        mChartUtil.initBarChart(barChart);
    }

    @Override
    protected int getContentId() {
        return R.layout.activity_chart_borrow;
    }

    /**
     * 初始化统计数据
     */
    private void initChartBeans() {
        mIDataFetcher.getAllBook(new FirebaseQueryResult() {
            @Override
            public void success(DataSnapshot dataSnapshot) {
                mChartBeans = new ArrayList<>();
                List<Book> books = FilterUtil.filterDataList(dataSnapshot, Book.class);
                if (books == null || books.size() == 0) {
                    /*new AlertDialog.Builder(mActivity)
                            .setTitle("Tip")
                            .setMessage("There's no book data")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
                                }
                            })
                            .create().show();*/
                    return;
                }
                for (Book item : books) {
                    final List<String> users = (item.getUsers() == null ? new ArrayList<String>() : item.getUsers());
                    int realBorrowedSize = getRealBorrowedSize(users); // 获得借书数量
                    mChartBeans.add(new ChartBean(item.getCount() - realBorrowedSize, realBorrowedSize, item.getName()));
                }
                LinkedHashMap<String, List<Integer>> chartDataMap = new LinkedHashMap<>();
                List<String> xValues = new ArrayList<>();
                List<Integer> yValue1 = new ArrayList<>();
                List<Integer> yValue2 = new ArrayList<>();
                List<Integer> colors = Arrays.asList(
                        getResources().getColor(R.color.have_color), getResources().getColor(R.color.borrow_color)
                );

                for (ChartBean chartBean : mChartBeans) {
                    xValues.add(chartBean.getBookName());
                    yValue1.add(chartBean.getHave());
                }
                for (ChartBean chartBean : mChartBeans) {
                    yValue2.add(chartBean.getBorrow());
                }
                chartDataMap.put("remaining", yValue1);
                chartDataMap.put("borrowed", yValue2);

                mChartUtil.showBarChart(xValues, chartDataMap, colors);
            }
        });
    }

    /**
     * 获取真实借书数量，排除正在审核的借书
     * @param users
     * @return
     */
    private int getRealBorrowedSize(List<String> users) {
        if(users == null || users.size() == 0) {
            return 0;
        }
        int totalBorrowed = 0;
        for(String single:users){
            if(!TextUtils.isEmpty(single) && single.indexOf(":waiting_agree") == -1){
                totalBorrowed += 1;
            }
        }
        return totalBorrowed;
    }

}
