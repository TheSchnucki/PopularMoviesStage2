package com.theschnucki.popularmoviesstage2.utilities;

import android.content.Context;

import com.theschnucki.popularmoviesstage2.model.Movie;
import com.theschnucki.popularmoviesstage2.model.Trailer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

public class TrailerJsonUtils {

    //Utility to handle the Movie JSON data from TMDb

    private static final String TAG = TrailerJsonUtils.class.getSimpleName();

    final static String TRAILER_LIST = "results";       //trailer list
    final static String TRAILER_NAME = "name";          //Name
    final static String TRAILER_KEY = "key";            //Key
    final static String TRAILER_TYPE = "type";          //Type

    //TODO insert the correct prefix
    final static String TRAILER_PATH_PREFIX = "http://image.tmdb.org/t/p/"; //poster path base path


    final static String TRAILER_MESSAGE_CODE = "cod";

    public static List<Trailer> getSimpleTrailerListFromJson(Context context, String movieJsonString) throws JSONException {

        //List that holds the Trailer
        List<Trailer> trailerList = null;

        JSONObject trailerListJson = new JSONObject(movieJsonString);

        //error handling
        if (trailerListJson.has(TRAILER_MESSAGE_CODE)) {
            int errorCode = trailerListJson.getInt(TRAILER_MESSAGE_CODE);

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
}