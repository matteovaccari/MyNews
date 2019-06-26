package com.matt.android.mynews.models.api.search;

public class Meta {
    private float hits;
    private float offset;
    private float time;


    // Getter Methods

    public float getHits() {
        return hits;
    }

    public float getOffset() {
        return offset;
    }

    public float getTime() {
        return time;
    }

    // Setter Methods

    public void setHits(float hits) {
        this.hits = hits;
    }

    public void setOffset(float offset) {
        this.offset = offset;
    }

    public void setTime(float time) {
        this.time = time;
    }
}
