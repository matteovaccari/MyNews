package com.matt.android.mynews.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.matt.android.mynews.R;
import com.matt.android.mynews.models.Result;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Adapter for RecyclerView who update articles content
 */
public class RecyclerViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.fragment_most_popular_item_title) TextView title;

    public RecyclerViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void updateWithArticle(final Result article) {
        this.title.setText(article.getTitle());
    }
}
