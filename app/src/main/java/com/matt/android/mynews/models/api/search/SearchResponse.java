package com.matt.android.mynews.models.api.search;

import java.util.ArrayList;

public class SearchResponse {
    private String status;
    private String copyright;
    Response ResponseObject;


    // Getter Methods

    public String getStatus() {
        return status;
    }

    public String getCopyright() {
        return copyright;
    }

    public Response getResponse() {
        return ResponseObject;
    }

    // Setter Methods

    public void setStatus(String status) {
        this.status = status;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public void setResponse(Response responseObject) {
        this.ResponseObject = responseObject;
    }
}
