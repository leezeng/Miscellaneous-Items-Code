package com.context.support;

import java.util.HashMap;
import java.util.Map;
import java.util.Locale;

import com.beans.TestBean;
import junit.framework.Test;
import junit.framework.TestSuite;

import com.beans.MutablePropertyValues;
import com.beans.factory.LBIInit;
import com.context.AbstractApplicationContextTests;
import com.context.ApplicationContext;
import com.context.ACATest;
import com.context.BeanThatListens;

/**
 * Classname doesn't match XXXXTestSuite pattern, so as to avoid
 * being invoked by Ant JUnit run, as it's abstract
 * @author Rod Johnson
 * @version $Revision: 1.6 $
 */
public class StaticApplicationContextTestSuite extends AbstractApplicationContextTests {

	protected StaticApplicationContext sac;

	public StaticApplicationContextTestSuite() throws Exception {
	}

	/** Run for each test */
	protected ApplicationContext createContext() throws Exception {
		StaticApplicationContext parent = new StaticApplicationContext();
		parent.addListener(parentListener) ;
		Map m = new HashMap();
		m.put("name", "Roderick");
		parent.registerPrototype("rod", TestBean.class, new MutablePropertyValues(m));
		m.put("name", "Albert");
		parent.registerPrototype("father", TestBean.class, new MutablePropertyValues(m));
		parent.rebuild();

		StaticMessageSource parentMessageSource = (StaticMessageSource) parent.getBean("messageSource");
		parentMessageSource.addMessage("code1", Locale.getDefault(), "message1");

		this.sac = new StaticApplicationContext(parent);
		sac.addListener(listener);
		sac.registerSingleton("beanThatListens", BeanThatListens.class, new MutablePropertyValues());
		sac.registerSingleton("aca", ACATest.class, new MutablePropertyValues());
		sac.registerPrototype("aca-prototype", ACATest.class, new MutablePropertyValues());
		LBIInit.createTestBeans(sac.defaultBeanFactory);
		sac.rebuild();

		StaticMessageSource sacMessageSource = (StaticMessageSource) sac.getBean("messageSource");
		sacMessageSource.addMessage("code2", Locale.getDefault(), "message2");

		return sac;
	}

	/** Overridden */
	public void testCount() throws Exception {
		assertCount(16);
	}

	protected void tearDown() {
	}

	public static void main(String[] args) {
		junit.textui.TestRunner.run(suite());
		//	junit.swingui.TestRunner.main(new String[] {PrototypeFactoryTests.class.getName() } );
	}

	public static Test suite() {
		return new TestSuite(StaticApplicationContextTestSuite.class);
	}


}
