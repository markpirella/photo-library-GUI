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
	
	public User(String username) {
		this.username = username;
		albums = new ArrayList<Album>();
		tagTypes = new ArrayList<TagType>();
		tagTypes.add(new TagType("location", false));
		tagTypes.add(new TagType("person", true));
	}
	
	public String getUsername() {
		return username;
	}
	
	public ArrayList<Album> getAlbums(){
		return albums;
	}
	
	public ArrayList<TagType> getTagTypes(){
		return tagTypes;
	}
	
	public String toString() {
		return username;
	}
}
