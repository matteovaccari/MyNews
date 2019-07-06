package com.matt.android.mynews.controllers.activities;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import androidx.work.Data;

import com.matt.android.mynews.R;
import com.matt.android.mynews.models.utils.Constants;
import com.matt.android.mynews.models.utils.SharedPreferencesManager;
import com.matt.android.mynews.models.worker.NotificationWorker;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.matt.android.mynews.models.utils.Constants.PREF_KEY_ARTS;
import static com.matt.android.mynews.models.utils.Constants.PREF_KEY_BUSINESS;
import static com.matt.android.mynews.models.utils.Constants.PREF_KEY_ENTREPRENEURS;
import static com.matt.android.mynews.models.utils.Constants.PREF_KEY_NOTIFICATION_URL;
import static com.matt.android.mynews.models.utils.Constants.PREF_KEY_POLITICS;
import static com.matt.android.mynews.models.utils.Constants.PREF_KEY_QUERY;
import static com.matt.android.mynews.models.utils.Constants.PREF_KEY_SPORTS;
import static com.matt.android.mynews.models.utils.Constants.PREF_KEY_SWITCH;
import static com.matt.android.mynews.models.utils.Constants.PREF_KEY_TRAVEL;
import static com.matt.android.mynews.models.utils.Constants.TAG_WORKER;

public class NotificationActivity extends AppCompatActivity {

    SharedPreferencesManager preferences;

    @BindView(R.id.search_query)
    EditText search_query;
    @BindView(R.id.switch_notification)
    Switch switchNotification;
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
        ButterKnife.bind(this);
        this.configureDisplayAndPrefs();
    }

    /**
     * Get and set toolbar
     */
    private void configureToolBar() {
        Toolbar toolbar = findViewById(R.id.toolbar_main_activity);
        setSupportActionBar(toolbar);

        //This is for back button
        if (toolbar != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    /**
     * Check if the switch has been activated by the user and if so, get the last user input
     */
    private void isSwitchChecked(){

        boolean switchIsChecked = preferences.getBoolean(PREF_KEY_SWITCH);
        if (switchIsChecked) {
            //Set switch to checked
            switchNotification.setChecked(true);
            //Set search query
            search_query.setText(preferences.getString(PREF_KEY_QUERY));
            //Check the checkboxes that were selected
            artsCheckBox.setChecked(preferences.getBoolean(PREF_KEY_ARTS));
            politicsCheckBox.setChecked(preferences.getBoolean(PREF_KEY_POLITICS));
            entrepreneursCheckBox.setChecked(preferences.getBoolean(PREF_KEY_ENTREPRENEURS));
            sportsCheckBox.setChecked(preferences.getBoolean(PREF_KEY_SPORTS));
            travelCheckBox.setChecked(preferences.getBoolean(PREF_KEY_TRAVEL));
            businessCheckBox.setChecked(preferences.getBoolean(PREF_KEY_BUSINESS));

        }
    }

    /**
     * Set listener to switch to enable notifications
     */
    private void initNotification() {
        //Open the keyboard automatically
        search_query.setSelection(0);
        Objects.requireNonNull(this.getWindow()).setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        //Set Listener to switch
        switchNotification.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    //Get user input
                    preferences.getUserInput(search_query, null, null,
                            artsCheckBox, businessCheckBox, entrepreneursCheckBox,
                            politicsCheckBox, sportsCheckBox, travelCheckBox);

                    //If at least 1 checkbox is selected and user has put one search query --> enable notifications
                    if(preferences.checkConditions()) {
                        saveNotificationUrlAndState();
                        enableNotification();
                    } else {
                        preferences.clearInput();
                        Toast.makeText(getApplicationContext(), "Please select at least a theme and a keyword", Toast.LENGTH_SHORT).show();
                        switchNotification.toggle();
                    }
                }
                //If switch is unchecked
                else {
                    cancelNotification();
                }
            }
        });
    }

    /**
     * Get the user input and save it in SharedPreferences
     */
    private void saveNotificationUrlAndState() {
        //Switch button
        preferences.putBoolean(PREF_KEY_SWITCH, true);
        //Edit hint text Text Query
        preferences.putString(PREF_KEY_QUERY, search_query.getText().toString());
        //CheckBoxes
        preferences.putBoolean(PREF_KEY_ARTS, artsCheckBox.isChecked());
        preferences.putBoolean(PREF_KEY_POLITICS, politicsCheckBox.isChecked());
        preferences.putBoolean(PREF_KEY_BUSINESS, businessCheckBox.isChecked());
        preferences.putBoolean(PREF_KEY_ENTREPRENEURS, entrepreneursCheckBox.isChecked());
        preferences.putBoolean(PREF_KEY_SPORTS, sportsCheckBox.isChecked());
        preferences.putBoolean(PREF_KEY_TRAVEL, travelCheckBox.isChecked());

        //Save search url
        preferences.createSearchUrlForAPIRequest();
        preferences.saveUrl(PREF_KEY_NOTIFICATION_URL);
    }

    /**
     * Use work manager to run a notification
     */
    private void enableNotification(){
        NotificationWorker.scheduleReminder(Constants.TAG_WORKER);
    }

    /**
     * Cancel notification switch
     */
    private void cancelNotification(){
        preferences.clearInput();
        preferences.putBoolean(PREF_KEY_SWITCH, false);

        NotificationWorker.cancelReminder(TAG_WORKER);

        Toast.makeText(NotificationActivity.this, "Notification disabled", Toast.LENGTH_SHORT).show();
    }

    /**
     * Method called to finish current Activity lifecycle and go back to previous activity
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void configureDisplayAndPrefs() {
        this.configureToolBar();
        preferences = new SharedPreferencesManager(this);
        this.isSwitchChecked();
        this.initNotification();
    }
}
