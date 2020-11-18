package model;

//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
import java.util.ArrayList;
//import java.util.List;
import java.io.Serializable;

/**
 * Program class, used to aid in holding important data read in from disk, and generated while application is running
 * @author Mark Pirella
 * @author Nicholas Farinella
 */
public class Program implements Serializable {
	/**
	 * auto-generated Serialization value
	 */
	private static final long serialVersionUID = 7099982614968657318L;
	
	/**
	 * collection of usernames that user can sign in as
	 */
	private ArrayList<String> usernames;
	
	/**
	 * User instance used to hold value of the current user signed into the photo library
	 */
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
