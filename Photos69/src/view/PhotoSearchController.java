package view;

import app.Photos;
import model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.Album;
import model.Photo;
import model.Tag;
import model.TagType;
import java.time.*;
import java.time.format.*;
import java.util.ArrayList;

public class PhotoSearchController {
	
	@FXML TextField startDate, endDate, firstTagValue, secondTagValue, newAlbumName;
	@FXML ComboBox<TagType> firstTagType, secondTagType;
	@FXML ChoiceBox<String> operation;
	@FXML ListView<Photo> searchResults;
	
	ObservableList<TagType> observableExistingTagTypes;
	User currentUser;
	ObservableList<Photo> observablePhotos;
	ArrayList<Image> loadedImages;
	ArrayList<Photo> searchResultsArrayList;
	
	public void start(Stage mainStage) {
		
		currentUser = Photos.programSession.getCurrentUser();
		
		operation.getItems().add("AND");
		operation.getItems().add("OR");
		operation.getItems().add("Only search using first tag");
		
		observableExistingTagTypes = FXCollections.observableArrayList(Photos.programSession.getCurrentUser().getTagTypes());
		firstTagType.setItems(observableExistingTagTypes);
		secondTagType.setItems(observableExistingTagTypes);
		
		// set cell factory for picture list display
		searchResults.setCellFactory(listView -> new ListCell<Photo>() {
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
		
	}
	
	@FXML private void handleSearchByDateButton(ActionEvent event) {
		
		if(startDate.getText().equals("") && endDate.getText().equals("")) { // both dates empty -> show error
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("You must enter values for the start date and end date");
			alert.setContentText("Please try again.");
			alert.showAndWait();
			return;
		}else if(startDate.getText().equals("")) { // start date empty -> show error
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("You must enter a value for the start date");
			alert.setContentText("Please try again.");
			alert.showAndWait();
			return;
		}else if(endDate.getText().equals("")) { // end date empty -> show error
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("You must enter a value for the end date");
			alert.setContentText("Please try again.");
			alert.showAndWait();
			return;
		}
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm");
		LocalDateTime startDateTime = null;
		LocalDateTime endDateTime = null;
		try {
			startDateTime = LocalDateTime.parse(startDate.getText()+" 00:00", formatter);
			endDateTime = LocalDateTime.parse(endDate.getText()+" 23:59", formatter);
		}catch(Exception e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("Invalid date formatting! Must use format mm/dd/yyyy");
			alert.setContentText("Please try again.");
			alert.showAndWait();
			return;
		}
		
		searchResultsArrayList = new ArrayList<Photo>();
		loadedImages = new ArrayList<Image>();
		
		// got start and end dates, now traverse through all albums and find all photos that fit requirements
		for(int i = 0; i < currentUser.getAlbums().size(); i++) { // traverse all of user's albums
			Album album = currentUser.getAlbums().get(i);
			for(int j = 0; j < album.getPhotos().size(); j++) { // traverse all songs in each album
				Photo photo = album.getPhotos().get(j);
				if(photo.getDate().compareTo(startDateTime) > 0 && photo.getDate().compareTo(endDateTime) < 0) { // falls in date range
					searchResultsArrayList.add(photo);
					Image image = null;
					try {
						image = new Image(photo.getImageFile().toURI().toURL().toExternalForm());
					}catch(Exception e) {
						
					}
					loadedImages.add(image);
				}
			}
		}
		
		observablePhotos = FXCollections.observableArrayList(searchResultsArrayList);
		searchResults.setItems(observablePhotos);
		searchResults.refresh();
		
		
	}
	
	@FXML private void handleBackButton(ActionEvent event) {
		
	}
	
	@FXML private void handleQuitButton(ActionEvent event) {
		
	}
	
	@FXML private void handleSearchByTagButton(ActionEvent event) {
		
	}
	
	@FXML private void handleCreateAlbumButton(ActionEvent event) {
		
	}
	
	private void loadImages() {
		loadedImages = new ArrayList<Image>();
		for(int i = 0; i < observablePhotos.size(); i++) {
			Image image = null;
			try {
				image = new Image(observablePhotos.get(i).getImageFile().toURI().toURL().toExternalForm());
			}catch(Exception e) {
				
			}
			loadedImages.add(image);
		}
	}
	
	private Image getImageForPhoto(Photo photo) {
		for(int i = 0; i < observablePhotos.size(); i++) {
			if(photo == observablePhotos.get(i)) {
				return loadedImages.get(i);
			}
		}
		return null;
	}

}
