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


    public static ArtsFragment newInstance() {
        return (new ArtsFragment());
    }


    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_arts;
    }

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
