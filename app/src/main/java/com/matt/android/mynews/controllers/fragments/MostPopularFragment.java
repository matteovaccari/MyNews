package com.matt.android.mynews.controllers.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.matt.android.mynews.R;
import com.matt.android.mynews.adapters.RecyclerViewAdapter;
import com.matt.android.mynews.models.MainDataObservable;
import com.matt.android.mynews.models.NYTStreams;
import com.matt.android.mynews.models.Result;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

/**
 * A simple {@link Fragment} subclass.
 */
public class MostPopularFragment extends Fragment {

    //For DATA
    private Disposable disposable;
    private List<Result> resultList;
    private RecyclerViewAdapter adapter;
    //For Design
    @BindView(R.id.most_popular_fragment_recyclerView) RecyclerView recyclerView;

    public static MostPopularFragment newInstance() {
        return (new MostPopularFragment());
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_most_popular, container, false);

        ButterKnife.bind(this, view);

        this.configureRecyclerView();
        this.executeHttpRequest();
        return view;


    }

    /**
     * When the fragment is distroy
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        this.disposeWhenDestroy();
    }

    private int getFragmentLayout() {
        return R.layout.fragment_most_popular;
    }

    /**
     * Create a stream request with Retrofit
     */

    private void executeHttpRequest() {
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
            public void onComplete() {
                Log.e("TAG", "Most popular request complete");
            }
        });

    }

    private void disposeWhenDestroy() {
        if (this.disposable != null && !this.disposable.isDisposed()) this.disposable.dispose();
    }

    //Update UI

    /**
     * Method to sort and orgenize a list of dates
     *
     * @param textArticle Dates of published articles
     */
    private void sortDateArray(final List<Result> textArticle) {
        for (int i = 0; i < textArticle.size(); i++)
            Collections.sort(textArticle, new Comparator<Result>() {
                public int compare(Result textArticle1, Result textArticle2) {
                    String date1 = textArticle1.getPublishedDate();
                    String date2 = textArticle2.getPublishedDate();
                    int result = date1.compareTo(date2);
                    if (result == 0) {
                        return date1.compareTo(date2);
                    }
                    return result;
                }
            });
        Iterator<Result> it = textArticle.iterator();
        while (it.hasNext())
            Log.i("List", "List finale" + it.next());
    }

    protected void updateUI(List<Result> textArticle) {
        resultList.clear();
        sortDateArray(textArticle);
        resultList.addAll(textArticle);
        // reverse the list to have an ascendant list of date in RecyclerView
        Collections.reverse(resultList);
        Log.i("List reverse", "List finale" + resultList);
        adapter.notifyDataSetChanged();
    }

    private void configureRecyclerView() {
        this.resultList = new ArrayList<>();
        adapter = new RecyclerViewAdapter(resultList);
        this.recyclerView.setAdapter(this.adapter);

        // Set layout manager to position the items
        this.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

}
