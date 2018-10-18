/**
 * Generic framework code included with 
 * <a href="http://www.amazon.com/exec/obidos/tg/detail/-/1861007841/">Expert One-On-One J2EE Design and Development</a>
 * by Rod Johnson (Wrox, 2002). 
 * This code is free to use and modify. However, please
 * acknowledge the source and include the above URL in each
 * class using or derived from this code. 
 * Please contact <a href="mailto:rod.johnson@com">rod.johnson@com</a>
 * for commercial support.
 */

package com.context.support;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.context.ApplicationContext;
import com.context.ApplicationContextAware;
import com.context.ApplicationContextException;

/**
 * Convenient superclass for application objects that want to be aware of
 * the application context, e.g. for custom lookup of collaborating beans
 * or for context-specific resource access. It saves the application
 * context reference and provides an initialization callback method.
 *
 * <p>There is no requirement to subclass this class: It just makes things
 * a little easier if you need access to the context, e.g. for access to
 * file resources or to the message source. Note that many application
 * objects do not need to be aware of the application context at all,
 * as they can receive collaborating beans via bean references.
 *
 * <p>Many framework classes are derived from this class, especially
 * within the web support.
 *
 * @author Rod Johnson
 * @author Juergen Hoeller
 */
public abstract class ApplicationObjectSupport implements ApplicationContextAware {
	
	/** Logger that is available to subclasses */
	protected final Log logger = LogFactory.getLog(getClass());
	
	/** ApplicationContext this object runs in */
	private ApplicationContext applicationContext;

	public final void setApplicationContext(ApplicationContext ctx) throws ApplicationContextException {
		// ignore reinitialization
		if (this.applicationContext == null) {
			this.applicationContext = ctx;
			initApplicationContext();
		}
	}
	
	/**
	 * Return the ApplicationContext instance used by this object.
	 */
	public final ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	/**
	 * Subclasses can override this for custom initialization behavior.
	 * Gets called by setApplicationContext() after setting the context instance.
	 * <p>Note: Does </i>not</i> get called on reinitialization of the context.
	 * @throws ApplicationContextException if initialization attempted
	 * by this object fails
	 */
	protected void initApplicationContext() throws ApplicationContextException {
	}

}
