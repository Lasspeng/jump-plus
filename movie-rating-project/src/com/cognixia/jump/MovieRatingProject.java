package com.cognixia.jump;

import java.sql.Connection;
import java.text.DecimalFormat;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import com.cognixia.jump.connection.ConnectionManager;
import com.cognixia.jump.dao.MovieTrackerDaoSql;
import com.cognixia.jump.model.Account;
import com.cognixia.jump.model.AccountMovie;
import com.cognixia.jump.model.AccountMovieJoin;
import com.cognixia.jump.model.Movie;

public class MovieRatingProject {
    
    public static void main(String[] args) {
        
        Connection conn;
        MovieTrackerDaoSql dao = new MovieTrackerDaoSql();
        try {
            conn = ConnectionManager.getConnection();
            dao.setConnection();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Connection failed.");
        }
     
        
        Scanner sc = new Scanner(System.in);
        int input = 0;
        Account currentAcc = null;
        
        while (true) {
        	System.out.println("+============================================================================+");
        	System.out.println("1. REGISTER");
        	System.out.println("2. LOGIN");
        	System.out.println("3. VIEW MOVIES");
        	System.out.println("4. VIEW YOUR RATED MOVIES");
        	System.out.println("5. EXIT");
        	System.out.println("+============================================================================+");
        	
        	try {
        		input = sc.nextInt();
        		if (input < 1 || input > 5) throw new Exception();
        	} catch (InputMismatchException e) {
        		System.out.println("Enter a number.");
        		input = 0;
        		sc.next();
        		continue;
        	} catch (Exception e) {
				System.out.println("Not a valid option");
				input = 0;
				continue;
			}
        	
        	switch(input) {
        	case 1: {
        		System.out.println("Enter your email address.");
        		
        		String email = sc.next();
        		
        		if (!Account.isValidEmail(email)) {
        			System.out.println("Email address is not valid. Try again.");
        			break;
        		}
        		System.out.println("Enter a username.");
        		String username = sc.next();
        		
        		System.out.println("Enter a password.");
        		String password = sc.next();
        		
        		dao.createAccount(email, username, password);
        		break;
        	}
        	case 2: {
        		System.out.println("Enter your username.");
        		String username = sc.next();
        		System.out.println("Enter your password");
        		String password = sc.next();
        		
        		int choice = 0;
        		do {
        			if (dao.doesAccountExist(username, password)) {
        				currentAcc = new Account(username, password);
        				DecimalFormat df = new DecimalFormat("0.0");
        				System.out.println("+============================================================================+");
        				System.out.printf("| %-30s | %-12s | %12s |%n", "Movie", "Avg. Rating", "# of Ratings");
        				System.out.println("=============================================================================");
        				List<Movie> movieList = dao.getAllMovies();
        				
        				for (Movie movie : movieList) {
        					System.out.printf("| %-30s | %-12s | %12s |%n", movie.getMovieId() + ". " + movie.getTitle(), df.format(movie.getRatingAvg()) + " stars", movie.getRatingCount());
        				}
        				System.out.println("");
        				System.out.println((movieList.size() + 1) + ". EXIT");
        				System.out.println("");
        				System.out.println("Select the movie you want to rate.");
        				int movieId = sc.nextInt();
        				if (movieId == movieList.size() + 1) break;
        				
        				System.out.println("Movie: " + movieList.get(movieId - 1).getTitle());
        				System.out.println("Rating:");
        				System.out.println("0. Really Bad");
        				System.out.println("1. Bad");
        				System.out.println("2. Not Good");
        				System.out.println("3. Okay");
        				System.out.println("4. Good");
        				System.out.println("5. Great");
        				System.out.println("");
        				System.out.println("");
        				System.out.println("6. EXIT");
        				System.out.println("Select the rating you want to give");
        				int rating = sc.nextInt();
        				
        				currentAcc.addMovieToList(movieId, rating);
        				
        				System.out.println("Would you like to rate another movie?");
        				System.out.println("1. Yes");
        				System.out.println("2. No");
        				choice = sc.nextInt();
        				
        			}  else {
        				System.out.println("Username and/or password were not found. Try again.");
        				break;
        			}
        			
        		}
        		while (choice == 1);
        		break;
        	}
        	case 3: {
        		
        		DecimalFormat df = new DecimalFormat("0.0");
        		System.out.println("+============================================================================+");
				System.out.printf("| %-30s | %-12s | %-12s | %12s |%n", "Movie", "Release Date", "Avg. Rating", "# of Ratings");
				System.out.println("=============================================================================");
				List<Movie> movieList = dao.getAllMovies();
				
				for (Movie movie : movieList) {
					System.out.printf("| %-30s | %-12s | %-12s | %12s |%n", movie.getMovieId() + ". " + movie.getTitle(), movie.getReleaseDate(), df.format(movie.getRatingAvg()) + " stars", movie.getRatingCount());
				}
				System.out.println("");
				System.out.println((movieList.size() + 1) + ". EXIT");
				int movieId = sc.nextInt();
				if (movieId == movieList.size() + 1) break;
        		
        	}
        	case 4: {
        		if (currentAcc == null) {
        			System.out.println("Log in first, please.");
        			break;
        		}
        		
        		List<AccountMovieJoin> accountMovieList = dao.getUserRatedMovies(currentAcc.getId());
        		
        		System.out.println("+============================================================================+");
				System.out.printf("| %-30s | %-12s | %-12s |%n", "Movie", "Release Date", "Rating");
				System.out.println("=============================================================================");
        		for (AccountMovieJoin accountMovieJoin : accountMovieList) {
        			System.out.printf("| %-30s | %-12s | %-12s |%n", accountMovieJoin.getId() + ". " + accountMovieJoin.getTitle(), accountMovieJoin.getReleaseDate(), accountMovieJoin.getRating() + " stars");
        		}
        		System.out.println("");
        		System.out.println((accountMovieList.size() + 1) + ". EXIT");
        		int movieId = sc.nextInt();
        		if (movieId == accountMovieList.size() + 1) break;
        		
        	}
        	case 5: {
        		System.out.println("Goodbye!");
        		return;
        	}
        	default: continue;
        	}
        }
    }
}
