package com.matt.android.mynews.controllers.fragments;

import com.matt.android.mynews.R;
import com.matt.android.mynews.models.api.MainDataObservable;
import com.matt.android.mynews.models.api.NYTStreams;

import io.reactivex.observers.DisposableObserver;

public class MostPopularFragment extends BaseFragment {

    public static MostPopularFragment newInstance() {
        return (new MostPopularFragment());
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_most_popular;
    }

    @Override
    protected void executeHttpRequest() {
        this.disposable = NYTStreams.streamFetchMostPopular(1).subscribeWith(new DisposableObserver<MainDataObservable>() {
            @Override
            public void onNext(MainDataObservable articles) {
                // Update RecyclerView after getting results from MostPopular API
                updateUI(articles.getResults());
            }
            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }
            @Override
            public void onComplete() { }
        });
    }

}
