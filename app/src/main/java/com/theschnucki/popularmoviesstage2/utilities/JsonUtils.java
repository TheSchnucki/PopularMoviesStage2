package com.theschnucki.popularmoviesstage2.utilities;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.theschnucki.popularmoviesstage2.data.MovieContract;
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

    final static String MOVIE_LIST = "results";          //movies list
    final static String MOVIE_TITLE = "title";           //Title
    final static String TMDB_ID = "id";                  //ID
    final static String RELEASE_DATE = "release_date";   //releaseDate
    final static String POSTER_PATH = "poster_path";     //Poster
    final static String VOTE_AVERAGE = "vote_average";   //VoteAverage
    final static String OVERVIEW = "overview";           //plotSynopsis

    final static String POSTER_PATH_PREFIX = "http://image.tmdb.org/t/p/"; //poster path base path
    final static String POSTER_RESOLUTION = "w185/";                       // resolution of the poster path

    final static String MOVIE_MESSAGE_CODE = "cod";

    public static List<Movie> getSimpleMovieListFromJson(Context context, String movieJsonString) throws JSONException {

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

            movie.setTMDbId(singleMovie.getInt(TMDB_ID));

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

    //converts a Cursor from the SQLdb to a MovieList
    public static List<Movie> cursorToMovieList (Cursor movieCursor) {

       List<Movie> movieList = new ArrayList<Movie>();

        movieCursor.moveToFirst();

        for (int i = 0; i < movieCursor.getCount(); i++){
            Movie movie = new Movie();

            movie.setTitle(movieCursor.getString(movieCursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_TITLE)));
            Log.v(TAG, "cursorToMovieList " + movie.getTitle() + " is the title");
            movie.setTMDbId(movieCursor.getInt(movieCursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_TMDB_ID)));
            movie.setReleaseDate(movieCursor.getString(movieCursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_RELEASE_DATE)));
            movie.setPosterPath(movieCursor.getString(movieCursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_POSTER_PATH)));
            movie.setVoteAverage(movieCursor.getString(movieCursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_VOTE_AVERAGE)));
            movie.setOverview(movieCursor.getString(movieCursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_OVERVIEW)));
            Log.v(TAG, "cursorToMovieList " + movie.getOverview() + " is the overview");

            movieList.add(movie);

            movieCursor.moveToNext();
            }

        return movieList;
    }
}