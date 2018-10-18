package com.web.context;

import java.util.Locale;

import javax.servlet.ServletContext;

import com.beans.ITestBean;
import com.context.AbstractApplicationContextTests;
import com.context.TestListener;
import com.context.ApplicationContext;
import com.context.NoSuchMessageException;
import com.web.context.support.XmlWebApplicationContext;
import com.web.mock.MockServletContext;
import junit.framework.Assert;

/**
 * @author Rod Johnson
 */
public class WebApplicationContextTestSuite extends AbstractApplicationContextTests {

	private ServletContext servletContext;
	
	private WebApplicationContext root;

	public WebApplicationContextTestSuite() throws Exception {
	}

	protected ApplicationContext createContext() throws Exception {
		root = new XmlWebApplicationContext();
		MockServletContext sc = new MockServletContext("", "/com/interface21/web/context/WEB-INF/web.xml");
		sc.addInitParameter(XmlWebApplicationContext.CONFIG_LOCATION_PARAM, "/com/interface21/web/context/WEB-INF/applicationContext.xml");
		sc.addInitParameter(XmlWebApplicationContext.CONFIG_LOCATION_PREFIX_PARAM, "/com/interface21/web/context/WEB-INF/");
		this.servletContext = sc;
		root.setServletContext(sc);
		WebApplicationContext wac = new XmlWebApplicationContext(root, "test-servlet");
		wac.setServletContext(sc);
		return wac;
	}

	/**
	 * Overridden as we can't trust superclass method
	 * @see AbstractApplicationContextTests#testEvents()
	 */
	public void testEvents() throws Exception {
		TestListener listener = (TestListener) this.applicationContext.getBean("testListener");
		listener.zeroCounter();
		TestListener parentListener = (TestListener) this.applicationContext.getParent().getBean("parentListener");
		parentListener.zeroCounter();
		
		parentListener.zeroCounter();
		Assert.assertTrue("0 events before publication", listener.getEventCount() == 0);
		Assert.assertTrue("0 parent events before publication", parentListener.getEventCount() == 0);
		this.applicationContext.publishEvent(new MyEvent(this));
		Assert.assertTrue("1 events after publication, not " + listener.getEventCount(), listener.getEventCount() == 1);
		Assert.assertTrue("1 parent events after publication", parentListener.getEventCount() == 1);
	}


	public void testWebApplicationContextExposedAsServletContextAttribute() throws Exception {
		WebApplicationContext wc = (WebApplicationContext) this.servletContext.getAttribute(WebApplicationContext.WEB_APPLICATION_CONTEXT_ATTRIBUTE_NAME);
		Assert.assertTrue("WebApplicationContext exposed in ServletContext as attribute", wc != null);
		Assert.assertTrue("WebApplicationContext exposed in ServletContext as attribute == root", wc == this.root);
	}

	/** Assumes web.xml defines testConfigObject of type TestConfigBean */
	public void testConfigObjects() throws Exception {
		Assert.assertTrue("has 'testConfigObject' attribute", servletContext.getAttribute("testConfigObject") != null);
		Object o = servletContext.getAttribute("testConfigObject");
		Assert.assertTrue("testConfigObject attribute is of type TestConfigBean", o instanceof TestConfigBean);
		TestConfigBean tcb = (TestConfigBean) o;
		Assert.assertTrue("tcb name=Tony", tcb.getName().equals("Tony"));
		Assert.assertTrue("tcb age=48", tcb.getAge() == 48);

		// Now test context aware config object
		Assert.assertTrue("has 'testConfigObject2' attribute", servletContext.getAttribute("testConfigObject2") != null);
		o = servletContext.getAttribute("testConfigObject2");
		Assert.assertTrue("testConfigObject2 attribute is of type ContextAwareTestConfigBean", o instanceof ContextAwareTestConfigBean);
		ContextAwareTestConfigBean ctcb = (ContextAwareTestConfigBean) o;
		Assert.assertTrue("ctcb name=Gordon", ctcb.getName().equals("Gordon"));
		Assert.assertTrue("ctcb age=49", ctcb.getAge() == 49);
		Assert.assertTrue("ctcb context is non null", ctcb.getApplicationContext() != null);
		Assert.assertTrue("ctcb context is context", ctcb.getApplicationContext() == root);
	}

	public void testCount() {
		Assert.assertTrue("should have 17 beans, not"+ this.applicationContext.getBeanDefinitionCount(),
			this.applicationContext.getBeanDefinitionCount() == 15);
	}

	public void testWithoutMessageSource() throws Exception {
		MockServletContext sc = new MockServletContext("", "/com/interface21/web/context/WEB-INF/web.xml");
		sc.addInitParameter(XmlWebApplicationContext.CONFIG_LOCATION_PREFIX_PARAM, "/com/interface21/web/context/WEB-INF/");
		WebApplicationContext wac = new XmlWebApplicationContext(null, "testNamespace");
		wac.setServletContext(sc);
		try {
			wac.getMessage("someMessage", null, Locale.getDefault());
			Assert.fail("Should have thrown NoSuchMessageException");
		}
		catch (NoSuchMessageException ex) {
			// expected;
		}
		String msg = wac.getMessage("someMessage", null, "default", Locale.getDefault());
		Assert.assertTrue("Default message returned", "default".equals(msg));
	}

	public void testContextNesting() {
		ITestBean father = (ITestBean) this.applicationContext.getBean("father");
		Assert.assertTrue("Bean from root context", father != null);

		ITestBean rod = (ITestBean) this.applicationContext.getBean("rod");
		Assert.assertTrue("Bean from child context", "Rod".equals(rod.getName()));
		Assert.assertTrue("Bean has external reference", rod.getSpouse() == father);

		rod = (ITestBean) this.root.getBean("rod");
		Assert.assertTrue("Bean from root context", "Roderick".equals(rod.getName()));
	}

}
