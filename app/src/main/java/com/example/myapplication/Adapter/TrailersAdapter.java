package com.example.myapplication.Adapter;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class TrailersAdapter extends RecyclerView.Adapter<TrailersAdapter.TViewHolder> {

    Context context;
    TextView trailer_tv;
    private List<String> moviesTrailers = new ArrayList<>();

    public TrailersAdapter(List<String> moviesTrailers) {
        this.moviesTrailers = moviesTrailers;
    }

    @NonNull
    @Override
    public TrailersAdapter.TViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.trailers;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean AttachImmediately = false;
        View view = inflater.inflate(layoutIdForListItem, viewGroup, AttachImmediately);
        TViewHolder viewHolder = new TViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TViewHolder tViewHolder, int position) {
        tViewHolder.bind(position);
    }


    @Override
    public int getItemCount() {
        return moviesTrailers.size();
    }


    class TViewHolder extends RecyclerView.ViewHolder {//================================== class

        public TViewHolder(View itemView) {
            super(itemView);
            trailer_tv = itemView.findViewById(R.id.trailer_tv);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    String key = moviesTrailers.get(position);
                    Intent intentApp = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + key));
                    Intent intentWeb =  new Intent(Intent.ACTION_VIEW,
                                         Uri.parse("http://www.youtube.com/watch?v=" + key));
                    try {
                        context.startActivity(intentApp);
                    } catch (ActivityNotFoundException ex) {
                        context.startActivity(intentWeb);
                    }
                }
            });
        }

        void bind(int position) {
            position++;
            trailer_tv.setText("Trailer no. "+position);
        }
    }
}
