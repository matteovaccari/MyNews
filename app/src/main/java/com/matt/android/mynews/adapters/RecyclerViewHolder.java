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
     * Set article image in RecyclerView
     * @param article article
     * @param glide Image
     */
    public void setArticleImage(Result article, RequestManager glide) {
        //Set Image if article is from Multimedia model (Top stories and arts API)
        if (article.getMultimedia() != null) {
            try {
                glide.load(article.getMultimedia().get(0).getUrl()).into(imageView);
            } catch (IndexOutOfBoundsException e) {
                Log.e("TAG", "no media");
            }
        }
        //Set Image if article is from Media model (Most popular API)
        else if (article.getMedia() != null) {
            try {
                glide.load(article.getMedia().get(0).getMediaMetadata().get(0).getUrl()).into(imageView);
            } catch (IndexOutOfBoundsException e) {
                Log.e("TAG", "no media") ;
            }
        }
    }

    /**
     * Take information and link it to each recycler view item (title, image, etc)
     * @param article article
     * @param glide image
     */
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
        // set Image
        this.setArticleImage(article, glide);
    }

}
