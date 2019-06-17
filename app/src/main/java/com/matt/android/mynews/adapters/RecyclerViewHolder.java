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

    public void setArticleImage(Result article, RequestManager glide) {
        // if image is from Multimedia model
        if (article.getMultimedia() != null) {
            // and if image is from Multimedia model
            if (article.getMultimedia().size() > 0) {
                // get image string
                String mUrlMultimedia = article.getMultimedia().get(0).getUrl();
                // clean the URL
                if (mUrlMultimedia.startsWith("images")) {
                    mUrlMultimedia = "https://www.nytimes.com/" + mUrlMultimedia;
                }
                glide.load(mUrlMultimedia).apply(new RequestOptions().fallback(R.drawable.ic_launcher_background)).into(imageView);
            } else {
                // default image
                getImageDefault(glide);
            }
        } else {
            // if image is from Media model
            if (article.getMedia().size() > 0) {
                // get Url
                String mUrlMedia = article.getMedia().get(0).getMediaMetadata().get(0).getUrl();
                // glide the string
                glide.load(mUrlMedia).apply(new RequestOptions().fallback(R.drawable.ic_launcher_background)).into(imageView);
            } else {
                //get image default
                getImageDefault(glide);
            }
        }
    }

    private void getImageDefault(RequestManager glide) {
        glide.clear(imageView);
        imageView.setImageResource(R.drawable.ic_image_default);
    }
}
