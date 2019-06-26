package com.matt.android.mynews.models.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Class used for updateUI(articles.getResults) for each fragment
 */

public class MainDataObservable {

    @SerializedName(value = "results")
    @Expose
    private List<Result> results = null;

    public List<Result> getResults() {
        return results;
    }

    /////////---Search

    //Number of hits
    @SerializedName("num_results")
    @Expose
    private int num_results;

    @SerializedName("response")
    @Expose
    private Result response;

    //Number of hits from Article Search
    @SerializedName("meta")
    @Expose
    private Result meta;

    @SerializedName("hits")
    @Expose
    private int hits;

    //List of news if NewsObject is from Article Search Api
    @SerializedName("docs")
    @Expose
    private ArrayList<Result> docs;


    public List getList() {
            return response.getDocs();
    }

    private int getNum_results() {
        return num_results;
    }

    void setNum_results(int num_results) {
        this.num_results = num_results;
    }

    Result getResponse() {
        return response;
    }

    ArrayList<Result> getDocs() {
        return docs;
    }

    void setResults(ArrayList<Result> results) {
        this.results = results;
    }

    void setResponse(Result response) {
        this.response = response;
    }

    void setDocs(ArrayList<Result> docs) {
        this.docs = docs;
    }

    public void setMeta(Result meta) {
        this.meta = meta;
    }

    public Result getMeta() {
        return meta;
    }

    public void setHits(int hits) {
        this.hits = hits;
    }

    private int getHits() {
        return hits;
    }

    //Get the number of hits depending on the API request
    public int checkIfResult() {
        if (results == null) {
            return getResponse().getMeta().getHits();
        } else {
            return getNum_results();
        }
    }
}
