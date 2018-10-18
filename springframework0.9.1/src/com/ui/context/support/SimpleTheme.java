package com.ui.context.support;

import com.ui.context.Theme;
import com.context.MessageSource;

/**
 * Default Theme implementation, wrapping a name and an
 * underlying MessageSource.
 * @author Juergen Hoeller
 * @since 17.06.2003
 */
public class SimpleTheme implements Theme {

	private String name;

	private MessageSource messageSource;

	public SimpleTheme(String name, MessageSource messageSource) {
		this.name = name;
		this.messageSource = messageSource;
	}

	public String getName() {
		return name;
	}

	public MessageSource getMessageSource() {
		return messageSource;
	}

}
