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
	private List<String> usernames;
	private User currentUser;
	
	public Program() {
		usernames = new ArrayList<String>();
		currentUser = null;
	}
	
	public List<String> getUsernames(){
		return usernames;
	}
	
	public User getCurrentUser() {
		return currentUser;
	}
	
	public void setCurrentUser(User user) {
		currentUser = user;
	}
}
