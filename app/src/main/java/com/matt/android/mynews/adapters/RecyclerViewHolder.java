package com.matt.android.mynews.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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

    public void updateWithArticle(final Result article) {
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
}
