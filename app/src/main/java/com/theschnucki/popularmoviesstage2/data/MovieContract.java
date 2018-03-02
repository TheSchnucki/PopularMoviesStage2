package com.theschnucki.popularmoviesstage2.data;

import android.net.Uri;
import android.provider.BaseColumns;

public class MovieContract {

    // Authority should be the same as in teh android manifest
    public static final String AUTHORITY ="com.theschnucki.popularmoviesstage2";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    public static final String PATH_MOVIES = "favoriteMovies";

    //MovieEntry is an inner class that defines the contens of the Movie table
    public static final class MovieEntry implements BaseColumns {

        //MovieEntry content Uri = base content Uri + path
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOVIES).build();

        //Movie table and column names
        public static final String TABLE_NAME = "favoriteMovies";

        //"_ID" column is automatically created
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_TMDB_ID = "TMDb_id";
        public static final String COLUMN_RELEASE_DATE = "release_date";
        public static final String COLUMN_POSTER_PATH = "poster_path";
        public static final String COLUMN_VOTE_AVERAGE = "vote_average";
        public static final String COLUMN_OVERVIEW = "overview";
        public static final String COLUMN_FAVORITE = "favorite";
    }
}
