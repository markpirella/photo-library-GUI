package model;

import javafx.collections.ObservableList;
import javafx.collections.FXCollections;

public class User {
	private String username;
	private ObservableList<Album> albums;
	
	public User(String username) {
		this.username = username;
		albums = FXCollections.observableArrayList();
	}
	
	public String getUsername() {
		return username;
	}
	
	public ObservableList<Album> getAlbums(){
		return albums;
	}
}
