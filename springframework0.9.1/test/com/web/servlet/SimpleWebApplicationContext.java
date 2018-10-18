package com.web.servlet;

import java.io.IOException;
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
import com.context.support.StaticMessageSource;
import com.ui.context.Theme;
import com.ui.context.ThemeSource;
import com.ui.context.support.SimpleTheme;
import com.ui.context.support.UiApplicationContextUtils;
import com.web.context.support.StaticWebApplicationContext;
import com.web.servlet.i18n.AcceptHeaderLocaleResolver;
import com.web.servlet.mvc.Controller;
import com.web.servlet.mvc.SimpleFormController;
import com.web.servlet.support.RequestContextUtils;
import com.web.servlet.theme.AbstractThemeResolver;

/**
 * @author Juergen Hoeller
 * @since 21.05.2003
 */
public class SimpleWebApplicationContext extends StaticWebApplicationContext {

	public SimpleWebApplicationContext(ApplicationContext parent, String namespace) throws BeansException, ApplicationContextException {
		super(parent, namespace);
	}

	public void setServletContext(ServletContext servletContext) {
		MutablePropertyValues pvs = new MutablePropertyValues();
		pvs.addPropertyValue(new PropertyValue("commandClass", "TestBean"));
		pvs.addPropertyValue(new PropertyValue("formView", "form"));
		registerSingleton("/form.do", SimpleFormController.class, pvs);

		registerSingleton("/locale.do", LocaleChecker.class, null);

		addMessage("test", Locale.ENGLISH, "test message");
		addMessage("test", Locale.CANADA, "Canadian & test message");

		registerSingleton(UiApplicationContextUtils.THEME_SOURCE_BEAN_NAME, DummyThemeSource.class, null);
		super.setServletContext(servletContext);
	}


	public static class LocaleChecker implements Controller {

		public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			if (!(RequestContextUtils.getWebApplicationContext(request) instanceof SimpleWebApplicationContext)) {
				throw new ServletException("Incorrect WebApplicationContext");
			}
			if (!(RequestContextUtils.getLocaleResolver(request) instanceof AcceptHeaderLocaleResolver)) {
				throw new ServletException("Incorrect LocaleResolver");
			}
			if (!Locale.CANADA.equals(RequestContextUtils.getLocale(request))) {
				throw new ServletException("Incorrect Locale");
			}
			return null;
		}
	}

	public static class DummyThemeSource implements ThemeSource {

		private StaticMessageSource messageSource;

		public DummyThemeSource() {
			messageSource = new StaticMessageSource();
			messageSource.addMessage("themetest", Locale.ENGLISH, "theme test message");
		}

		public Theme getTheme(String themeName) {
			if (AbstractThemeResolver.ORIGINAL_DEFAULT_THEME_NAME.equals(themeName)) {
				return new SimpleTheme(AbstractThemeResolver.ORIGINAL_DEFAULT_THEME_NAME, messageSource);
			}
			else {
				return null;
			}
		}
	}

}
