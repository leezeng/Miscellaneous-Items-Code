package com.web.servlet.theme;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.web.servlet.HandlerInterceptor;
import com.web.servlet.ThemeResolver;
import com.web.servlet.support.RequestContextUtils;

/**
 * Interceptor that allows for changing the current theme on every request,
 * via a configurable request parameter.
 * @author Juergen Hoeller
 * @since 20.06.2003
 * @see ThemeResolver
 */
public class ThemeChangeInterceptor implements HandlerInterceptor {

	public static final String DEFAULT_PARAM_NAME = "theme";

	private String paramName = DEFAULT_PARAM_NAME;

	/**
	 * Set the name of the parameter that contains a theme specification
	 * in a theme change request. Default is "theme".
	 */
	public void setParamName(String paramName) {
		this.paramName = paramName;
	}

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws ServletException {
		ThemeResolver themeResolver = RequestContextUtils.getThemeResolver(request);
		String newTheme = request.getParameter(this.paramName);
		if (newTheme != null) {
			themeResolver.setThemeName(request, response, newTheme);
		}
		// proceed in any case
		return true;
	}

	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
	}

}
