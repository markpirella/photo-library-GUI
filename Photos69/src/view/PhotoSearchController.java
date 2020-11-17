package view;

import app.Photos;
import model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Album;
import model.Photo;
import model.Tag;
import model.TagType;
import java.time.*;
import java.time.format.*;
import java.util.ArrayList;
import java.util.Optional;
import java.io.IOException;

public class PhotoSearchController {
	
	@FXML TextField startDate, endDate, firstTagValue, secondTagValue, newAlbumName, dateDisplay;
	@FXML TextArea captionDisplay;
	@FXML ComboBox<TagType> firstTagType, secondTagType;
	@FXML ChoiceBox<String> operation;
	@FXML ListView<Photo> searchResults;
	@FXML ListView<Tag> tagsList;
	@FXML ImageView photoDisplay;
	
	ObservableList<TagType> observableExistingTagTypes;
	User currentUser;
	ObservableList<Photo> observablePhotos;
	ArrayList<Image> loadedImages;
	ArrayList<Photo> searchResultsArrayList;
	ObservableList<Tag> observableTags;
	ArrayList<Tag> tagsArrayList;
	ArrayList<Tag> garbage;
	boolean execute;
	
	public void start(Stage mainStage) {
		
		currentUser = Photos.programSession.getCurrentUser();
		
		operation.getItems().add("AND");
		operation.getItems().add("OR");
		operation.getItems().add("Only search using first tag");
		operation.getItems().add("Only search using second tag");
		
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
		
		
		
		mainStage.setOnCloseRequest(event -> {
			try {
				Photos.writeUserObj(Photos.programSession.getCurrentUser());
			}catch(Exception e) {
				e.printStackTrace();
			}
		});
		
		boolean execute = true;
		garbage = new ArrayList<Tag>();
		observableTags = FXCollections.observableArrayList(garbage);
		
		// add listener to ensure that whenever user selects different album, displayed details are updated
		searchResults.getSelectionModel().selectedIndexProperty().addListener((obs, oldVal, newVal) -> displayPhotoDetails(false));
		
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
				if(photo.getDate().compareTo(startDateTime) > 0 
						&& photo.getDate().compareTo(endDateTime) < 0
						&& searchResultsArrayList.indexOf(photo) < 0) { // falls in date range and isnt already in search results
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
		execute = false;
		searchResults.setItems(observablePhotos);
		searchResults.refresh();
		
		searchResults.getSelectionModel().select(0);
		
		execute = true;
		if(observablePhotos.size() <= 0) {
			displayPhotoDetails(true);
		}else {
			displayPhotoDetails(false);
		}
		
		
	}
	
	@FXML private void handlePreviousPhotoButton(ActionEvent event) {
		
		int index = searchResults.getSelectionModel().getSelectedIndex()-1;
		if(index >= 0) {
			searchResults.getSelectionModel().select(index);
		}
		
	}
	
	@FXML private void handleNextPhotoButton(ActionEvent event) {
		
		int index = searchResults.getSelectionModel().getSelectedIndex()+1;
		if(index < observablePhotos.size()) {
			searchResults.getSelectionModel().select(index);
		}
		
	}
	
	@FXML private void handleBackButton(ActionEvent event) throws IOException {
		
		// **go to UserSubsystem stage
		Stage stage = new Stage();
	    stage.setTitle("User Subsystem");
	    FXMLLoader myLoader = new FXMLLoader(getClass().getResource("UserSubsystem.fxml"));
	    AnchorPane myPane = (AnchorPane) myLoader.load();            
	    Scene scene = new Scene(myPane);
	    stage.setScene(scene);
	    
	    // grab current stage (in kind of a cheap way, *shrug*) and close it
	    Stage currStage = (Stage)searchResults.getScene().getWindow();
	    currStage.close();
	    
	    UserSubsystemController userSubsystemController = myLoader.getController();
	    userSubsystemController.start(stage);
	    
	    // finally, switch to new stage
	    stage.show();
		
	}
	
	@FXML private void handleQuitButton(ActionEvent event) {
		
		try {
			Photos.writeUserObj(Photos.programSession.getCurrentUser());
		}catch(Exception e) {
			e.printStackTrace();
		}
		System.exit(0);
		
	}
	
	@FXML private void handleSearchByTagButton(ActionEvent event) {
		
		if(operation.getValue() == null) { // operation not specified
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("You must enter specify the search instructions from the drop-down menu");
			alert.setContentText("Please try again.");
			alert.showAndWait();
			return;
		}else if( (firstTagValue.getText().equals("") || firstTagType.getValue() == null || secondTagValue.getText().equals("") || secondTagType.getValue() == null) && ( operation.getValue().equals("AND") || operation.getValue().equals("OR") )) { // both tgs need to be completely filled out but aren't
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("You must enter a type and value for both tags to do an AND or OR search");
			alert.setContentText("Please try again.");
			alert.showAndWait();
			return;
		}else if((firstTagValue.getText().equals("") || firstTagType.getValue() == null) && !(operation.getValue().equals("Only search using second tag"))) { // some field for first tag left empty
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("You must enter values for the first tag's type and value");
			alert.setContentText("Please try again.");
			alert.showAndWait();
			return;
		}else if((secondTagValue.getText().equals("") || secondTagType.getValue() == null) && !(operation.getValue().equals("Only search using first tag"))) { // some field for second tag left empty
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("You must enter values for the second tag's type and value");
			alert.setContentText("Please try again.");
			alert.showAndWait();
			return;
		}
		
		Tag tag1 = null;
		Tag tag2 = null;
		searchResultsArrayList = new ArrayList<Photo>();
		loadedImages = new ArrayList<Image>();
		
		// now grab tag values and search with them
		if(operation.getValue().equals("AND")) { // grab both tags
			tag1 = new Tag(firstTagType.getValue().getType(), firstTagValue.getText());
			tag2 = new Tag(secondTagType.getValue().getType(), secondTagValue.getText());
			// now search
			for(int i = 0; i < currentUser.getAlbums().size(); i++) { // traverse all of user's albums
				Album album = currentUser.getAlbums().get(i);
				for(int j = 0; j < album.getPhotos().size(); j++) { // traverse all songs in each album
					Photo photo = album.getPhotos().get(j);
					int index1 = -1;
					int index2 = -1;
					for(int k = 0; k < photo.getTags().size(); k++) {
						if(tag1.getType().equals(photo.getTags().get(k).getType()) && tag1.getValue().equals(photo.getTags().get(k).getValue())) {
							index1 = k;
						}
						if(tag2.getType().equals(photo.getTags().get(k).getType()) && tag2.getValue().equals(photo.getTags().get(k).getValue())) {
							index2 = k;
						}
						if(index1 != -1 && index2 != -1) {
							break;
						}
					}
					if(index1 != -1 && // found first tag
							index2 != -1 && // found second tag
							searchResultsArrayList.indexOf(photo) < 0) { // falls in date range and isnt already in search results
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
			
		}else if(operation.getValue().equals("OR")) { // grab both tags
			tag1 = new Tag(firstTagType.getValue().getType(), firstTagValue.getText());
			tag2 = new Tag(secondTagType.getValue().getType(), secondTagValue.getText());
			// now search
			for(int i = 0; i < currentUser.getAlbums().size(); i++) { // traverse all of user's albums
				Album album = currentUser.getAlbums().get(i);
				for(int j = 0; j < album.getPhotos().size(); j++) { // traverse all songs in each album
					Photo photo = album.getPhotos().get(j);
					int index1 = -1;
					int index2 = -1;
					for(int k = 0; k < photo.getTags().size(); k++) {
						if(tag1.getType().equals(photo.getTags().get(k).getType()) && tag1.getValue().equals(photo.getTags().get(k).getValue())) {
							index1 = k;
							break;
						}
						if(tag2.getType().equals(photo.getTags().get(k).getType()) && tag2.getValue().equals(photo.getTags().get(k).getValue())) {
							index2 = k;
							break;
						}
					}
					if((index1 != -1 || // found first tag
							index2 != -1) && // found second tag
							searchResultsArrayList.indexOf(photo) < 0) { // falls in date range and isnt already in search results
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
		
		}else if(operation.getValue().equals("Only search using first tag")) { // get first tag
			System.out.println("reached right spot");
			tag1 = new Tag(firstTagType.getValue().getType(), firstTagValue.getText());
			// now search
			for(int i = 0; i < currentUser.getAlbums().size(); i++) { // traverse all of user's albums
				Album album = currentUser.getAlbums().get(i);
				for(int j = 0; j < album.getPhotos().size(); j++) { // traverse all songs in each album
					Photo photo = album.getPhotos().get(j);
					int index = -1;
					for(int k = 0; k < photo.getTags().size(); k++) {
						if(tag1.getType().equals(photo.getTags().get(k).getType()) && tag1.getValue().equals(photo.getTags().get(k).getValue())) {
							index = k;
							break;
						}
					}
					if(index != -1 && // found first tag
							searchResultsArrayList.indexOf(photo) < 0) { // falls in date range and isnt already in search results
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
		}else if(operation.getValue().equals("Only search using second tag")) { // get second tag
			tag2 = new Tag(secondTagType.getValue().getType(), secondTagValue.getText());
			// now search
			for(int i = 0; i < currentUser.getAlbums().size(); i++) { // traverse all of user's albums
				Album album = currentUser.getAlbums().get(i);
				for(int j = 0; j < album.getPhotos().size(); j++) { // traverse all songs in each album
					Photo photo = album.getPhotos().get(j);
					int index = -1;
					for(int k = 0; k < photo.getTags().size(); k++) {
						if(tag2.getType().equals(photo.getTags().get(k).getType()) && tag2.getValue().equals(photo.getTags().get(k).getValue())) {
							index = k;
							break;
						}
					}
					if(index != -1 && // found second tag
							searchResultsArrayList.indexOf(photo) < 0) { // falls in date range and isnt already in search results
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
		}
		
		if(searchResultsArrayList.size() < 1) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Information Dialog");
			alert.setHeaderText(null);
			alert.setContentText("No results found.");
			alert.showAndWait();
		}
		
		// display results
		observablePhotos = FXCollections.observableArrayList(searchResultsArrayList);
		System.out.println("about to set searchResults");
		execute = false;
		searchResults.setItems(observablePhotos);
		System.out.println("just set searchResults");
		searchResults.refresh();
		if(observablePhotos.size() >= 1) {
			System.out.println("about to select 0 searchResults");
			searchResults.getSelectionModel().select(0);
			System.out.println("just selected 0 in searchResults");
			
			//tagsArrayList = searchResults.getSelectionModel().getSelectedItem().getTags();
		}
		
		execute = true;
		if(observablePhotos.size() <= 0) {
			displayPhotoDetails(true);
		}else {
			displayPhotoDetails(false);
		}
		
	}
	
	@FXML private void handleCreateAlbumButton(ActionEvent event) {
		
		if(newAlbumName.getText().equals("")) { // operation not specified
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("You must enter a name for your new album");
			alert.setContentText("Please try again.");
			alert.showAndWait();
			return;
		}
		
		// see if album name already exists
		int index = -1;
		for(int i = 0; i < currentUser.getAlbums().size(); i++) {
			if(currentUser.getAlbums().get(i).getName().equals(newAlbumName.getText())) {
				index = i;
			}
		}
		
		if(index != -1) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("This album name is already being used");
			alert.setContentText("Please try a different name.");
			alert.showAndWait();
			return;
		}
		
		for(int i = 0; i < searchResultsArrayList.size(); i++) {
			System.out.println(searchResultsArrayList.get(i));
		}
		
		
		// check if user is ok with making an empty album, if it's empty
		if(searchResultsArrayList.size() < 1) {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Confirmation Dialog");
			alert.setHeaderText("Confirmation needed!");
			alert.setContentText("Are you sure you want to create an empty album?");
	
			Optional<ButtonType> result = alert.showAndWait();
			if (!(result.get() == ButtonType.OK)){ // user did not press ok
				return;
			}
		}
		
		// check for duplicate album name for this user
		for(Album a : currentUser.getAlbums()) {
			if(a.getName().equals(newAlbumName.getText())) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error");
				alert.setHeaderText("This album name is already being used");
				alert.setContentText("Please enter a different album name.");
				alert.showAndWait();
				return;
			}
		}
		
		// album name is good, so create it!
		Album newAlbum = new Album(newAlbumName.getText());
		for(Photo p : searchResultsArrayList) {
			newAlbum.addPhoto(p);
		}
		currentUser.getAlbums().add(newAlbum);
		// alert user to successful creation
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Information Dialog");
		alert.setHeaderText(null);
		alert.setContentText("New album successfully created!");
		alert.showAndWait();
		// clear input
		newAlbumName.setText("");
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
	
	private void displayPhotoDetails(boolean makeEverythingEmpty) {
		System.out.println("DISPLAY PHOTO DETAILS CALLED");
		if(!execute) {
			return;
		}
		if(searchResults.getSelectionModel().getSelectedIndex() < 0 || searchResults.getSelectionModel().getSelectedIndex() >= observablePhotos.size()) {
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
		if(loadedImages.size() <= 0 || observablePhotos.size() <= 0 || searchResultsArrayList.size() <= 0) {
			photoDisplay.setImage(null);
			dateDisplay.setText("");
			captionDisplay.setText("");
			tagsList = null;
			return;
		}
		Image image = loadedImages.get(searchResults.getSelectionModel().getSelectedIndex());
		if(image == null) {
			return;
		}
		searchResults.refresh();
		photoDisplay.setImage(image);
		dateDisplay.setText(searchResults.getSelectionModel().getSelectedItem().getDate().toString());
		captionDisplay.setText(searchResults.getSelectionModel().getSelectedItem().getCaption());
		// fill in tagsArrayList
		/*
		tagsArrayList = new ArrayList<Tag>();
		if(searchResults.getSelectionModel().getSelectedItem() != null) {
			for(Tag t : searchResultsArrayList.get(searchResults.getSelectionModel().getSelectedIndex()).getTags()) {
				System.out.println("adding tag "+t+" to tagsList");
				tagsArrayList.add(t);
			}
		}else {
			tagsArrayList = null;
		}
		*/
		//tagsArrayList = searchResults.getSelectionModel().getSelectedItem().getTags();
		if(observablePhotos.size() > 0 && searchResults.getSelectionModel().getSelectedIndex() >= 0 && searchResults.getSelectionModel().getSelectedIndex() < observablePhotos.size()) {
			
			/*
			ObservableList<Tag> newObservableTags;
			newObservableTags = FXCollections.observableArrayList(tagsArrayList);
			*/
			//observableTags = newObservableTags;
			//observableTags.removeAll();
			/*
			for(int i = 0; i < observableTags.size(); i++) {
				observableTags.remove(i);
			}
			*/
			observableTags.clear();
			System.out.print("Printing observable tags after removing all: ");
			for(Tag t : observableTags) {
				System.out.print(t+"// ");
				//observableTags.add(t);
			}
			System.out.print("Printing observable tags after adding all: ");
			for(Tag t : searchResults.getSelectionModel().getSelectedItem().getTags()) {
				//System.out.print(t+"// ");
				observableTags.add(t);
			}
			for(Tag t : observableTags) {
				System.out.print(t+"// ");
				//observableTags.add(t);
			}
			//observableTags.remove(0);
			System.out.println();
			if(observableTags.size() > 0) {
				//tagsList = null;
				tagsList.setItems(observableTags);
			}else {
				tagsList.setItems(null);
			}
		}else {
			tagsList.setItems(null);
		}
		System.out.println("-----------------------------------");
	}

}