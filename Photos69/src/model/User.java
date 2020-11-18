package model;

//import javafx.collections.ObservableList;
import java.util.ArrayList;
//import javafx.collections.FXCollections;
import java.io.Serializable;

public class User implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1593856730203724865L;
	
	private String username;
	private ArrayList<Album> albums;
	private ArrayList<TagType> tagTypes;
	
	/**
	 * constructor to create User object
	 * @param username String to set username field to
	 */
	public User(String username) {
		this.username = username;
		albums = new ArrayList<Album>();
		tagTypes = new ArrayList<TagType>();
		tagTypes.add(new TagType("location", false));
		tagTypes.add(new TagType("person", true));
	}
	
	/**
	 * method to get username field
	 * @return String containing username
	 */
	public String getUsername() {
		return username;
	}
	
	/**
	 * method to get list of albums that belong to a User
	 * @return ArrayList of Album objects that belong to a User
	 */
	public ArrayList<Album> getAlbums(){
		return albums;
	}
	
	/**
	 * method to get list of user-created tag types
	 * @return ArrayList of TagType objects created by user
	 */
	public ArrayList<TagType> getTagTypes(){
		return tagTypes;
	}
	
	/**
	 * generic toString method for object
	 * @return String containing username of User
	 */
	public String toString() {
		return username;
	}
}
