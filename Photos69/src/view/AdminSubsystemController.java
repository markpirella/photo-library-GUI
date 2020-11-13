package view;
import model.User;
import app.Photos;

import java.util.Arrays;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.ListView;

public class AdminSubsystemController {
	@FXML TextField inputUsername;
	@FXML ListView<String> userList;
	
	ObservableList<String> observableUsernames;// = FXCollections.observableArrayList(Photos.programSession.getUsernames());

	public void start(Stage mainStage) {
		//Photos.setStage(mainStage);
		
		// load in saved users list (Program class)
		
		// display user list in listview
		
		observableUsernames = FXCollections.observableArrayList(Photos.programSession.getUsernames());
		userList.setItems(observableUsernames);
		
		mainStage.setOnCloseRequest(event -> {
			try {
				Photos.writeProgramObj(Photos.programSession);
			}catch(Exception e) {
				
			}
		});
		
	}
	
	@FXML private void handleSubmitButton(ActionEvent event) {
		
		//Photos.programSession.getUsernames().add(inputUsername.getText());
		observableUsernames.add(inputUsername.getText());
		Photos.programSession.getUsernames().add(inputUsername.getText());
		//System.out.println("printing usernames: " + Arrays.toString(Photos.programSession.getUsernames().toArray()));
		userList.setItems(observableUsernames);
		inputUsername.setText("");
	}
	
	@FXML private void handleDeleteButton(ActionEvent event) {
		
		int index = userList.getSelectionModel().getSelectedIndex();
		if(index >= 0) {
			observableUsernames.remove(index);
			Photos.programSession.getUsernames().remove(index);
		}
	}
	
	@FXML private void handleLogoutButton(ActionEvent event) throws IOException {
		
		Stage stage = new Stage();
	    stage.setTitle("Photos Login");
	    FXMLLoader myLoader = new FXMLLoader(getClass().getResource("LoginScreen.fxml"));
	    AnchorPane myPane = (AnchorPane) myLoader.load();            
	    Scene scene = new Scene(myPane);
	    stage.setScene(scene);
	    //Photos.getStage().close();
	    Stage currStage = (Stage)inputUsername.getScene().getWindow();
	    currStage.close();
	    //setPrevStage(stage);
	    stage.show();
	    
	    Photos.writeProgramObj(Photos.programSession);
		
	}
	
	@FXML private void handleQuitButton(ActionEvent event) throws IOException {
		Photos.writeProgramObj(Photos.programSession);
		System.exit(0);
	}
}
