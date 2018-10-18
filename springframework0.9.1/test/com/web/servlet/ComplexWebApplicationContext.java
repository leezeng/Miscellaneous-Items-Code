package com.web.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.beans.BeansException;
import com.beans.MutablePropertyValues;
import com.beans.PropertyValue;
import com.context.ApplicationContext;
import com.context.ApplicationContextException;
import com.context.support.ApplicationObjectSupport;
import com.core.Ordered;
import com.web.context.support.StaticWebApplicationContext;
import com.web.servlet.handler.SimpleUrlHandlerMapping;
import com.web.servlet.i18n.LocaleChangeInterceptor;
import com.web.servlet.theme.ThemeChangeInterceptor;
import com.web.servlet.i18n.SessionLocaleResolver;
import com.web.servlet.mvc.SimpleControllerHandlerAdapter;
import com.web.servlet.mvc.SimpleFormController;
import com.web.servlet.support.RequestContextUtils;
import com.web.servlet.support.UserRoleAuthorizationInterceptor;
import com.web.servlet.view.ResourceBundleViewResolver;
import com.web.servlet.theme.SessionThemeResolver;

/**
 * @author Juergen Hoeller
 * @since 21.05.2003
 */
class ComplexWebApplicationContext extends StaticWebApplicationContext {

	public ComplexWebApplicationContext(ApplicationContext parent, String namespace) throws BeansException, ApplicationContextException {
		super(parent, namespace);
	}

	public void setServletContext(ServletContext servletContext) {
		registerSingleton(DispatcherServlet.LOCALE_RESOLVER_BEAN_NAME, SessionLocaleResolver.class, null);
		registerSingleton(DispatcherServlet.THEME_RESOLVER_BEAN_NAME, SessionThemeResolver.class, null);

		MutablePropertyValues pvs = new MutablePropertyValues();
		pvs.addPropertyValue(new PropertyValue("mappings", "/form.do=localeHandler\n/locale.do=localeHandler"));
		registerSingleton("myUrlMapping1", SimpleUrlHandlerMapping.class, pvs);

		pvs = new MutablePropertyValues();
		pvs.addPropertyValue(new PropertyValue("mappings", "/form.do=localeHandler\n/unknown.do=unknownHandler"));
		pvs.addPropertyValue(new PropertyValue("order", "2"));
		registerSingleton("myUrlMapping2", SimpleUrlHandlerMapping.class, pvs);

		pvs = new MutablePropertyValues();
		pvs.addPropertyValue(new PropertyValue("mappings", "/form.do=formHandler"));
		pvs.addPropertyValue(new PropertyValue("order", "1"));
		registerSingleton("myUrlMapping3", SimpleUrlHandlerMapping.class, pvs);

		registerSingleton("myDummyAdapter", MyDummyAdapter.class, null);
		registerSingleton("myHandlerAdapter", MyHandlerAdapter.class, null);
		registerSingleton("standardHandler", SimpleControllerHandlerAdapter.class, null);

		pvs = new MutablePropertyValues();
		pvs.addPropertyValue(new PropertyValue("basename", "com.web.servlet.complexviews"));
		registerSingleton(DispatcherServlet.VIEW_RESOLVER_BEAN_NAME, ResourceBundleViewResolver.class, pvs);

		pvs = new MutablePropertyValues();
		pvs.addPropertyValue(new PropertyValue("commandClass", "TestBean"));
		pvs.addPropertyValue(new PropertyValue("formView", "form"));
		registerSingleton("formHandler", SimpleFormController.class, pvs);

		registerSingleton("localeHandler", ComplexLocaleChecker.class, null);
		registerSingleton("unknownHandler", String.class, null);

		addMessage("test", Locale.ENGLISH, "test message");
		addMessage("test", Locale.CANADA, "Canadian & test message");

		super.setServletContext(servletContext);

		SimpleUrlHandlerMapping myUrlMapping1 = (SimpleUrlHandlerMapping) getBean("myUrlMapping1");
		LocaleChangeInterceptor interceptor1 = new LocaleChangeInterceptor();
		LocaleChangeInterceptor interceptor2 = new LocaleChangeInterceptor();
		interceptor2.setParamName("locale2");
		ThemeChangeInterceptor interceptor3 = new ThemeChangeInterceptor();
		ThemeChangeInterceptor interceptor4 = new ThemeChangeInterceptor();
		interceptor4.setParamName("theme2");
		UserRoleAuthorizationInterceptor interceptor5 = new UserRoleAuthorizationInterceptor();
		interceptor5.setAuthorizedRoles(new String[] {"role1", "role2"});

		List interceptors = new ArrayList();
		interceptors.add(interceptor5);
		interceptors.add(interceptor1);
		interceptors.add(interceptor2);
		interceptors.add(interceptor3);
		interceptors.add(interceptor4);
		interceptors.add(new MyHandlerInterceptor1());
		interceptors.add(new MyHandlerInterceptor2());
		myUrlMapping1.setInterceptors(interceptors);
	}


	public static interface MyHandler {

		public void doSomething(HttpServletRequest request) throws ServletException;
	}


	public static class MyHandlerAdapter extends ApplicationObjectSupport implements HandlerAdapter, Ordered {

		public int getOrder() {
			return 99;
		}

		public boolean supports(Object handler) {
			return handler != null && MyHandler.class.isAssignableFrom(handler.getClass());
		}

		public ModelAndView handle(HttpServletRequest request, HttpServletResponse response, Object delegate)
		    throws IOException, ServletException {
			((MyHandler) delegate).doSomething(request);
			return null;
		}

		public long getLastModified(HttpServletRequest request, Object delegate) {
			return -1;
		}
	}


	public static class MyDummyAdapter extends ApplicationObjectSupport implements HandlerAdapter {

		public boolean supports(Object handler) {
			return handler != null && MyHandler.class.isAssignableFrom(handler.getClass());
		}

		public ModelAndView handle(HttpServletRequest request, HttpServletResponse response, Object delegate)
		    throws IOException, ServletException {
			throw new ServletException("dummy");
		}

		public long getLastModified(HttpServletRequest request, Object delegate) {
			return -1;
		}
	}


	public static class MyHandlerInterceptor1 implements HandlerInterceptor {

		public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
		    throws ServletException {
			if (request.getAttribute("test2") != null) {
				throw new ServletException("Wrong interceptor order");
			}
			request.setAttribute("test1", "test1");
			return true;
		}

		public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
		    throws ServletException {
			if (request.getAttribute("test2") != null) {
				throw new ServletException("Wrong interceptor order");
			}
			if (!"test1".equals(request.getAttribute("test1"))) {
				throw new ServletException("Incorrect request attribute");
			}
			request.removeAttribute("test1");
		}

	}


	public static class MyHandlerInterceptor2 implements HandlerInterceptor {

		public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
		    throws ServletException {
			if (request.getAttribute("test1") == null) {
				throw new ServletException("Wrong interceptor order");
			}
			request.setAttribute("test2", "test2");
			return true;
		}

		public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
		    throws ServletException {
			if (request.getAttribute("test1") == null) {
				throw new ServletException("Wrong interceptor order");
			}
			if (!"test2".equals(request.getAttribute("test2"))) {
				throw new ServletException("Incorrect request attribute");
			}
			request.removeAttribute("test2");
		}

	}


	public static class ComplexLocaleChecker implements MyHandler {

		public void doSomething(HttpServletRequest request) throws ServletException {
			if (!(RequestContextUtils.getWebApplicationContext(request) instanceof ComplexWebApplicationContext)) {
				throw new ServletException("Incorrect WebApplicationContext");
			}
			if (!(RequestContextUtils.getLocaleResolver(request) instanceof SessionLocaleResolver)) {
				throw new ServletException("Incorrect LocaleResolver");
			}
			if (!Locale.CANADA.equals(RequestContextUtils.getLocale(request))) {
				throw new ServletException("Incorrect Locale");
			}
			if (!(RequestContextUtils.getThemeResolver(request) instanceof SessionThemeResolver)) {
				throw new ServletException("Incorrect ThemeResolver");
			}
			if (!"theme".equals(RequestContextUtils.getThemeResolver(request).resolveThemeName(request))) {
				throw new ServletException("Incorrect theme name");
			}
		}
	}

}
