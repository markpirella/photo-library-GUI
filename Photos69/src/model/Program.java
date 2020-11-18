package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;

public class Program implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7099982614968657318L;
	private ArrayList<String> usernames;
	private User currentUser;
	
	/**
	 * constructor to create Program object
	 */
	public Program() {
		usernames = new ArrayList<String>();
		currentUser = null;
	}
	
	/**
	 * method to get list containing usernames of all users
	 * @return ArrayList of Strings containing usernames
	 */
	public ArrayList<String> getUsernames(){
		return usernames;
	}
	
	/**
	 * method to get current user of program
	 * @return User object containing information of current user
	 */
	public User getCurrentUser() {
		return currentUser;
	}
	
	/**
	 * method to set current user of program
	 * @param user User object to set as current user
	 */
	public void setCurrentUser(User user) {
		currentUser = user;
	}
}
