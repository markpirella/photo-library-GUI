package view;
import model.Album;

import java.io.IOException;

import app.Photos;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class AddAlbumController {
	
	/*
	
	@FXML TextField inputAlbumName;

	@FXML private void handleConfirmButton(ActionEvent event) throws IOException {
		
		// check for duplicate album name for this user
		for(Album a : UserSubsystemController.albums) {
			if(a.getName().equals(inputAlbumName.getText())) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error");
				alert.setHeaderText("This album name is already being used");
				alert.setContentText("Please enter a different album name.");
				alert.showAndWait();
				return;
			}
		}
		
		if(inputAlbumName.getText().equals("")) { // user tried to create an album with no name
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("Album name cannot be empty");
			alert.setContentText("Please try again.");
			alert.showAndWait();
		}else { // user inputted a proper name
			Album newAlbum = new Album(inputAlbumName.getText());
			UserSubsystemController.albums.add(newAlbum);
			UserSubsystemController.observableAlbums.add(newAlbum);
			
			// grab current stage (in kind of a cheap way, *shrug*) and close it
		    Stage currStage = (Stage)inputAlbumName.getScene().getWindow();
		    currStage.close();
		}
	
	}
	
	@FXML private void handleCancelButton(ActionEvent event) throws IOException {
	
		// grab current stage (in kind of a cheap way, *shrug*) and close it
	    Stage currStage = (Stage)inputAlbumName.getScene().getWindow();
	    currStage.close();
		
	}
	
	@FXML private void handleQuitButton(ActionEvent event) throws IOException {
	
		try {
			Photos.writeUserObj(Photos.programSession.getCurrentUser());
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	*/
	
}