package view;

import app.Photos;
import model.Album;

import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;

public class UserSubsystemController {
	
	@FXML ListView<Album> albumList;
	@FXML TextField earliestDateDisplay, latestDateDisplay, numberOfPhotosDisplay;
	
	static ArrayList<Album> albums;
	static ObservableList<Album> observableAlbums;

	public void start(Stage mainStage) {
		//Photos.programSession.setCurrentUser(read);
		albums = Photos.programSession.getCurrentUser().getAlbums();
		observableAlbums = FXCollections.observableArrayList(albums);
		albumList.setItems(observableAlbums);
		
		albumList.getSelectionModel().select(0);
		displayAlbumDetails();
		
		// add listener to ensure that whenever user selects different album, displayed details are updated
		albumList.getSelectionModel().selectedIndexProperty().addListener((obs, oldVal, newVal) -> displayAlbumDetails());
		
		mainStage.setOnCloseRequest(event -> {
			try {
				Photos.writeUserObj(Photos.programSession.getCurrentUser());
			}catch(Exception e) {
				e.printStackTrace();
			}
		});
	}
	
	@FXML private void handleOpenButton(ActionEvent event) {
		
	}
	
	@FXML private void handleAddButton(ActionEvent event) throws IOException {
		
		// **go to AddAlbum stage
		Stage stage = new Stage();
	    stage.setTitle("Add New Album");
	    FXMLLoader myLoader = new FXMLLoader(getClass().getResource("AddAlbum.fxml"));
	    AnchorPane myPane = (AnchorPane) myLoader.load();            
	    Scene scene = new Scene(myPane);
	    stage.setScene(scene);
	    stage.show();
		/*
		Album newAlbum = new Album("new_album_test");
		albums.add(newAlbum);
		observableAlbums.add(newAlbum);
		*/
	}
	
	@FXML private void handleEditButton(ActionEvent event) {
		
	}
	
	@FXML private void handleDeleteButton(ActionEvent event) {
		int index = albumList.getSelectionModel().getSelectedIndex();
		if(index >= 0) {
			observableAlbums.remove(index);
			albums.remove(index);
		}
	}
	
	@FXML private void handleSearchButton(ActionEvent event) {
		
	}
	
	@FXML private void handleLogoutButton(ActionEvent event) {
		
	}
	
	@FXML private void handleQuitButton(ActionEvent event) {
		
	}
	
	private void displayAlbumDetails() {
		if(albumList.getSelectionModel().getSelectedIndex() >= 0) {
			Album current = albumList.getSelectionModel().getSelectedItem();
			numberOfPhotosDisplay.setText(""+current.getPhotos().size());
			earliestDateDisplay.setText(""+current.getEarliestDate());
			latestDateDisplay.setText(""+current.getLatestDate());
		}
	}
	
}
