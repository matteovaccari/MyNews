package com.matt.android.mynews.models.api;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Create streams with observable
 */
public class NYTStreams {

    public static Observable<MainDataObservable> streamFetchMostPopular(int period) {
        NYTService nytService = NYTService.retrofitMostPopular.create(NYTService.class);
        return nytService.getNyMostPopular(period)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);
    }

    public static Observable<MainDataObservable> streamFetchTopStories(String section) {
        NYTService nytService = NYTService.retrofitTopStories.create(NYTService.class);
        return nytService.getNyTopStories(section)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);

    }
}
