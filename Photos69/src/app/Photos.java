package app;

import model.Program;
import model.User;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import view.LoginScreenController;

public class Photos extends Application {

	//public static final String storeDir = "dat";
	public static final String storeFile = "Program.dat";
	
	public static Program programSession;
	
	//public static User currentUser;
	
	public static void writeProgramObj(Program programObj) throws IOException {
		// set currentUser to null to preserve disk space, currentUser data will be stored elsewhere
		programObj.setCurrentUser(null);
		
		//System.out.println("reached writeprogramobj function");
		//ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(storeDir + File.separator + storeFile));
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(storeFile));
		oos.writeObject(programObj);
		oos.close();
	}
	
	public static Program readProgramObj() throws IOException, ClassNotFoundException {
		//ObjectInputStream ois = new ObjectInputStream(new FileInputStream(storeDir + File.separator + storeFile));
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream(storeFile));
		Program programObj = (Program)ois.readObject();
		ois.close();
		return programObj;
	}
	
	public static void setCurrentUserWithUsername(String username) throws IOException, ClassNotFoundException {
		System.out.println("got to setCurrentUserWithUsername function");
		if(username == null) {
			programSession.setCurrentUser(null);
			return;
		}
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream(username+".dat"));
		System.out.println("grabbed ois");
		programSession.setCurrentUser((User)ois.readObject());
		System.out.println("made currentUser object");
		ois.close();
		//System.out.println("success! grabbed user object with username: " + currentUser.getUsername());
	}
	
	public static User readUserObj(String username) throws IOException, ClassNotFoundException {
		//System.out.println("reached writeprogramobj function");
		//ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(storeDir + File.separator + storeFile));
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream(username+".dat"));
		User userObj = (User)ois.readObject();
		ois.close();
		return userObj;
	}
	
	public static void writeUserObj(User userObj) throws IOException {
		//System.out.println("reached writeprogramobj function");
		//ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(storeDir + File.separator + storeFile));
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(programSession.getCurrentUser().getUsername()+".dat"));
		oos.writeObject(userObj);
		oos.close();
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		programSession = new Program();
		try {
			//System.out.println("trying to find Program.dat");
			programSession = readProgramObj(); // will read in Program.dat file if it exists
			//System.out.println("usernames list: " + programSession.getUsernames().get(0));
		}catch(Exception e) {
			System.out.println("did not find Program.dat");
			// if Program.dat does not exist then just continue on - this is first time program has been run, so Program object 
			// (importantly containing ArrayList of usernames) will be written to a new file once execution ends
		}
		
		/*
		FXMLLoader loader = new FXMLLoader();   
	    loader.setLocation(getClass().getResource("/view/LoginScreen.fxml"));
	    AnchorPane root = (AnchorPane)loader.load();

	    LoginScreenController loginScreenController = loader.getController();
	    loginScreenController.start(primaryStage);

	    Scene scene = new Scene(root, 367, 400);
	    primaryStage.setTitle("Photos Login");
	    primaryStage.setScene(scene);
	    primaryStage.show();
	    */
	    
	    Stage stage = new Stage();
	    stage.setTitle("Photos Login");
	    FXMLLoader myLoader = new FXMLLoader(getClass().getResource("/view/LoginScreen.fxml"));
	    AnchorPane myPane = (AnchorPane) myLoader.load();            
	    Scene scene = new Scene(myPane);
	    stage.setScene(scene);
	    stage.show();  
	}

	public static void main(String[] args) {
		launch(args);
	}
	
}
