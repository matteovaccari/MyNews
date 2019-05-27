package com.matt.android.mynews.controllers.activities;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.matt.android.mynews.R;
import com.matt.android.mynews.adapters.PageAdapter;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.configureDisplay();
    }

    //Inflate and add the menu (icons-button) to the toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_main, menu);
        return true;
    }

    //Actions to do depending of which icon (search, settings) is selected
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
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

    //Allow phone back button to return to default MainActivity home page
    @Override
    public void onBackPressed() {
        if (this.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            this.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.main_activity_menu_option1:
                Toast.makeText(this, "Option 1", Toast.LENGTH_SHORT).show();
                break;
            case R.id.main_activity_menu_option2:
                Toast.makeText(this, "Option 2", Toast.LENGTH_SHORT).show();
                break;
            case R.id.main_activity_menu_option3:
                Toast.makeText(this, "Option 3", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }

        this.drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    //Get and set the toolbar
    private void configureToolBar() {
        //Get the toolbar from MainActivity's layout
        this.toolbar = findViewById(R.id.toolbar_main_activity);
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
        TabLayout tabs = (TabLayout) findViewById(R.id.tab_layout_main_activity);
        //Link it to viewpager
        tabs.setupWithViewPager(pager);
        //Tabs have the same width
        tabs.setTabMode(TabLayout.MODE_FIXED);
    }

    private void configureDrawerLayout() {
        this.drawerLayout = (DrawerLayout) findViewById(R.id.main_activity_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    private void configureNavigationView() {
        this.navigationView = (NavigationView) findViewById(R.id.main_activity_navigation_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void configureDisplay() {
        this.configureToolBar();
        this.configureViewPagerAndTabs();
        this.configureDrawerLayout();
        this.configureNavigationView();
    }
}
