package com.example.myapplication.utils;

import com.example.myapplication.Models.Movies;
import com.example.myapplication.Models.Reviews;
import com.example.myapplication.Models.Trailers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JSONutils {

    public static final String KEY_TITLE = "title";
    public static final String KEY_RESULTS = "results";
    public static final String KEY_ID = "id";
    public static final String KEY_DATE = "release_date";
    public static final String KEY_OVERVIEW = "overview";
    public static final String KEY_POSTER = "poster_path";
    public static final String KEY_AVERAGE = "vote_average";
    public static final String KEY_KEY = "key";
    public static final String KEY_AUTHOR = "author";
    public static final String KEY_CONTENT = "content";

    public static List<Movies> getMoves(String searchResults){
        List<Movies>  moves = new ArrayList<>();
        try {

            JSONObject jsonObject = new JSONObject(searchResults);
            JSONArray results = jsonObject.getJSONArray(KEY_RESULTS);


            for (int i=0; i<results.length(); i++){
                JSONObject index = results.getJSONObject(i);
                int id           = index.optInt(KEY_ID,-1);
                String title        = index.optString(KEY_TITLE);
                String release_date = index.optString(KEY_DATE);
                String plot_synopsis= index.optString(KEY_OVERVIEW);
                String poster       = index.optString(KEY_POSTER);
                String vote_avg     = index.optString(KEY_AVERAGE);
                Movies moviesObj = new Movies(id, title, release_date,plot_synopsis,poster, vote_avg);
                moves.add(moviesObj);
            }
        }  catch (JSONException e) {
            e.printStackTrace();
        }

    return moves;
    }


    public static Trailers getTrailers(String searchResults){
        int id =-1;
        List<String>  trailer_key = new ArrayList<>();


        try {
            JSONObject jsonObject = null;
            jsonObject = new JSONObject(searchResults);

            id = jsonObject.optInt(KEY_ID);

            JSONArray results = jsonObject.getJSONArray(KEY_RESULTS);
            for (int i=0; i<results.length(); i++) {
                JSONObject index = results.getJSONObject(i);
                trailer_key.add(index.optString(KEY_KEY));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return new Trailers(id, trailer_key);
    }

    public static Reviews getReviews(String searchResults){
        int id =-1;
        List<String>  usernameList = new ArrayList<>();
        List<String>  reviewList = new ArrayList<>();

        try {
            JSONObject jsonObject = null;
            jsonObject = new JSONObject(searchResults);

            id = jsonObject.optInt(KEY_ID);

            JSONArray results = jsonObject.getJSONArray(KEY_RESULTS);
            for (int i=0; i<results.length(); i++) {
                JSONObject index = results.getJSONObject(i);
                usernameList.add(index.optString(KEY_AUTHOR));
                reviewList.add(index.optString(KEY_CONTENT));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return new Reviews(id, usernameList, reviewList);
    }


}
