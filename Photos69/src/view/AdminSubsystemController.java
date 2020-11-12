package view;
import model.User;
import app.Photos;

import java.io.IOException;

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
	@FXML ListView<User> userList;

	public void start(Stage mainStage) {
		//Photos.setStage(mainStage);
		
		// load in saved users list (Program class)
		
	}
	
	@FXML private void handleSubmitButton(ActionEvent event) {
		
		
		
	}
	
	@FXML private void handleDeleteButton(ActionEvent event) {
		
		
		
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
		
	}
	
	@FXML private void handleQuitButton(ActionEvent event) {
		
	}
}
