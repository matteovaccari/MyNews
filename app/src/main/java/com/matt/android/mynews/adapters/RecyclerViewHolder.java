package com.matt.android.mynews.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.matt.android.mynews.R;
import com.matt.android.mynews.models.api.search.NewsItem;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Adapter for RecyclerView who update articles content
 */
public class RecyclerViewHolder extends RecyclerView.ViewHolder {

    // FOR DESIGN
    @BindView(R.id.fragment_main_item_title)
    TextView textViewTitle;
    @BindView(R.id.fragment_main_section)
    TextView textViewSection;
    @BindView(R.id.fragment_main_date)
    TextView textViewDate;
    @BindView(R.id.fragment_main_item_image)
    ImageView imageView;

    //Constructor
    public RecyclerViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    /**
     * Set article image in RecyclerView
     * @param article News Item Article
     * @param context Context
     */
    public void setArticleImage(final NewsItem article, final Context context) {
       try {
           String url = article.getImageUrl();
           Glide.with(context)
                   .load(url)
                   .into(imageView);
       } catch (Exception e) {
           Glide.with(context)
                   .load(R.drawable.ic_image_default)
                   .into(imageView);
       }
    }

    /**
     * Take information and link it to each recycler view item (title, image, etc)
     * @param article article
     * @param context Context
     */
    public void updateWithArticleContent(final NewsItem article, final Context context) {
        // set Section and subSection
        this.textViewSection.setText(article.sectionAndSubsection());
        // set Title
        this.textViewTitle.setText(article.getTitle());
        // set Date
        this.textViewDate.setText(article.getPublished_date());
        // set Image
        this.setArticleImage(article, context);
    }

}
