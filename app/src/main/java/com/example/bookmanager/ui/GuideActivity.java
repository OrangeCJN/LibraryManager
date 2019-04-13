package com.example.bookmanager.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.bookmanager.R;
import com.example.bookmanager.ui.base.BaseActivity;
import com.example.bookmanager.util.SPUtils;

/**
 * 引导页面，第一次打开app时展示
 */
public class GuideActivity extends BaseActivity implements View.OnClickListener {

    private ViewPager pager;
    private TextView skip;
    private TextView enter;
    // 轮播图片资源id数组
    private int[] mImages;

    @Override
    protected int getContentId() {
        return R.layout.activity_guide;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 只有第一次展示
        boolean showGuide = SPUtils.getBoolean("showGuide", true);
        if (!showGuide) {
            gotoMain();
            return;
        }
        SPUtils.putBoolean("showGuide", false);
        pager = (ViewPager) findViewById(R.id.pager);
        skip = (TextView) findViewById(R.id.skip);
        enter = (TextView) findViewById(R.id.enter);
        mImages = new int[]{R.mipmap.guide1, R.mipmap.guide2, R.mipmap.guide3, R.mipmap.guide4, R.mipmap.guide5};
        skip.setOnClickListener(this);
        enter.setOnClickListener(this);
        pager.setOffscreenPageLimit(3);
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            /**
             * 滑动轮播页面的监听
             * @param position
             */
            @Override
            public void onPageSelected(int position) {
                if (position == mImages.length - 1) {
                    skip.setVisibility(View.GONE);
                    enter.setVisibility(View.VISIBLE);
                } else {
                    skip.setVisibility(View.VISIBLE);
                    enter.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        // 为Pager设置适配器
        pager.setAdapter(new PagerAdapter() {

            /**
             * 绑定数据
             * @param container
             * @param position
             * @return
             */
            @NonNull
            @Override
            public Object instantiateItem(@NonNull ViewGroup container, int position) {
                // 获取控件，展示对应位置的图片资源
                View v = getLayoutInflater().inflate(R.layout.guide_item, container, false);
                ImageView img = v.findViewById(R.id.img);
                Glide.with(mActivity).load(mImages[position]).into(img);
                container.addView(v);
                return v;
            }

            @Override
            public int getCount() {
                return mImages.length;
            }

            @Override
            public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
                return view == object;
            }

            @Override
            public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
                container.removeView((View) object);
            }
        });
    }

    /**
     * skip 和 enter 的点击事件
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        gotoMain();
    }

    /**
     * 跳转到用户选择页面
     */
    private void gotoMain() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
