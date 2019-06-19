package com.matt.android.mynews.controllers.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import com.matt.android.mynews.R;

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        this.configureToolBar();
    }

    /**
     * Inflate the menu and add it to the toolbar
     * @param menu BackButton
     * @return true
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_search, menu);
        return true;
    }

    /**
     * Get and set toolbar
     */
    private void configureToolBar(){
        Toolbar toolbar = findViewById(R.id.search_activity_toolbar);
        setSupportActionBar(toolbar);
    }
}
