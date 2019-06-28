package com.matt.android.mynews.controllers.fragments.tabs;


import com.matt.android.mynews.R;

/**
 * Arts Tab
 */
public class ArtsFragment extends BaseFragment {

    // Instantiate fragment for page adapter
    public static ArtsFragment newInstance() {
        return (new ArtsFragment());
    }

    /**
     * Fragment layout
     * @return fragment layout for BaseFragment's OnCreateView method
     */
    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_arts;
    }

    @Override
    public void setUrl() {
        url = this.getResources().getString(R.string.arts_url);
    }
}