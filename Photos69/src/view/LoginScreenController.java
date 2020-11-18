package view;
import model.User;

import java.util.*;

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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class LoginScreenController { //implements Serializable {
	/**
	 * 
	 */
	//private static final long serialVersionUID = -298392680050871117L;

	@FXML TextField inputUsername;
	
	//private ObservableList<User> users;
	
	/*
	public static final String storeDir = "dat";
	public static final String storeFile = "users.dat";
	
	public static void writeLoginObj(LoginScreenController loginObj) throws IOException {
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(storeDir + File.separator + storeFile));
		oos.writeObject(loginObj);
		oos.close();
	}
	
	public static LoginScreenController readLoginObj() throws IOException, ClassNotFoundException {
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream(storeDir + File.separator + storeFile));
		LoginScreenController loginObj = (LoginScreenController)ois.readObject();
		ois.close();
		return loginObj;
	}
	
	public LoginScreenController() {
		users = FXCollections.observableArrayList();
	}
	*/

	//public void start(Stage mainStage) {
		/*
		LoginScreenController loginObj = new LoginScreenController();
		// load in saved users list (Program class)
		try {
			loginObj = readLoginObj();
		}catch(Exception e) { // no .dat file, so create one
			try {
				writeLoginObj(loginObj);
			}catch(Exception e2) {
				e2.printStackTrace();
			}
		}
		
		
		
		// end
		try {
			writeLoginObj(loginObj);
		}catch(Exception e) {
			e.printStackTrace();
		}
		*/
		
	//}
	
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
	
	@FXML private void handleQuitButton(ActionEvent event) {
		System.exit(0);
	}
}
