package com.matt.android.mynews.controllers.fragments.tabs;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.matt.android.mynews.R;
import com.matt.android.mynews.adapters.articles.ArticleAdapter;
import com.matt.android.mynews.controllers.activities.WebViewActivity;
import com.matt.android.mynews.models.api.NYTStreams;
import com.matt.android.mynews.models.api.NewsItem;
import com.matt.android.mynews.models.api.NewsObject;
import com.matt.android.mynews.models.utils.ItemClickSupport;
import com.matt.android.mynews.models.utils.Logger;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

/**
 * A simple {@link Fragment} subclass.
 */
public abstract class BaseFragment extends Fragment {

    protected abstract int getFragmentLayout();

    public abstract void setUrl();

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
    protected String url;
    // Declare lists & Adapters
    private List<NewsItem> resultList;
    private ArticleAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(getFragmentLayout(), container, false);
        // Instantiate ButterKnife
        ButterKnife.bind(this, view);
        //Set url
        this.setUrl();
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
        //Reset list
        this.resultList = new ArrayList<>();
        //Create adapter
        adapter = new ArticleAdapter(resultList);
        //Set adapter to recycler view
        this.recyclerView.setAdapter(adapter);
        //Re-organize LayoutManager for item positions
        this.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //Set click on for recyclerView
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
     * @param article NewsObject
     */
    protected void updateUI(NewsObject article) {
        if (article.checkIfResult() == 0) {
            Snackbar.make(recyclerView, "No results found", Snackbar.LENGTH_LONG).show();
        } else {
            swipeRefreshLayout.setRefreshing(false);
            resultList.clear();
            resultList.addAll(article.getList());
            adapter.notifyDataSetChanged();
        }

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

    /**
     * Execute Http Request, using Stream (Observable and Observer)
     * OnNext updateUI method take an article List and update it to recyclerView
     */
    protected void executeHttpRequest() {

        //- Execute the stream subscribing to Observable defined inside NewsStream
        this.disposable = NYTStreams.streamFetchUrl(url).subscribeWith(new DisposableObserver<NewsObject>() {
            @Override
            public void onNext(NewsObject news) {
                // - Update UI with list of news
                updateUI(news);
            }

            @Override
            public void onError(Throwable e) {
                Logger.e(e.getMessage());

            }

            @Override
            public void onComplete() {
                Logger.e("request completed");
            }
        });
    }

}
