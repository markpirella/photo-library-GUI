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
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

//import java.io.File;
import java.util.ArrayList;
import java.util.Optional;

/**
 * controller for UserSubsystem Stage
 * @author Mark Pirella
 * @author Nicholas Farinella
 */
public class UserSubsystemController {
	
	/**
	 * used to display existing Album objects
	 */
	@FXML ListView<Album> albumList;
	
	/**
	 * used to display earliest date of image file present in currently selected album
	 */
	@FXML TextField earliestDateDisplay;
	
	/**
	 * used to display latest date of image file present in currently selected album
	 */
	@FXML TextField latestDateDisplay;
	
	/**
	 * used to display number of Photo objects in currently selected album
	 */
	@FXML TextField numberOfPhotosDisplay;
	
	/**
	 * user inputs desired new album name here
	 */
	@FXML TextField inputNewAlbumName;
	
	/**
	 * user edits album name of currently selected album here
	 */
	@FXML TextField inputAlbumRename;
	
	/**
	 * used to store all Album objects that belong to the current user
	 */
	ArrayList<Album> albums;
	
	/**
	 * used to help display all Album objects that belong to the current user
	 */
	ObservableList<Album> observableAlbums;

	/**
	 * method to be called when switching to UserSubsystem Stage
	 * @param mainStage UserSubsystem Stage
	 */
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
	
	/**
	 * method to handle Open button pressed (switches to OpenedAlbumDisplay Stage)
	 * @param event Open button pressed
	 * @throws IOException if accessing OpenedAlbumDisplay.fxml fails
	 */
	@FXML private void handleOpenButton(ActionEvent event) throws IOException {
		
		if(albumList.getSelectionModel().getSelectedIndex() < 0 || albumList.getSelectionModel().getSelectedIndex() >= observableAlbums.size()) {
			return;
		}
		
		// **go to OpenedAlbumDisplay stage
		Stage stage = new Stage();
	    stage.setTitle(albumList.getSelectionModel().getSelectedItem().getName());
	    FXMLLoader myLoader = new FXMLLoader(getClass().getResource("OpenedAlbumDisplay.fxml"));
	    AnchorPane myPane = (AnchorPane) myLoader.load();            
	    Scene scene = new Scene(myPane);
	    stage.setScene(scene);
	    
	    // grab current stage (in kind of a cheap way, *shrug*) and close it
	    Stage currStage = (Stage)albumList.getScene().getWindow();
	    currStage.close();
	    
	    OpenedAlbumDisplayController openedAlbumDisplayController = myLoader.getController();
	    openedAlbumDisplayController.start(stage, albumList.getSelectionModel().getSelectedItem());
	    
	    // finally, switch to new stage
	    stage.show();
		
	}
	
	/**
	 * method to handle Add button (creates a new Album object for user)
	 * @param event Add button pressed
	 */
	@FXML private void handleAddButton(ActionEvent event) {
		
		// check for duplicate album name for this user
		for(Album a : albums) {
			if(a.getName().equals(inputNewAlbumName.getText())) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error");
				alert.setHeaderText("This album name is already being used");
				alert.setContentText("Please enter a different album name.");
				alert.showAndWait();
				return;
			}
		}
		
		if(inputNewAlbumName.getText().equals("")) { // user tried to create an album with no name
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("Album name cannot be empty");
			alert.setContentText("Please try again.");
			alert.showAndWait();
		}else { // user inputted a proper name
			Album newAlbum = new Album(inputNewAlbumName.getText());
			albums.add(newAlbum);
			observableAlbums.add(newAlbum);
			inputNewAlbumName.setText("");
			//System.out.println("about to select index num " + observableAlbums.size());
			albumList.getSelectionModel().select(observableAlbums.size()-1);
		}
		
		displayAlbumDetails();
		/*
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
	
	/**
	 * method to handle Edit Album Name button pressed
	 * @param event Edit Album Name button pressed
	 */
	@FXML private void handleEditButton(ActionEvent event) {
		
		if(observableAlbums.size() <= 0) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("No albums exist to edit");
			alert.setContentText("Please add some albums and try again.");
			alert.showAndWait();
			displayAlbumDetails();
			return;
		}
		
		if(inputAlbumRename.getText().equals("")) { // user tried to create an album with no name
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("Album name cannot be empty");
			alert.setContentText("Please try again.");
			alert.showAndWait();
			displayAlbumDetails();
			return;
		}
		
		// check for if user made no changes to album name
		if(albumList.getSelectionModel().getSelectedItem().getName().equals(inputAlbumRename.getText())) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("No changes made");
			alert.setContentText("Press make changes to the album name and try again.");
			alert.showAndWait();
			displayAlbumDetails();
			return;
		}
		
		// check for if user tried to make duplicate name of existing album
		for(Album a : albums) {
			if(a.getName().equals(inputAlbumRename.getText())) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error");
				alert.setHeaderText("This album name is already being used");
				alert.setContentText("Please enter a different album name.");
				alert.showAndWait();
				displayAlbumDetails();
				return;
			}
		}
			
		// user inputted a proper name
		
		int currentIndex = albumList.getSelectionModel().getSelectedIndex();
		
		albums.get(currentIndex).setName(inputAlbumRename.getText());
		observableAlbums.get(currentIndex).setName(inputAlbumRename.getText());
		albumList.getSelectionModel().select(-1);
		albumList.getSelectionModel().select(currentIndex);
		
		displayAlbumDetails();
			
		
		/*
		Photos.currentAlbumName = albumList.getSelectionModel().getSelectedItem().getName();
		Photos.currentIndex = albumList.getSelectionModel().getSelectedIndex();
		
		// **go to RenameAlbum stage
		Stage stage = new Stage();
	    stage.setTitle("Rename Album");
	    FXMLLoader myLoader = new FXMLLoader(getClass().getResource("RenameAlbum.fxml"));
	    AnchorPane myPane = (AnchorPane) myLoader.load();            
	    Scene scene = new Scene(myPane);
	    stage.setScene(scene);
	    stage.show();
	    */
	}
	
	/**
	 * method to handle Delete Album button being pressed 
	 * @param event Delete button pressed
	 */
	@FXML private void handleDeleteButton(ActionEvent event) {
		int index = albumList.getSelectionModel().getSelectedIndex();
		
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Confirmation Dialog");
		alert.setHeaderText("Confirmation needed!");
		alert.setContentText("Are you sure you want to delete album '" + observableAlbums.get(index)+"'?");

		Optional<ButtonType> result = alert.showAndWait();
		if (!(result.get() == ButtonType.OK)){ // user did not press ok
			return;
		}
		
		if(index >= 0) {
			observableAlbums.remove(index);
			albums.remove(index);
		}
		if(observableAlbums.size() <= 0) {
			earliestDateDisplay.setText("");
			latestDateDisplay.setText("");
			numberOfPhotosDisplay.setText("");
			inputAlbumRename.setText("");
		}
	}
	
	/**
	 * method to handle Search Photos button being pressed (go to PhotoSearch Stage)
	 * @param event Search Photos button pressed
	 * @throws IOException if accessing PhotoSearch.fxml fails
	 */
	@FXML private void handleSearchButton(ActionEvent event) throws IOException {
		
		// **go to PhotoSearch stage
		Stage stage = new Stage();
	    stage.setTitle("Search Photos");
	    FXMLLoader myLoader = new FXMLLoader(getClass().getResource("PhotoSearch.fxml"));
	    AnchorPane myPane = (AnchorPane) myLoader.load();            
	    Scene scene = new Scene(myPane);
	    stage.setScene(scene);
	    
	    // grab current stage (in kind of a cheap way, *shrug*) and close it
	    Stage currStage = (Stage)albumList.getScene().getWindow();
	    currStage.close();
	    
	    PhotoSearchController photoSearchController = myLoader.getController();
	    photoSearchController.start(stage);
	    
	    // finally, switch to new stage
	    stage.show();
		
	}
	
	/**
	 * method to handle Logout button being pressed (go to LoginScreen Stage)
	 * @param event Logout button pressed
	 * @throws IOException if accessing LoginScreen.fxml fails
	 */
	@FXML private void handleLogoutButton(ActionEvent event) throws IOException {
		
		Stage stage = new Stage();
	    stage.setTitle("Photos Login");
	    FXMLLoader myLoader = new FXMLLoader(getClass().getResource("LoginScreen.fxml"));
	    AnchorPane myPane = (AnchorPane) myLoader.load();            
	    Scene scene = new Scene(myPane);
	    stage.setScene(scene);
	    //Photos.getStage().close();
	    Stage currStage = (Stage)albumList.getScene().getWindow();
	    
	    try {
			Photos.writeUserObj(Photos.programSession.getCurrentUser());
		}catch(Exception e) {
			e.printStackTrace();
		}
	    
	    currStage.close();
	    //setPrevStage(stage);
	    stage.show();
		
	}
	
	/**
	 * method to handle Quit button being pressed
	 * @param event Quit button pressed
	 */
	@FXML private void handleQuitButton(ActionEvent event) {
		
		try {
			Photos.writeUserObj(Photos.programSession.getCurrentUser());
		}catch(Exception e) {
			e.printStackTrace();
		}
		System.exit(0);
		
	}
	
	/**
	 * method to display details of currently selected Album
	 */
	private void displayAlbumDetails() {
		if(albumList.getSelectionModel().getSelectedIndex() >= 0) {
			Album current = albumList.getSelectionModel().getSelectedItem();
			/*
			if(current.getPhotos().size() > 0) {
				current.setEarliestAndLatestDates();
			}
			*/
			numberOfPhotosDisplay.setText(""+current.getPhotos().size());
			earliestDateDisplay.setText(""+current.getEarliestDate());
			latestDateDisplay.setText(""+current.getLatestDate());
			inputAlbumRename.setText(current.getName());
		}
	}
	
}
