package model;

import java.util.ArrayList;
//import javafx.collections.ObservableList;
//import javafx.collections.FXCollections;
import java.io.File;
import java.util.Date;

public class Album {
	private String name;
	private ArrayList<Photo> photos;
	private Date earliestDate;
	private Date latestDate;
	
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
	private Date findEarliestDate() {
		long earliest = new File(photos.get(0).filepath).lastModified();
		for(int i = 1; i < photos.size(); i++) {
			long temp = new File(photos.get(i).filepath).lastModified();
			if(temp < earliest) {
				earliest = temp;
			}
		}
		return new Date(earliest);
	}
	
	/**
	 * method to obtain latest date of photos in album
	 * @return Date object containing latest date
	 */
	private Date findLatestDate() {
		long latest = new File(photos.get(0).filepath).lastModified();
		for(int i = 1; i < photos.size(); i++) {
			long temp = new File(photos.get(i).filepath).lastModified();
			if(temp > latest) {
				latest = temp;
			}
		}
		return new Date(latest);
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
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Date getEarliestDate() {
		return earliestDate;
	}
	
	public Date getLatestDate() {
		return latestDate;
	}
}
