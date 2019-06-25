package com.matt.android.mynews.controllers.fragments.tabs;

import com.matt.android.mynews.R;
import com.matt.android.mynews.models.api.MainDataObservable;
import com.matt.android.mynews.models.api.NYTStreams;
import com.matt.android.mynews.models.utils.Logger;

import io.reactivex.observers.DisposableObserver;

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

    /**
     * Execute Http Request, using Stream (Observable and Observer)
     * OnNext updateUI method take an article List and update it to recyclerView
     */
    @Override
    protected void executeHttpRequest() {
        //Get url from string values
        String url = this.getResources().getString(R.string.most_popular_url);

        this.disposable = NYTStreams.streamFetchUrl(url).subscribeWith(new DisposableObserver<MainDataObservable>() {

            @Override
            public void onNext(MainDataObservable articles) {
                updateUI(articles.getResults());
            }

            @Override
            public void onError(Throwable e) {
                Logger.e(e.getMessage());
            }

            @Override
            public void onComplete() {
                Logger.e("Most popular http request done");
            }
        });
    }
}
