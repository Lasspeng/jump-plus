package com.cognixia.jump.model;

public class AccountMovieJoin {
	
	private int id;
	private String title;
	private int releaseDate;
	private int rating;
	public AccountMovieJoin(int id, String title, int releaseDate, int rating) {
		super();
		this.id = id;
		this.title = title;
		this.releaseDate = releaseDate;
		this.rating = rating;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getReleaseDate() {
		return releaseDate;
	}
	public void setReleaseDate(int releaseDate) {
		this.releaseDate = releaseDate;
	}
	public int getRating() {
		return rating;
	}
	public void setRating(int rating) {
		this.rating = rating;
	}
	

}
