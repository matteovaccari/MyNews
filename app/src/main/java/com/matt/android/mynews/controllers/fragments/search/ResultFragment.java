package com.matt.android.mynews.controllers.fragments.search;


import android.support.v4.app.Fragment;
import com.matt.android.mynews.R;
import com.matt.android.mynews.controllers.fragments.tabs.BaseFragment;
import com.matt.android.mynews.models.utils.SharedPreferencesManager;

import static com.matt.android.mynews.models.utils.Constants.PREF_KEY_SEARCH;

/**
 * A simple {@link Fragment} subclass.
 */
public class ResultFragment extends BaseFragment {

    /**
     * Fragment layout
     * @return fragment layout for BaseFragment's OnCreateView method
     */
    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_result;
    }

    public void setUrl() {
       SharedPreferencesManager preferences = new SharedPreferencesManager(this.getActivity().getApplicationContext());
        url = preferences.getString(PREF_KEY_SEARCH);
    }

}
