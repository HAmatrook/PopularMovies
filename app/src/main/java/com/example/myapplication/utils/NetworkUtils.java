package com.example.myapplication.utils;

import android.net.Uri;

import com.example.myapplication.BuildConfig;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

final public class NetworkUtils {

    static String MOVES_BASE_URL= "https://api.themoviedb.org/3/movie/popular";
    final static String PARM_API = "api_key";
    final static String API_KEY= BuildConfig.API_KEY;

    private NetworkUtils() { }

    public static URL buildUrl(String MovieSort) {
             MOVES_BASE_URL ="https://api.themoviedb.org/3/movie/"+MovieSort;
        Uri builtUri = Uri.parse(MOVES_BASE_URL).buildUpon()
                .appendQueryParameter(PARM_API, API_KEY)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }


    public static String getResponse(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}

