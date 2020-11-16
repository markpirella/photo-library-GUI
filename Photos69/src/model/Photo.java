package model;

import javafx.scene.image.Image;
import java.util.ArrayList;
import java.util.Date;
import java.io.File;
import java.io.Serializable;

public class Photo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2391064262988040253L;
	//String filepath;
	private File imageFile;
	private String caption;
	private ArrayList<Tag> tags;
	private Date date;
	
	public Photo(File imageFile) {
		//this.filepath = filepath;
		this.imageFile = imageFile;
		caption = "";
		tags = new ArrayList<Tag>(5);
		date = new Date(imageFile.lastModified());
	}
	
	public String getCaption() {
		return caption;
	}
	
	public void setCaption(String caption) {
		this.caption = caption;
	}
	
	public ArrayList<Tag> getTags(){
		return tags;
	}
	
	public File getImageFile() {
		return imageFile;
	}
	
	public Date getDate() {
		return date;
	}
	
	public String toString() {
		return caption;
	}
	
	/*
	public String getFilepath() {
		return filepath;
	}
	
	/*
	// allows for extensibility to add the feature to keep track of filepath if user moves file on their computer
	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}
	*/
}
