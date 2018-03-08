package com.theschnucki.popularmoviesstage2.utilities;

import android.content.Context;

import com.theschnucki.popularmoviesstage2.model.Movie;
import com.theschnucki.popularmoviesstage2.model.Review;
import com.theschnucki.popularmoviesstage2.model.Trailer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    //Utility to handle the Movie JSON data from TMDb

    private static final String TAG = JsonUtils.class.getSimpleName();

    private final static String MOVIE_LIST = "results";          //movies list
    private final static String MOVIE_TITLE = "title";           //Title
    private final static String TMDB_ID = "id";                  //ID
    private final static String RELEASE_DATE = "release_date";   //releaseDate
    private final static String POSTER_PATH = "poster_path";     //Poster
    private final static String VOTE_AVERAGE = "vote_average";   //VoteAverage
    private final static String OVERVIEW = "overview";           //plotSynopsis

    private final static String POSTER_PATH_PREFIX = "http://image.tmdb.org/t/p/"; //poster path base path
    private final static String POSTER_RESOLUTION = "w185/";                       // resolution of the poster path


    private final static String TRAILER_LIST = "results";       //trailer list
    private final static String TRAILER_NAME = "name";          //Name
    private final static String TRAILER_KEY = "key";            //Key
    private final static String TRAILER_TYPE = "type";          //Type

    private final static String REVIEW_LIST = "results";        //review list
    private final static String REVIEW_AUTHOR = "author";       //author
    private final static String REVIEW_CONTENTS = "content";    //contents
    private final static String REVIEW_URL = "url";             //URL

    private final static String ERROR_MESSAGE_CODE = "cod";

    private final static String TRAILER_PATH_PREFIX = "http://image.tmdb.org/t/p/"; //poster path base path


    public static List<Movie> getSimpleMovieListFromJson(Context context, String movieJsonString) throws JSONException {

        //List that holds the Movies
        List<Movie> movieList = null;

        JSONObject movieListJson = new JSONObject(movieJsonString);

        //error handling
        if (movieListJson.has(ERROR_MESSAGE_CODE)) {
            int errorCode = movieListJson.getInt(ERROR_MESSAGE_CODE);

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

    public static List<Trailer> getSimpleTrailerListFromJson(Context context, String trailerJsonString) throws JSONException {

        //List that holds the Trailer
        List<Trailer> trailerList = null;

        JSONObject trailerListJson = new JSONObject(trailerJsonString);

        //error handling
        if (trailerListJson.has(ERROR_MESSAGE_CODE)) {
            int errorCode = trailerListJson.getInt(ERROR_MESSAGE_CODE);

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

        trailerList = new ArrayList<Trailer>();

        JSONArray trailerArray = trailerListJson.getJSONArray(TRAILER_LIST);

        for (int i = 0; i < trailerArray.length(); i++){

            Trailer trailer = new Trailer();

            JSONObject singleTrailer = trailerArray.getJSONObject(i);

            trailer.setKey(singleTrailer.getString(TRAILER_KEY));
            trailer.setName(singleTrailer.getString(TRAILER_NAME));
            trailer.setType(singleTrailer.getString(TRAILER_TYPE));

            // trailerPath is special because the JSON data ony delivers the suffix
            String trailerPath = TRAILER_PATH_PREFIX +  singleTrailer.getString(TRAILER_KEY);
            trailer.setPath(trailerPath);

            trailerList.add(trailer);
        }

        return trailerList;
    }

    public static List<Review> getSimpleReviewListFromJson(Context context, String reviewJsonString) throws JSONException {

        //List that holds the Reviews
        List<Review> reviewList = null;

        JSONObject reviewListJson = new JSONObject(reviewJsonString);

        //error handling
        if (reviewListJson.has(ERROR_MESSAGE_CODE)) {
            int errorCode = reviewListJson.getInt(ERROR_MESSAGE_CODE);

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

        reviewList = new ArrayList<Review>();

        JSONArray reviewArray = reviewListJson.getJSONArray(REVIEW_LIST);

        for (int i = 1; i < reviewArray.length(); i++) {

            Review review = new Review();

            JSONObject singleReview = reviewArray.getJSONObject(i);

            review.setAuthor(singleReview.getString(REVIEW_AUTHOR));
            review.setContents(singleReview.getString(REVIEW_CONTENTS));
            review.setReviewUrl(singleReview.getString(REVIEW_URL));

            reviewList.add(review);
        }

        return reviewList;
    }
}