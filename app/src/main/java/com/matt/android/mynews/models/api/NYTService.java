package com.matt.android.mynews.models.api;

import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * NYT API requests and retrofit builds
 */
public interface NYTService {

    @GET("{period}.json?api-key=BCdKtxQWdZXLs9x3O9S4CY1cAJUgTCmm")
    Observable<MainDataObservable> getNyMostPopular(@Path("period") int period);

    Retrofit retrofitMostPopular = new Retrofit.Builder()
            .baseUrl("https://api.nytimes.com/svc/mostpopular/v2/viewed/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build();

    @GET("{section}.json?api-key=BCdKtxQWdZXLs9x3O9S4CY1cAJUgTCmm")
    Observable<MainDataObservable> getNyTopStories(@Path("section") String section);

    Retrofit retrofitTopStories = new Retrofit.Builder()
            .baseUrl("https://api.nytimes.com/svc/topstories/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build();
}
