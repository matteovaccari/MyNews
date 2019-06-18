package com.matt.android.mynews.controllers.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.matt.android.mynews.R;
import com.matt.android.mynews.models.api.MainDataObservable;
import com.matt.android.mynews.models.api.NYTStreams;

import io.reactivex.observers.DisposableObserver;


public class ArtsFragment extends BaseFragment {

    /** Method called by ViewPager for each fragment instance
     * @return new Fragment instance
     */
    public static ArtsFragment newInstance() {
        return (new ArtsFragment());
    }

    /**
     * Method called in OnCreateView BaseFragment (parent class)
     * @return fragment layout
     */
    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_arts;
    }

    /**
     * Execute Http Request, using Stream, Observable and Observer - specified section for Arts tab (arts)
     * OnNext updateUI method take an article List and update it to recyclerView
     */
    @Override
    protected void executeHttpRequest() {
        this.disposable = NYTStreams.streamFetchTopStories("arts").subscribeWith(new DisposableObserver<MainDataObservable>(){
            @Override
            public void onNext(MainDataObservable articles) {
                updateUI(articles.getResults());
            }

            @Override
            public void onError(Throwable e) {
                e.getMessage();
            }

            @Override
            public void onComplete() {
                Log.e("TAG", "Arts http request done...");
            }
        });
    }
}
