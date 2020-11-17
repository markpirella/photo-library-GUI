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
	
	public TagType(String type, boolean canHaveMultipleValues) {
		this.type = type;
		this.canHaveMultipleValues = canHaveMultipleValues;
	}
	
	public String getType() {
		return type;
	}
	
	public boolean canHaveMultipleValues() {
		return canHaveMultipleValues;
	}
	
	public String toString() {
		return type;
	}

}
