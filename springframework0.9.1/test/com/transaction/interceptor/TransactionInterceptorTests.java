/*
 * The Spring Framework is published under the terms
 * of the Apache Software License.
 */

package com.transaction.interceptor;

import java.lang.reflect.Method;

import junit.framework.TestCase;
import org.aopalliance.intercept.AttributeRegistry;
import org.easymock.EasyMock;
import org.easymock.MockControl;

import com.aop.framework.ProxyFactory;
import com.beans.ITestBean;
import com.beans.TestBean;
import com.transaction.CannotCreateTransactionException;
import com.transaction.PlatformTransactionManager;
import com.transaction.TransactionStatus;
import com.transaction.UnexpectedRollbackException;
import com.transaction.TransactionSystemException;

/**
 * Mock object based tests for TransactionInterceptor.
 * True unit test in that it tests how the TransactionInterceptor uses
 * the PlatformTransactionManager helper, rather than indirectly
 * testing the helper implementation.
 * @author Rod Johnson
 * @since 16-Mar-2003
 * @version $Revision: 1.5 $
 */
public class TransactionInterceptorTests extends TestCase {

	public void testNoTransaction() throws Exception {
		// Could do this
		/*
		Method m = Object.class.getMethod("hashCode", null);
		Object proxy = new Object();
		MethodInvocationImpl mi = new MethodInvocationImpl(proxy, null, m.getDeclaringClass(), //?
		m, null,
		interceptors, // could customize here
		r);
		*/

		//TransactionAttribute txatt =

		MockControl arControl = EasyMock.controlFor(AttributeRegistry.class);
		AttributeRegistry r = (AttributeRegistry) arControl.getMock();
		r.getAttributes(ITestBean.class.getMethod("getName", null));
		arControl.setReturnValue(null);
		arControl.activate();

		MockControl ptxControl = EasyMock.controlFor(PlatformTransactionManager.class);
		PlatformTransactionManager ptm = (PlatformTransactionManager) ptxControl.getMock();

		// expect no calls
		ptxControl.activate();

		TestBean tb = new TestBean();
		TransactionInterceptor ti = new TransactionInterceptor();
		ti.setTransactionManager(ptm);
		assertTrue(ptm == ti.getTransactionManager());

		ProxyFactory pf = new ProxyFactory(tb);
		pf.setAttributeRegistry(r);
		pf.addInterceptor(0, ti);
		ITestBean itb = (ITestBean) pf.getProxy();

		// verification!?
		itb.getName();

		arControl.verify();
		ptxControl.verify();

	}

	/**
	 * Check that a transaction is created and committed
	 * @throws java.lang.Exception
	 */
	public void testTransactionShouldSucceed() throws Exception {
		TransactionAttribute txatt = new DefaultTransactionAttribute();

		Method m = ITestBean.class.getMethod("getName", null);
		MockControl arControl = EasyMock.controlFor(AttributeRegistry.class);
		AttributeRegistry r = (AttributeRegistry) arControl.getMock();
		r.getAttributes(m);
		arControl.setReturnValue(new Object[] { txatt }, 1);
		arControl.activate();

		TransactionStatus status = new TransactionStatus(null, false);
		MockControl ptxControl = EasyMock.controlFor(PlatformTransactionManager.class);
		PlatformTransactionManager ptm = (PlatformTransactionManager) ptxControl.getMock();
		// Expect a transaction
		ptm.getTransaction(txatt);
		ptxControl.setReturnValue(status, 1);
		ptm.commit(status);
		ptxControl.setVoidCallable(1);
		ptxControl.activate();

		TestBean tb = new TestBean();
		TransactionInterceptor ti = new TransactionInterceptor();
		ti.setTransactionManager(ptm);

		ProxyFactory pf = new ProxyFactory(tb);
		pf.setAttributeRegistry(r);
		pf.addInterceptor(0, ti);
		ITestBean itb = (ITestBean) pf.getProxy();

		// verification!?
		itb.getName();

		arControl.verify();
		ptxControl.verify();
	}


	/**
	 * Test that TransactionControl.setRollbackOnly works
	 * @throws java.lang.Exception
	 */
	public void testProgrammaticRollback() throws Exception {
		TransactionAttribute txatt = new DefaultTransactionAttribute();

		Method m = ITestBean.class.getMethod("getName", null);
		MockControl arControl = EasyMock.controlFor(AttributeRegistry.class);
		AttributeRegistry r = (AttributeRegistry) arControl.getMock();
		r.getAttributes(m);
		arControl.setReturnValue(new Object[] { txatt}, 1);
		arControl.activate();

		TransactionStatus status = new TransactionStatus(null, false);
		MockControl ptxControl = EasyMock.controlFor(PlatformTransactionManager.class);
		PlatformTransactionManager ptm = (PlatformTransactionManager) ptxControl.getMock();

		ptm.getTransaction(txatt);
		ptxControl.setReturnValue(status, 1);
		ptm.commit(status);
		ptxControl.setVoidCallable(1);
		ptxControl.activate();

		final String name = "jenny";
		TestBean tb = new TestBean() {
			public String getName() {
				TransactionStatus txStatus = TransactionInterceptor.currentTransactionStatus();
				txStatus.setRollbackOnly();
				return name;
			}
		};
		TransactionInterceptor ti = new TransactionInterceptor();
		ti.setTransactionManager(ptm);

		ProxyFactory pf = new ProxyFactory(tb);
		// Need to use programmatic rollback
		pf.setExposeInvocation(true);
		pf.setAttributeRegistry(r);
		pf.addInterceptor(0, ti);
		ITestBean itb = (ITestBean) pf.getProxy();

		// verification!?
		assertTrue(name.equals(itb.getName()));

		arControl.verify();
		ptxControl.verify();
	}

	public void testRollbackOnCheckedException() throws Throwable {
		testRollbackOnException(new Exception(), true, false);
	}

	public void testNoRollbackOnCheckedException() throws Throwable {
		testRollbackOnException(new Exception(), false, false);
	}

	public void testRollbackOnUncheckedException() throws Throwable {
		testRollbackOnException(new RuntimeException(), true, false);
	}

	public void testNoRollbackOnUncheckedException() throws Throwable {
		testRollbackOnException(new RuntimeException(), false, false);
	}

	public void testRollbackOnCheckedExceptionWithRollbackException() throws Throwable {
		testRollbackOnException(new Exception(), true, true);
	}

	public void testNoRollbackOnCheckedExceptionWithRollbackException() throws Throwable {
		testRollbackOnException(new Exception(), false, true);
	}

	public void testRollbackOnUncheckedExceptionWithRollbackException() throws Throwable {
		testRollbackOnException(new RuntimeException(), true, true);
	}

	public void testNoRollbackOnUncheckedExceptionWithRollbackException() throws Throwable {
		testRollbackOnException(new RuntimeException(), false, true);
	}

	/**
	 * Check that the given exception thrown by the target can
	 * produce the desired behaviour with the appropriate transaction
	 * attribute
	 * @param ex exception to be thrown by the target
	 * @param shouldRollback whether this should cause a transaction rollback
	 * @throws java.lang.Throwable
	 */
	private void testRollbackOnException(final Exception ex, final boolean shouldRollback, boolean rollbackException)
	    throws Throwable {

		TransactionAttribute txatt = new DefaultTransactionAttribute() {
			public boolean rollbackOn(Throwable t) {
				assertTrue(t == ex);
				return shouldRollback;
			}
		};

		Method m = ITestBean.class.getMethod("exceptional", new Class[] { Throwable.class });
		MockControl arControl = EasyMock.controlFor(AttributeRegistry.class);
		AttributeRegistry r = (AttributeRegistry) arControl.getMock();
		r.getAttributes(m);
		arControl.setReturnValue(new Object[] { txatt }, 1);
		arControl.activate();

		TransactionStatus status = new TransactionStatus(null, false);
		MockControl ptxControl = EasyMock.controlFor(PlatformTransactionManager.class);
		PlatformTransactionManager ptm = (PlatformTransactionManager) ptxControl.getMock();
		// Gets additional call(s) from TransactionControl

		ptm.getTransaction(txatt);
		ptxControl.setReturnValue(status, 1);

		if (shouldRollback) {
			ptm.rollback(status);
		}
		else {
			ptm.commit(status);
		}
		TransactionSystemException tex = new TransactionSystemException("system exception");
		if (rollbackException) {
			ptxControl.setThrowable(tex, 1);
		}
		else {
			ptxControl.setVoidCallable(1);
		}
		ptxControl.activate();

		TestBean tb = new TestBean();
		TransactionInterceptor ti = new TransactionInterceptor();
		ti.setTransactionManager(ptm);

		ProxyFactory pf = new ProxyFactory(tb);
		pf.setAttributeRegistry(r);
		pf.addInterceptor(0, ti);
		ITestBean itb = (ITestBean) pf.getProxy();

		try {
			itb.exceptional(ex);
			fail("Should have thrown exception");
		}
		catch (Throwable t) {
			if (rollbackException) {
				assertTrue("Caught " + t + " expecting " + tex, t == tex);
			}
			else {
				assertTrue("Caught " + t + " expecting " + ex, t == ex);
			}
		}

		arControl.verify();
		ptxControl.verify();
	}

	/**
	 * Simulate a transaction infrastructure failure.
	 * Shouldn't invoke target method.
	 * @throws java.lang.Exception
	 */
	public void testCannotCreateTransaction() throws Exception {
		TransactionAttribute txatt = new DefaultTransactionAttribute();

		Method m = ITestBean.class.getMethod("getName", null);
		MockControl arControl = EasyMock.controlFor(AttributeRegistry.class);
		AttributeRegistry r = (AttributeRegistry) arControl.getMock();
		r.getAttributes(m);
		arControl.setReturnValue(new Object[] { txatt }, 1);
		arControl.activate();

		MockControl ptxControl = EasyMock.controlFor(PlatformTransactionManager.class);
		PlatformTransactionManager ptm = (PlatformTransactionManager) ptxControl.getMock();
		// Expect a transaction
		ptm.getTransaction(txatt);
		CannotCreateTransactionException ex = new CannotCreateTransactionException("foobar", null);
		ptxControl.setThrowable(ex);
		ptxControl.activate();

		TestBean tb = new TestBean() {
			public String getName() {
				throw new UnsupportedOperationException("Shouldn't have invoked target method when couldn't create transaction for transactional method");
			}
		};
		TransactionInterceptor ti = new TransactionInterceptor();
		ti.setTransactionManager(ptm);

		ProxyFactory pf = new ProxyFactory(tb);
		pf.setAttributeRegistry(r);
		pf.addInterceptor(0, ti);
		ITestBean itb = (ITestBean) pf.getProxy();

		try {
			itb.getName();
			fail("Shouldn't have invoked method");
		}
		catch (CannotCreateTransactionException thrown) {
			assertTrue(thrown == ex);
		}

		arControl.verify();
		ptxControl.verify();
	}

	/**
	 * Simulate failure of the underlying transaction infrastructure
	 * to commit.
	 * Check that the target method was invoked, but that the
	 * transaction infrastructure exception was thrown to the
	 * client
	 * @throws java.lang.Exception
	 */
	public void testCannotCommitTransaction() throws Exception {
		TransactionAttribute txatt = new DefaultTransactionAttribute();

		Method m = ITestBean.class.getMethod("setName", new Class[] { String.class} );
		MockControl arControl = EasyMock.controlFor(AttributeRegistry.class);
		AttributeRegistry r = (AttributeRegistry) arControl.getMock();
		r.getAttributes(m);
		arControl.setReturnValue(new Object[] { txatt }, 1);
		Method m2 = ITestBean.class.getMethod("getName", null);
		r.getAttributes(m2);
		arControl.setReturnValue(null);
		arControl.activate();

		MockControl ptxControl = EasyMock.controlFor(PlatformTransactionManager.class);
		PlatformTransactionManager ptm = (PlatformTransactionManager) ptxControl.getMock();

		TransactionStatus status = new TransactionStatus(null, false);
		ptm.getTransaction(txatt);
		ptxControl.setReturnValue(status);
		UnexpectedRollbackException ex = new UnexpectedRollbackException("foobar", null);
		ptm.commit(status);
		ptxControl.setThrowable(ex);
		ptxControl.activate();

		TestBean tb = new TestBean();
		TransactionInterceptor ti = new TransactionInterceptor();
		ti.setTransactionManager(ptm);

		ProxyFactory pf = new ProxyFactory(tb);
		pf.setAttributeRegistry(r);
		pf.addInterceptor(0, ti);
		ITestBean itb = (ITestBean) pf.getProxy();

		String name = "new name";
		try {
			itb.setName(name);
			fail("Shouldn't have succeeded");
		}
		catch (UnexpectedRollbackException thrown) {
			assertTrue(thrown == ex);
		}

		// Should have invoked target and changed name
		assertTrue(itb.getName() == name);
		arControl.verify();
		ptxControl.verify();
	}

}
