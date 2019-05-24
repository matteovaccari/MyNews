package com.matt.android.mynews.controllers.activities;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.matt.android.mynews.R;
import com.matt.android.mynews.adapters.PageAdapter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.configureViewPagerAndTabs();
    }

    private void configureViewPagerAndTabs() {
        //Get ViewPager
        ViewPager pager = (ViewPager) findViewById(R.id.view_pager_main_activity);
        //Link it to adapter
        pager.setAdapter(new PageAdapter(getSupportFragmentManager(), this));

        //Get TabLayout
        TabLayout tabs= (TabLayout)findViewById(R.id.tab_layout_main_activity);
        //Link it to viewpager
        tabs.setupWithViewPager(pager);
        //Tabs have the same width
        tabs.setTabMode(TabLayout.MODE_FIXED);
    }
}
