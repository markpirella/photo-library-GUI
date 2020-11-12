package model;

import javafx.scene.image.Image;

public class Photo extends Image {
	String filepath;
	private String caption;
	public Photo(String filepath) {
		super(filepath);
		this.filepath = filepath;
		caption = "";
	}
	
	public String getCaption() {
		return caption;
	}
	
	public void setCaption(String caption) {
		this.caption = caption;
	}
}
