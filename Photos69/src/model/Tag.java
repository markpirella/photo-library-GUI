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
	
	/**
	 * constructor to create Tag object
	 * @param type String containing label for Tag
	 * @param value String containing value of Tag
	 */
	public Tag(String type, String value) {
		this.type = type;
		this.value = value;
	}
	
	/**
	 * method to get type of Tag
	 * @return String containing type of tag
	 */
	public String getType() {
		return type;
	}
	
	/**
	 * method to get value of Tag
	 * @return String containing value of tag
	 */
	public String getValue() {
		return value;
	}
	
	/**
	 * method to set value of Tag
	 * @param value String containing value of tag
	 */
	public void setValue(String value) {
		this.value = value;
	}
	
	/**
	 * generic toString method for object
	 * @return String containing type and value of tag
	 */
	public String toString() {
		return "(\""+type+"\""+","+"\""+value+"\")";
	}
	
	/**
	 * method to check if a tag has the same type and value as another tag
	 * @return Boolean value if the tags have the same type and value (true) or not (false)
	 */
	public boolean equals(Tag otherTag) {
		return otherTag.getType() == this.type && otherTag.getValue() == this.value;
	}
}
