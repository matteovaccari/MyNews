package com.matt.android.mynews.controllers.fragments.search;


import android.app.DatePickerDialog;
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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.matt.android.mynews.R;

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
    CheckBox bussinessCheckBox;
    @BindView(R.id.search_fragment_entrepreneurs_check_box)
    CheckBox entrepreneursCheckBox;
    @BindView(R.id.search_fragment_politics_check_box)
    CheckBox politicsCheckBox;
    @BindView(R.id.search_fragment_sports_check_box)
    CheckBox sportsCheckBox;
    @BindView(R.id.search_fragment_travel_check_box)
    CheckBox travelCheckBox;
    //Layout for show or hide it
    @BindView(R.id.search_date_linear_layout)
    LinearLayout dateLinearLayout;
    @BindView(R.id.check_box_linear_layout)
    LinearLayout checkBoxLinearLayout;

    // Date
    private String dateBeginForData;
    private String endDateForData;
    private DatePickerDialog.OnDateSetListener onDateSetListenerBeginDate;
    private DatePickerDialog.OnDateSetListener onDateSetListenerEndDate;
    private String dateBegin;
    private String endDate;
    // Query & Filter
    private String query;
    private String filter;
    private SearchFragment.OnButtonClickedListener callback;
    private int screenId;

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
        intentResultActivity.putExtra(FILTER, mFilter);

        if (!mFilter.equals("")) {
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
        if (bussinessCheckBox.isChecked()) {
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
}
