package model;

//import java.util.ArrayList;
import java.io.Serializable;

/**
 * TagType class used to determine whether or not a given tag type can have multiple values associated with it or just one value
 * @author Mark Pirella
 * @author Nicholas Farinella
 */
public class TagType implements Serializable {

	/**
	 * auto-generated Serialization value
	 */
	private static final long serialVersionUID = 3191242282844726948L;
	
	/**
	 * tag type
	 */
	private String type;
	
	/**
	 * true/false value for whether or not type can have multiple values associated with it
	 */
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
