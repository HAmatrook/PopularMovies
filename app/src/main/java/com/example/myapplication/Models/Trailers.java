package com.example.myapplication.Models;

import java.util.List;

public class Trailers {

    int movieID;
    List<String> trailer_key;

    public Trailers(int movieID, List<String> trailer_key) {
        this.movieID = movieID;
        this.trailer_key = trailer_key;
    }

    public int getMovieID() {
        return movieID;
    }

    public List<String> getTrailer_key() {
        return trailer_key;
    }
}
