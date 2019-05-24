package com.matt.android.mynews.adapters;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.matt.android.mynews.R;
import com.matt.android.mynews.controllers.fragments.ArtsFragment;
import com.matt.android.mynews.controllers.fragments.MostPopularFragment;
import com.matt.android.mynews.controllers.fragments.TopStoriesFragment;

public class PageAdapter extends FragmentPagerAdapter {

    //Constructor
    public PageAdapter(FragmentManager mgr) {
        super(mgr);
    }

    //Method who return a Fragment depending of ViewPager's position
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return TopStoriesFragment.newInstance();
            case 1:
                return MostPopularFragment.newInstance();
            case 2:
                return ArtsFragment.newInstance();
            default:
                return null;
        }
    }

    //Method who return the number of tabs
    @Override
    public int getCount() {
        return 3;
    }

    //Method who return each tab title name
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        CharSequence cs;
        switch (position) {
            case 0:
                return cs = String.valueOf(R.string.top_stories_tab_name);
            case 1:
                return cs = String.valueOf(R.string.most_popular_tab_name);
            case 2:
                return cs = String.valueOf(R.string.arts_tab_name);
            default:
                return null;
        }
    }
}
