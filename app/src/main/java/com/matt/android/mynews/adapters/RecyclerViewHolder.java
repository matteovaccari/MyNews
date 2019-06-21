package com.matt.android.mynews.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.matt.android.mynews.R;
import com.matt.android.mynews.models.api.Result;
import com.matt.android.mynews.models.utils.UpdateTextItems;
import com.matt.android.mynews.models.api.MultiMedium;

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
    @BindView(R.id.fragment_main_subsection)
    TextView textViewSubSection;
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
     * Image manager, take url of image + an article
     *
     * @param article article
     * @param glide   image
     */
    public void setArticleImageTopStoriesAndArts(Result article, RequestManager glide) {
        //Set article content
        updateWithArticleContent(article, glide);
        //Set Image
        try {
            glide.load(article.getMultimedia().get(0).getUrl()).into(imageView);
        } catch (IndexOutOfBoundsException e) {
            Log.e("TAG", "no media");
        }
    }

    public void setArticleImageMostPopular(Result article, RequestManager glide) {
        //Set article content
        updateWithArticleContent(article, glide);
        //Set Image
        try {
            glide.load(article.getMedia().get(0).getMediaMetadata().get(0).getUrl()).into(imageView);
        } catch (IndexOutOfBoundsException e){
            Log.e("TAG", "no media");
        }
    }

    public void updateWithArticleContent(final Result article, RequestManager glide) {
        UpdateTextItems update = new UpdateTextItems();
        // set Section
        this.textViewSection.setText(update.setSection(article));
        // set Subsection
        this.textViewSubSection.setText(update.setSubSection(article));
        // set Title
        this.textViewTitle.setText(update.setTitle(article));
        // set Date
        this.textViewDate.setText(update.setDate(article));
    }


    /**
     * Set default image when resource is missing
     *
     * @param glide image
     */
    private void getImageDefault(RequestManager glide) {
        glide.clear(imageView);
        imageView.setImageResource(R.drawable.ic_image_default);
    }
}
