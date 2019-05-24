package com.matt.android.mynews.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class PageAdapter extends FragmentPagerAdapter {

    public PageAdapter(FragmentManager mgr) {
        super(mgr);
    }

    @Override
    public Fragment getItem(int position) {
        return ();
    }

    @Override
    public int getCount() {
        return 3;
    }
}
