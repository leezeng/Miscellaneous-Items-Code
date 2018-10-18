package com.web.context;

import javax.servlet.ServletContext;

import com.web.context.support.WebApplicationContextUtils;
import com.web.context.support.XmlWebApplicationContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.beans.BeansException;
import com.context.ApplicationContextException;

/**
 * Performs the actual initialization work for the root application context.
 * Called by ContextLoaderListener.
 *
 * <p>Regards a "contextClass" parameter at the servlet context resp. web.xml root level,
 * falling back to the default context class (XmlWebApplicationContext) if not found.
 *
 * @author Juergen Hoeller
 * @since 17.02.2003
 * @see ContextLoaderListener
 * @see XmlWebApplicationContext
 */
public class ContextLoader {

	/**
	 * Config param for the root WebApplicationContext implementation class to use.
	 */
	public static final String CONTEXT_CLASS_PARAM = "contextClass";

	public static final Class DEFAULT_CONTEXT_CLASS = XmlWebApplicationContext.class;

	private static final Log logger = LogFactory.getLog(ContextLoader.class);

	/**
	 * Initializes Spring's web application context for the given servlet context,
	 * solely regarding the servlet context init parameter.
	 * @param servletContext current servlet context
	 * @return the new WebApplicationContext
	 */
	public static WebApplicationContext initContext(ServletContext servletContext) throws ApplicationContextException {
		servletContext.log("Loading root WebApplicationContext");
		String contextClass = servletContext.getInitParameter(CONTEXT_CLASS_PARAM);

		// Now we must load the WebApplicationContext.
		// It configures itself: all we need to do is construct the class with a no-arg
		// constructor, and invoke setServletContext.
		try {
			Class clazz = (contextClass != null ? Class.forName(contextClass) : DEFAULT_CONTEXT_CLASS);
			logger.info("Loading root WebApplicationContext: using context class '" + clazz.getName() + "'");

			if (!WebApplicationContext.class.isAssignableFrom(clazz)) {
				throw new ApplicationContextException("Context class is no WebApplicationContext: " + contextClass);
			}

			WebApplicationContext webApplicationContext = (WebApplicationContext) clazz.newInstance();
			webApplicationContext.setServletContext(servletContext);
			return webApplicationContext;

		} catch (ApplicationContextException ex) {
			handleException("Failed to initialize application context", ex);

		} catch (BeansException ex) {
			handleException("Failed to initialize beans in application context", ex);

		} catch (ClassNotFoundException ex) {
			handleException("Failed to load config class '" + contextClass + "'", ex);

		} catch (InstantiationException ex) {
			handleException("Failed to instantiate config class '" + contextClass + "': does it have a public no arg constructor?", ex);

		} catch (IllegalAccessException ex) {
			handleException("Illegal access while finding or instantiating config class '" + contextClass + "': does it have a public no arg constructor?", ex);

		} catch (Throwable ex) {
			handleException("Unexpected error loading context configuration", ex);
		}

		return null;
	}

	private static void handleException(String msg, Throwable ex) throws ApplicationContextException {
		logger.error(msg, ex);
		if (ex instanceof Error) {
			throw (Error) ex;
		}
		else if (ex instanceof ApplicationContextException) {
			throw (ApplicationContextException) ex;
		}
		else {
			throw new ApplicationContextException(msg, ex);
		}
	}

	public static void closeContext(ServletContext servletContext) {
		servletContext.log("Closing root WebApplicationContext");
		WebApplicationContextUtils.getWebApplicationContext(servletContext).close();
	}

}
