package model;

import javafx.scene.image.Image;
import java.util.ArrayList;
import java.util.Date;
import java.time.Instant;
import java.time.ZoneId;
import java.time.LocalDateTime;
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
	private LocalDateTime date;
	
	/**
	 * constructor to create Photo object
	 * @param imageFile File object that contains an image file
	 */
	public Photo(File imageFile) {
		//this.filepath = filepath;
		this.imageFile = imageFile;
		caption = "";
		tags = new ArrayList<Tag>(5);
		date = LocalDateTime.ofInstant(Instant.ofEpochMilli(imageFile.lastModified()), ZoneId.systemDefault());

		//imageFile.lastModified());
	}
	
	/**
	 * method to get caption of photo
	 * @return String that contains caption of photo
	 */
	public String getCaption() {
		return caption;
	}
	
	/**
	 * method to set caption of photo
	 * @param caption String to set caption of photo as
	 */
	public void setCaption(String caption) {
		this.caption = caption;
	}
	
	/**
	 * method to get tags of photo
	 * @return ArrayList of Tag objects containing information on photo tags
	 */
	public ArrayList<Tag> getTags(){
		return tags;
	}
	
	/**
	 * method to get image file of photo
	 * @return File object containing image
	 */
	public File getImageFile() {
		return imageFile;
	}
	
	/**
	 * method to get date photo was taken
	 * @return Date object containing date photo was taken
	 */
	public LocalDateTime getDate() {
		return date;
	}
	
	/**
	 * generic toString method for an object
	 * @return String containing caption of photo
	 */
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
