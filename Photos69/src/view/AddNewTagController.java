package view;

import app.Photos;
import model.TagType;
import java.util.ArrayList;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.Album;
import model.Photo;
import model.Tag;

/**
 * controller for AddTag.fxml
 * @author Mark Pirella
 * @author Nicholas Farinella
 */
public class AddNewTagController {
	
	@FXML TextField inputExistingTagValue, inputNewTagType, inputNewTagValue;
	@FXML ComboBox<TagType> existingTagTypesList;
	@FXML ChoiceBox<String> singleOrMultipleChoice;
	@FXML ListView<Tag> tagsListOnPreviousStage;
	
	ObservableList<TagType> observableExistingTagTypes;
	ObservableList<Tag> observableTagsOnPreviousStage;
	
	/**
	 * Photo object that user is trying to add tag to
	 */
	Photo currentPhoto;
	
	/**
	 * method to be called when switching to AddNewTag stage
	 * @param mainStage contains AddNewTag stage
	 * @param openedPhoto Photo object to add tag to
	 * @param observableTags ObservableList<Tag> of photo's tags
	 * @param tagsListOnPrevStage ListView<Tag> of photo's tags on OpenedAlbumDisplay stage
	 */
	public void start(Stage mainStage, Photo openedPhoto, ObservableList<Tag> observableTags, ListView<Tag> tagsListOnPrevStage) {
		
		currentPhoto = openedPhoto;
		
		singleOrMultipleChoice.getItems().add("Yes");
		singleOrMultipleChoice.getItems().add("No");
		
		observableExistingTagTypes = FXCollections.observableArrayList(Photos.programSession.getCurrentUser().getTagTypes());
		existingTagTypesList.setItems(observableExistingTagTypes);
		
		observableTagsOnPreviousStage = observableTags;
		tagsListOnPreviousStage = tagsListOnPrevStage;
		
	}
	
	/**
	 * method to handle Confirm button for adding a tag with a pre-existing tag type
	 * @param event Confirm button pressed
	 */
	@FXML private void handleExistingTagConfirmButton(ActionEvent event) {
		
		if(existingTagTypesList.getValue() == null) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("Make sure to select a tag type");
			alert.setContentText("Please try again.");
			alert.showAndWait();
			return;
		}
		if(inputExistingTagValue.getText().equals("")) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("Make sure to enter a value for the new tag");
			alert.setContentText("Please try again.");
			alert.showAndWait();
			return;
		}
		
		// look for exact duplicate tag
		for(int i = 0; i < currentPhoto.getTags().size(); i++) {
			
			Tag tag = currentPhoto.getTags().get(i);
			
			if(tag.getType().equals(existingTagTypesList.getValue().getType()) && tag.getValue().equals(inputExistingTagValue.getText())) { // exact duplicate tag -> alert user and abort
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error");
				alert.setHeaderText("This tag is an exact duplicate of an existing tag for this photo");
				alert.setContentText("Please try again.");
				alert.showAndWait();
				inputNewTagType.setText("");
				inputNewTagValue.setText("");
				return;
			}
			
		}
		
		boolean canHaveMultipleValues = existingTagTypesList.getSelectionModel().getSelectedItem().canHaveMultipleValues();
		if(canHaveMultipleValues) { // freely add tag
			
			Tag newTag = new Tag(existingTagTypesList.getValue().getType(), inputExistingTagValue.getText());
			currentPhoto.getTags().add(newTag);
			observableTagsOnPreviousStage.add(newTag);
			// close window
			Stage currStage = (Stage)existingTagTypesList.getScene().getWindow();
		    currStage.close();
		    return;
			
		}else { // tag type cannot be duplicated, so ask to overwrite
			
			int index = -1;
			for(int i = 0; i < currentPhoto.getTags().size(); i++) {
				if(currentPhoto.getTags().get(i).getType().equals(existingTagTypesList.getValue().getType())) {
					index = i;
					break;
				}
			}
			
			if(index == -1) { // tag not in photo tag list yet, can add freely
				Tag newTag = new Tag(existingTagTypesList.getValue().getType(), inputExistingTagValue.getText());
				currentPhoto.getTags().add(newTag);
				observableTagsOnPreviousStage.add(newTag);
				// close window
				Stage currStage = (Stage)existingTagTypesList.getScene().getWindow();
			    currStage.close();
			    return;
			}
			
			// *** confirmation message to ensure user is ok with proposed changes ***
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Confirmation Dialog");
			alert.setHeaderText("The tag type you inserted already exists and is not compatible with multiple values.");
			alert.setContentText("Overwrite the value associated with the tag type '"+
			currentPhoto.getTags().get(index).getType()+"' from '"+currentPhoto.getTags().get(index).getValue()+
			"' to '"+inputExistingTagValue.getText()+"'?");

			Optional<ButtonType> result = alert.showAndWait();
			if (!(result.get() == ButtonType.OK)){ // user chose cancel or closed the dialog
			    // clear inputs and return
				return;
			}
			Stage currStage = (Stage)existingTagTypesList.getScene().getWindow();
		    currStage.close();
			currentPhoto.getTags().get(index).setValue(inputExistingTagValue.getText());
			tagsListOnPreviousStage.refresh();
			//observableTagsOnPreviousStage
		}
		
	}
	
	/**
	 * method to handle either Cancel button being pressed
	 * @param event Cancel button pressed
	 */
	@FXML private void handleCancelButton(ActionEvent event) {
		
		// close window
		Stage currStage = (Stage)existingTagTypesList.getScene().getWindow();
	    currStage.close();
	    return;
		
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
	 * method to handle Confirm button for adding a new tag with a new, user-inputted tag type
	 * @param event Confirm button for new tag with new tag type pressed
	 */
	@FXML private void handleNewTagConfirmButton(ActionEvent event) {
		
		if(inputNewTagType.getText().equals("")) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("Make sure to enter a new tag type");
			alert.setContentText("Please try again.");
			alert.showAndWait();
			return;
		}
		if(inputNewTagValue.getText().equals("")) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("Make sure to enter a new tag value");
			alert.setContentText("Please try again.");
			alert.showAndWait();
			return;
		}
		if(singleOrMultipleChoice.getValue() == null) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("Make sure to specify whether new tag type can have multiple values or just a single value");
			alert.setContentText("Please try again.");
			alert.showAndWait();
			return;
		}
		
		ArrayList<TagType> tagTypes = Photos.programSession.getCurrentUser().getTagTypes();
		
		// check and see if "new" tag type already exists. if it does but can have multiple values, then it's ok. if it can't have
		// multiple values, then ask to overwrite
		int index = -1;
		for(int i = 0; i < tagTypes.size(); i++) {
			if(tagTypes.get(i).getType().equals(inputNewTagType.getText())) {
				index = i;
				break;
			}
		}
		if(index == -1) { // tag type is completely new
			
			boolean multipleValues;
			if(singleOrMultipleChoice.getValue().equals("Yes")) {
				multipleValues = true;
			}else {
				multipleValues = false;
			}
			// add new TagType to user's TagTypes list and add new Tag to photo's Tags list
			tagTypes.add(new TagType(inputNewTagType.getText(), multipleValues));
			Tag newTag = new Tag(inputNewTagType.getText(), inputNewTagValue.getText());
			currentPhoto.getTags().add(newTag);
			observableTagsOnPreviousStage.add(newTag);
			
			// close window
			Stage currStage = (Stage)existingTagTypesList.getScene().getWindow();
		    currStage.close();
		    return;
			
		}
		
		// else, tag type found
		
		// look for exact duplicate tag
		for(int i = 0; i < currentPhoto.getTags().size(); i++) {
			
			Tag tag = currentPhoto.getTags().get(i);
			
			if(tag.getType().equals(inputNewTagType.getText()) && tag.getValue().equals(inputNewTagValue.getText())) { // exact duplicate tag -> alert user and abort
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error");
				alert.setHeaderText("This tag is an exact duplicate of an existing tag for this photo");
				alert.setContentText("Please try again.");
				alert.showAndWait();
				inputNewTagType.setText("");
				inputNewTagValue.setText("");
				return;
			}
			
		}
		
		boolean canHaveMultipleValues = tagTypes.get(index).canHaveMultipleValues();
		//Tag newTag = new Tag(inputNewTagType.getText(), inputNewTagValue.getText());
		// find if there is matching type in photo's tags
		index = -1;
		for(int i = 0; i < currentPhoto.getTags().size(); i++) {
			if(currentPhoto.getTags().get(i).getType().equals(inputNewTagType.getText())) {
				index = i;
				break;
			}
		}
		
		if(canHaveMultipleValues) { // tag type can have multiple values -> add new tag and close window
			// add new tag to photo's tag list and end
			Tag newTag = new Tag(inputNewTagType.getText(), inputNewTagValue.getText());
			currentPhoto.getTags().add(newTag);
			observableTagsOnPreviousStage.add(newTag);
			// close window
			Stage currStage = (Stage)existingTagTypesList.getScene().getWindow();
		    currStage.close();
		    return;
		}else { // tag type cannot have multiple values -> ask to overwrite existing tag
			
			// *** confirmation message to ensure user is ok with proposed changes ***
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Confirmation Dialog");
			alert.setHeaderText("The tag type you inserted already exists and is not compatible with multiple values.");
			alert.setContentText("Overwrite the value associated with the tag type '"+
			currentPhoto.getTags().get(index).getType()+"' from '"+currentPhoto.getTags().get(index).getValue()+
			"' to '"+inputNewTagValue.getText()+"'?");

			Optional<ButtonType> result = alert.showAndWait();
			if (!(result.get() == ButtonType.OK)){ // user chose cancel or closed the dialog
			    // clear inputs and return
				return;
			}
			currentPhoto.getTags().get(index).setValue(inputNewTagValue.getText());
			
			/*
			Tag newTag = new Tag(inputNewTagType.getText(), inputNewTagValue.getText());
			currentPhoto.getTags().add(newTag);
			observableTagsOnPreviousStage.add(newTag);
			*/
			
		}
		
	}

}
