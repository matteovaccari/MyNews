package com.matt.android.mynews.controllers.fragments.search;


import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.matt.android.mynews.R;

import butterknife.BindView;

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
    private String mQuery;
    private String mFilter;
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
        return view;
    }

}
