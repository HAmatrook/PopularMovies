package com.example.myapplication;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.Adapter.ReviewsAdapter;
import com.example.myapplication.Adapter.TrailersAdapter;
import com.example.myapplication.Database.MovieViewModel;
import com.example.myapplication.Models.Movies;
import com.example.myapplication.Models.Reviews;
import com.example.myapplication.Models.Trailers;
import com.example.myapplication.utils.JSONutils;
import com.example.myapplication.utils.NetworkUtils;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.URL;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MovieDetails extends AppCompatActivity {
    //public TextView release_date, plot_synopsis, vote_avg;
    //public ImageView poster, star;
    public boolean favorite = false;
    public boolean checkFav = false;
    public MovieViewModel viewModel;
    public static Trailers trailers;
    public static Reviews reviews;
    public int id;
    public String titleS, posterS, vote_avgS, release_dateS, plot_synopsisS;
    public static RecyclerView trailerRv, reviewsRv;
    public static TrailersAdapter trailersAdapter;
    public static ReviewsAdapter reviewsAdapter;
    public static LinearLayoutManager linearLayout, layoutManager2;
    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.poster_iv)
    ImageView posterIv;
    @BindView(R.id.star)
    ImageView star;
    @BindView(R.id.date_tv)
    TextView dateTv;
    @BindView(R.id.vote_tv)
    TextView voteTv;
    @BindView(R.id.overview_tv)
    TextView overviewTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        ButterKnife.bind(this);
        trailerRv = findViewById(R.id.trailer_rv);
        reviewsRv = findViewById(R.id.reviews_rv);
        linearLayout = new LinearLayoutManager(this);
        layoutManager2 = new LinearLayoutManager(this);

        viewModel = ViewModelProviders.of(this).get(MovieViewModel.class);

        new CheckFavoriteTask().execute();

        Intent intent = new Intent();
        intent.getExtras();
        id = getIntent().getIntExtra("id", -1);
        titleS = getIntent().getStringExtra("title");
        posterS = getIntent().getStringExtra("poster");
        vote_avgS = getIntent().getStringExtra("vote_avg");
        release_dateS = getIntent().getStringExtra("release_date");
        plot_synopsisS = getIntent().getStringExtra("plot_synopsis");


        titleTv.setText(titleS);
        dateTv.setText(release_dateS);
        overviewTv.setText(plot_synopsisS);
        voteTv.setText(vote_avgS);
        Picasso.get()
                .load(posterS)
                .into(posterIv);
        URL Trailer_URL = NetworkUtils.buildUrl(id + "/" + getString(R.string.videos));
        new TrailersTask().execute(Trailer_URL);

        URL Reviews_URL = NetworkUtils.buildUrl(id + "/" + getString(R.string.reviews));
        new ReviewsTask().execute(Reviews_URL);
        //==================

    }

    //public void starClick(View view) { new FavoriteTask().execute(viewModel);}

    @OnClick(R.id.star)
    public void onViewClicked() {
        new FavoriteTask().execute(viewModel);
    }

    class CheckFavoriteTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            if (viewModel.getMovieById(id) != null) {
                star.setImageResource(R.drawable.filled_star);
                favorite = true;
                checkFav = true;
            }
            return null;
        }
    }

    class FavoriteTask extends AsyncTask<MovieViewModel, Void, MovieViewModel> {

        @Override
        protected MovieViewModel doInBackground(MovieViewModel... movieViewModels) {

            if (favorite) {
                viewModel.removeFavorite(id);
                star.setImageResource(R.drawable.unfilled_star);
                favorite= false;
            } else {
                Movies movie = new Movies(id, titleS, release_dateS, plot_synopsisS, posterS, vote_avgS);
                viewModel.addFavorite(movie);
                star.setImageResource(R.drawable.filled_star);
                favorite=true;
            }

            return null;
        }


    }


    public static class TrailersTask extends AsyncTask<URL, Void, String> {

        @Override
        protected String doInBackground(URL... urls) {
            URL searchUrl = urls[0];
            String SearchResults = null;
            try {
                SearchResults = NetworkUtils.getResponse(searchUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return SearchResults;
        }

        @Override
        protected void onPostExecute(String searchResults) {
            if (searchResults != null && !searchResults.equals("")) {
                trailers = JSONutils.getTrailers(searchResults);
                trailerRv.setLayoutManager(linearLayout);
                trailerRv.setHasFixedSize(true);
                trailersAdapter = new TrailersAdapter(trailers.getTrailer_key());
                trailerRv.setAdapter(trailersAdapter);
                trailersAdapter.notifyDataSetChanged();
            }
        }

    }

    public static class ReviewsTask extends AsyncTask<URL, Void, String> {
        @Override
        protected String doInBackground(URL... urls) {
            URL searchUrl = urls[0];
            String SearchResults = null;
            try {
                SearchResults = NetworkUtils.getResponse(searchUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return SearchResults;
        }

        @Override
        protected void onPostExecute(String searchResults) {
            if (searchResults != null && !searchResults.equals("")) {
                reviews = JSONutils.getReviews(searchResults);
                if (reviews.getUsername().size() >= 0) {
                    reviewsRv.setLayoutManager(layoutManager2);
                    reviewsRv.setHasFixedSize(true);
                    reviewsAdapter = new ReviewsAdapter(reviews.getUsername(), reviews.getReviewText());
                    reviewsRv.setAdapter(reviewsAdapter);
                    reviewsAdapter.notifyDataSetChanged();
                }
            }
        }
    }
}
