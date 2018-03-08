package com.theschnucki.popularmoviesstage2.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Movie implements Parcelable {

    private static final String TAG = Movie.class.getSimpleName();

    private String title;
    private int TMDbId;
    private String releaseDate;
    private String posterPath;
    private String voteAverage;
    private String overview;
    private boolean isFavorite;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getTMDbId() {
        return TMDbId;
    }

    public void setTMDbId(int IMDbId) {
        this.TMDbId = IMDbId;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(String voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public boolean getIsFavorite () {return isFavorite;}

    public void setIsFavorite (boolean isFavorite) {this.isFavorite = isFavorite;}

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeInt(this.TMDbId);
        dest.writeString(this.releaseDate);
        dest.writeString(this.posterPath);
        dest.writeString(this.voteAverage);
        dest.writeString(this.overview);
        dest.writeByte((byte) (isFavorite ? 1 : 0));  //if isFavorite == true, byte == 1
    }

    public Movie() {
    }

    protected Movie(Parcel in) {
        this.title = in.readString();
        this.TMDbId = in.readInt();
        this.releaseDate = in.readString();
        this.posterPath = in.readString();
        this.voteAverage = in.readString();
        this.overview = in.readString();
        this.isFavorite = in.readByte()!= 0;  //isFavorite == true if byte != 0
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}
