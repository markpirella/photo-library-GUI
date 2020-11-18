package model;

import java.util.ArrayList;
import java.io.Serializable;

public class TagType implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3191242282844726948L;
	
	private String type;
	private boolean canHaveMultipleValues;
	
	/**
	 * constructor to create TagType object
	 * @param type String containing type of tag
	 * @param canHaveMultipleValues Boolean determining if the TagType can have multiple values (true) or not (false)
	 */
	public TagType(String type, boolean canHaveMultipleValues) {
		this.type = type;
		this.canHaveMultipleValues = canHaveMultipleValues;
	}
	
	/**
	 * method to get type of TagType
	 * @return String containing type field of TagType
	 */
	public String getType() {
		return type;
	}
	
	/**
	 * method to get if a TagType can have multiple values
	 * @return Boolean value that is true if the TagType can have multiple values, false if not
	 */
	public boolean canHaveMultipleValues() {
		return canHaveMultipleValues;
	}
	
	/**
	 * generic toString method for object
	 * @return String containing value of tag
	 */
	public String toString() {
		return type;
	}

}
