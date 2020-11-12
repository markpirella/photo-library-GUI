package app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import view.LoginScreenController;

public class Photos extends Application {

	@Override
	public void start(Stage primaryStage)  throws Exception {
		FXMLLoader loader = new FXMLLoader();   
	    loader.setLocation(getClass().getResource("/view/LoginScreen.fxml"));
	    AnchorPane root = (AnchorPane)loader.load();

	    LoginScreenController loginScreenController = loader.getController();
	    loginScreenController.start(primaryStage);

	    Scene scene = new Scene(root, 367, 400);
	    primaryStage.setTitle("Photos");
	    primaryStage.setScene(scene);
	    primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
	
}
