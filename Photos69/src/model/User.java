package model;

import javafx.collections.ObservableList;
import java.util.ArrayList;
import javafx.collections.FXCollections;

public class User {
	private String username;
	private ObservableList<Album> albums;
	private ArrayList<String> tagTypes;
	
	public User(String username) {
		this.username = username;
		albums = FXCollections.observableArrayList();
		tagTypes = new ArrayList<String>();
	}
	
	public String getUsername() {
		return username;
	}
	
	public ObservableList<Album> getAlbums(){
		return albums;
	}
	
	public ArrayList<String> getTagTypes(){
		return tagTypes;
	}
	
	public String toString() {
		return username;
	}
}
