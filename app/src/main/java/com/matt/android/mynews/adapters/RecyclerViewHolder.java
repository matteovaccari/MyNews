package com.matt.android.mynews.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
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
     * Update article list with items
     *
     * @param article article
     * @param glide   image
     */
    public void updateWithArticle(final Result article, RequestManager glide) {
        UpdateTextItems update = new UpdateTextItems();
        // set Section
        this.textViewSection.setText(update.setSection(article));
        // set Subsection
        this.textViewSubSection.setText(update.setSubSection(article));
        // set Title
        this.textViewTitle.setText(update.setTitle(article));
        // set Date
        this.textViewDate.setText(update.setDate(article));
        //set image
        this.setArticleImage(article, glide);
    }

    /**
     * Image manager, take url of image + an article
     *
     * @param article article
     * @param glide   image
     */
    public void setArticleImage(Result article, RequestManager glide) {
        //If article url isn't null
        if (article.getMultimedia() != null) {
            if (article.getMultimedia().size() > 0) {
                // get image string
                String urlMultimedia = article.getMultimedia().get(0).getUrl();
                // clean the URL
                if (urlMultimedia.startsWith("images")) {
                    urlMultimedia = "https://www.nytimes.com/" + urlMultimedia;
                }
                glide.load(urlMultimedia).apply(new RequestOptions().fallback(R.drawable.ic_launcher_background)).into(imageView);
            } else {
                // default image
                getImageDefault(glide);
            }
        }/* else {

            if (article.getMedia() == null) {
                //image default
                getImageDefault(glide);
            } else {
                // get Url
                String mUrlMedia = article.getMedia().get(0).getMediaMetadata().get(0).getUrl();
                // glide the string
                glide.load(mUrlMedia).apply(new RequestOptions().fallback(R.drawable.ic_launcher_background)).into(imageView);
            }
        } */
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
