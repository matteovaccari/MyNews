package com.matt.android.mynews.controllers.fragments.search;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
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
import com.matt.android.mynews.models.api.MainDataObservable;
import com.matt.android.mynews.models.api.NYTStreams;
import com.matt.android.mynews.models.api.Result;
import com.matt.android.mynews.models.utils.ItemClickSupport;
import com.matt.android.mynews.models.utils.Logger;
import com.matt.android.mynews.models.utils.SharedPreferencesManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

import static com.matt.android.mynews.controllers.fragments.tabs.BaseFragment.BUNDLE_URL;
import static com.matt.android.mynews.models.utils.Constants.PREF_KEY_SEARCH;

/**
 * A simple {@link Fragment} subclass.
 */
public class ResultFragment extends Fragment {

    @BindView(R.id.main_fragment_recyclerView)
    RecyclerView recyclerView;
    protected String url;

    private List<Result> resultList;
    private ArticleAdapter adapter;

    // Declare disposable to call Retrofit
    protected Disposable disposable;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_result, container, false);
        //Initialize ButterKnife
        ButterKnife.bind(this, view);
        executeHttpRequest();
        configureRecyclerView();
        return view;
    }
    protected void updateUIWithListOfNews(MainDataObservable news) {
        if (news.checkIfResult() == 0) {
            Snackbar.make(recyclerView, "No articles found", Snackbar.LENGTH_LONG).show();

        } else {
            resultList.addAll(news.getList());
            adapter.notifyDataSetChanged();
        }
    }

    protected void executeHttpRequest() {

        setUrl();

        this.disposable = NYTStreams.streamFetchUrl(url).subscribeWith(new DisposableObserver<MainDataObservable>() {
            @Override
            public void onNext(MainDataObservable newsObject) {
                updateUIWithListOfNews(newsObject);
                Logger.e("Search result content : " + newsObject.getResults());
            }

            @Override
            public void onError(Throwable e) {
                Logger.e("Error : " + e.getMessage());
            }

            @Override
            public void onComplete() {
                Logger.e("Search display completed");
            }
        });
    }

    public void setUrl() {
       SharedPreferencesManager preferences = new SharedPreferencesManager(this.getActivity().getApplicationContext());
        url = preferences.getString(PREF_KEY_SEARCH);
    }

    private void configureRecyclerView() {
        this.resultList = new ArrayList<>();
        adapter = new ArticleAdapter(resultList, Glide.with(this));
        this.recyclerView.setAdapter(adapter);
        //Re-organize LayoutManager for item positions
        this.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        this.configureOnClickRecyclerView();
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
