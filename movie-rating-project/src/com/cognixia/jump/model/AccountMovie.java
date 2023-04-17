package com.cognixia.jump.model;

public class AccountMovie {
    
    private int accountId;
    private int movieId;
    private int rating;


    public AccountMovie(int accountId, int movieId, int rating) {
        this.accountId = accountId;
        this.movieId = movieId;
        this.rating = rating;
    }

    public int getAccountId() {
        return this.accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public int getMovieId() {
        return this.movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public int getRating() {
        return this.rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "{" +
            " accountId='" + getAccountId() + "'" +
            ", movieId='" + getMovieId() + "'" +
            ", rating='" + getRating() + "'" +
            "}";
    }

}
