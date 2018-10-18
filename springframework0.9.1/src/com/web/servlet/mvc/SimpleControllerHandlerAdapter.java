package com.web.servlet.mvc;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.context.support.ApplicationObjectSupport;
import com.web.servlet.DispatcherServlet;
import com.web.servlet.HandlerAdapter;
import com.web.servlet.LastModified;
import com.web.servlet.ModelAndView;

/**
 * Adapter to use the Controller workflow interface with the generic
 * DispatcherServlet. This is an SPI class, not used directly by
 * application code.
 * @author Rod Johnson
 * @see DispatcherServlet
 * @version $Id: SimpleControllerHandlerAdapter.java,v 1.4 2003/06/06 16:15:09 jhoeller Exp $
 */
public class SimpleControllerHandlerAdapter extends ApplicationObjectSupport implements HandlerAdapter {
	
	public boolean supports(Object handler) {
		return handler != null && Controller.class.isAssignableFrom(handler.getClass());
	}
	
	public ModelAndView handle(HttpServletRequest request, HttpServletResponse response, Object handler)
	    throws ServletException, IOException {
		Controller controller = (Controller) handler;
		return controller.handleRequest(request, response);
	}
	
	public long getLastModified(HttpServletRequest request, Object handler) {
		if (handler instanceof LastModified) {
			return ((LastModified) handler).getLastModified(request);
		}
		return -1L;
	}

}
