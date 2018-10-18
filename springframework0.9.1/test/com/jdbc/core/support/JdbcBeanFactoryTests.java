
package com.jdbc.core.support;

import com.beans.TestBean;
import com.jdbc.core.MockConnectionFactory;
import com.mockobjects.sql.MockConnection;
import junit.framework.TestCase;
import org.easymock.EasyMock;
import org.easymock.MockControl;

import javax.sql.DataSource;

/**
 * @author Rod Johnson
 */
public class JdbcBeanFactoryTests extends TestCase {

	/**
	 * Constructor for JdbcBeanFactoryTest.
	 * @param arg0
	 */
	public JdbcBeanFactoryTests(String arg0) {
		super(arg0);
	}
	
	public void testValid() throws Exception {
		String sql = "SELECT NAME AS NAME, PROPERTY AS PROPERTY, VALUE AS VALUE FROM T";
		MockControl dsControl = EasyMock.controlFor(DataSource.class);
		DataSource ds = (DataSource) dsControl.getMock();

		String[][] results = {
			{ "one", "class", "TestBean" },
			{ "one", "age", "53" },
		};
	
		MockConnection con = MockConnectionFactory.statement(sql, results, true, null, null);
		con.setExpectedCloseCalls(2);

		ds.getConnection();
		dsControl.setReturnValue(con);
		dsControl.activate();
		
		JdbcBeanFactory bf = new JdbcBeanFactory(ds, sql);
		assertTrue(bf.getBeanDefinitionCount() == 1);
		TestBean tb = (TestBean) bf.getBean("one");
		assertTrue(tb.getAge() == 53);
	}
	

}
