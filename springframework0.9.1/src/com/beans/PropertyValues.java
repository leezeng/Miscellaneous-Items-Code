
package com.beans;

/**
 * Object containing 0 or more PropertyValues comprising
 * one update.
 * @author  Rod Johnson
 * @since 13 May 2001
 * @version $Id: PropertyValues.java,v 1.1.1.1 2003/02/11 08:10:11 johnsonr Exp $
 */
public interface PropertyValues {
   
	/** 
	 * Return an array of the PropertyValue objects
	 * held in this object.
	 * @return an array of the PropertyValue objects
	 * held in this object.
	 */
	PropertyValue[] getPropertyValues();	
	
	/** 
	 * Is there a propertyValue object for this property?
	 * @param propertyName name of the property we're interested in
	 *@return whether there is a propertyValue object for this property?
	 */
	boolean contains(String propertyName);
	
	/** 
	 * Return the property value with the given name
	 * @param propertyName name to search for
	 * @return pv or null
	 */
	PropertyValue getPropertyValue(String propertyName);
	
	/**
	 * Return the changes since the previous PropertyValues.
	 * Subclasses should also override equals.
	 * @param old old property values
	 * @return PropertyValues updated or new properties.
	 * Return the empty PropertyValues if there are no changes.
	 */
	PropertyValues changesSince(PropertyValues old);

}
