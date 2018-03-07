package com.theschnucki.popularmoviesstage2;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.theschnucki.popularmoviesstage2.data.MovieContract;
import com.theschnucki.popularmoviesstage2.model.Movie;

public class DetailTabedActivity extends AppCompatActivity {

    public static final String TAG = DetailTabedActivity.class.getSimpleName();

    public static Movie movie = null;

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_tabed);

        Intent intent = getIntent();

        if (intent == null) {
            Log.v(TAG, "Intent is null");
            closeOnError();
        }

        //getting the extra from the starting activity
        movie = intent.getParcelableExtra("movie_parcel");
        if (movie == null){
            Log.v(TAG, "movie_parcel not found");
            closeOnError();
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

        final FloatingActionButton favoriteChangeFab = (FloatingActionButton) findViewById(R.id.favorite_fab);

        setImageOnFab(favoriteChangeFab);
        //Set image on FAB

        favoriteChangeFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickChangeFavorite(favoriteChangeFab);
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                //        .setAction("Action", null).show();
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail_tabed, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void closeOnError() {
        finish();
    }




    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_placeholder, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }
    }

    /**
     * The Details fragment containing the Details view.
     */

    public static class DetailsFragment extends Fragment {

        // The fragment argument representing the section number for this fragment.

        private static final String ARG_SECTION_NUMBER = "section_number";

        public DetailsFragment() {}

        // Returns a new instance of this fragment for the given section number.
        public static DetailsFragment newInstance(int sectionNumber) {
            DetailsFragment fragment = new DetailsFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
            ImageView posterIv = rootView.findViewById(R.id.poster_Iv);
            Picasso.with(getContext()).load(movie.getPosterPath()).into(posterIv);

            TextView releaseDateTv = rootView.findViewById(R.id.release_date_Tv);
            TextView voteAverageTv = rootView.findViewById(R.id.vote_average_Tv);
            TextView overviewTv = rootView.findViewById(R.id.overview_Tv);

            releaseDateTv.setText(movie.getReleaseDate());
            voteAverageTv.setText(movie.getVoteAverage());
            overviewTv.setText(movie.getOverview());

            return rootView;
        }
    }

    /*public static class TrailerFragment extends Fragment {

        private static final String ARG_SECTION_NUMBER = "section_number";

        public TrailerFragment() {}

        public static DetailsFragment newInstance(int sectionNumber) {
            DetailsFragment fragment = new DetailsFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            View rootView = inflater.inflate(R.layout.fragment_trailer, container, false);
            // 1. get a reference to recyclerView
            RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.trailer_rv);

            // 2. set layoutManger
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

            // this is data fro recycler view
            ItemData itemsData[] = {
                    new ItemData("Indigo", R.drawable.circle),
                    new ItemData("Red", R.drawable.color_ic_launcher),
                    new ItemData("Blue", R.drawable.indigo),
                    new ItemData("Green", R.drawable.circle),
                    new ItemData("Amber", R.drawable.color_ic_launcher),
                    new ItemData("Deep Orange", R.drawable.indigo)
            };


            // 3. create an adapter
            TrailerAdapter mAdapter = new TrailerAdapter(itemsData);
            // 4. set adapter
            recyclerView.setAdapter(mAdapter);
            // 5. set item animator to DefaultAnimator
            recyclerView.setItemAnimator(new DefaultItemAnimator());

            return rootView;
        }
    }*/
    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            if (position == 0)  return DetailsFragment.newInstance(position + 1);
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }
    }


    /**
     * Here the Section for handling the button begins
     * wired to a button on the UI to change the status of favorite or not
     *
     */
    public void onClickChangeFavorite (FloatingActionButton favoriteChangeFab){
        //todo write movie to database if not in remove if in
        //todo  change appearance of the icon

        if (!movie.getIsFavorite()){
            favoriteChangeFab.setImageResource(R.drawable.ic_favorite);
            movie.setIsFavorite(true);
            Log.v(TAG, "Favorite = " + movie.getIsFavorite());
            addMovieToFavorites();
        } else {
            favoriteChangeFab.setImageResource(R.drawable.ic_favorite_border);
            movie.setIsFavorite(false);
            Log.v(TAG, "Favorite = " + movie.getIsFavorite());
            deleteMovieFromFavorites();
        }

    }

    public int checkIfIsFavorite () {

        String stringTBDbId = Integer.toString(movie.getTMDbId());
        Uri uri = MovieContract.MovieEntry.CONTENT_URI;
        uri = uri.buildUpon().appendPath(stringTBDbId).build();

        Cursor testCursor = getContentResolver().query(uri,
                null,
                null,
                null,
                null);
        Log.v(TAG, "Cursor length = " + testCursor.getCount());

        return testCursor.getCount();
    }

    public void addMovieToFavorites () {

        if (checkIfIsFavorite() == 0) {

            ContentValues contentValues = new ContentValues();

            contentValues.put(MovieContract.MovieEntry.COLUMN_TITLE, movie.getTitle());
            contentValues.put(MovieContract.MovieEntry.COLUMN_TMDB_ID, movie.getTMDbId());
            contentValues.put(MovieContract.MovieEntry.COLUMN_RELEASE_DATE, movie.getReleaseDate());
            contentValues.put(MovieContract.MovieEntry.COLUMN_POSTER_PATH, movie.getPosterPath());
            contentValues.put(MovieContract.MovieEntry.COLUMN_VOTE_AVERAGE, movie.getVoteAverage());
            contentValues.put(MovieContract.MovieEntry.COLUMN_OVERVIEW, movie.getOverview());

            //insert Movie via Content resolver
            Uri uri = getContentResolver().insert(MovieContract.MovieEntry.CONTENT_URI, contentValues);

            //TODO remove this log entry
            if (uri != null) {
                Log.v(TAG, "Movie entry successful");
            }
        } else {
            Log.v(TAG, "Movie already in Favorites");
        }
    }

    public void deleteMovieFromFavorites() {

        String stringTBDbId = Integer.toString(movie.getTMDbId());
        Uri uri = MovieContract.MovieEntry.CONTENT_URI;
        uri = uri.buildUpon().appendPath(stringTBDbId).build();

        int deleted = getContentResolver().delete(uri, null, null);
        Log.v(TAG, "Movies deleted " + deleted);
    }

    private void setImageOnFab(FloatingActionButton favoriteChangeFab){
        if (checkIfIsFavorite() != 0){
            favoriteChangeFab.setImageResource(R.drawable.ic_favorite);
            movie.setIsFavorite(true);
        } else {
            favoriteChangeFab.setImageResource(R.drawable.ic_favorite_border);
            movie.setIsFavorite(false);
        }
    }
}

