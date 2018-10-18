package com.web.context;

import java.util.Date;
import java.util.Locale;

import com.context.AbstractApplicationContextTests;
import com.context.ApplicationContext;
import com.context.MessageSource;
import com.context.NoSuchMessageException;
import com.ui.context.Theme;
import com.web.context.support.XmlWebApplicationContext;
import com.web.mock.MockServletContext;
import com.web.servlet.theme.AbstractThemeResolver;
import junit.framework.Assert;


/**
 * Creates a WebApplicationContext that points to a "web.xml" file that
 * contains the entry for what file to use for the applicationContext
 * (file "com/interface21/web/context/WEB-INF/applicationContext.xml").
 * That file then has an entry for a bean called "messageSource".
 * Whatever the basename property is set to for this bean is what the name of
 * a properties file in the classpath must be (in our case the name is
 * "messages" - note no package names).
 * Thus the catalog filename will be in the root of where the classes are compiled
 * to and will be called "messages_XX_YY.properties" where "XX" and "YY" are the
 * language and country codes known by the ResourceBundle class.
 *
 * <p>NOTE: The main method of this class is the "createContext(...)" method, and
 * it was copied from the WebApplicationContextTestSuite
 * class.
 *
 * @author Rod Johnson
 * @author Tony Falabella
 * @author Jean-Pierre Pawlak
 * @version
 */
public class ResourceBundleMessageSourceTestSuite extends AbstractApplicationContextTests {

	/**
	 * We use ticket WAR root for file structure.
	 * We don't attempt to read web.xml.
	 */
	public static final String WAR_ROOT = "/com/interface21/web/context";

	private WebApplicationContext root;

	private MessageSource themeMsgSource;

	public ResourceBundleMessageSourceTestSuite() throws Exception {
	}

	public void testCount() {
		Assert.assertTrue("should have 17 beans, not " +
							 this.applicationContext.getBeanDefinitionCount(),
							 this.applicationContext.getBeanDefinitionCount() == 15);
	}

	/**
	 * Overridden as we can't trust superclass method.
	 * @see AbstractApplicationContextTests#testEvents()
	 */
	public void testEvents() throws Exception {
		// Do nothing
	}

	/**
	 * @see com.context.support.AbstractNestingMessageSource for more details.
	 * NOTE: Messages are contained within the "test/com/interface21/web/context/WEB-INF/messagesXXX.properties" files.
	 */
	public void testGetMessageWithDefaultPassedInAndFoundInMsgCatalog() {
		Assert.assertTrue("valid msg from resourcebundle with default msg passed in returned default msg. Expected msg from catalog.",
							 root.getMessage("message.format.example2", null, "This is a default msg if not found in msg.cat.", Locale.US
							 )
							 .equals("This is a test message in the message catalog with no args."));
		// root.getTheme("theme").getMessageSource().getMessage()
	}

	/**
	 * @see com.context.support.AbstractNestingMessageSource for more details.
	 * NOTE: Messages are contained within the "test/com/interface21/web/context/WEB-INF/messagesXXX.properties" files.
	 */
	public void testGetMessageWithDefaultPassedInAndNotFoundInMsgCatalog() {
		Assert.assertTrue("bogus msg from resourcebundle with default msg passed in returned default msg",
							 root.getMessage("bogus.message", null, "This is a default msg if not found in msg.cat.", Locale.UK
							 )
							 .equals("This is a default msg if not found in msg.cat."));
	}

	/**
	 * The underlying implementation uses a hashMap to cache messageFormats
	 * once a message has been asked for.  This test is an attempt to
	 * make sure the cache is being used properly.
	 * NOTE:  Messages are contained within the "test/com/interface21/web/context/WEB-INF/messagesXXX.properties" files.
	 * @see com.context.support.AbstractNestingMessageSource for more details.
	 */
	public void testGetMessageWithMessageAlreadyLookedFor() throws Exception {
		Object[] arguments = {
			new Integer(7), new Date(System.currentTimeMillis()),
			"a disturbance in the Force"
		};

		// The first time searching, we don't care about for this test
		root.getMessage("message.format.example1", arguments, Locale.US);

		// Now msg better be as expected
		Assert.assertTrue("2nd search within MsgFormat cache returned expected message for Locale.US",
							 root.getMessage("message.format.example1", arguments, Locale.US
							 )
							 .indexOf("there was \"a disturbance in the Force\" on planet 7.") != -1);

		Object[] newArguments = {
			new Integer(8), new Date(System.currentTimeMillis()),
			"a disturbance in the Force"
		};

		// Now msg better be as expected even with different args
		Assert.assertTrue("2nd search within MsgFormat cache with different args returned expected message for Locale.US",
							 root.getMessage("message.format.example1", newArguments, Locale.US
							 )
							 .indexOf("there was \"a disturbance in the Force\" on planet 8.") != -1);
	}

	/**
	 * @see com.context.support.AbstractNestingMessageSource for more details.
	 * NOTE:  Messages are contained within the "test/com/interface21/web/context/WEB-INF/messagesXXX.properties" files.
	 * Example taken from the javadocs for the java.text.MessageFormat class
	 */
	public void testGetMessageWithNoDefaultPassedInAndFoundInMsgCatalog()
			throws Exception {
		Object[] arguments = {
			new Integer(7), new Date(System.currentTimeMillis()),
			"a disturbance in the Force"
		};


		/*
		 Try with Locale.US
		 Since the msg has a time value in it, we will use String.indexOf(...)
		 to just look for a substring without the time.  This is because it is
		 possible that by the time we store a time variable in this method
		 and the time the ResourceBundleMessageSource resolves the msg the
		 minutes of the time might not be the same.
		 */
		Assert.assertTrue("msg from resourcebundle for Locale.US substituting args for placeholders is as expected",
							 root.getMessage("message.format.example1", arguments, Locale.US
							 )
							 .indexOf("there was \"a disturbance in the Force\" on planet 7.") != -1);

		// Try with Locale.UK
		Assert.assertTrue("msg from resourcebundle for Locale.UK substituting args for placeholders is as expected",
							 root.getMessage("message.format.example1", arguments, Locale.UK
							 )
							 .indexOf("there was \"a disturbance in the Force\" on station number 7.") != -1);

		// Try with Locale.US - different test msg that requires no args
		Assert.assertTrue("msg from resourcebundle that requires no args for Locale.US is as expected",
							 root.getMessage("message.format.example2", null, Locale.US)
							 .equals("This is a test message in the message catalog with no args."));
	}

	/**
	 * @see com.context.support.AbstractNestingMessageSource for more details.
	 * NOTE:  Messages are contained within the "test/com/interface21/web/context/WEB-INF/messagesXXX.properties" files.
	 */
	public void testGetMessageWithNoDefaultPassedInAndNotFoundInMsgCatalog() {
		// Expecting an exception
		try {
			root.getMessage("bogus.message", null, Locale.UK);
			Assert.fail("bogus msg from resourcebundle without default msg should have thrown exception");
		}
		catch (NoSuchMessageException tExcept) {
			Assert.assertTrue("bogus msg from resourcebundle without default msg threw expected exception",
								 true);
		}
	}

	/**
	 * @see com.context.support.AbstractNestingMessageSource for more details.
	 * NOTE:  Messages are contained within the "test/com/interface21/web/context/WEB-INF/themeXXX.properties" files.
	 */
	public void testGetMessageWithDefaultPassedInAndFoundInThemeCatalog() {

		// Try with Locale.US
		String msg = getThemeMessage("theme.example1", null, "This is a default theme msg if not found in theme cat.", Locale.US);
		Assert.assertTrue("valid msg from theme resourcebundle with default msg passed in returned default msg.  Expected msg from catalog. Received: " + msg,
							 msg.equals("This is a test message in the theme message catalog."));
		// Try with Locale.UK
		msg = getThemeMessage("theme.example1", null, "This is a default theme msg if not found in theme cat.", Locale.UK);
		Assert.assertTrue("valid msg from theme resourcebundle with default msg passed in returned default msg.  Expected msg from catalog.",
							 msg.equals("This is a test message in the theme message catalog with no args."));
	}

	/**
	 * @see com.context.support.AbstractNestingMessageSource for more details.
	 * NOTE:  Messages are contained within the "test/com/interface21/web/context/WEB-INF/themeXXX.properties" files.
	 */
	public void testGetMessageWithDefaultPassedInAndNotFoundInThemeCatalog() {
		Assert.assertTrue("bogus msg from theme resourcebundle with default msg passed in returned default msg",
							 getThemeMessage("bogus.message", null, "This is a default msg if not found in theme cat.", Locale.UK
							 )
							 .equals("This is a default msg if not found in theme cat."));
	}

	public void testThemeSourceNesting() throws NoSuchMessageException {
		String overriddenMsg = getThemeMessage("theme.example2", null, null, Locale.UK);
		String originalMsg = root.getTheme(AbstractThemeResolver.ORIGINAL_DEFAULT_THEME_NAME).getMessageSource().getMessage("theme.example2", null, Locale.UK);
    Assert.assertTrue("correct overridden msg", "test-message2".equals(overriddenMsg));
		Assert.assertTrue("correct original msg", "message2".equals(originalMsg));
	}

	private String getThemeMessage(String code, Object args[], String defaultMessage, Locale locale) {
		return themeMsgSource.getMessage(code, args, defaultMessage, locale);
	}

	protected ApplicationContext createContext() throws Exception {
		root = new XmlWebApplicationContext();
		MockServletContext sc = new MockServletContext("", "/com/interface21/web/context/WEB-INF/web.xml");
		sc.addInitParameter(XmlWebApplicationContext.CONFIG_LOCATION_PARAM, "/com/interface21/web/context/WEB-INF/applicationContext.xml");
		sc.addInitParameter(XmlWebApplicationContext.CONFIG_LOCATION_PREFIX_PARAM, "/com/interface21/web/context/WEB-INF/");
		root.setServletContext(sc);
		WebApplicationContext wac = new XmlWebApplicationContext(root, "test-servlet");
		wac.setServletContext(sc);

		Theme theme = wac.getTheme(AbstractThemeResolver.ORIGINAL_DEFAULT_THEME_NAME);
		Assert.assertNotNull(theme);
		Assert.assertTrue("Theme name has to be the default theme name", AbstractThemeResolver.ORIGINAL_DEFAULT_THEME_NAME.equals(theme.getName()));
		themeMsgSource = theme.getMessageSource();
		Assert.assertNotNull(themeMsgSource);

		// Add listeners expected by parent test case
		//wac.(this.listener);
		return wac;
	}

}
