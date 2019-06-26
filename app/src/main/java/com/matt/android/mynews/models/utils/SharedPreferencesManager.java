package com.matt.android.mynews.models.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

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
