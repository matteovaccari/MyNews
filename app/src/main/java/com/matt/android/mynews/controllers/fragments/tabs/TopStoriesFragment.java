package com.matt.android.mynews.controllers.fragments.tabs;


import com.matt.android.mynews.R;
/**
 * Top Stories tab
 */

public class TopStoriesFragment extends BaseFragment {


    // Instantiate fragment for page adapter
    public static TopStoriesFragment newInstance() {
        return (new TopStoriesFragment());
    }

    /**
     * Fragment layout
     * @return fragment layout for BaseFragment's OnCreateView method
     */
    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_top_stories;
    }

    @Override
    public void setUrl() {
        url = this.getResources().getString(R.string.top_stories_url);
    }
}

