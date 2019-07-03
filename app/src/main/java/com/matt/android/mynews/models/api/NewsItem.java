package com.matt.android.mynews.models.api;

//These are the data we want to show in our recycler view
public interface NewsItem {

    String getTitle();

    String getPublished_date();

    String getImageUrl();

    String getUrl();

    String sectionAndSubsection();

}
