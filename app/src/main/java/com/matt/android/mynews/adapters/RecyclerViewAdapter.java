package com.matt.android.mynews.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.matt.android.mynews.R;
import com.matt.android.mynews.models.Result;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {

    private List<Result> articlesList;

    //Constructor
    public RecyclerViewAdapter(List <Result> articlesList) {
        this.articlesList = articlesList;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Create viewHolder and inflating its xml layout
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.most_popular_item, parent, false);

        return new RecyclerViewHolder(view);
    }

    //Update view holder with a githubUser
    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder viewHolder, int position) {
        viewHolder.updateWithArticle(this.articlesList.get(position));
    }

    //Return the total count of items in the list
    @Override
    public int getItemCount() {
        return this.articlesList.size();
    }
}


