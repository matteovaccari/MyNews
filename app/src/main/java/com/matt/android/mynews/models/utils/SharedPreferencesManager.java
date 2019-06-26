package com.matt.android.mynews.models.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import static com.matt.android.mynews.models.utils.Constants.PREF_KEY_NAME;

/**
 * Util class to manage SharedPreferences
 */
public class SharedPreferencesManager {

    private String url = "";
    private String query;
    private String beginDate = "";
    private String endDate = "";
    private String checkboxQuery = "";

    private SharedPreferences preferences;
    private Context context;

    //Constructor
    public SharedPreferencesManager (Context context) {
        preferences = context.getSharedPreferences(PREF_KEY_NAME, Context.MODE_PRIVATE);
        this.context = context;
    }

    /**
     * Method who pick up what user typed/selected and put it into related variables
     * who will be used for Search API request
     *
     * @param search_query Search hint EditText
     * @param search_begin_date Begin Date EditText
     * @param search_end_date End Date EditText
     * @param artsCheckBox Arts checkbox
     * @param businessCheckBox Business checkbox
     * @param entrepreneursCheckBox Entrepreneurs checkbox
     * @param politicsCheckBox Politics checkbox
     * @param sportsCheckBox Sports checkbox
     * @param travelCheckBox Travel checkbox
     */
    public void getUserInput(EditText search_query, EditText search_begin_date, EditText search_end_date,
                             CheckBox artsCheckBox, CheckBox businessCheckBox, CheckBox entrepreneursCheckBox,
                             CheckBox politicsCheckBox, CheckBox sportsCheckBox, CheckBox travelCheckBox) {

        //Get what user typed
        query = search_query.getText().toString();

        //Check if date interval isn't null and put dates into related string vars
        if(search_begin_date != null && search_end_date != null) {
            beginDate = search_begin_date.getText().toString();
            beginDate = beginDate.replace("/", "");
            endDate = search_end_date.getText().toString();
            endDate = endDate.replace("/", "");
        }

        //Add every checkbox to a list and in fine fill checkBoxQuery String with every box who is checked
        List<CheckBox> checkBoxList = new ArrayList<>();
        checkBoxList.add(artsCheckBox);
        checkBoxList.add(businessCheckBox);
        checkBoxList.add(politicsCheckBox);
        checkBoxList.add(sportsCheckBox);
        checkBoxList.add(travelCheckBox);
        checkBoxList.add(entrepreneursCheckBox);
        for (CheckBox box : checkBoxList) {
            if(box.isChecked()) {
                checkboxQuery += "\"" + box.getText() + "\"" + "%20";
            }
        }
    }


    /**
     * Save the url in shared preferences
     *
     * @param key the key you want to associate the url
     */
    public void saveUrl(String key){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, url);
        editor.apply();
        checkboxQuery = "";
        Logger.e("url = " + url);
    }

    public String getUrl(){
        return url;
    }

    public void clearInput() {
        checkboxQuery = "";
        url = "";
        query = "";
        beginDate = "";
        endDate = "";
    }

    /**
     * Check that the user has chosen at least one checkbox category and one search query
     *
     * @return true if conditions are met
     */
    public boolean checkConditions() {
        return (!checkboxQuery.isEmpty() && !query.isEmpty());
    }



}
