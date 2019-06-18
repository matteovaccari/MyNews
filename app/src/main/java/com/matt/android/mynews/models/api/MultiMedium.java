package com.matt.android.mynews.models.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Class for image management (url > getUrl > article.getMultimedia) for getting article image resource
 */

public class MultiMedium {

    @SerializedName("url")
    @Expose
    private String url;

    public String getUrl() {
        return url;
    }

}

