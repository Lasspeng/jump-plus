package com.cognixia.jump.dao;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import com.cognixia.jump.model.Movie;

public interface MovieTrackerDao {
    
    public void setConnection() throws FileNotFoundException, ClassNotFoundException, IOException, SQLException;

    public List<Movie> getAllMovies();

    public Optional<Movie> getMovieById(int id);

    public void createMovie(Movie movie);

    public void deleteMovie(int id);

    public void updateMovie(Movie movie);
}
