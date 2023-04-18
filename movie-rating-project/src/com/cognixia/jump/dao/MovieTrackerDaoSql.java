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
import com.cognixia.jump.model.AccountMovieJoin;
import com.cognixia.jump.model.Movie;

public class MovieTrackerDaoSql implements MovieTrackerDao {

    private Connection conn;
    
    @Override
    public void setConnection() throws FileNotFoundException, ClassNotFoundException, IOException, SQLException {
        conn = ConnectionManager.getConnection();
    }

    public boolean createAccount(String email, String username, String password) {
        boolean validEmail = Account.isValidEmail(email);
        if (!validEmail) {
            System.out.println("Account could not be created.");
            return false;
        }

        try (Statement stmt = conn.createStatement();) {
            int updated = stmt.executeUpdate("INSERT INTO account values(null, " + "'" + email + "', " + "'" + username + "', " + "'" + password + "'" + ")");

            if (updated != 0) {
                System.out.println("Account successfully created.");
                return true;
            } else {
                System.out.println("Account could not be created.");
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Account could not be created");
            e.printStackTrace();
            return false;
        }
    }

    public boolean doesAccountExist(String username, String password) {
        try(Statement stmt = conn.createStatement();) {
            ResultSet rs = stmt.executeQuery("SELECT * FROM account WHERE username = " + "'" + username + "' AND password = " + "'" + password + "'");

            if (rs.next()) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
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
                float ratingAvg = rs.getFloat("rating_avg");
                int ratingCount = rs.getInt("rating_count");

                Movie movie = new Movie(movieId, title, releaseDate, ratingAvg, ratingCount);
                movieList.add(movie);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return movieList;
    }
    
    public List<AccountMovieJoin> getUserRatedMovies(int userId) {
    	List<AccountMovieJoin> accountMovieList = new ArrayList<AccountMovieJoin>();
    	
    	try (Statement stmt = conn.createStatement();) {
    		ResultSet rs = stmt.executeQuery("SELECT m.id, m.title, m.release_date, am.rating FROM account_movie am JOIN movie m ON am.movie_id = m.id WHERE am.account_id = " + userId);
    		
    		while (rs.next()) {
    			int movieId = rs.getInt("m.id");
    			String movieTitle = rs.getString("m.title");
    			int releaseDate = rs.getInt("m.release_date");
    			int rating = rs.getInt("am.rating");
    			
    			accountMovieList.add(new AccountMovieJoin(movieId, movieTitle, releaseDate, rating));
    		}
    	} catch (SQLException e) {
    		e.printStackTrace();
    	}
    	return accountMovieList;
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
                float ratingAvg = rs.getFloat("rating_avg");
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
