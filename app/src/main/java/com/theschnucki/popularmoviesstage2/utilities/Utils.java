package com.theschnucki.popularmoviesstage2.utilities;

import android.database.Cursor;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import com.theschnucki.popularmoviesstage2.MainActivity;
import com.theschnucki.popularmoviesstage2.data.MovieContract;
import com.theschnucki.popularmoviesstage2.model.Movie;


import java.util.ArrayList;
import java.util.List;

public class Utils {

    private static final String TAG = Utils.class.getSimpleName();


    //converts a Cursor from the SQLdb to a MovieList
    public static List<Movie> cursorToMovieList (Cursor movieCursor) {

        List<Movie> movieList = new ArrayList<Movie>();

        movieCursor.moveToFirst();

        for (int i = 0; i < movieCursor.getCount(); i++){
            Movie movie = new Movie();

            movie.setTitle(movieCursor.getString(movieCursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_TITLE)));
            movie.setTMDbId(movieCursor.getInt(movieCursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_TMDB_ID)));
            movie.setReleaseDate(movieCursor.getString(movieCursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_RELEASE_DATE)));
            movie.setPosterPath(movieCursor.getString(movieCursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_POSTER_PATH)));
            movie.setVoteAverage(movieCursor.getString(movieCursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_VOTE_AVERAGE)));
            movie.setOverview(movieCursor.getString(movieCursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_OVERVIEW)));

            movieList.add(movie);

            movieCursor.moveToNext();
        }

        return movieList;
    }




}
