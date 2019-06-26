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
     * Method who link together every search info (what user typed + checkbox + date interval)
     * into a unique url (String) who will be used for API request
     */
    public void createSearchUrlForAPIRequest(){

        //Add default url
        url = "http://api.nytimes.com/svc/search/v2/articlesearch.json?&" +
                 "api-key=BCdKtxQWdZXLs9x3O9S4CY1cAJUgTCmm&fq=news_desk:("

                //Add checkbox filters
                + checkboxQuery

                //Add what user typed
                +")&q=";
        if(!query.isEmpty()) {
            url += query;
        }

        //Add date interval
        if (!beginDate.isEmpty()) {
            url += "&begin_date=" + beginDate;
        }
        if(!endDate.isEmpty()) {
            url += "&end_date=" + endDate;
        }
        //Sort results by newest
        url += "&sort=newest";
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

    /**
     * Method call to clear last input to avoid error like user can search using last search elements
     */
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
