package com.ui.context.support;

import com.context.ApplicationContext;
import com.context.support.AbstractXmlApplicationContext;
import com.ui.context.Theme;
import com.ui.context.ThemeSource;

/**
 * Adds theme capabilities for UI contexts.
 * @author Jean-Pierre Pawlak
 */
public abstract class AbstractXmlUiApplicationContext
	extends AbstractXmlApplicationContext implements ThemeSource {

	private ThemeSource themeSource;

	/**
	 * Standard constructor.
	 */
	public AbstractXmlUiApplicationContext() {
		super();
	}

	/**
	 * Constructor with parent context.
	 */
	public AbstractXmlUiApplicationContext(ApplicationContext parent) {
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
