package com.matt.android.mynews.models.api.search;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;


//This is the object that will be received from the API request
public class NewsObject {

    //List of news if NewsObject is from Top stories or Most Popular Api
    @SerializedName("results")
    @Expose
    private ArrayList<NewsStories> results;

    //Number of hits
    @SerializedName("num_results")
    @Expose
    private int num_results;

    //List of news if NewsObject is from Article Search Api
    @SerializedName("docs")
    @Expose
    private ArrayList<NewsSearch> docs;

    @SerializedName("response")
    @Expose
    private NewsObject response;

    //Number of hits from Article Search
    @SerializedName("meta")
    @Expose
    private NewsObject meta;

    @SerializedName("hits")
    @Expose
    private int hits;


    //Get the list of news depending on which type of API Request
    public ArrayList getList() {
        if (results == null) {
            return response.getDocs();
        } else {
            return results;
        }
    }

    private int getNum_results() {
        return num_results;
    }

    void setNum_results(int num_results) {
        this.num_results = num_results;
    }

    NewsObject getResponse() {
        return response;
    }

    ArrayList<NewsSearch> getDocs() {
        return docs;
    }

    void setResults(ArrayList<NewsStories> results) {
        this.results = results;
    }

    void setResponse(NewsObject response) {
        this.response = response;
    }

    void setDocs(ArrayList<NewsSearch> docs) {
        this.docs = docs;
    }

    public void setMeta(NewsObject meta) {
        this.meta = meta;
    }

    public NewsObject getMeta() {
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
