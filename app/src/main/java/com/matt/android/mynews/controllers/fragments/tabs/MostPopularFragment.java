package com.matt.android.mynews.controllers.fragments.tabs;

import com.matt.android.mynews.R;

/**
 * Most Popular tab
 */
public class MostPopularFragment extends BaseFragment {

    // Instantiate fragment for page adapter
    public static MostPopularFragment newInstance() {
        return (new MostPopularFragment());
    }

    /**
     * Fragment layout
     * @return fragment layout for BaseFragment's OnCreateView method
     */
    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_most_popular;
    }

    @Override
    public void setUrl() {
        url = this.getResources().getString(R.string.most_popular_url);
    }
}
