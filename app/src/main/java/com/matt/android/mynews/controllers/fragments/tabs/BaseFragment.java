package com.matt.android.mynews.controllers.fragments.tabs;


import android.content.Intent;
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
import com.matt.android.mynews.adapters.articles.ArticleAdapter;
import com.matt.android.mynews.controllers.activities.WebViewActivity;
import com.matt.android.mynews.models.api.Result;
import com.matt.android.mynews.models.utils.ItemClickSupport;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.Disposable;

/**
 * A simple {@link Fragment} subclass.
 */
public abstract class BaseFragment extends Fragment {

    protected abstract int getFragmentLayout();

    protected abstract void executeHttpRequest();

    //Url for WebView
    public static final String BUNDLE_URL = "BUNDLE_URL";

    // ---- FOR DESIGN ----

    // Declare the RecyclerView
    @BindView(R.id.main_fragment_recyclerView)
    RecyclerView recyclerView; // 1 - Declare RecyclerView

    // Declare the SwipeRefreshLayout
    @BindView(R.id.main_fragment_swipe_refresh)
    SwipeRefreshLayout swipeRefreshLayout;

    // ----FOR DATA ----
    // Declare disposable to call Retrofit
    protected Disposable disposable;
    // Declare lists & Adapters
    private List<Result> resultList;
    private ArticleAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(getFragmentLayout(), container, false);
        // Instantiate ButterKnife
        ButterKnife.bind(this, view);
        // Configure RecyclerView (+click)
        this.configureRecyclerView();
        // API request
        this.executeHttpRequest();
        // SwipeRefreshLayout
        this.configureSwipeRefreshLayout();
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.disposeWhenDestroy();
    }

    /**
     * Method called in OnDestroy method
     */
    private void disposeWhenDestroy() {
        if (this.disposable != null && !this.disposable.isDisposed()) this.disposable.dispose();
    }

    //---UI CONFIGURATION---

    /**
     * Configure RecyclerView, Adapter, LayoutManager and link it together
     */
    private void configureRecyclerView() {
        this.resultList = new ArrayList<>();
        adapter = new ArticleAdapter(resultList, Glide.with(this));
        this.recyclerView.setAdapter(adapter);
        //Re-organize LayoutManager for item positions
        this.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        this.configureOnClickRecyclerView();
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
     * Configure item click on RecyclerView
     */
    private void configureOnClickRecyclerView(){
        ItemClickSupport.addTo(recyclerView, R.layout.fragment_item)
                .setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        // 1 - Get url from adapter
                        String article = adapter.getURL(position);
                        // 2 - Show result in a new WebView
                        Intent intent = new Intent(getActivity(), WebViewActivity.class);
                        intent.putExtra(BUNDLE_URL, article);
                        startActivity(intent);
                    }

                });
    }

}
