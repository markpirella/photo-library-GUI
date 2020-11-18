package view;

import java.io.File;

//import javax.imageio.ImageIO;
import java.io.IOException;

import app.Photos;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.FileChooser;
import javafx.scene.image.Image;
import javafx.scene.control.ListCell;
import model.Album;
import model.Photo;
import model.Tag;
import java.util.ArrayList;
import java.util.Optional;

/**
 * controller for OpenedAlbumDisplay.fxml
 * @author Mark Pirella
 * @author Nicholas Farinella
 */
public class OpenedAlbumDisplayController {
	
	/**
	 * ListView to display Photo objects in Album
	 */
	@FXML ListView<Photo> photosList;
	
	/**
	 * ListView to display Tags for selected Photo
	 */
	@FXML ListView<Tag> tagsList;
	
	/**
	 * TextField to display date of selected Photo
	 */
	@FXML TextField dateDisplay;
	
	/**
	 * TextArea to display caption of selected Photo
	 */
	@FXML TextArea captionDisplay;
	
	/**
	 * ImageView to display corresponding Image object of selected Photo
	 */
	@FXML ImageView photoDisplay;
	
	/**
	 * ComboBox used to display Albums that selected Photo can be copied to
	 */
	@FXML ComboBox<Album> copyToAlbumList;
	
	/**
	 * ComboBox used to display Albums that selected Photo can be moved to
	 */
	@FXML ComboBox<Album> moveToAlbumList;
	
	/**
	 * ArrayList of Image objects loaded in for each Photo object in opened album
	 */
	ArrayList<Image> loadedImages;
	
	/**
	 * holds the current stage (OpenedAlbumDisplay stage)
	 */
	Stage currentStage;
	
	/**
	 * holds the currently opened Album object
	 */
	Album openedAlbum;
	
	/**
	 * used to help display Photo objects in Album
	 */
	ObservableList<Photo> observablePhotos;
	
	/**
	 * used to help display other Albums besides currently open Album
	 */
	ObservableList<Album> observableAlbums;
	
	/**
	 * used to help display Tags of currently selected Photo
	 */
	ObservableList<Tag> observableTags;
	
	/**
	 * method to be called when switching to OpenedAlbumDisplay stage
	 * @param mainStage contains OpenedAlbumDisplay stage
	 * @param openedAlbum Album object of currently opened album
	 */
	public void start(Stage mainStage, Album openedAlbum) {
		//openedAlbum.setEarliestAndLatestDates();
		currentStage = mainStage;
		this.openedAlbum = openedAlbum;
		observablePhotos = FXCollections.observableArrayList(openedAlbum.getPhotos());
		photosList.setItems(observablePhotos);
		
		observableAlbums = FXCollections.observableArrayList(Photos.programSession.getCurrentUser().getAlbums());
		observableAlbums.remove(openedAlbum);
		copyToAlbumList.setItems(observableAlbums);
		moveToAlbumList.setItems(observableAlbums);
		
		loadedImages = new ArrayList<Image>();
		for(int i = 0; i < observablePhotos.size(); i++) {
			Image image = null;
			try {
				image = new Image(observablePhotos.get(i).getImageFile().toURI().toURL().toExternalForm());
			}catch(Exception e) {
				
			}
			loadedImages.add(image);
		}
		
		
		photosList.setCellFactory(listView -> new ListCell<Photo>() {
		    private ImageView imageView = new ImageView();
		    //imageView.setPreserveRatio(true);
		    //imageView.setFitHeight(100);
		    
		    @Override
		    public void updateItem(Photo photo, boolean empty) {
		        super.updateItem(photo, empty);
		        if (empty) {
		            setText(null);
		            setGraphic(null);
		        } else {
		        	Image image = getImageForPhoto(photo);
		        	imageView.setPreserveRatio(true);
				    imageView.setFitWidth(85);
		            imageView.setImage(image);
		            setText(photo.toString());
		            setGraphic(imageView);
		        }
		    }
		});
		
		mainStage.setOnCloseRequest(event -> {
			try {
				Photos.writeUserObj(Photos.programSession.getCurrentUser());
			}catch(Exception e) {
				e.printStackTrace();
			}
		});
		
		// add listener to ensure that whenever user selects different album, displayed details are updated
		photosList.getSelectionModel().selectedIndexProperty().addListener((obs, oldVal, newVal) -> displayPhotoDetails(false));
		
		photosList.getSelectionModel().select(0);
		
		if(photosList.getSelectionModel().getSelectedIndex() >= 0 && photosList.getSelectionModel().getSelectedIndex() < observablePhotos.size()) {
			observableTags = FXCollections.observableArrayList(photosList.getSelectionModel().getSelectedItem().getTags());
			tagsList.setItems(observableTags);
		}
	}
	
	/**
	 * method to handle caption create/edit button (edits currently existing caption to what user inputs)
	 * @param event caption create/edit button pressed
	 */
	@FXML private void handleEditCaptionButton(ActionEvent event) {
		
		photosList.getSelectionModel().getSelectedItem().setCaption(captionDisplay.getText());
		//int index = photosList.getSelectionModel().getSelectedIndex();
		// ensure caption in listview updates
		photosList.refresh();
		/*
		photosList.getSelectionModel().select(-1);
		photosList.getSelectionModel().select(index);
		/*
		photosList.setItems(null);
		photosList.setItems(observablePhotos);
		*/
		
	}
	
	/**
	 * method to handle previous photo button (select previous photo in album)
	 * @param event previous photo button pressed
	 */
	@FXML private void handlePreviousPhotoButton(ActionEvent event) {
		
		int index = photosList.getSelectionModel().getSelectedIndex()-1;
		if(index >= 0) {
			photosList.getSelectionModel().select(index);
		}
		
	}
	
	/**
	 * method to handle next photo button pressed (select next photo in album)
	 * @param event next photo button pressed
	 */
	@FXML private void handleNextPhotoButton(ActionEvent event) {
		
		int index = photosList.getSelectionModel().getSelectedIndex()+1;
		if(index < observablePhotos.size()) {
			photosList.getSelectionModel().select(index);
		}
		
	}
	
	/**
	 * method to handle add photo button pressed (add new File object for picture to album)
	 * @param event add photo button pressed
	 */
	@FXML private void handleAddPhotoButton(ActionEvent event) {
		
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Picture File");
		File selectedFile = fileChooser.showOpenDialog(currentStage);
		if(selectedFile == null) {
			return;
		}
		// check to make sure file is a picture file
		String extension = "";
		int i = selectedFile.getName().lastIndexOf('.');
		if (i > 0) {
		    extension = selectedFile.getName().substring(i+1);
		}
		if(!(extension.equals("jpg") || extension.equals("jpeg") || extension.equals("png") || extension.equals("gif") || extension.equals("bmp"))) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("This is not a picture file");
			alert.setContentText("Please try again.");
			alert.showAndWait();
			return;
		}
		
		Photo newPhoto = new Photo(selectedFile);
		
		// check if photo is a duplicate of existing photo in album
		for(Photo p : openedAlbum.getPhotos()) {
			if(p.getImageFile().getAbsolutePath().equals(newPhoto.getImageFile().getAbsolutePath())) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error");
				alert.setHeaderText("This photo already exists in this album");
				alert.setContentText("Please try again with a different photo.");
				alert.showAndWait();
				return;
			}
		}
		
		// add preloaded image to loadedImages
		Image image = null;
		try {
			image = new Image(selectedFile.toURI().toURL().toExternalForm());
		}catch(Exception e) {
			
		}
		loadedImages.add(image);
		openedAlbum.addPhoto(newPhoto);
		observablePhotos.add(newPhoto);
		
		photosList.getSelectionModel().select(observablePhotos.size()-1);
		displayPhotoDetails(false);
		
		
		
	}
	
	/**
	 * method to handle delete photo button pressed (remove File object from Album)
	 * @param event delete photo button pressed
	 */
	@FXML private void handleDeletePhotoButton(ActionEvent event) {
		int index = photosList.getSelectionModel().getSelectedIndex();
		
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Confirmation Dialog");
		alert.setHeaderText("Confirmation needed!");
		alert.setContentText("Are you sure you want to delete this photo from the album?");

		Optional<ButtonType> result = alert.showAndWait();
		if (!(result.get() == ButtonType.OK)){ // user did not press ok
			return;
		}
		
		if(index >= 0) {
			observablePhotos.remove(index);
			openedAlbum.removePhoto(index);
			loadedImages.remove(index);
		}
		
		if(observablePhotos.size() <= 0) {
			displayPhotoDetails(true);
		}else {
			displayPhotoDetails(false);
		}
		
	}
	
	/**
	 * method to handle copy photo to another album button
	 * @param event copy photo to another album button pressed
	 */
	@FXML private void handleCopyPhotoButton(ActionEvent event) {
		
		// ensure user made a selection
		if(copyToAlbumList.getValue() == null) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("No destination album selected");
			alert.setContentText("Please select an album from the drop-down menu.");
			alert.showAndWait();
			return;
		}
		
		// copy photo to album
		copyToAlbumList.getValue().addPhoto(photosList.getSelectionModel().getSelectedItem());
		
	}
	
	/**
	 * method to handle move photo to another album button
	 * @param event move photo to another album button
	 */
	@FXML private void handleMovePhotoButton(ActionEvent event) {
		
		// ensure user made a selection
		if(moveToAlbumList.getValue() == null) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("No destination album selected");
			alert.setContentText("Please select an album from the drop-down menu.");
			alert.showAndWait();
			return;
		}
		
		// copy photo to new album
		moveToAlbumList.getValue().addPhoto(photosList.getSelectionModel().getSelectedItem());
		
		// remove photo from current album
		int index = photosList.getSelectionModel().getSelectedIndex();
		if(index >= 0) {
			observablePhotos.remove(index);
			openedAlbum.removePhoto(index);
			loadedImages.remove(index);
		}
		
		if(observablePhotos.size() <= 0) {
			displayPhotoDetails(true);
		}else {
			displayPhotoDetails(false);
		}
		
		
	}
	
	/**
	 * method to handle back button pressed (go back to UserSubsystem stage)
	 * @param event back button pressed
	 * @throws IOException if accessing UserSubsystem.fxml fails
	 */
	@FXML private void handleBackButton(ActionEvent event) throws IOException {
		
		// **go to UserSubsystem stage
		Stage stage = new Stage();
	    stage.setTitle("User Subsystem");
	    FXMLLoader myLoader = new FXMLLoader(getClass().getResource("UserSubsystem.fxml"));
	    AnchorPane myPane = (AnchorPane) myLoader.load();            
	    Scene scene = new Scene(myPane);
	    stage.setScene(scene);
	    
	    // grab current stage (in kind of a cheap way, *shrug*) and close it
	    Stage currStage = (Stage)photosList.getScene().getWindow();
	    currStage.close();
	    
	    UserSubsystemController userSubsystemController = myLoader.getController();
	    userSubsystemController.start(stage);
	    
	    // finally, switch to new stage
	    stage.show();
		
	}
	
	/**
	 * method to handle Quit button being pressed (exit application)
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
	 * method to handle Add Tag button being pressed (switch to AddNewTag stage)
	 * @param event Add Tag button pressed
	 * @throws IOException if accessing AddNewTag.fxml fails
	 */
	@FXML private void handleAddTagButton(ActionEvent event) throws IOException {
		
		// **go to AddTag stage
		Stage stage = new Stage();
	    stage.setTitle("Add New Tag to Photo");
	    FXMLLoader myLoader = new FXMLLoader(getClass().getResource("AddNewTag.fxml"));
	    AnchorPane myPane = (AnchorPane) myLoader.load();            
	    Scene scene = new Scene(myPane);
	    stage.setScene(scene);
	    
	    AddNewTagController addNewTagController = myLoader.getController();
	    addNewTagController.start(stage, photosList.getSelectionModel().getSelectedItem(), observableTags, tagsList);
	    
	    // finally, switch to new stage
	    stage.show();
		
	}
	
	/**
	 * method to handle Delete Tag button pressed (remove Tag object from currently selected Photo object)
	 * @param event Delete Tag button pressed
	 */
	@FXML private void handleDeleteTagButton(ActionEvent event) {
		
		photosList.getSelectionModel().getSelectedItem().getTags().remove(tagsList.getSelectionModel().getSelectedItem());
		observableTags.remove(tagsList.getSelectionModel().getSelectedItem());
		
	}
	
	/**
	 * display currently selected Photo details
	 * @param makeEverythingEmpty if true, set all displays to null/empty
	 */
	private void displayPhotoDetails(boolean makeEverythingEmpty) {
		if(photosList.getSelectionModel().getSelectedIndex() < 0 || photosList.getSelectionModel().getSelectedIndex() >= observablePhotos.size()) {
			photoDisplay.setImage(null);
			dateDisplay.setText("");
			captionDisplay.setText("");
			tagsList = null;
			return;
		}
		if(makeEverythingEmpty) {
			photoDisplay.setImage(null);
			dateDisplay.setText("");
			captionDisplay.setText("");
			tagsList = null;
			return;
		}
		if(loadedImages.size() <= 0 || observablePhotos.size() <= 0 || openedAlbum.getPhotos().size() <= 0) {
			photoDisplay.setImage(null);
			dateDisplay.setText("");
			captionDisplay.setText("");
			tagsList = null;
			return;
		}
		Image image = loadedImages.get(photosList.getSelectionModel().getSelectedIndex());
		if(image == null) {
			return;
		}
		photoDisplay.setImage(image);
		dateDisplay.setText(photosList.getSelectionModel().getSelectedItem().getDate().toString());
		captionDisplay.setText(photosList.getSelectionModel().getSelectedItem().getCaption());
		if(photosList.getSelectionModel().getSelectedIndex() >= 0 && photosList.getSelectionModel().getSelectedIndex() < observablePhotos.size()) {
			observableTags = FXCollections.observableArrayList(photosList.getSelectionModel().getSelectedItem().getTags());
			tagsList.setItems(observableTags);
		}
	}
	
	/**
	 * method to obtain corresponding Image object for currently selected Photo object
	 * @param photo currently selected Photo object
	 * @return corresponding Image object
	 */
	private Image getImageForPhoto(Photo photo) {
		for(int i = 0; i < observablePhotos.size(); i++) {
			if(photo == observablePhotos.get(i)) {
				return loadedImages.get(i);
			}
		}
		return null;
	}

}
