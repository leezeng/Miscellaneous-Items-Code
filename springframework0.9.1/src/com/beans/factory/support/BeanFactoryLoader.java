/*
 * The Spring Framework is published under the terms
 * of the Apache Software License.
 */
 
package com.beans.factory.support;

import com.beans.factory.BeanFactory;

/**
 * Interface to be implemented by objects that can load BeanFactories
 * (usually on behalf of application components such as EJBs).
 * @author Rod Johnson
 * @since 20-Jul-2003
 * @version $Id: BeanFactoryLoader.java,v 1.2 2003/07/31 18:43:07 jhoeller Exp $
 */
public interface BeanFactoryLoader {
	
	/**
	 * Load the BeanFactory.
	 * @return BeanFactory loaded BeanFactory. Never returns null. 
	 * @throws BootstrapException if a BeanFactory cannot be loaded
	 */
	BeanFactory loadBeanFactory() throws BootstrapException;

}
