package com.theschnucki.popularmoviesstage2.utilities;

import android.content.Context;

import com.theschnucki.popularmoviesstage2.model.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    //Utility to handle the JSON data from TMDb

    private static final String TAG = JsonUtils.class.getSimpleName();

    public static List<Movie> getSimpleMovieListFromJson(Context context, String movieJsonString) throws JSONException {


        final String MOVIE_LIST = "results";          //movies list

        final String MOVIE_TITLE = "title";           //Title
        final String RELEASE_DATE = "release_date";   //releaseDate
        final String POSTER_PATH = "poster_path";     //Poster
        final String VOTE_AVERAGE = "vote_average";   //VoteAverage
        final String OVERVIEW = "overview";           //plotSynopsis

        final String POSTER_PATH_PREFIX = "http://image.tmdb.org/t/p/"; //poster path base path
        final String POSTER_RESOLUTION = "w185/";                       // resolution of the poster path

        String MOVIE_MESSAGE_CODE = "cod";

        //List that holds the Movies
        List<Movie> movieList = null;

        JSONObject movieListJson = new JSONObject(movieJsonString);

        //error handling
        if (movieListJson.has(MOVIE_MESSAGE_CODE)) {
            int errorCode = movieListJson.getInt(MOVIE_MESSAGE_CODE);

            switch (errorCode) {
                case HttpURLConnection.HTTP_OK:
                    break;
                case HttpURLConnection.HTTP_NOT_FOUND:
                    //sortOrder invalid
                    return null;
                default:
                    //Server down probably
                    return null;
            }
        }

        movieList = new ArrayList<Movie>();

        JSONArray movieArray = movieListJson.getJSONArray(MOVIE_LIST);

        for (int i = 0; i < movieArray.length(); i++){

            Movie movie = new Movie();

            JSONObject singleMovie = movieArray.getJSONObject(i);



            movie.setTitle(singleMovie.getString(MOVIE_TITLE));

            movie.setReleaseDate(singleMovie.getString(RELEASE_DATE));

            // posterPath is special because the JSON data ony delivers the suffix
            String posterPath = POSTER_PATH_PREFIX + POSTER_RESOLUTION + singleMovie.getString(POSTER_PATH);
            movie.setPosterPath(posterPath);

            movie.setVoteAverage(singleMovie.getString(VOTE_AVERAGE));
            movie.setOverview(singleMovie.getString(OVERVIEW));

            movieList.add(movie);
        }

        return movieList;
    }
}