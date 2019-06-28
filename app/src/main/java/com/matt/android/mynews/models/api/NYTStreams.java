package com.matt.android.mynews.models.api;


import com.matt.android.mynews.models.api.search.NewsObject;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Create streams with observable
 */
public class NYTStreams {

    public static Observable<NewsObject> streamFetchUrl(String url) {
        NYTService nytService = NYTService.retrofit.create(NYTService.class);
        return nytService.getNews(url)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);
    }
}
