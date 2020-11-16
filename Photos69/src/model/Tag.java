package model;

import java.util.ArrayList;
import java.io.Serializable;

public class Tag implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1480471599797664839L;
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
