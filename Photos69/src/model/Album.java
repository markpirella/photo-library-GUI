package model;

import java.util.ArrayList;
//import javafx.collections.ObservableList;
//import javafx.collections.FXCollections;
import java.io.File;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class Album implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6482233240369102096L;
	private String name;
	private ArrayList<Photo> photos;
	private LocalDateTime earliestDate;
	private LocalDateTime latestDate;
	
	/**
	 * Constructor for use when user simply wants to create new empty album
	 * @param name Name of new album
	 */
	public Album(String name) {
		this.name = name;
		photos = new ArrayList<Photo>();
		earliestDate = null;
		latestDate = null;
	}
	
	/**
	 * Constructor for use when user creates an album from search results
	 * @param name Name of new album
	 * @param photos ObservableList of photos to be contained in album
	 */
	public Album(String name, ArrayList<Photo> photos) {
		this.name = name;
		this.photos = photos;
		earliestDate = findEarliestDate();
		latestDate = findLatestDate();
	}
	
	/**
	 * method to obtain earliest date of photos in album
	 * @return Date object containing earliest date
	 */
	private LocalDateTime findEarliestDate() {
		if(photos.size() <= 0) {
			return null;
		}
		long earliest = photos.get(0).getImageFile().lastModified();
		for(int i = 1; i < photos.size(); i++) {
			long temp = photos.get(i).getImageFile().lastModified();
			if(temp < earliest) {
				earliest = temp;
			}
		}
		//System.out.println("earliest date for album " + name + " is " + new Date(earliest));
		//return new Date(earliest);
		LocalDateTime date = LocalDateTime.ofInstant(Instant.ofEpochMilli(earliest), ZoneId.systemDefault());
		return date;
	}
	
	/**
	 * method to obtain latest date of photos in album
	 * @return Date object containing latest date
	 */
	private LocalDateTime findLatestDate() {
		if(photos.size() <= 0) {
			return null;
		}
		long latest = photos.get(0).getImageFile().lastModified();
		for(int i = 1; i < photos.size(); i++) {
			long temp = photos.get(i).getImageFile().lastModified();
			if(temp > latest) {
				latest = temp;
			}
		}
		//System.out.println("latest date for album " + name + " is " + new Date(latest));
		//return new Date(latest);
		LocalDateTime date = LocalDateTime.ofInstant(Instant.ofEpochMilli(latest), ZoneId.systemDefault());
		return date;
	}
	
	/**
	 * method to add photo to album, ensures that if new photo has new earliest or latest date, this field will be updated
	 * @param photo Photo object to add to album
	 */
	public void addPhoto(Photo photo) {
		photos.add(photos.size(), photo);
		earliestDate = findEarliestDate();
		latestDate = findLatestDate();
	}
	
	public void removePhoto(int index) {
		photos.remove(index);
		earliestDate = findEarliestDate();
		latestDate = findLatestDate();
	}
	
	public ArrayList<Photo> getPhotos(){
		return photos;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public LocalDateTime getEarliestDate() {
		return earliestDate;
	}
	
	public LocalDateTime getLatestDate() {
		return latestDate;
	}
	
	public void setEarliestAndLatestDates() {
		earliestDate = findEarliestDate();
		latestDate = findLatestDate();
	}
	
	public String toString() {
		return name;
	}
}
