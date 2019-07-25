package com.example.myapplication.Database;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.example.myapplication.Models.Movies;

import java.util.List;

public class MovieViewModel extends AndroidViewModel {

    private LiveData<List<Movies>> favorite;
    private AppDatabase database;
    public MovieViewModel(@NonNull Application application) {
        super(application);
        database = AppDatabase.getDatabase(this.getApplication());
        favorite = database.MovieDAO().getAllMovies();
    }

    public LiveData<List<Movies>> getFavorite() {
        return favorite;
    }

    public Movies getMovieById(int id) {
        return  database.MovieDAO().getMovieById(id);
    }

    public void addFavorite(Movies movie){
            database.MovieDAO().insertMovie(movie);

    }

    public void removeFavorite(int id){
        database.MovieDAO().deleteMovie(id);

    }
}
