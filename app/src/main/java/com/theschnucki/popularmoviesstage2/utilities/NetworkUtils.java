package com.theschnucki.popularmoviesstage2.utilities;

import android.net.Uri;
import android.util.Log;

import com.theschnucki.popularmoviesstage2.BuildConfig;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;

public class NetworkUtils {

    //this utility will be used to communicate with the TMDb server

    private static final String TAG = NetworkUtils.class.getSimpleName();

    private static final String API_KEY = BuildConfig.API_KEY;

    private static final String MOVIE_BASE_URL = "https://api.themoviedb.org/3";

    //other URL format additions
    final static String MEDIUM_PARAM = "movie";
    final static String VIDEO_PARAM = "videos";
    final static String REVIEW_PARAM = "reviews";

    //build the URL to communicate Movies with the server
    public static URL buildMovieUrl(String sortOrder) {
        Uri builtUri = Uri.parse(MOVIE_BASE_URL).buildUpon()
                .appendPath(MEDIUM_PARAM)
                .appendPath(sortOrder)
                .appendQueryParameter("api_key", API_KEY)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.v(TAG, "Built URI " + url);

        return url;
    }

    //build the URL to communicate Trailer with the server
    public static URL buildTrailerUrl(int movieId) {
        Uri builtUri = Uri.parse(MOVIE_BASE_URL).buildUpon()
                .appendPath(MEDIUM_PARAM)
                .appendPath(String.valueOf(movieId))
                .appendPath(VIDEO_PARAM)
                .appendQueryParameter("api_key", API_KEY)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.v(TAG, "Built URI " + url);

        return url;
    }

    //build the URL to communicate Reviews with he server
    public static URL buildReviewUrl(int movieId) {
        Uri builtUri = Uri.parse(MOVIE_BASE_URL).buildUpon()
                .appendPath(MEDIUM_PARAM)
                .appendPath(String.valueOf(movieId))
                .appendPath(REVIEW_PARAM)
                .appendQueryParameter("api_key", API_KEY)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.v(TAG, "Built URI " + url);

        return url;
    }

    //this method returns the result of the HTTPS response
    public static String getResponseFromHttpsURL(URL url) throws IOException {
        HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
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
