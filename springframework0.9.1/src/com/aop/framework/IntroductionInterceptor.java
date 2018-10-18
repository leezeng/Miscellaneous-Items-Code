/*
 * The Spring Framework is published under the terms
 * of the Apache Software License.
 */

package com.aop.framework;

import org.aopalliance.intercept.MethodInterceptor;

/**
 * Subinterface of MethodInterceptor that allows additional 
 * interfaces to be implemented by the interceptor, and available
 * via a proxy using that interceptor. This is commonly 
 * referred to as <b>introduction</b>.
 * DefaultProxyConfig and subclasses will automatically recognise
 * introduction interceptors and expose any interfaces they
 * introduce.
 * @see DefaultProxyConfig
 * @author Rod Johnson
 * @version $Id: IntroductionInterceptor.java,v 1.2 2003/07/23 18:44:23 johnsonr Exp $
 */
public interface IntroductionInterceptor extends MethodInterceptor {
	
	/**
	 * Return the introduced interfaces added by this object
	 * @return Class[]
	 */
	Class[] getIntroducedInterfaces();

}
