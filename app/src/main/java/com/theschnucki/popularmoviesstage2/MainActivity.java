package com.theschnucki.popularmoviesstage2;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.theschnucki.popularmoviesstage2.model.Movie;
import com.theschnucki.popularmoviesstage2.utilities.JsonUtils;
import com.theschnucki.popularmoviesstage2.utilities.NetworkUtils;

import java.net.URL;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieAdapterOnClickHandler {

    private static final String TAG = MainActivity.class.getSimpleName();

    private static final String API_KEY = BuildConfig.API_KEY;

    private RecyclerView mRecyclerView;
    private MovieAdapter mMovieAdapter;

    private TextView mErrorMessageDisplay;

    private ProgressBar mLoadingIndicator;

    private String sortOrder = "popular";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
        *Using findViewById to get a reference to the grid RecyclerView
        *allows to set adapter and toggle visibility
        */
        mRecyclerView = (RecyclerView) findViewById(R.id.grid_rv);

        mErrorMessageDisplay = (TextView) findViewById(R.id.error_message_tv);

        //GridLayoutManager supports different number of columns
        int numberOfColumns = calculateNumberOfColumns(this);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, numberOfColumns));

        // MovieAdapter is responsible for linking the Movie data to the Views that will be displayed
        mMovieAdapter = new MovieAdapter(this);

        //Attach the Adapter to the RecyclerView
        mRecyclerView.setAdapter(mMovieAdapter);

        //Progress bar indicates that Data is loading
        mLoadingIndicator = (ProgressBar) findViewById(R.id.loading_indicator_pb);

        //Once all of the views are set up, load Movie data
        loadMovieData();
    }

    private void loadMovieData() {
        showMovieDataView();
        new FetchMoviesTask().execute(sortOrder);
    }

    @Override
    public void onClick(Movie movie) {
        Context context = this;

        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra("movie_parcel", movie);
        startActivity(intent);
        //TODO get the information if movie is set favorite
    }

    //This method will make the MovieGrid visible and hide the error message
    private void showMovieDataView() {
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    //This method will show an error message and hide the MovieGrid
    private void showErrorMessage() {
        mRecyclerView.setVisibility(View.INVISIBLE);
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
    }

    public class FetchMoviesTask extends AsyncTask<String, Void, List<Movie>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingIndicator.setVisibility(View.VISIBLE);
        }

        @Override
        protected List<Movie> doInBackground(String... params) {

            //if there are no search parameter
            if (params.length == 0) return null;

            String sortOrder = params[0];
            URL movieRequestUrl = NetworkUtils.buildUrl(sortOrder);

            try {
                String jsonMovieResponse = NetworkUtils.getResponseFromHttpsURL(movieRequestUrl);

                List<Movie> simpleMovieList = JsonUtils.getSimpleMovieListFromJson(MainActivity.this, jsonMovieResponse);

                return simpleMovieList;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<Movie> loadedMovieList) {
            mLoadingIndicator.setVisibility(View.INVISIBLE);
            if (loadedMovieList != null) {
                showMovieDataView();
                mMovieAdapter.setMovieList(loadedMovieList);
            } else {
                showErrorMessage();
            }
        }
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public boolean onOptionsItemSelected (MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.pop_sort)  sortOrder = "popular";
        if (id == R.id.rate_sort) sortOrder = "top_rated";
        loadMovieData();
        return true;
    }

    // this should calculate the number of rows in the main grid view
    private int calculateNumberOfColumns (Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        int scalingFactor = 180; //see JsonUtils for resolution of downloaded image
        int noOfColumns = (int) (dpWidth/scalingFactor);
        return noOfColumns;
    }

}
