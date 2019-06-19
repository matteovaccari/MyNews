package com.matt.android.mynews.controllers.fragments.tabs;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
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
 * Parent class for Fragments, containing all shared methods for all tabs
 */
public abstract class BaseFragment extends Fragment {

    //For each Fragment, will return layout ID
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
        //Init ButterKnife
        ButterKnife.bind(this, view);
        this.configureRequestsAndUI();
        return view;
    }

    /**
     * Called when fragment is destroy
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
        adapter = new RecyclerViewAdapter(resultList, Glide.with(this));
        this.recyclerView.setAdapter(adapter);
        //Re-organize LayoutManager for item positions
        this.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    /**
     * Method used for using SwipeRefreshLayout (refresh article list when swiping)
     */
    private void configureSwipeRefreshLayout() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                executeHttpRequest();
            }
        });
    }

    /**
     * At each update, resultList is cleared, sorted, added then NotifyDataSetChanged is called for adapter
     *
     * @param textArticle Article list
     */
    protected void updateUI(List<Result> textArticle) {
        swipeRefreshLayout.setRefreshing(false);
        resultList.clear();
        sortDateArray(textArticle);
        resultList.addAll(textArticle);
        // reverse the list to have an ascendant list of date in RecyclerView
        Collections.reverse(resultList);
        adapter.notifyDataSetChanged();
    }

    /**
     * Method who call the 3 methods above and is called in OnCreateView
     */
    private void configureRequestsAndUI() {
        this.configureRecyclerView();
        this.executeHttpRequest();
        this.configureSwipeRefreshLayout();
    }

    /**
     * Sort article list by Date
     *
     * @param textArticle Article list
     */
    private void sortDateArray(final List<Result> textArticle) {
        for (int i = 0; i < textArticle.size(); i++)
            Collections.sort(textArticle, new Comparator<Result>() {
                public int compare(Result textArticle1, Result textArticle2) {
                    String date1 = (String) textArticle1.getPublishedDate();
                    String date2 = (String) textArticle2.getPublishedDate();
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

    /**
     * Method called in OnDestroy method
     */
    private void disposeWhenDestroy() {
        if (this.disposable != null && !this.disposable.isDisposed()) this.disposable.dispose();
    }
}