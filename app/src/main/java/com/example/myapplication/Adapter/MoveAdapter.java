package com.example.myapplication.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.Models.Movies;
import com.example.myapplication.MovieDetails;
import com.example.myapplication.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MoveAdapter extends RecyclerView.Adapter<MoveAdapter.MViewHolder> {

    Context context;
    private List<Movies> moviesList = new ArrayList<>();
    public TextView title;
    public ImageView poster;

    public MoveAdapter( List<Movies> moviesList) {

        this.moviesList= moviesList;
    }

    @Override
    public MViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.items;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean AttachImmediately = false;
        View view = inflater.inflate(layoutIdForListItem, viewGroup, AttachImmediately);
        MViewHolder viewHolder = new MViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MViewHolder mViewHolder, int position) {
        mViewHolder.bind(position);
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }
    @Override
    public int getItemViewType(int position) {
        return position;
    }

    class MViewHolder extends RecyclerView.ViewHolder {//================================== class

        public MViewHolder(View itemView) {
            super(itemView);
            poster = itemView.findViewById(R.id.poster_iv);
            title = itemView.findViewById(R.id.title_tv);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    Movies movies = moviesList.get(position);
                    Intent intent = new Intent(context, MovieDetails.class);
                    intent.putExtra("poster", movies.getPoster());
                    intent.putExtra("title", movies.getTitle());
                    intent.putExtra("release_date", movies.getRelease_date());
                    intent.putExtra("vote_avg", movies.getVote_avg());
                    intent.putExtra("plot_synopsis", movies.getPlot_synopsis());
                    intent.putExtra("id",movies.getId());
                    context.startActivity(intent);
                }
            });
        }

        void bind(int position) {
            Movies movie = moviesList.get(position);
            title.setText(movie.getTitle());

            Picasso.get()
                    .load(movie.getPoster())
                    .into(poster);
        }
    }





}

