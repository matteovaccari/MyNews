package com.matt.android.mynews.models.utils;

import android.content.Context;
import android.content.SharedPreferences;

import static com.matt.android.mynews.models.utils.Constants.PREF_KEY_NAME;

/**
 * Util class to manage SharedPreferences
 */
public class SharedPreferencesManager {

    private SharedPreferences preferences;
    private Context context;

    //Constructor
    public SharedPreferencesManager (Context context) {
        preferences = context.getSharedPreferences(PREF_KEY_NAME, Context.MODE_PRIVATE);
        this.context = context;
    }

    public void saveUrl(){

    }


}
