package view;

import java.io.File;

import javax.imageio.ImageIO;

import app.Photos;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.FileChooser;
import javafx.scene.image.Image;
import javafx.scene.control.ListCell;
import model.Album;
import model.Photo;
import model.Tag;
import java.util.ArrayList;

public class OpenedAlbumDisplayController {
	
	@FXML ListView<Photo> photosList;
	@FXML ListView<Tag> tagsList;
	@FXML TextField dateDisplay;
	@FXML TextArea captionDisplay;
	@FXML ImageView photoDisplay;
	
	ArrayList<Image> loadedImages;
	Stage currentStage;
	Album openedAlbum;
	ObservableList<Photo> observablePhotos;
	
	public void start(Stage mainStage, Album openedAlbum) {
		//openedAlbum.setEarliestAndLatestDates();
		currentStage = mainStage;
		this.openedAlbum = openedAlbum;
		observablePhotos = FXCollections.observableArrayList(openedAlbum.getPhotos());
		photosList.setItems(observablePhotos);
		
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
	}
	
	@FXML private void handleEditCaptionButton(ActionEvent event) {
		
	}
	
	@FXML private void handlePreviousPhotoButton(ActionEvent event) {
		
		int index = photosList.getSelectionModel().getSelectedIndex()-1;
		if(index >= 0) {
			photosList.getSelectionModel().select(index);
		}
		
	}
	
	@FXML private void handleNextPhotoButton(ActionEvent event) {
		
		int index = photosList.getSelectionModel().getSelectedIndex()+1;
		if(index < observablePhotos.size()) {
			photosList.getSelectionModel().select(index);
		}
		
	}
	
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
		if(!(extension.equals("jpg") || extension.equals("jpeg") || extension.equals("png") || extension.equals("gif") || extension.equals("tiff"))) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("This is not a picture file");
			alert.setContentText("Please try again.");
			alert.showAndWait();
			return;
		}
		
		Photo newPhoto = new Photo(selectedFile);
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
	
	@FXML private void handleDeletePhotoButton(ActionEvent event) {
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
	
	private void displayPhotoDetails(boolean makeEverythingEmpty) {
		if(makeEverythingEmpty) {
			photoDisplay.setImage(null);
			dateDisplay.setText("");
			captionDisplay.setText("");
			return;
		}
		if(loadedImages.size() <= 0 || observablePhotos.size() <= 0 || openedAlbum.getPhotos().size() <= 0) {
			photoDisplay.setImage(null);
			dateDisplay.setText("");
			captionDisplay.setText("");
			return;
		}
		Image image = loadedImages.get(photosList.getSelectionModel().getSelectedIndex());
		if(image == null) {
			return;
		}
		photoDisplay.setImage(image);
		dateDisplay.setText(photosList.getSelectionModel().getSelectedItem().getDate().toString());
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
