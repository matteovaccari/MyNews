package com.matt.android.mynews.controllers.activities;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.matt.android.mynews.R;
import com.matt.android.mynews.adapters.PageAdapter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.configureToolBar();
        this.configureViewPagerAndTabs();
    }

    //Inflate and add the menu (icons-button) to the toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_main, menu);
        return true;
    }

    //Actions to do depending of which icon (search, settings) is selected
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.search_button_main_activity:
                Toast.makeText(this, "This will launch search feature", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.settings_button_main_activity:
                Toast.makeText(this, "This will launch settings feature", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //Get and set the toolbar
    private void configureToolBar() {
        //Get the toolbar from MainActivity's layout
        Toolbar toolbar = findViewById(R.id.toolbar_main_activity);
        //Set the toolbar
        setSupportActionBar(toolbar);
    }

    //Get and configure the ViewPager
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
