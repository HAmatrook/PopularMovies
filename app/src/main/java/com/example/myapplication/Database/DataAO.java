package com.example.myapplication.Database;
import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;


import com.example.myapplication.Models.Movies;

import java.util.List;

@Dao
public interface DataAO {

    @Query("Select * from Movies")
    public LiveData<List<Movies>> getAllMovies();

    @Query("Select * from Movies where id = :id")
    public Movies getMovieById(int id);

    @Insert
    public void insertMovie(Movies movie);

    @Query("DELETE FROM Movies WHERE id = :id")
    void deleteMovie(int id);



}
