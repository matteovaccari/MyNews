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

import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //ButterKnife init
        ButterKnife.bind(this);

        //Configure display
        this.configureDisplay();
    }

    /**
     * Inflate and add the menu (buttons) to the toolbar
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_main, menu);
        return true;
    }

    /**
     * Actions to do depending of which button (search, settings) is selected in the home page (toolbar)
     */
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

    /**
     * Allow phone back button to return to default MainActivity home page
     */
    @Override
    public void onBackPressed() {
        if (this.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            this.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    /**
     * Method who update display depending of which button user clicked in NavigationView (drop down menu)
     *
     * @param item tab (fragment) or search / notification button
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.main_activity_menu_top_stories:
                viewPager.setCurrentItem(0);
                break;
            case R.id.main_activity_menu_most_popular:
                viewPager.setCurrentItem(1);
                break;
            case R.id.main_activity_menu_arts:
                viewPager.setCurrentItem(2);
                break;
            case R.id.main_activity_menu_search:
                Toast.makeText(this, "Search option", Toast.LENGTH_SHORT).show();
                break;
            case R.id.main_activity_menu_notifications:
                Toast.makeText(this, "Params notifications", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }

        this.drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * Get and set toolbar
     */
    private void configureToolBar() {
        //Get the toolbar from MainActivity's layout
        this.toolbar = findViewById(R.id.toolbar_main_activity);
        //Set the toolbar
        setSupportActionBar(toolbar);
    }

    /**
     * Get and configure ViewPager and tabs
     */
    private void configureViewPagerAndTabs() {
        //Get ViewPager
        viewPager = (ViewPager) findViewById(R.id.view_pager_main_activity);
        //Link it to adapter
        viewPager.setAdapter(new PageAdapter(getSupportFragmentManager(), this));

        //Get TabLayout
        TabLayout tabs = (TabLayout) findViewById(R.id.tab_layout_main_activity);
        //Link it to viewpager
        tabs.setupWithViewPager(viewPager);
        //Tabs have the same width
        tabs.setTabMode(TabLayout.MODE_FIXED);
    }

    /**
     * Method who link DrawerLayout
     */
    private void configureDrawerLayout() {
        this.drawerLayout = (DrawerLayout) findViewById(R.id.main_activity_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    /**
     * Method who link navigation-view (drop-down menu) and add listeners to each element
     */
    private void configureNavigationView() {
        this.navigationView = (NavigationView) findViewById(R.id.main_activity_navigation_view);
        navigationView.setNavigationItemSelectedListener(this);
    }


    /**
     * Method that unify all UI related management and is called in OnCreate method
     */
    private void configureDisplay() {
        this.configureToolBar();
        this.configureViewPagerAndTabs();
        this.configureDrawerLayout();
        this.configureNavigationView();
    }
}
