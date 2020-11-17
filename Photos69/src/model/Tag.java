package model;

import java.util.ArrayList;
import java.io.Serializable;

public class Tag implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1480471599797664839L;
	private String type;
	private String value;
	
	public Tag(String type, String value) {
		this.type = type;
		this.value = value;
	}
	
	public String getType() {
		return type;
	}
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
	public String toString() {
		return "(\""+type+"\""+","+"\""+value+"\")";
	}
}
