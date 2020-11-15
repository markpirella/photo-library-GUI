package view;

import app.Photos;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import model.Album;

public class RenameAlbumController {
	/*
	
	@FXML TextField inputAlbumName;

	public void start(Stage mainStage) {
		inputAlbumName.setText(Photos.currentAlbumName);		
	}
	
	@FXML private void handleConfirmButton(ActionEvent event) {
		// check for duplicate album name for this user
		if(Photos.currentAlbumName.equals(inputAlbumName.getText())) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("No changes made");
			alert.setContentText("Press cancel if you would not like to make any changes.");
			alert.showAndWait();
			return;
		}
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
			
			UserSubsystemController.albums.get(Photos.currentIndex).setName(inputAlbumName.getText());
			UserSubsystemController.observableAlbums.get(Photos.currentIndex).setName(inputAlbumName.getText());
			
			// grab current stage (in kind of a cheap way, *shrug*) and close it
		    Stage currStage = (Stage)inputAlbumName.getScene().getWindow();
		    currStage.close();
		}
	}
	
	@FXML private void handleCancelButton(ActionEvent event) {
		
	}
	
	@FXML private void handleQuitButton(ActionEvent event) {
		
	}
	*/
	
}
