package com.matt.android.mynews.models.api.search;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

class MediaImage {
    @SerializedName("url")
    @Expose
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
