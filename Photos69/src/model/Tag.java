package model;

import java.util.ArrayList;

public class Tag {
	private String type;
	private ArrayList<String> values;
	
	public Tag(String type, ArrayList<String> values) {
		this.type = type;
		this.values = values;
	}
	
	public String getType() {
		return type;
	}
	
	public ArrayList<String> getValues() {
		return values;
	}
}
