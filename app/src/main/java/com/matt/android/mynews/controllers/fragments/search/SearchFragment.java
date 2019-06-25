package com.matt.android.mynews.controllers.fragments.search;


import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.matt.android.mynews.R;
import com.matt.android.mynews.controllers.activities.ResultActivity;
import com.matt.android.mynews.models.utils.Logger;

import java.util.Calendar;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.app.SearchManager.QUERY;

/**
 * Fragment who will contain all search-related options (date,key words, section)
 */
public class SearchFragment extends Fragment implements View.OnClickListener {

    // --- FOR DESIGN
    //For date (begin + end date)
    @BindView(R.id.search_fragment_begin_date)
    TextView beginDate;
    @BindView(R.id.search_fragment_end_date)
    TextView endDate;
    //Hint edit text
    @BindView(R.id.search_fragment_edit_text)
    EditText editText;
    //Search Button
    @BindView(R.id.search_fragment_button_search)
    Button searchButton;
    //CheckBox
    @BindView(R.id.search_fragment_arts_check_box)
    CheckBox artsCheckBox;
    @BindView(R.id.search_fragment_business_check_box)
    CheckBox businessCheckBox;
    @BindView(R.id.search_fragment_entrepreneurs_check_box)
    CheckBox entrepreneursCheckBox;
    @BindView(R.id.search_fragment_politics_check_box)
    CheckBox politicsCheckBox;
    @BindView(R.id.search_fragment_sports_check_box)
    CheckBox sportsCheckBox;
    @BindView(R.id.search_fragment_travel_check_box)
    CheckBox travelCheckBox;

    // Date
    private String dateBeginForData;
    private String endDateForData;
    private DatePickerDialog.OnDateSetListener onDateSetListenerBeginDate;
    private DatePickerDialog.OnDateSetListener onDateSetListenerEndDate;
    private String dateBeginText;
    private String endDateText;
    public static final String DATE_BEGIN = "dateBegin";
    public static final String END_DATE = "endDate";
    public static final String FILTER = "filter";
    // Query & Filter
    private String query;
    private String filter;
    private SearchFragment.OnButtonClickedListener callback;

    //Constructor
    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        //Initialize ButterKnife
        ButterKnife.bind(this, view);

        this.getEditTextForQuery();
        // Display date
        this.getDisplayBeginDate();
        this.getDisplayDateEnding();
        //Search button
        this.getSearchButton();

        return view;
    }

    /**
     * Override method from View.OnClickListener, things to do when user click on Search Button
     * @param view View
     */
    @Override
    public void onClick(View view) {
        // CheckBox
        getCheckBox();
        callback.onButtonClicked(view);
        Intent intentResultActivity = new Intent(getActivity(), ResultActivity.class); //Result Activity have to be created
        // put data into activity
        intentResultActivity.putExtra(QUERY, getQuery());
        intentResultActivity.putExtra(DATE_BEGIN, dateBeginForData);
        intentResultActivity.putExtra(END_DATE, endDateForData);
        intentResultActivity.putExtra(FILTER, filter);

        if (!filter.equals("")) {
            startActivity(intentResultActivity);
        }
    }
    /**
     * Configure CheckBox
     */
    protected void getCheckBox() {
        filter = "";
        if (artsCheckBox.isChecked()) {
            filter = "Arts";
        }
        if (businessCheckBox.isChecked()) {
            filter = "Business";
        }
        if (entrepreneursCheckBox.isChecked()) {
            filter = "Entrepreneurs";
        }
        if (politicsCheckBox.isChecked()) {
            filter = "Politics";
        }
        if (sportsCheckBox.isChecked()) {
            filter = "Sports";
        }
        if (travelCheckBox.isChecked()) {
            filter = "Travel";
        }
        if (filter.equals("")) {
            Toast.makeText(getContext(), R.string.error_message_search_fragment, Toast.LENGTH_SHORT).show();
        }
    }
    /**
     * Button Listener
     */
    public interface OnButtonClickedListener {
        void onButtonClicked(View view);
    }

    /**
     * Enable Search Button when user typed something in edit text
     */
    private void getEditTextForQuery() {
        //Edit text
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence text, int start, int before, int count) {
                searchButton.setEnabled(text.toString().length() != 0);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }
    /**
     * Method who return what user typed
     * @return String query
     */
    private String getQuery() {
        query = "";
        query = editText.getText().toString();
        Log.i("Query", query);
        return query;
    }

    /**
     * Date Display
     */
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

                dateBeginText = dayOfMonth + "/" + month + "/" + year;

                dateBeginForData = dayOfMonth + "/" + month + "/" + year;
                if (month < 10) {
                    dateBeginForData = year + "0" + month + "" + dayOfMonth;
                } else dateBeginForData = year + "" + month + "" + dayOfMonth;

                beginDate.setText(dateBeginText);
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
                endDateText = dayOfMonth + "/" + month + "/" + year;
                if (month < 10) {
                    endDateForData = year + "0" + month + "" + dayOfMonth;
                } else endDateForData = year + "" + month + "" + dayOfMonth;
                System.out.println(endDateForData);
                endDate.setText(endDateText);
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
                day, month, year);
        dialog.getDatePicker().setMaxDate(cal.getTimeInMillis());
        dialog.getDatePicker().setMinDate(cal.getTimeInMillis() - 86400000);
        dialog.show();
    }

    private void createCallbackToParentActivity() {
        try {
            callback = (OnButtonClickedListener) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(e.toString() + "must implement OnButtonClickListener");
        }
    }

    private void getSearchButton() {
        searchButton.setOnClickListener(this);
        // Search Button is not enabled
        searchButton.setEnabled(false);
    }


    /**
     * Attach the fragment with activity
     * @param context Context
     */ /*
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.createCallbackToParentActivity();
    } */
}
