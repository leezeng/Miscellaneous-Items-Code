/*
 * The Spring Framework is published under the terms
 * of the Apache Software License.
 */
 
package com.beans.factory;

import com.beans.factory.BeanFactory;
import com.beans.factory.InitializingBean;
import com.beans.factory.BeanFactoryAware;

/**
 * Simple test of BeanFactory initialization
 * and lifecycle callbacks.
 * @author Rod Johnson
 * @since 12-Mar-2003
 * @version $Revision: 1.3 $
 */
public class LifecycleBean implements InitializingBean, BeanFactoryAware, DisposableBean {

	private boolean inited;

	private BeanFactory owningFactory;
	
	private boolean destroyed;

	public void afterPropertiesSet() {
		this.inited = true;
	}

	public void setBeanFactory(BeanFactory beanFactory) {
		if (!inited)
			throw new RuntimeException("Factory didn't call afterPropertiesSet() before invoking setBeanFactory on lifecycle bean");
		this.owningFactory = beanFactory;
	}

	/**
	 * Dummy business method that will fail unless the factory
	 * managed the bean's lifecycle correctly
	 */
	public void businessMethod() {
		if (!this.inited || this.owningFactory == null)
			throw new RuntimeException("Factory didn't initialize lifecycle object correctly");
	}

	public void destroy() {
		if (this.destroyed) {
			throw new IllegalStateException("Already destroyed");
		}
		this.destroyed = true;
	}

	public boolean isDestroyed() {
		return destroyed;
	}

}
