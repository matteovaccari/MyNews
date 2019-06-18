package com.matt.android.mynews.controllers.fragments.tabs;

import com.matt.android.mynews.R;
import com.matt.android.mynews.models.api.MainDataObservable;
import com.matt.android.mynews.models.api.NYTStreams;

import io.reactivex.observers.DisposableObserver;

public class MostPopularFragment extends BaseFragment {

    /** Method called by ViewPager for each fragment instance
     * @return new Fragment instance
     */
    public static MostPopularFragment newInstance() {
        return (new MostPopularFragment());
    }

    /**
     * Method called in OnCreateView BaseFragment (parent class)
     * @return fragment layout
     */
    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_most_popular;
    }

    /**
     * Execute Http Request, using Stream, Observable and Observer
     * OnNext updateUI method take an article List and update it to recyclerView
     */
    @Override
    protected void executeHttpRequest() {
        this.disposable = NYTStreams.streamFetchMostPopular(1).subscribeWith(new DisposableObserver<MainDataObservable>() {
            @Override
            public void onNext(MainDataObservable articles) {
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
