package com.example.myapplication;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.example.myapplication.Adapter.MoveAdapter;
import com.example.myapplication.Database.MovieViewModel;
import com.example.myapplication.Models.Movies;
import com.example.myapplication.Settings.Settings_Activity;
import com.example.myapplication.utils.JSONutils;
import com.example.myapplication.utils.NetworkUtils;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity  implements SharedPreferences.OnSharedPreferenceChangeListener {

    private static final String RECYCLER_LAYOUT_KEY = "recycler_layout";
    Parcelable savedRecyclerLayoutState;

     static RecyclerView recycler;
     static MoveAdapter adapter;
     static  List<Movies> moviesList = new ArrayList<>();
     static GridLayoutManager gridLayoutManager ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);
        recycler = findViewById(R.id.recycler_view);
        String sortByPre = getSortValue();
         gridLayoutManager =
                new GridLayoutManager(MainActivity.this, 3);
        makeSearchQuery(sortByPre);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PreferenceManager.getDefaultSharedPreferences(this)
                .unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(RECYCLER_LAYOUT_KEY, recycler.getLayoutManager().onSaveInstanceState());
    }
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        if (savedInstanceState != null) {
            savedRecyclerLayoutState = savedInstanceState.getParcelable(RECYCLER_LAYOUT_KEY);
            if(savedRecyclerLayoutState!=null){
                gridLayoutManager.onRestoreInstanceState(savedRecyclerLayoutState);
            }
        }
    }

    private void makeSearchQuery( String sortByPre) {
        String sortBy = null;
        if(sortByPre.equalsIgnoreCase(getString(R.string.favorite_movies))){
            MovieViewModel viewModel = ViewModelProviders.of(this).get(MovieViewModel.class);
            viewModel.getFavorite().observe(this, new Observer<List<Movies>>() {

                @Override
                public void onChanged(@Nullable List<Movies> movies) {
                    String sortByPre = getSortValue();
                    if(sortByPre.equalsIgnoreCase(getString(R.string.favorite_movies))) {
                        moviesList.clear();
                        moviesList.addAll(movies);
                        setUpRecycler();
                    }
                }
            });
        }else {
            if (sortByPre.equalsIgnoreCase(getString(R.string.vote_average_desc)))
                sortBy = "top_rated";
            else if (sortByPre.equalsIgnoreCase(getString(R.string.popularity_desc)))
                sortBy = "popular";
            URL MoveDB_URL = NetworkUtils.buildUrl(sortBy);
            new networkTask().execute(MoveDB_URL);
        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(getString(R.string.sort_by_key))) {
            makeSearchQuery(sharedPreferences.getString(key, getString(R.string.popularity_desc)));
        }
    }

    public static class networkTask extends AsyncTask<URL, Void, String> {

        @Override
        protected String doInBackground(URL... params) {
            URL searchUrl = params[0];
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
                moviesList.clear();
                moviesList.addAll(JSONutils.getMoves(searchResults));
                setUpRecycler();
            }
        }
    }

    public static void setUpRecycler(){

        recycler.setLayoutManager(gridLayoutManager);
        recycler.setHasFixedSize(true);
        adapter = new MoveAdapter(moviesList);
        recycler.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemThatWasClickedId = item.getItemId();
        if (itemThatWasClickedId == R.id.settings_menu_item) {
            Intent intent = new Intent(this, Settings_Activity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private String getSortValue() {
        //Get the selected sort value from SharedPreferences
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        sharedPrefs.registerOnSharedPreferenceChangeListener(this);
        return sharedPrefs.getString(getString(R.string.sort_by_key), getString(R.string.sort_by_pref));
    }
}
