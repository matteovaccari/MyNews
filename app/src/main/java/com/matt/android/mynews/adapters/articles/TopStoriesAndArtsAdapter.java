package com.matt.android.mynews.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.RequestManager;
import com.matt.android.mynews.R;
import com.matt.android.mynews.models.api.Result;

import java.util.List;

/**
 * RecyclerView adapter for Top Stories and Arts tab
 */

public class TopStoriesAndArtsAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {

    //Article list
    private List<Result> articlesList;
    //Image
    private RequestManager glide;

    //Constructor
    public TopStoriesAndArtsAdapter(List<Result> articlesList, RequestManager glide) {
        this.articlesList = articlesList;
        this.glide = glide;
    }

    /**
     * Create viewHolder and inflating its layout
     *
     * @param parent Parent Activity
     * @param viewType view
     * @return viewHolder(view)
     */
    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.fragment_item, parent, false);

        return new RecyclerViewHolder(view);
    }

    /**
     * Update with an article
     *
     * @param viewHolder article view holder
     * @param position   position in article list
     */
    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder viewHolder, int position) {
        viewHolder.updateArticleTopStoriesAndArts(this.articlesList.get(position), this.glide);
    }

    /**
     * Return the size of article list
     *
     * @return article list size
     */
    @Override
    public int getItemCount() {
        return this.articlesList.size();
    }
}


