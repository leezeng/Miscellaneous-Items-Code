package com.web.servlet.tags;

import com.beans.TestBean;
import com.mockobjects.servlet.MockPageContext;
import com.validation.BindException;
import com.web.bind.ServletRequestDataBinder;
import com.web.mock.MockHttpServletRequest;
import com.web.mock.MockServletContext;
import com.web.servlet.DispatcherServlet;
import com.web.servlet.LocaleResolver;
import com.web.servlet.SimpleWebApplicationContext;
import com.web.servlet.ThemeResolver;
import com.web.servlet.i18n.AcceptHeaderLocaleResolver;
import com.web.servlet.theme.FixedThemeResolver;
import junit.framework.TestCase;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @author Juergen Hoeller
 * @since 20.06.2003
 */
public class TagTestSuite extends TestCase {

	public void testHtmlEscapeTag() throws JspException {
		PageContext pc = createPageContext();
		HtmlEscapeTag tag = new HtmlEscapeTag();
		tag.setPageContext(pc);
		RequestContextAwareTag testTag = new RequestContextAwareTag() {
			public int doStartTagInternal() throws Exception {
				return EVAL_BODY_INCLUDE;
			}
		};
		testTag.setPageContext(pc);

		assertTrue("Correct default", !HtmlEscapeTag.isDefaultHtmlEscape(pc));
		assertTrue("Correctly applied", !testTag.isHtmlEscape());
		tag.setDefaultHtmlEscape("true");
		assertTrue("Correct doStartTag return value", tag.doStartTag() == Tag.EVAL_BODY_INCLUDE);
		assertTrue("Correctly enabled", HtmlEscapeTag.isDefaultHtmlEscape(pc));
		assertTrue("Correctly applied", testTag.isHtmlEscape());
		tag.setDefaultHtmlEscape("false");
		assertTrue("Correct doStartTag return value", tag.doStartTag() == Tag.EVAL_BODY_INCLUDE);
		assertTrue("Correctly disabled", !HtmlEscapeTag.isDefaultHtmlEscape(pc));
		assertTrue("Correctly applied", !testTag.isHtmlEscape());

		tag.setDefaultHtmlEscape("true");
		assertTrue("Correct doStartTag return value", tag.doStartTag() == Tag.EVAL_BODY_INCLUDE);
		testTag.setHtmlEscape("true");
		assertTrue("Correctly enabled", HtmlEscapeTag.isDefaultHtmlEscape(pc));
		assertTrue("Correctly applied", testTag.isHtmlEscape());
		testTag.setHtmlEscape("false");
		assertTrue("Correctly enabled", HtmlEscapeTag.isDefaultHtmlEscape(pc));
		assertTrue("Correctly applied", !testTag.isHtmlEscape());
		tag.setDefaultHtmlEscape("false");
		assertTrue("Correct doStartTag return value", tag.doStartTag() == Tag.EVAL_BODY_INCLUDE);
		testTag.setHtmlEscape("true");
		assertTrue("Correctly disabled", !HtmlEscapeTag.isDefaultHtmlEscape(pc));
		assertTrue("Correctly applied", testTag.isHtmlEscape());
		testTag.setHtmlEscape("false");
		assertTrue("Correctly disabled", !HtmlEscapeTag.isDefaultHtmlEscape(pc));
		assertTrue("Correctly applied", !testTag.isHtmlEscape());
	}

	public void testHtmlEscapeTagWithContextParamTrue() throws JspException {
		PageContext pc = createPageContext();
		MockServletContext sc = (MockServletContext) pc.getServletContext();
		HtmlEscapeTag tag = new HtmlEscapeTag();
		tag.setPageContext(pc);

		sc.addInitParameter(HtmlEscapeTag.HTML_ESCAPE_CONTEXT_PARAM, "true");
		assertTrue("Correct default", HtmlEscapeTag.isDefaultHtmlEscape(pc));
		tag.setDefaultHtmlEscape("true");
		assertTrue("Correct doStartTag return value", tag.doStartTag() == Tag.EVAL_BODY_INCLUDE);
		assertTrue("Correctly enabled", HtmlEscapeTag.isDefaultHtmlEscape(pc));
		tag.setDefaultHtmlEscape("false");
		assertTrue("Correct doStartTag return value", tag.doStartTag() == Tag.EVAL_BODY_INCLUDE);
		assertTrue("Correctly disabled", !HtmlEscapeTag.isDefaultHtmlEscape(pc));
	}

	public void testHtmlEscapeTagWithContextParamFalse() throws JspException {
		PageContext pc = createPageContext();
		MockServletContext sc = (MockServletContext) pc.getServletContext();
		HtmlEscapeTag tag = new HtmlEscapeTag();
		tag.setPageContext(pc);

		sc.addInitParameter(HtmlEscapeTag.HTML_ESCAPE_CONTEXT_PARAM, "false");
		assertTrue("Correct default", !HtmlEscapeTag.isDefaultHtmlEscape(pc));
		tag.setDefaultHtmlEscape("true");
		assertTrue("Correct doStartTag return value", tag.doStartTag() == Tag.EVAL_BODY_INCLUDE);
		assertTrue("Correctly enabled", HtmlEscapeTag.isDefaultHtmlEscape(pc));
		tag.setDefaultHtmlEscape("false");
		assertTrue("Correct doStartTag return value", tag.doStartTag() == Tag.EVAL_BODY_INCLUDE);
		assertTrue("Correctly disabled", !HtmlEscapeTag.isDefaultHtmlEscape(pc));
	}

	public void testBindErrorsTagWithErrors() throws JspException {
		MockPageContext pc = createPageContext();
		ServletRequestDataBinder errors = new ServletRequestDataBinder(new TestBean(), "tb");
		errors.reject("test", null, "test");
		pc.getRequest().setAttribute(BindException.ERROR_KEY_PREFIX + "tb", errors);
		BindErrorsTag tag = new BindErrorsTag();
		tag.setPageContext(pc);
		tag.setName("tb");
		assertTrue("Correct doStartTag return value", tag.doStartTag() == Tag.EVAL_BODY_INCLUDE);
		assertTrue("Has errors variable", pc.getAttribute(BindErrorsTag.ERRORS_VARIABLE_NAME) == errors);
	}

	public void testBindErrorsTagWithoutErrors() throws JspException {
		MockPageContext pc = createPageContext();
		ServletRequestDataBinder errors = new ServletRequestDataBinder(new TestBean(), "tb");
		pc.getRequest().setAttribute(BindException.ERROR_KEY_PREFIX + "tb", errors);
		BindErrorsTag tag = new BindErrorsTag();
		tag.setPageContext(pc);
		tag.setName("tb");
		assertTrue("Correct doStartTag return value", tag.doStartTag() == Tag.SKIP_BODY);
		assertTrue("Doesn't have errors variable", pc.getAttribute(BindErrorsTag.ERRORS_VARIABLE_NAME) == null);
	}

	public void testBindErrorsTagWithoutBean() throws JspException {
		MockPageContext pc = createPageContext();
		BindErrorsTag tag = new BindErrorsTag();
		tag.setPageContext(pc);
		tag.setName("tb");
		try {
			tag.doStartTag();
			fail("Should have thrown JspException");
		}
		catch (JspException ex) {
			// expected
		}
	}

	public void testBindTagWithoutErrors() throws JspException {
		MockPageContext pc = createPageContext();
		ServletRequestDataBinder errors = new ServletRequestDataBinder(new TestBean(), "tb");
		pc.getRequest().setAttribute(BindException.ERROR_KEY_PREFIX + "tb", errors);
		BindTag tag = new BindTag();
		tag.setPageContext(pc);
		tag.setPath("tb");
		assertTrue("Correct doStartTag return value", tag.doStartTag() == Tag.EVAL_BODY_INCLUDE);
		BindStatus status = (BindStatus) pc.getAttribute(BindTag.STATUS_VARIABLE_NAME);
		assertTrue("Has status variable", status != null);
		assertTrue("Correct expression", status.getExpression() == null);
		assertTrue("Correct value", status.getValue() == null);
		assertTrue("Correct displayValue", "".equals(status.getDisplayValue()));
		assertTrue("Correct isError", !status.isError());
		assertTrue("Correct errorCodes", status.getErrorCodes().length == 0);
		assertTrue("Correct errorMessages", status.getErrorMessages().length == 0);
		assertTrue("Correct errorCode", "".equals(status.getErrorCode()));
		assertTrue("Correct errorMessage", "".equals(status.getErrorMessage()));
		assertTrue("Correct errorMessagesAsString", "".equals(status.getErrorMessagesAsString(",")));
	}

	public void testBindTagWithGlobalErrors() throws JspException {
		MockPageContext pc = createPageContext();
		ServletRequestDataBinder errors = new ServletRequestDataBinder(new TestBean(), "tb");
		errors.reject("code1", null, "message1");
		pc.getRequest().setAttribute(BindException.ERROR_KEY_PREFIX + "tb", errors);
		BindTag tag = new BindTag();
		tag.setPageContext(pc);
		tag.setPath("tb");
		assertTrue("Correct doStartTag return value", tag.doStartTag() == Tag.EVAL_BODY_INCLUDE);
		BindStatus status = (BindStatus) pc.getAttribute(BindTag.STATUS_VARIABLE_NAME);
		assertTrue("Has status variable", status != null);
		assertTrue("Correct expression", status.getExpression() == null);
		assertTrue("Correct value", status.getValue() == null);
		assertTrue("Correct displayValue", "".equals(status.getDisplayValue()));
		assertTrue("Correct isError", status.isError());
		assertTrue("Correct errorCodes", status.getErrorCodes().length == 1);
		assertTrue("Correct errorMessages", status.getErrorMessages().length == 1);
		assertTrue("Correct errorCode", "code1".equals(status.getErrorCode()));
		assertTrue("Correct errorMessage", "message1".equals(status.getErrorMessage()));
		assertTrue("Correct errorMessagesAsString", "message1".equals(status.getErrorMessagesAsString(",")));
	}

	public void testBindTagWithFieldErrors() throws JspException {
		MockPageContext pc = createPageContext();
		TestBean tb = new TestBean();
		tb.setName("name1");
		ServletRequestDataBinder errors = new ServletRequestDataBinder(tb, "tb");
		errors.rejectValue("name", "code1", "message & 1");
		errors.rejectValue("name", "code2", "message2");
		pc.getRequest().setAttribute(BindException.ERROR_KEY_PREFIX + "tb", errors);
		BindTag tag = new BindTag();
		tag.setPageContext(pc);
		tag.setPath("tb.name");
		tag.setHtmlEscape("true");
		assertTrue("Correct doStartTag return value", tag.doStartTag() == Tag.EVAL_BODY_INCLUDE);
		BindStatus status = (BindStatus) pc.getAttribute(BindTag.STATUS_VARIABLE_NAME);
		assertTrue("Has status variable", status != null);
		assertTrue("Correct expression", "name".equals(status.getExpression()));
		assertTrue("Correct value", "name1".equals(status.getValue()));
		assertTrue("Correct displayValue", "name1".equals(status.getDisplayValue()));
		assertTrue("Correct isError", status.isError());
		assertTrue("Correct errorCodes", status.getErrorCodes().length == 2);
		assertTrue("Correct errorMessages", status.getErrorMessages().length == 2);
		assertTrue("Correct errorCode", "code1".equals(status.getErrorCode()));
		assertTrue("Correct errorMessage", "message &amp; 1".equals(status.getErrorMessage()));
		assertTrue("Correct errorMessagesAsString", "message &amp; 1 - message2".equals(status.getErrorMessagesAsString(" - ")));
	}

	public void testBindTagWithNestedFieldErrors() throws JspException {
		MockPageContext pc = createPageContext();
		TestBean tb = new TestBean();
		tb.setName("name1");
		TestBean spouse = new TestBean();
		spouse.setName("name2");
		tb.setSpouse(spouse);
		ServletRequestDataBinder errors = new ServletRequestDataBinder(tb, "tb");
		errors.rejectValue("spouse.name", "code1", "message1");
		pc.getRequest().setAttribute(BindException.ERROR_KEY_PREFIX + "tb", errors);
		BindTag tag = new BindTag();
		tag.setPageContext(pc);
		tag.setPath("tb.spouse.name");
		assertTrue("Correct doStartTag return value", tag.doStartTag() == Tag.EVAL_BODY_INCLUDE);
		BindStatus status = (BindStatus) pc.getAttribute(BindTag.STATUS_VARIABLE_NAME);
		assertTrue("Has status variable", status != null);
		assertTrue("Correct expression", "spouse.name".equals(status.getExpression()));
		assertTrue("Correct value", "name2".equals(status.getValue()));
		assertTrue("Correct displayValue", "name2".equals(status.getDisplayValue()));
		assertTrue("Correct isError", status.isError());
		assertTrue("Correct errorCodes", status.getErrorCodes().length == 1);
		assertTrue("Correct errorMessages", status.getErrorMessages().length == 1);
		assertTrue("Correct errorCode", "code1".equals(status.getErrorCode()));
		assertTrue("Correct errorMessage", "message1".equals(status.getErrorMessage()));
		assertTrue("Correct errorMessagesAsString", "message1".equals(status.getErrorMessagesAsString(" - ")));
	}

	public void testPropertyExposing() throws JspException {
		MockPageContext pc = createPageContext();
		TestBean tb = new TestBean();
		tb.setName("name1");
		BindException errors = new BindException(tb, "tb");
		errors.rejectValue("name", "code1", null, "message & 1");
		errors.rejectValue("name", "code2", null, "message2");
		pc.getRequest().setAttribute(BindException.ERROR_KEY_PREFIX + "tb", errors);

		// test global property (should be null)
		BindTag tag = new BindTag();
		tag.setPageContext(pc);
		tag.setPath("tb");
		assertTrue("Correct doStartTag return value", tag.doStartTag() == Tag.EVAL_BODY_INCLUDE);
		assertNull(tag.getProperty());

		// test property set (tb.name)
		tag.release();
		tag.setPageContext(pc);
		tag.setPath("tb.name");
		assertTrue("Correct doStartTag return value", tag.doStartTag() == Tag.EVAL_BODY_INCLUDE);
		assertEquals("name", tag.getProperty());
	}


	public void testBindTagWithoutBean() throws JspException {
		MockPageContext pc = createPageContext();
		BindTag tag = new BindTag();
		tag.setPageContext(pc);
		tag.setPath("tb");
		try {
			tag.doStartTag();
			fail("Should have thrown JspException");
		}
		catch (JspException ex) {
			// expected
		}
	}

	public void testMessageTagWithCode1() throws JspException {
		MockPageContext pc = createPageContext();
		final StringBuffer message = new StringBuffer();
		MessageTag tag = new MessageTag() {
			protected void writeMessage(String msg) throws IOException {
				message.append(msg);
			}
		};
		tag.setPageContext(pc);
		tag.setCode("test");
		assertTrue("Correct doStartTag return value", tag.doStartTag() == Tag.EVAL_BODY_INCLUDE);
		assertTrue("Correct message", "test message".equals(message.toString()));
	}

	public void testMessageTagWithCode2() throws JspException {
		MockPageContext pc = createPageContext();
		MockHttpServletRequest request = (MockHttpServletRequest) pc.getRequest();
		request.addPreferredLocale(Locale.CANADA);
		final StringBuffer message = new StringBuffer();
		MessageTag tag = new MessageTag() {
			protected void writeMessage(String msg) throws IOException {
				message.append(msg);
			}
		};
		tag.setPageContext(pc);
		tag.setCode("test");
		tag.setHtmlEscape("true");
		assertTrue("Correct doStartTag return value", tag.doStartTag() == Tag.EVAL_BODY_INCLUDE);
		assertTrue("Correct message", "Canadian &amp; test message".equals(message.toString()));
	}

	public void testMessageTagWithCodeAndText1() throws JspException {
		MockPageContext pc = createPageContext();
		final StringBuffer message = new StringBuffer();
		MessageTag tag = new MessageTag() {
			protected void writeMessage(String msg) throws IOException {
				message.append(msg);
			}
		};
		tag.setPageContext(pc);
		tag.setCode("test");
		tag.setText("testtext");
		assertTrue("Correct doStartTag return value", tag.doStartTag() == Tag.EVAL_BODY_INCLUDE);
		assertTrue("Correct message", "test message".equals(message.toString()));
	}

	public void testMessageTagWithCodeAndText2() throws JspException {
		MockPageContext pc = createPageContext();
		final StringBuffer message = new StringBuffer();
		MessageTag tag = new MessageTag() {
			protected void writeMessage(String msg) throws IOException {
				message.append(msg);
			}
		};
		tag.setPageContext(pc);
		tag.setCode("test2");
		tag.setText("test & text");
		assertTrue("Correct doStartTag return value", tag.doStartTag() == Tag.EVAL_BODY_INCLUDE);
		assertTrue("Correct message", "test & text".equals(message.toString()));
	}

	public void testMessageTagWithText() throws JspException {
		MockPageContext pc = createPageContext();
		final StringBuffer message = new StringBuffer();
		MessageTag tag = new MessageTag() {
			protected void writeMessage(String msg) throws IOException {
				message.append(msg);
			}
		};
		tag.setPageContext(pc);
		tag.setText("test & text");
		tag.setHtmlEscape("true");
		assertTrue("Correct doStartTag return value", tag.doStartTag() == Tag.EVAL_BODY_INCLUDE);
		assertTrue("Correct message", "test &amp; text".equals(message.toString()));
	}

	public void testThemeTag() throws JspException {
		MockPageContext pc = createPageContext();
		final StringBuffer message = new StringBuffer();
		ThemeTag tag = new ThemeTag() {
			protected void writeMessage(String msg) throws IOException {
				message.append(msg);
			}
		};
		tag.setPageContext(pc);
		tag.setCode("themetest");
		assertTrue("Correct doStartTag return value", tag.doStartTag() == Tag.EVAL_BODY_INCLUDE);
		assertTrue("Correct message", "theme test message".equals(message.toString()));
	}

	private MockPageContext createPageContext() {
		MockServletContext sc = new MockServletContext();
		MockHttpServletRequest request = new MockHttpServletRequest(sc, "GET", "/test");
		SimpleWebApplicationContext wac = new SimpleWebApplicationContext(null, "test");
		wac.setServletContext(sc);
		request.setAttribute(DispatcherServlet.WEB_APPLICATION_CONTEXT_ATTRIBUTE, wac);
		LocaleResolver lr = new AcceptHeaderLocaleResolver();
		request.setAttribute(DispatcherServlet.LOCALE_RESOLVER_ATTRIBUTE, lr);
		ThemeResolver tr = new FixedThemeResolver();
		request.setAttribute(DispatcherServlet.THEME_RESOLVER_ATTRIBUTE, tr);

		MockPageContext pc = new MockPageContext() {
			private Map attributes = new HashMap();
			public void setAttribute(String s, Object o) {
				attributes.put(s, o);
			}
			public Object getAttribute(String s) {
				return attributes.get(s);
			}
		};
		pc.setServletContext(sc);
		pc.setRequest(request);
		return pc;
	}

}
