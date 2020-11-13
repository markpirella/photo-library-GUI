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
	private ArrayList<String> tagTypes;
	
	public User(String username) {
		this.username = username;
		albums = new ArrayList<Album>();
		tagTypes = new ArrayList<String>();
	}
	
	public String getUsername() {
		return username;
	}
	
	public ArrayList<Album> getAlbums(){
		return albums;
	}
	
	public ArrayList<String> getTagTypes(){
		return tagTypes;
	}
	
	public String toString() {
		return username;
	}
}
