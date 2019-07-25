package com.example.myapplication.Models;

import java.util.List;

public class Reviews {

    int movieID;
    List<String> username;
    List<String> reviewText;

    public Reviews(int movieID, List<String> username, List<String> reviewText) {
        this.movieID = movieID;
        this.username = username;
        this.reviewText = reviewText;
    }

    public int getMovieID() {
        return movieID;
    }

    public List<String> getUsername() {
        return username;
    }

    public List<String> getReviewText() {
        return reviewText;
    }
}