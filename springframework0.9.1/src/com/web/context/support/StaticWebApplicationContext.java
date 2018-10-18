package com.web.context.support;

import javax.servlet.ServletContext;

import com.beans.BeansException;
import com.context.ApplicationContext;
import com.context.ApplicationContextException;
import com.ui.context.support.StaticUiApplicationContext;
import com.web.context.WebApplicationContext;

/**
 * WebApplicationContext implementation for testing.
 * Not for use in production applications.
 */
public class StaticWebApplicationContext extends StaticUiApplicationContext implements WebApplicationContext {

	private String namespace;

	private ServletContext servletContext;

	public StaticWebApplicationContext() {
	}

	public StaticWebApplicationContext(ApplicationContext parent, String namespace)
	    throws BeansException, ApplicationContextException {
		super(parent);
		this.namespace = namespace;
	}

	public String getNamespace() {
		return namespace;
	}

	/**
	 * Normally this would cause loading, but this class doesn't rely on loading.
	 * @see WebApplicationContext#setServletContext(ServletContext)
	 */
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
		refresh();
		WebApplicationContextUtils.publishConfigObjects(this);
		// Expose as a ServletContext object
		WebApplicationContextUtils.publishWebApplicationContext(this);
	}
	

	public ServletContext getServletContext() {
		return servletContext;
	}

}
