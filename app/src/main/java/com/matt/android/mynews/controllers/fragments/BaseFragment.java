package com.matt.android.mynews.controllers.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.matt.android.mynews.R;
import com.matt.android.mynews.adapters.RecyclerViewAdapter;
import com.matt.android.mynews.models.api.Result;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.Disposable;

/**
 * Class to manage fragments
 */
public abstract class BaseFragment extends Fragment {

    //For each Fragment, will return ID
    protected abstract int getFragmentLayout();

    //For connecting to the NyTimes API
    protected abstract void executeHttpRequest();

    public static final String BUNDLE_URL = "BUNDLE_URL";

    //---FOR DESIGN---

    //Declare the recycler view
    @BindView(R.id.main_fragment_recyclerView)
    RecyclerView recyclerView;
    //Declare the SwipeRefreshLayout
    @BindView(R.id.main_fragment_swipe_refresh)
    SwipeRefreshLayout swipeRefreshLayout;

    //---FOR DATA---
    //Declare disposable
    protected Disposable disposable;
    //Declare List and Adapter
    private List<Result> resultList;
    private RecyclerViewAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate view
        View view = inflater.inflate(getFragmentLayout(), container, false);
        //Init Butterknife
        ButterKnife.bind(this, view);
        this.configureRequestsAndUI();
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

    //---UI CONFIGURATION---

    /**
     * Configure RecyclerView, Adapter, LayoutManager and link it together
     */
    private void configureRecyclerView() {
        this.resultList = new ArrayList<>();
        adapter = new RecyclerViewAdapter(resultList);
        this.recyclerView.setAdapter(adapter);
        //Re-organize LayoutManager for item positions
        this.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void configureSwipeRefreshLayout() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                executeHttpRequest();
            }
        });
    }

    protected void updateUI(List<Result> textArticle) {
        swipeRefreshLayout.setRefreshing(false);
        resultList.clear();
        sortDateArray(textArticle);
        resultList.addAll(textArticle);
        // reverse the list to have an ascendant list of date in RecyclerView
        Collections.reverse(resultList);
        adapter.notifyDataSetChanged();
    }

    //Sort articles by dates
    private void sortDateArray(final List<Result> textArticle) {
        for (int i = 0; i < textArticle.size(); i++)
            Collections.sort(textArticle, new Comparator<Result>() {
                public int compare(Result textArticle1, Result textArticle2) {
                    String date1 = (String)textArticle1.getPublishedDate();
                    String date2 = (String)textArticle2.getPublishedDate();
                    int result = date1.compareTo(date2);
                    if(result == 0){
                        return date1.compareTo(date2);
                    }
                    return result;
                }
            });
        Iterator<Result> it = textArticle.iterator();
        while (it.hasNext())
            Log.i("List", "List finale" + it.next());
    }

    private void configureRequestsAndUI() {
        this.configureRecyclerView();
        this.executeHttpRequest();
        this.configureSwipeRefreshLayout();
    }

    private void disposeWhenDestroy(){
        if (this.disposable != null && !this.disposable.isDisposed()) this.disposable.dispose();
    }
}
