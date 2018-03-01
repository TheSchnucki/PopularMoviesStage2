package com.theschnucki.popularmoviesstage2;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.theschnucki.popularmoviesstage2.model.Movie;

public class DetailActivity extends AppCompatActivity {

    public static final String TAG = DetailActivity.class.getSimpleName();

    public Movie movie = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView posterIv = findViewById(R.id.poster_Iv);

        Intent intent = getIntent();

        if (intent == null) {
            Log.v(TAG, "Intent is null");
            closeOnError();
        }

        //getting the extra from the starting activity
        movie = (Movie) intent.getParcelableExtra("movie_parcel");
        if (movie == null){
            Log.v(TAG, "movie_parcel not found");
            closeOnError();
        }

        populateUI(movie);
        Picasso.with(this).load(movie.getPosterPath()).into(posterIv);
        setTitle(movie.getTitle());
    }

    private void closeOnError() {
        finish();
    }

    private void populateUI(Movie movie) {
        TextView releaseDateTv = findViewById(R.id.release_date_Tv);
        TextView voteAverageTv = findViewById(R.id.vote_average_Tv);
        TextView overviewTv = findViewById(R.id.overview_Tv);

        releaseDateTv.setText(movie.getReleaseDate());
        voteAverageTv.setText(movie.getVoteAverage());
        overviewTv.setText(movie.getOverview());
    }

    //wired to a button on the UI to change the status of favorite or not
    public void onClickChangeFavorite (View view){
        //todo write movie to database if not in remove if in
        //todo  change appearance of the icon
        FloatingActionButton fovoriteChangeFlb = findViewById(R.id.favorite_fab);

    }
}