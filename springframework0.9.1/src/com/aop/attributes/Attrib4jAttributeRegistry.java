/*
 * The Spring Framework is published under the terms
 * of the Apache Software License.
 */
 
package com.aop.attributes;

import java.lang.reflect.AccessibleObject;

import org.aopalliance.intercept.AttributeRegistry;

//import attrib4j.Attributes;

/**
 * @author Rod Johnson
 * @since 13-Mar-2003
 * @version $Revision: 1.5 $
 */
public class Attrib4jAttributeRegistry implements AttributeRegistry {

	/**
	 * @see org.aopalliance.intercept.AttributeRegistry#getAttributes(java.lang.reflect.AccessibleObject)
	 */
	public Object[] getAttributes(AccessibleObject ao) {
		// TODO: could cache, or want independent instances?
//		Method m = (Method) ao;
//		attrib4j.Attribute[] attrib4jAtts = Attributes.getAttributes(m);
//		return attrib4jAtts;

		throw new UnsupportedOperationException("Attrib4j integration not yet implemented");
	}
		

	/**
	 * @see org.aopalliance.intercept.AttributeRegistry#getAttributes(java.lang.Class)
	 */
	public Object[] getAttributes(Class clazz) {
		throw new UnsupportedOperationException("Attrib4j integration not yet implemented");
	}

}
