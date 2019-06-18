package com.matt.android.mynews.models.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Class used for updateUI(articles.getResults) for each fragment
 */

public class MainDataObservable {

    @SerializedName(value = "results", alternate = "docs")
    @Expose
    private List<Result> results = null;

    public List<Result> getResults() {
        return results;
    }
}
