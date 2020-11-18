package model;

import javafx.scene.image.Image;
import java.util.ArrayList;
import java.util.Date;
import java.time.Instant;
import java.time.ZoneId;
import java.time.LocalDateTime;
import java.io.File;
import java.io.Serializable;

/**
 * Photo class used to store images
 * @author Mark Pirella
 * @author Nicholas Farinella
 */
public class Photo implements Serializable {
	/**
	 * auto-generated Serialization value
	 */
	private static final long serialVersionUID = 2391064262988040253L;
	//String filepath;
	
	/**
	 * File object that contains details about image files users add
	 */
	private File imageFile;
	
	/**
	 * caption for an image
	 */
	private String caption;
	
	/**
	 * collection of Tag objects for an image
	 */
	private ArrayList<Tag> tags;
	
	/**
	 * date of the image file
	 */
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
