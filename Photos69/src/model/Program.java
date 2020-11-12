package model;

import javafx.collections.ObservableList;

public class Program {
	private ObservableList<User> users;
	
	public Program(ObservableList<User> users) {
		this.users = users;
	}
	
	public ObservableList<User> getUsers(){
		return users;
	}
}
