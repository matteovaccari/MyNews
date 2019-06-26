package com.matt.android.mynews.models.api.search;

import com.matt.android.mynews.models.api.Result;

import java.util.ArrayList;

public class Response {
    ArrayList<Result> docs = new ArrayList < Result > ();
    Meta MetaObject;


    // Getter Methods

    public Meta getMeta() {
        return MetaObject;
    }

    // Setter Methods

    public void setMeta(Meta metaObject) {
        this.MetaObject = metaObject;
    }
}
