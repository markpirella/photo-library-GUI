package view;
import model.Program;
import model.User;
import app.Photos;

import java.util.Arrays;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;

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
		userList.getSelectionModel().select(0);
		
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
		//userList.setItems(observableUsernames);
		
		/*
		// create new .dat for newly created user - will be written to when photos/albums/etc are changed/added to
		File f = new File(inputUsername.getText() + ".dat");
		try {
			f.createNewFile();
		}catch(Exception e) {
			e.printStackTrace();
		}
		*/
		
		// create new user object and serialize and write it to <username>.dat file
		User newUser = new User(inputUsername.getText());
		try {
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(inputUsername.getText()+".dat"));
			System.out.println("newUser object has username "+newUser.getUsername()+" and it should be " +inputUsername.getText());
			oos.writeObject(newUser);
			System.out.println("wrote object");
			oos.close();
			System.out.println("wrote User object to file with username: " + newUser.getUsername());
		}catch(Exception e) {
			
		}
		
		// clear text field
		inputUsername.setText("");
		
	}
	
	@FXML private void handleDeleteButton(ActionEvent event) {
		
		int index = userList.getSelectionModel().getSelectedIndex();
		if(index >= 0) {
			// delete user's .dat file
			File f = new File(observableUsernames.get(index) + ".dat");
			f.delete();
			
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