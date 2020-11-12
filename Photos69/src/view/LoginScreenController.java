package view;
import app.Photos;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

public class LoginScreenController {
	@FXML TextField inputUsername;

	public void start(Stage mainStage) {
		
		// load in saved users list (Program class)
		
	}
	
	@FXML private void handleSubmitButton(ActionEvent event) throws IOException {
		
		// user submits "admin"
		if(inputUsername.getText().equals("admin")) {
			
			// go to AdminSubsystem stage
			Stage stage = new Stage();
		    stage.setTitle("Admin Subsystem");
		    FXMLLoader myLoader = new FXMLLoader(getClass().getResource("AdminSubsystem.fxml"));
		    AnchorPane myPane = (AnchorPane) myLoader.load();            
		    Scene scene = new Scene(myPane);
		    stage.setScene(scene);
		    
		    // grab current stage (in kind of a cheap way, *shrug*) and close it
		    Stage currStage = (Stage)inputUsername.getScene().getWindow();
		    currStage.close();
		    
		    // finally, switch to new stage
		    stage.show();  
		    
		}else { // user submitted a username besides "admin"
			
		}
		
	}
	
	@FXML private void handleQuitButton(ActionEvent event) {
		
	}
}
