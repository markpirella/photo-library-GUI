package view;
//import model.User;

//import java.util.*;

import app.Photos;

import java.io.*;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;

/**
 * controller for LoginScreen.fxml
 * @author Mark Pirella
 * @author Nicholas Farinella
 */
public class LoginScreenController {

	/**
	 * TextField for username input by user
	 */
	@FXML TextField inputUsername;
	
	
	/**
	 * method to handle Submit button press (either log user in, go to admin subsystem, or alert that user doesn't exist)
	 * @param event Submit button pressed
	 * @throws IOException if <<username>>.dat file can't be opened
	 */
	@FXML private void handleSubmitButton(ActionEvent event) throws IOException {
		
		// user submits "admin"
		if(inputUsername.getText().equals("admin")) {
			
			// **go to AdminSubsystem stage
			Stage stage = new Stage();
		    stage.setTitle("Admin Subsystem");
		    FXMLLoader myLoader = new FXMLLoader(getClass().getResource("AdminSubsystem.fxml"));
		    AnchorPane myPane = (AnchorPane) myLoader.load();            
		    Scene scene = new Scene(myPane);
		    stage.setScene(scene);
		    
		    // grab current stage (in kind of a cheap way, *shrug*) and close it
		    Stage currStage = (Stage)inputUsername.getScene().getWindow();
		    currStage.close();
		    
		    AdminSubsystemController adminSubsystemController = myLoader.getController();
		    adminSubsystemController.start(stage);
		    
		    // finally, switch to new stage
		    stage.show();  
		    
		}else { // user submitted a username besides "admin"
			
			if( !(Photos.programSession.getUsernames().contains(inputUsername.getText())) ) { // username not found
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error");
				alert.setHeaderText("This user does not exist");
				alert.setContentText("Please try again.");
				alert.showAndWait();
			}else { // username found
				try {
					//System.out.println("username found: " +inputUsername.getText());
					Photos.setCurrentUserWithUsername(inputUsername.getText());
				}catch(Exception e) {
					e.printStackTrace();
				}
				
				// **go to UserSubsystem stage
				Stage stage = new Stage();
			    stage.setTitle("User Subsystem");
			    FXMLLoader myLoader = new FXMLLoader(getClass().getResource("UserSubsystem.fxml"));
			    AnchorPane myPane = (AnchorPane) myLoader.load();            
			    Scene scene = new Scene(myPane);
			    stage.setScene(scene);
			    
			    // grab current stage (in kind of a cheap way, *shrug*) and close it
			    Stage currStage = (Stage)inputUsername.getScene().getWindow();
			    currStage.close();
			    
			    UserSubsystemController userSubsystemController = myLoader.getController();
			    userSubsystemController.start(stage);
			    
			    // finally, switch to new stage
			    stage.show();
			}
		}
		
	}
	
	/**
	 * method to handle Quit button pressed (exit application)
	 * @param event Quit button pressed
	 */
	@FXML private void handleQuitButton(ActionEvent event) {
		System.exit(0);
	}
}
