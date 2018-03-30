package com.daixu.dagger.demo.view.order;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;

import com.daixu.dagger.demo.R;
import com.daixu.dagger.demo.view.BaseActivity;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class MyOrderActivity extends BaseActivity implements HasSupportFragmentInjector {
    private List<Fragment> mList;
    private List<String> mTitleList = new ArrayList<>();

    TabLayout mTabLayout;
    ViewPager mViewpager;

    public int position = 0;

    @Inject
    DispatchingAndroidInjector<Fragment> supportFragmentInjector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order);

        mTabLayout = findViewById(R.id.tabLayout);
        mViewpager = findViewById(R.id.viewpager);

        mList = new ArrayList<>();
        mTitleList.add("全部");
        mTitleList.add("待付款");
        mTitleList.add("待发货");
        mTitleList.add("待收货");
        mTitleList.add("已完成");

        MyOrderFragment allFragment = MyOrderFragment.newInstance(0, "");
        mList.add(allFragment);
        MyOrderFragment unpaidFragment = MyOrderFragment.newInstance(1, "");
        mList.add(unpaidFragment);
        MyOrderFragment undeliveredFragment = MyOrderFragment.newInstance(2, "");
        mList.add(undeliveredFragment);
        MyOrderFragment unreceivedFragment = MyOrderFragment.newInstance(3, "");
        mList.add(unreceivedFragment);
        MyOrderFragment completedFragment = MyOrderFragment.newInstance(4, "");
        mList.add(completedFragment);

        MyOrderAdapter mAdapter = new MyOrderAdapter(getSupportFragmentManager());
        mViewpager.setAdapter(mAdapter);

        mTabLayout.setupWithViewPager(mViewpager);
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);

        mTabLayout.post(new Runnable() {
            @Override
            public void run() {
                setIndicator(mTabLayout);
            }
        });

        TabLayout.Tab tab = mTabLayout.getTabAt(position);
        if (null != tab) {
            tab.select();
        }
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return supportFragmentInjector;
    }

    private void setIndicator(TabLayout tabs) {
        Class<?> tabLayout = tabs.getClass();
        Field tabStrip = null;
        try {
            tabStrip = tabLayout.getDeclaredField("mTabStrip");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        LinearLayout llTab = null;
        if (null != tabStrip) {
            tabStrip.setAccessible(true);

            try {
                llTab = (LinearLayout) tabStrip.get(tabs);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        int left = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, Resources.getSystem().getDisplayMetrics());
        int right = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, Resources.getSystem().getDisplayMetrics());

        if (null != llTab) {
            for (int i = 0; i < llTab.getChildCount(); i++) {
                View child = llTab.getChildAt(i);
                child.setPadding(0, 0, 0, 0);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
                params.leftMargin = left;
                params.rightMargin = right;
                child.setLayoutParams(params);
                child.invalidate();
            }
        }
    }

    private class MyOrderAdapter extends FragmentPagerAdapter {

        private MyOrderAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mList.get(position);
        }

        @Override
        public int getCount() {
            return mList.size();
        }

        //重写这个方法，将设置每个Tab的标题
        @Override
        public CharSequence getPageTitle(int position) {
            return mTitleList.get(position);
        }
    }
}
