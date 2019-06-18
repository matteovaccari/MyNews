package com.matt.android.mynews.controllers.fragments.tabs;


import android.util.Log;

import com.matt.android.mynews.R;
import com.matt.android.mynews.models.api.MainDataObservable;
import com.matt.android.mynews.models.api.NYTStreams;

import io.reactivex.observers.DisposableObserver;

/**
 * Top Stories tab
 */

public class TopStoriesFragment extends BaseFragment {

    /**
     * Method called by ViewPager for each fragment instance
     *
     * @return new Fragment instance
     */
    public static TopStoriesFragment newInstance() {
        return (new TopStoriesFragment());
    }

    /**
     * Method called in OnCreateView BaseFragment (parent class)
     * @return fragment layout
     */
    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_top_stories;
    }

    /**
     * Execute Http Request, using Stream, Observable and Observer - specified section for Top Stories (home)
     * OnNext updateUI method take an article List and update it to recyclerView
     */
    @Override
    protected void executeHttpRequest() {
        this.disposable = NYTStreams.streamFetchTopStories("home").subscribeWith(new DisposableObserver<MainDataObservable>() {
            @Override
            public void onNext(MainDataObservable articles) {
                updateUI(articles.getResults());
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onComplete() {
                Log.e("TAG", "Top stories http request done");
            }
        });
    }

}
