package com.matt.android.mynews.controllers.activities;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Switch;

import com.matt.android.mynews.R;

import butterknife.BindView;

public class NotificationActivity extends AppCompatActivity {

    @BindView(R.id.search_query)
    EditText search_query;
    @BindView(R.id.switch_notification)
    Switch switchNotify;
    @BindView(R.id.travel_check_box)
    CheckBox travelCheckBox;
    @BindView(R.id.sports_check_box)
    CheckBox sportsCheckBox;
    @BindView(R.id.arts_check_box)
    CheckBox artsCheckBox;
    @BindView(R.id.politics_check_box)
    CheckBox politicsCheckBox;
    @BindView(R.id.entrepreneurs_check_box)
    CheckBox entrepreneursCheckBox;
    @BindView(R.id.business_check_box)
    CheckBox businessCheckBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        this.configureToolBar();
    }

    /**
     * Get and set toolbar
     */
    private void configureToolBar() {
        Toolbar toolbar = findViewById(R.id.toolbar_main_activity);
        setSupportActionBar(toolbar);

        //This is for back button
        //Get a support Action bar corresponding to above toolbar
        ActionBar actionBar = getSupportActionBar();
        //Enable the up button
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }
}
