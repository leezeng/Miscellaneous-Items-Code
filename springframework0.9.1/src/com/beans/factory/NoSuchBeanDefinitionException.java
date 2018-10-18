package com.beans.factory;

import com.beans.BeansException;

/**
 * Exception thrown when a BeanFactory is asked for a bean
 * instance name for which it cannot find a definition.
 * @author Rod Johnson
 * @version $Id: NoSuchBeanDefinitionException.java,v 1.3 2003/08/12 18:13:34 jhoeller Exp $
 */
public class NoSuchBeanDefinitionException extends BeansException {

	/** Name of the missing bean */
	private final String name;

	/**
	 * Creates new <code>NoSuchBeanDefinitionException</code>..
	 * @param name the name of the missing bean
	 * @param message further, detailed message describing the problem.
	 */
	public NoSuchBeanDefinitionException(String name, String message) {
		super("No bean named [" + name + "] is defined {" + message + "}", null);
		this.name = name;
	}

	/**
	 * Return the name of the missing bean.
	 * @return the name of the missing bean
	 */
	public String getBeanName() {
		return name;
	}

}
