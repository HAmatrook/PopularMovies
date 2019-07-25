package com.example.myapplication.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.RViewHolder>{

    Context context;
    TextView username_tv,review_tv;
    private List<String> usernamesList = new ArrayList<>();
    private List<String> reviewsTextList = new ArrayList<>();

    public ReviewsAdapter(List<String> usernamesList, List<String> reviewsTextList) {
        this.usernamesList = usernamesList;
        this.reviewsTextList = reviewsTextList;
    }

    @NonNull
    @Override
    public RViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.reviews;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean AttachImmediately = false;
        View view = inflater.inflate(layoutIdForListItem, viewGroup, AttachImmediately);
        RViewHolder viewHolder = new RViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RViewHolder rViewHolder, int position) {
        rViewHolder.bind(position);
    }

    @Override
    public int getItemCount() {
        return usernamesList.size();
    }

    class RViewHolder extends RecyclerView.ViewHolder {//================================== class

        public RViewHolder(View itemView) {
            super(itemView);
            username_tv = itemView.findViewById(R.id.username_tv);
            review_tv    = itemView.findViewById(R.id.review_tv);
        }

        void bind(int position) {
            username_tv.setText(usernamesList.get(position));
            review_tv.setText(reviewsTextList.get(position));
        }
    }
}
