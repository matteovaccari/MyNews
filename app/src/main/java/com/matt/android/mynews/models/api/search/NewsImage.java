package com.matt.android.mynews.models.api.search;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

class NewsImage {

    @SerializedName("url")
    @Expose
    private String url;

    @SerializedName("media-metadata")
    @Expose
    private ArrayList<MediaImage> metadata;

    ArrayList<MediaImage> getMetadata() {
        return metadata;
    }

    void setMetadata(ArrayList<MediaImage> metadata) {
        this.metadata = metadata;
    }

    public String getUrl() {
        if (!url.startsWith("https://static01.nyt.com/")) {
            url = "https://static01.nyt.com/" + url;
        }
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
