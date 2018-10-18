/*
 * The Spring Framework is published under the terms
 * of the Apache Software License.
 */
 
package com.aop.framework;

import com.core.NestedRuntimeException;

/**
 * 
 * @author Rod Johnson
 * @since 13-Mar-2003
 * @version $Revision: 1.1 $
 */
public class AopConfigException extends NestedRuntimeException {

	/**
	 * @param mesg
	 */
	public AopConfigException(String mesg) {
		super(mesg);
	}


}
