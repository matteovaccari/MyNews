package com.matt.android.mynews.controllers.fragments.search;


import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.matt.android.mynews.R;
import com.matt.android.mynews.controllers.activities.ResultActivity;
import com.matt.android.mynews.models.utils.Logger;
import com.matt.android.mynews.models.utils.SharedPreferencesManager;

import java.util.Calendar;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.matt.android.mynews.models.utils.Constants.PREF_KEY_SEARCH;

/**
 * Fragment who will contain all search-related options (date,key words, section)
 */
public class SearchFragment extends Fragment {

    // --- FOR DESIGN
    //For date (begin + end date)
    @BindView(R.id.search_fragment_begin_date)
    TextView beginDate;
    @BindView(R.id.search_fragment_end_date)
    TextView endDate;
    //Hint edit text
    @BindView(R.id.search_query)
    EditText search_query;
    //Search Button
    @BindView(R.id.search_fragment_button_search)
    Button searchButton;
    //CheckBox
    @BindView(R.id.arts_check_box)
    CheckBox artsCheckBox;
    @BindView(R.id.business_check_box)
    CheckBox businessCheckBox;
    @BindView(R.id.entrepreneurs_check_box)
    CheckBox entrepreneursCheckBox;
    @BindView(R.id.politics_check_box)
    CheckBox politicsCheckBox;
    @BindView(R.id.sports_check_box)
    CheckBox sportsCheckBox;
    @BindView(R.id.travel_check_box)
    CheckBox travelCheckBox;

    protected SharedPreferencesManager preferences;
    // Date
    private DatePickerDialog.OnDateSetListener onDateSetListenerBeginDate;
    private DatePickerDialog.OnDateSetListener onDateSetListenerEndDate;
    private String beginDateText;
    private String endDateText;
    private String beginDateDisplay;
    private String endDateDisplay;

    //Constructor
    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        //Initialize ButterKnife
        ButterKnife.bind(this, view);

        // Display date
        this.getDisplayBeginDate();
        this.getDisplayDateEnding();
        this.getUrlForSearchQuery();

        return view;
    }

    private void getUrlForSearchQuery(){
        preferences = new SharedPreferencesManager(this.getActivity().getApplicationContext());

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Get user input
                preferences.getUserInput(search_query, beginDateText, endDateText, artsCheckBox, travelCheckBox,
                        politicsCheckBox, businessCheckBox, entrepreneursCheckBox, sportsCheckBox);

                //Start result activity if conditions are met
                if (preferences.checkConditions()) {

                    //Create url from user input + save it
                    preferences.createSearchUrlForAPIRequest();
                    preferences.saveUrl(PREF_KEY_SEARCH);

                    startActivity(new Intent(getContext(), ResultActivity.class));
                } else {
                    preferences.clearInput();
                    Toast.makeText(getContext(), "Please select at least one categorie and a keyword", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }



    //---- Date display ----

    private void getDisplayBeginDate() {
        // Date of begin
        beginDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createDisplay(onDateSetListenerBeginDate);
            }
        });

        onDateSetListenerBeginDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                month = month + 1;

                beginDateText ="" + year + month + dayOfMonth;

                //Set date display different from what will be used in API request
                if (month < 10 && dayOfMonth > 10) {
                    beginDateText = year + "0" + month + dayOfMonth;
                    beginDateDisplay = year + "/0" + month + "/" + dayOfMonth;
                } else if (month < 10 && dayOfMonth < 10) {
                    beginDateDisplay = year + "/0" + month + "/0" + dayOfMonth;
                } else {
                    beginDateDisplay = year + "/" + month + "/" + dayOfMonth;
                }

                Logger.e("begin1 : " + beginDateText);
                Logger.e("begin2 : " + beginDateDisplay);
                beginDate.setText(beginDateDisplay);
            }

        };
    }
    private void getDisplayDateEnding() {
        // End of date
        endDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createDisplay(onDateSetListenerEndDate);

            }
        });

        onDateSetListenerEndDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;

                endDateText = "" +year + month + dayOfMonth;

                //Set date display different from what will be used in API request
                if (month < 10 && dayOfMonth > 10) {
                    endDateText = year + "0" + month + dayOfMonth;
                    endDateDisplay = year + "/0" + month + "/" + dayOfMonth;
                } else if (month < 10 && dayOfMonth < 10) {
                    endDateDisplay = year + "/0" + month + "/0" + dayOfMonth;
                } else {
                    endDateDisplay = year + "/" + month + "/" + dayOfMonth;
                }

                Logger.e("end1 : " + endDateText);
                Logger.e("end2 : " + endDateDisplay);
                endDate.setText(endDateDisplay);
            }
        };
    }

    /**
     * Date Listener
     * @param dateSetListener OnDate Listener
     */
    private void createDisplay(DatePickerDialog.OnDateSetListener dateSetListener) {
        Calendar cal = Calendar.getInstance();
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int month = cal.get(Calendar.MONTH);
        int year = cal.get(Calendar.YEAR);
        DatePickerDialog dialog = new DatePickerDialog(
                Objects.requireNonNull(getActivity()),
                R.style.DatePickerDialogTheme,
                dateSetListener,
                year, month , day);
        dialog.getDatePicker().setMaxDate(cal.getTimeInMillis());
        cal.add(Calendar.YEAR, -20);
        dialog.getDatePicker().setMinDate(cal.getTimeInMillis());
        dialog.show();
    }

}
