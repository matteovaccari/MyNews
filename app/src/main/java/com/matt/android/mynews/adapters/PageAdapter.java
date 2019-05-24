package com.matt.android.mynews.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.matt.android.mynews.controllers.fragments.ArtsFragment;
import com.matt.android.mynews.controllers.fragments.MostPopularFragment;
import com.matt.android.mynews.controllers.fragments.TopStoriesFragment;

public class PageAdapter extends FragmentPagerAdapter {

    
    public PageAdapter(FragmentManager mgr) {
        super(mgr);
    }

    @Override
    public Fragment getItem(int position) {
        switch(position){
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

    @Override
    public int getCount() {
        return 3;
    }
}
