package model;

import javafx.scene.image.Image;
import java.util.ArrayList;

public class Photo extends Image {
	String filepath;
	private String caption;
	private ArrayList<Tag> tags;
	
	public Photo(String filepath) {
		super(filepath);
		this.filepath = filepath;
		caption = "";
		tags = new ArrayList<Tag>(5);
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
}
