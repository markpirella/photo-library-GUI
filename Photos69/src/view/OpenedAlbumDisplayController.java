package view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.Album;
import model.Photo;
import model.Tag;

public class OpenedAlbumDisplayController {
	
	@FXML ListView<Photo> photosList;
	@FXML ListView<Tag> tagsList;
	@FXML TextField dateDisplay;
	@FXML TextArea captionDisplay;
	@FXML ImageView photoDisplay;
	
	public void start(Stage mainStage) {
	
	}
	
	@FXML private void handleEditCaptionButton(ActionEvent event) {
		
	}
	
	@FXML private void handlePreviousPhotoButton(ActionEvent event) {
		
	}
	
	@FXML private void handleNextPhotoButton(ActionEvent event) {
		
	}
	
	@FXML private void handleAddPhotoButton(ActionEvent event) {
		
	}
	
	@FXML private void handleDeletePhotoButton(ActionEvent event) {
		
	}
	
	@FXML private void handleCopyPhotoButton(ActionEvent event) {
		
	}
	
	@FXML private void handleMovePhotoButton(ActionEvent event) {
		
	}
	
	@FXML private void handleBackButton(ActionEvent event) {
		
	}
	
	@FXML private void handleQuitButton(ActionEvent event) {
		
	}
	
	@FXML private void handleAddTagButton(ActionEvent event) {
		
	}
	
	@FXML private void handleDeleteTagButton(ActionEvent event) {
		
	}

}
