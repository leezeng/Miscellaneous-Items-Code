package com.ui.context.support;

import com.beans.BeansException;
import com.context.ApplicationContext;
import com.context.ApplicationContextException;
import com.context.support.StaticApplicationContext;
import com.ui.context.Theme;
import com.ui.context.ThemeSource;

/**
 * Adds theme capabilities for UI contexts.
 * @author Jean-Pierre Pawlak
 */
public class StaticUiApplicationContext extends StaticApplicationContext implements ThemeSource {

	private ThemeSource themeSource;

	/**
	 * Standard constructor.
	 */
	public StaticUiApplicationContext()	throws BeansException, ApplicationContextException {
		super();
	}

	/**
	 * Constructor with parent context.
	 */
	public StaticUiApplicationContext(ApplicationContext parent) throws BeansException, ApplicationContextException {
		super(parent);
	}

	/**
	 * Initialize the theme capability.
	 */
	protected void onRefresh() {
		this.themeSource = UiApplicationContextUtils.initThemeSource(this);
	}

	public Theme getTheme(String themeName) {
		return this.themeSource.getTheme(themeName);
	}

}
