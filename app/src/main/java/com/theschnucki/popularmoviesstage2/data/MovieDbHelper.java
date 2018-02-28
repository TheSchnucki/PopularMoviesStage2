package com.theschnucki.popularmoviesstage2.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MovieDbHelper extends SQLiteOpenHelper {


    //Name of the Database
    private static final String DATABASE_NAME = "moviesDb.db";

    //Version of the Database (needs to be incremented if something is changed)
    private static final int VERSION = 1;

    MovieDbHelper(Context context){
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
