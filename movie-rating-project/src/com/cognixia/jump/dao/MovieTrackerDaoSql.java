package com.cognixia.jump.dao;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.cognixia.jump.connection.ConnectionManager;
import com.cognixia.jump.model.Account;
import com.cognixia.jump.model.Movie;

public class MovieTrackerDaoSql implements MovieTrackerDao {

    private Connection conn;
    
    @Override
    public void setConnection() throws FileNotFoundException, ClassNotFoundException, IOException, SQLException {
        conn = ConnectionManager.getConnection();
    }

    public void createAccount(String email, String username, String password) {
        Account acct = new Account(email, username, password);
    }
    
    @Override
    public List<Movie> getAllMovies() {
        List<Movie> movieList = new ArrayList<Movie>();

        try (Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM movie");
        ) {
            while(rs.next()) {
                int movieId = rs.getInt("id");
                String title = rs.getString("title");
                int releaseDate = rs.getInt("release_date");
                double ratingAvg = rs.getFloat("rating_avg");
                int ratingCount = rs.getInt("rating_count");

                Movie movie = new Movie(movieId, title, releaseDate, ratingAvg, ratingCount);
                movieList.add(movie);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return movieList;
    }

    @Override
    public Optional<Movie> getMovieById(int id) {
        try (PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM movie WHERE id = ?");
        ) {
            pstmt.setInt(1, id);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                int movieId = rs.getInt("id");
                String title = rs.getString("title");
                int releaseDate = rs.getInt("release_date");
                double ratingAvg = rs.getFloat("rating_avg");
                int ratingCount = rs.getInt("rating_count");

                Movie movie = new Movie(movieId, title, releaseDate, ratingAvg, ratingCount);
                Optional<Movie> foundMovie = Optional.of(movie);
                return foundMovie;
            } else {
                rs.close();
                return Optional.empty();
            }
        } catch (SQLException e) {
            return Optional.empty();
        }
    }

    @Override
    public void createMovie(Movie movie) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createMovie'");
    }

    @Override
    public void deleteMovie(int id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteMovie'");
    }

    @Override
    public void updateMovie(Movie movie) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateMovie'");
    }
    
}
