package com.cognixia.jump.progresstracker.dao;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;



public interface UserDao {
	
	public void setConnection() throws FileNotFoundException, ClassNotFoundException, IOException, SQLException;
	
	// Obtains the list of user shows
	public boolean getShows(int id);
	
	public boolean getAllShows(int id);

	public void getAllOtherUsers(User currentUser);

	public void getMessages(int currentUserId, String otherUsername);
	public void createMessage(int currentUserId, String otherUsername, String message);

	public User getUserByUsername(String username);
	
	public Optional<Show> getShowById(int id);
	
	public Optional<UserShow> getUserShow(int userID, int showID);
	public Optional<User> authenticateUser(String username, String password);
	public boolean addUser(User user) throws SQLException;
	public boolean addShows(UserShow show);
	public boolean addFavorite(UserShow show) throws SQLException;
	public boolean removeFavorite(int userID,int showID) throws SQLException;
	public boolean getFavorites(int userID) throws SQLException;
	public boolean updateShows(UserShow show);


	
	public boolean deleteUserShowById(int showId, int userId);
	
	// Admin class that implements this interface will have create, update, and delete functions within its own class
	
	
}