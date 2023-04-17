package com.cognixia.jump.model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.cognixia.jump.connection.ConnectionManager;

public class Account {

    private static final String PATTERN = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";
    private Connection conn;
    
    private int id;
    private String email;
    private String username;
    private String password;
    private List<AccountMovie> list;


    public Account(int id, String email, String username, String password) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.password = password;
        this.list = new ArrayList<AccountMovie>();

        try {
			this.conn = ConnectionManager.getConnection();
		} catch(Exception e) {
			e.printStackTrace();
		}
		createList();
    }

    

    public Account(String email, String username, String password) {
        this.email = email;
        this.username = username;
        this.password = password;

        try {
			this.conn = ConnectionManager.getConnection();
		} catch(Exception e) {
			e.printStackTrace();
		}
    }



    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<AccountMovie> getList() {
        return this.list;
    }

    public void setList(List<AccountMovie> list) {
        this.list = list;
    }

    public static boolean isValidEmail(String email) {
        Pattern pattern = Pattern.compile(PATTERN);
        Matcher matcher = pattern.matcher(email);
        
        if (matcher.find())
            return true;
        else
            return false; 
    }

    private void createList() {

        try(Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM account_movie WHERE account_id " + this.id);
        ) {
            while (rs.next()) {
                int accountId = rs.getInt("account_id");
                int movieId = rs.getInt("movie_id");
                int rating = rs.getInt("rating");

                AccountMovie accountMovie = new AccountMovie(accountId, movieId, rating);
                list.add(accountMovie);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean addMovieToList(int movieId, int rating) {
        try (Statement stmt = conn.createStatement()) {
            int updated = stmt.executeUpdate("INSERT INTO account_movie values(" + getId() + ", " + movieId + ", " + rating + ")");

            if (updated != 0) {
                System.out.println("Show successfully added to list.");
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Show cannot be added to list. Try again.");
            return false;
        }
        return false;
    }

    public boolean updateMovieInList(int movieId, int rating) throws SQLException {
        int updated = 0;
        double ratingAverage = 0;
        int ratingCount = 0;

        // Get current rating numbers for the movie about to be rated
        try(Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT rating_avg, rating_count FROM movie WHERE id = " + movieId);
        ) {
            while(rs.next()) {
                ratingAverage = rs.getDouble("rating_avg");
                ratingCount = rs.getInt("rating_count");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Add the rating to the user's list
        try (Statement stmt = conn.createStatement();) {

            updated = stmt.executeUpdate("UPDATE account_movie SET rating = " + rating + " WHERE account_id = " + getId() + " AND movie_id = " + movieId);

        } catch (SQLException e) {
            System.out.println("List cannot be updated. Try again");
            return false;
        }
        if (updated == 0) {
            System.out.println("List cannot be updated.");
            return false;
        }
        int newRatingCount = ratingCount + 1;
        double newRatingAvg = ((ratingAverage * ratingCount) + rating) / newRatingCount;

        // Add rating changes to the movie in the movie table
        try(Statement stmt = conn.createStatement();) {
            int updated2 = stmt.executeUpdate("UPDATE movie SET rating_avg = " + newRatingAvg + ", rating_count = " + newRatingCount + " WHERE id = " + movieId);

            if (updated2 != 0) {
                System.out.println("List entry and movie successfully updated.");
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            System.out.println("List and movie cannot be updated. Try again.");
            return false;
        }
        
    }

    @Override
    public String toString() {
        return "{" +
            ", id='" + getId() + "'" +
            ", email='" + getEmail() + "'" +
            ", username='" + getUsername() + "'" +
            ", password='" + getPassword() + "'" +
            ", list='" + getList() + "'" +
            "}";
    }
    
}
