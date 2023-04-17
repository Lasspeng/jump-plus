package com.cognixia.jump.model;

public class Movie {
    
    private int movieId;
    private String title;
    private int releaseDate;
    private double ratingAvg;
    private int ratingCount;


    public Movie(int movieId, String title, int releaseDate, double ratingAvg, int ratingCount) {
        this.movieId = movieId;
        this.title = title;
        this.releaseDate = releaseDate;
        this.ratingAvg = ratingAvg;
        this.ratingCount = ratingCount;
    }

    public int getMovieId() {
        return this.movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getReleaseDate() {
        return this.releaseDate;
    }

    public void setReleaseDate(int releaseDate) {
        this.releaseDate = releaseDate;
    }

    public double getRatingAvg() {
        return this.ratingAvg;
    }

    public void setRatingAvg(double ratingAvg) {
        this.ratingAvg = ratingAvg;
    }

    public int getRatingCount() {
        return this.ratingCount;
    }

    public void setRatingCount(int ratingCount) {
        this.ratingCount = ratingCount;
    }

    @Override
    public String toString() {
        return "{" +
            " movieId='" + getMovieId() + "'" +
            ", title='" + getTitle() + "'" +
            ", releaseDate='" + getReleaseDate() + "'" +
            ", ratingAvg='" + getRatingAvg() + "'" +
            ", ratingCount='" + getRatingCount() + "'" +
            "}";
    }

}
