package com.example.myapplication.Models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Movies {

    @PrimaryKey
    private int id;
    private String title, release_date,plot_synopsis,poster,vote_avg;

    public Movies(int id, String title, String release_date, String plot_synopsis, String poster, String vote_avg) {
        this.id = id;
        this.title = title;
        this.release_date = release_date;
        this.plot_synopsis = plot_synopsis;
        this.poster = poster;
        this.vote_avg = vote_avg;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getRelease_date() {
        return release_date;
    }

    public String getPlot_synopsis() {
        return plot_synopsis;
    }

    public String getPoster() {
        if(poster.contains("http")) return  poster;
        return "http://image.tmdb.org/t/p/w185/"+poster;
    }

    public String getVote_avg() {
        return vote_avg;
    }

}

