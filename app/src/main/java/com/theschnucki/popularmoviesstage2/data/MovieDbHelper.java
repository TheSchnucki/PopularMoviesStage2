package com.theschnucki.popularmoviesstage2.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.theschnucki.popularmoviesstage2.data.MovieContract.MovieEntry;

public class MovieDbHelper extends SQLiteOpenHelper {


    //Name of the Database
    private static final String DATABASE_NAME = "moviesDb.db";

    //Version of the Database (needs to be incremented if something is changed)
    private static final int VERSION = 1;

    //Consturctor
    MovieDbHelper(Context context){
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        final String CREATE_TABLE = "CREATE TABLE " + MovieEntry.TABLE_NAME + " (" +
                                    MovieEntry._ID + " INTEGER PRIMARY KEY, " +
                                    MovieEntry.COLUMN_TITLE + " TEXT NOT NULL, " +
                                    MovieEntry.COLUMN_TMDB_ID + " INTEGER NOT NULL, " +
                                    MovieEntry.COLUMN_RELEASE_DATE + " TEXT, " +
                                    MovieEntry.COLUMN_POSTER_PATH + " TEXT, " +
                                    MovieEntry.COLUMN_POSTER_IMAGE + " BLOB, " +
                                    MovieEntry.COLUMN_VOTE_AVERAGE + " TEXT, " +
                                    MovieEntry.COLUMN_OVERVIEW + " TEXT, " +
                                    MovieEntry.COLUMN_FAVORITE + " TEXT);";

         db.execSQL(CREATE_TABLE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + MovieEntry.TABLE_NAME);
        onCreate(db);
    }
}
