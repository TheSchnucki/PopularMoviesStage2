package com.theschnucki.popularmoviesstage2.model;

public class Review {

    private static final String TAG = Review.class.getSimpleName();

    private String author;
    private String contents;
    private String reviewUrl;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getReviewUrl() {
        return reviewUrl;
    }

    public void setReviewUrl(String reviewUrl) {
        this.reviewUrl = reviewUrl;
    }
}
