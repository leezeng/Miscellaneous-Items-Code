package com.beans.factory;

import com.beans.BeansException;
import com.beans.MutablePropertyValues;
import com.beans.PropertyValue;
import com.beans.factory.support.ListableBeanFactoryImpl;

import java.util.HashMap;
import java.util.Map;


public class LBIInit {
	
	/** Create beans necessary to run tests
	 */
	public static void createTestBeans(ListableBeanFactoryImpl lbf) throws BeansException {
		Map m = new HashMap();
		// Rod is a singleton
		m.put("rod.class", "TestBean");
		m.put("rod.name", "Rod");
		m.put("rod.age", "31");
		
		m.put("roderick.parent","rod");
		m .put("roderick.name", "Roderick");
		
		// Kerry is a singleton
		m.put("kerry.class", "TestBean");
		m.put("kerry.name", "Kerry");
		m.put("kerry.age", "34");
		m.put("kerry.spouse(ref)", "rod");
		
		// Kathy is a type
		m.put("kathy.class", "TestBean");
		m.put("kathy.(singleton)", "false");
		
		
		m.put("typeMismatch.class", "TestBean");
		m.put("typeMismatch.name", "typeMismatch");
		m.put("typeMismatch.age", "34x");
		m.put("typeMismatch.spouse(ref)", "rod");
		m.put("typeMismatch.(singleton)","false");
		
		m.put("validEmpty.class", "TestBean");
		
		m.put("listenerVeto.class", "TestBean");
		
		m.put("typeMismatch.name", "typeMismatch");
		m.put("typeMismatch.age", "34x");
		m.put("typeMismatch.spouse(ref)", "rod");
		
		m.put("singletonFactory.class", "DummyFactory");
		m.put("singletonFactory.singleton", "true");
		
		m.put("prototypeFactory.class", "DummyFactory");
		m.put("prototypeFactory.singleton", "false");
		
		m.put("factoryPassThrough.class", "DummyFactory");
		m.put("factoryPassThrough.singleton", "true");
		MutablePropertyValues pvs = new MutablePropertyValues();
		pvs.addPropertyValue(new PropertyValue("name", "passThrough"));
		m.put("factoryPassThrough.propertyValues", pvs);
		
		m.put("mustBeInitialized.class", "MustBeInitialized");
		
		m.put("lifecycle.class", "LifecycleBean");
		
		lbf.registerBeanDefinitions(m, null);
	}

}

