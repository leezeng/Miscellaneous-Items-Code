package com.orm.hibernate;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;
import javax.transaction.Status;
import javax.transaction.UserTransaction;

import junit.framework.TestCase;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Session;
import net.sf.hibernate.SessionFactory;
import net.sf.hibernate.Transaction;
import net.sf.hibernate.Interceptor;
import org.easymock.EasyMock;
import org.easymock.MockControl;

import com.jdbc.datasource.DataSourceUtils;
import com.transaction.InvalidTimeoutException;
import com.transaction.JtaTransactionTestSuite;
import com.transaction.PlatformTransactionManager;
import com.transaction.TransactionDefinition;
import com.transaction.TransactionStatus;
import com.transaction.jta.JtaTransactionManager;
import com.transaction.support.TransactionCallback;
import com.transaction.support.TransactionCallbackWithoutResult;
import com.transaction.support.TransactionSynchronizationManager;
import com.transaction.support.TransactionTemplate;

/**
 * @author Juergen Hoeller
 */
public class HibernateTransactionManagerTests extends TestCase {

	public void testTransactionCommit() throws SQLException, HibernateException {
		MockControl dsControl = EasyMock.controlFor(DataSource.class);
		final DataSource ds = (DataSource) dsControl.getMock();
		MockControl conControl = EasyMock.controlFor(Connection.class);
		Connection con = (Connection) conControl.getMock();
		MockControl sfControl = EasyMock.controlFor(SessionFactory.class);
		final SessionFactory sf = (SessionFactory) sfControl.getMock();
		MockControl sessionControl = EasyMock.controlFor(Session.class);
		Session session = (Session) sessionControl.getMock();
		MockControl txControl = EasyMock.controlFor(Transaction.class);
		Transaction tx = (Transaction) txControl.getMock();
		con.getTransactionIsolation();
		conControl.setReturnValue(Connection.TRANSACTION_READ_COMMITTED);
		con.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
		conControl.setVoidCallable(1);
		con.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
		conControl.setVoidCallable(1);
		con.isReadOnly();
		conControl.setReturnValue(false, 1);
		sf.openSession();
		sfControl.setReturnValue(session, 1);
		session.beginTransaction();
		sessionControl.setReturnValue(tx, 1);
		session.connection();
		sessionControl.setReturnValue(con, 4);
		session.close();
		sessionControl.setReturnValue(null, 1);
		tx.commit();
		txControl.setVoidCallable();
		dsControl.activate();
		conControl.activate();
		sfControl.activate();
		sessionControl.activate();
		txControl.activate();

		PlatformTransactionManager tm = new HibernateTransactionManager(sf, ds);
		TransactionTemplate tt = new TransactionTemplate(tm);
		tt.setIsolationLevel(TransactionDefinition.ISOLATION_SERIALIZABLE);
		final List l = new ArrayList();
		l.add("test");
		assertTrue("Hasn't thread session", !SessionFactoryUtils.getThreadObjectManager().hasThreadObject(sf));
		assertTrue("Hasn't thread connection", !DataSourceUtils.getThreadObjectManager().hasThreadObject(ds));
		assertTrue("JTA synchronizations not active", !TransactionSynchronizationManager.isActive());

		try {
			Object result = tt.execute(new TransactionCallback() {
				public Object doInTransaction(TransactionStatus status) {
					assertTrue("Has thread session", SessionFactoryUtils.getThreadObjectManager().hasThreadObject(sf));
					assertTrue("Has thread connection", DataSourceUtils.getThreadObjectManager().hasThreadObject(ds));
					HibernateTemplate ht = new HibernateTemplate(sf);
					return ht.executeFind(new HibernateCallback() {
						public Object doInHibernate(Session session) throws HibernateException {
							return l;
						}
					});
				}
			});
			assertTrue("Correct result list", result == l);
		}
		catch (RuntimeException ex) {
			fail("Should not have thrown RuntimeException");
		}

		assertTrue("Hasn't thread session", !SessionFactoryUtils.getThreadObjectManager().hasThreadObject(sf));
		assertTrue("Hasn't thread connection", !DataSourceUtils.getThreadObjectManager().hasThreadObject(ds));
		assertTrue("JTA synchronizations not active", !TransactionSynchronizationManager.isActive());
		dsControl.verify();
		conControl.verify();
		sfControl.verify();
		sessionControl.verify();
		txControl.verify();
	}

	public void testTransactionRollback() throws HibernateException, SQLException {
		MockControl conControl = EasyMock.controlFor(Connection.class);
		Connection con = (Connection) conControl.getMock();
		MockControl sfControl = EasyMock.controlFor(SessionFactory.class);
		final SessionFactory sf = (SessionFactory) sfControl.getMock();
		MockControl sessionControl = EasyMock.controlFor(Session.class);
		Session session = (Session) sessionControl.getMock();
		MockControl txControl = EasyMock.controlFor(Transaction.class);
		Transaction tx = (Transaction) txControl.getMock();
		sf.openSession();
		sfControl.setReturnValue(session, 1);
		session.beginTransaction();
		sessionControl.setReturnValue(tx, 1);
		session.close();
		sessionControl.setReturnValue(null, 1);
		tx.rollback();
		txControl.setVoidCallable(1);
		session.connection();
		sessionControl.setReturnValue(con, 1);
		con.isReadOnly();
		conControl.setReturnValue(false, 1);
		sfControl.activate();
		sessionControl.activate();
		txControl.activate();

		PlatformTransactionManager tm = new HibernateTransactionManager(sf, null);
		TransactionTemplate tt = new TransactionTemplate(tm);
		assertTrue("Hasn't thread session", !SessionFactoryUtils.getThreadObjectManager().hasThreadObject(sf));
		assertTrue("JTA synchronizations not active", !TransactionSynchronizationManager.isActive());

		try {
			tt.execute(new TransactionCallback() {
				public Object doInTransaction(TransactionStatus status) {
					assertTrue("Has thread session", SessionFactoryUtils.getThreadObjectManager().hasThreadObject(sf));
					HibernateTemplate ht = new HibernateTemplate(sf);
					return ht.executeFind(new HibernateCallback() {
						public Object doInHibernate(Session session) {
							throw new RuntimeException("application exception");
						}
					});
				}
			});
			fail("Should have thrown RuntimeException");
		}
		catch (RuntimeException ex) {
			// expected
		}

		assertTrue("Hasn't thread session", !SessionFactoryUtils.getThreadObjectManager().hasThreadObject(sf));
		assertTrue("JTA synchronizations not active", !TransactionSynchronizationManager.isActive());
		sfControl.verify();
		sessionControl.verify();
		txControl.verify();
	}

	public void testTransactionRollbackOnly() throws HibernateException, SQLException {
		MockControl conControl = EasyMock.controlFor(Connection.class);
		Connection con = (Connection) conControl.getMock();
		MockControl sfControl = EasyMock.controlFor(SessionFactory.class);
		final SessionFactory sf = (SessionFactory) sfControl.getMock();
		MockControl sessionControl = EasyMock.controlFor(Session.class);
		Session session = (Session) sessionControl.getMock();
		MockControl txControl = EasyMock.controlFor(Transaction.class);
		Transaction tx = (Transaction) txControl.getMock();
		sf.openSession();
		sfControl.setReturnValue(session, 1);
		session.beginTransaction();
		sessionControl.setReturnValue(tx, 1);
		session.flush();
		sessionControl.setVoidCallable(1);
		session.close();
		sessionControl.setReturnValue(null, 1);
		tx.rollback();
		txControl.setVoidCallable(1);
		session.connection();
		sessionControl.setReturnValue(con, 1);
		con.isReadOnly();
		conControl.setReturnValue(false, 1);
		sfControl.activate();
		sessionControl.activate();
		txControl.activate();

		PlatformTransactionManager tm = new HibernateTransactionManager(sf, null);
		TransactionTemplate tt = new TransactionTemplate(tm);
		assertTrue("Hasn't thread session", !SessionFactoryUtils.getThreadObjectManager().hasThreadObject(sf));

		try {
			tt.execute(new TransactionCallback() {
				public Object doInTransaction(TransactionStatus status) {
					assertTrue("Has thread session", SessionFactoryUtils.getThreadObjectManager().hasThreadObject(sf));
					HibernateTemplate ht = new HibernateTemplate(sf);
					ht.setFlushMode(HibernateTemplate.FLUSH_EAGER);
					ht.execute(new HibernateCallback() {
						public Object doInHibernate(Session session) {
							return null;
						}
					});
					status.setRollbackOnly();
					return null;
				}
			});
		}
		catch (RuntimeException ex) {
			fail("Should not have thrown RuntimeException");
		}

		assertTrue("Hasn't thread session", !SessionFactoryUtils.getThreadObjectManager().hasThreadObject(sf));
		sfControl.verify();
		sessionControl.verify();
		txControl.verify();
	}

	public void testNestedTransactionCommit() throws HibernateException, SQLException {
		MockControl conControl = EasyMock.controlFor(Connection.class);
		Connection con = (Connection) conControl.getMock();
		MockControl sfControl = EasyMock.controlFor(SessionFactory.class);
		final SessionFactory sf = (SessionFactory) sfControl.getMock();
		MockControl sessionControl = EasyMock.controlFor(Session.class);
		Session session = (Session) sessionControl.getMock();
		MockControl txControl = EasyMock.controlFor(Transaction.class);
		Transaction tx = (Transaction) txControl.getMock();
		sf.openSession();
		sfControl.setReturnValue(session, 1);
		session.beginTransaction();
		sessionControl.setReturnValue(tx, 1);
		session.flush();
		sessionControl.setVoidCallable(1);
		session.close();
		sessionControl.setReturnValue(null, 1);
		tx.commit();
		txControl.setVoidCallable(1);
		session.connection();
		sessionControl.setReturnValue(con, 1);
		con.isReadOnly();
		conControl.setReturnValue(false, 1);
		sfControl.activate();
		sessionControl.activate();
		txControl.activate();

		PlatformTransactionManager tm = new HibernateTransactionManager(sf, null);
		final TransactionTemplate tt = new TransactionTemplate(tm);
		final List l = new ArrayList();
		l.add("test");

		try {
			Object result = tt.execute(new TransactionCallback() {
				public Object doInTransaction(TransactionStatus status) {
					return tt.execute(new TransactionCallback() {
						public Object doInTransaction(TransactionStatus status) {
							HibernateTemplate ht = new HibernateTemplate(sf);
							ht.setFlushMode(HibernateTemplate.FLUSH_EAGER);
							return ht.executeFind(new HibernateCallback() {
								public Object doInHibernate(Session session) {
									return l;
								}
							});
						}
					});
				}
			});
			assertTrue("Correct result list", result == l);
		}
		catch (RuntimeException ex) {
			fail("Should not have thrown RuntimeException");
		}

		sfControl.verify();
		sessionControl.verify();
		txControl.verify();
	}

	public void testNestedTransactionRollback() throws HibernateException, SQLException {
		MockControl conControl = EasyMock.controlFor(Connection.class);
		Connection con = (Connection) conControl.getMock();
		MockControl sfControl = EasyMock.controlFor(SessionFactory.class);
		final SessionFactory sf = (SessionFactory) sfControl.getMock();
		MockControl sessionControl = EasyMock.controlFor(Session.class);
		Session session = (Session) sessionControl.getMock();
		MockControl txControl = EasyMock.controlFor(Transaction.class);
		Transaction tx = (Transaction) txControl.getMock();
		sf.openSession();
		sfControl.setReturnValue(session, 1);
		session.beginTransaction();
		sessionControl.setReturnValue(tx, 1);
		session.close();
		sessionControl.setReturnValue(null, 1);
		tx.rollback();
		txControl.setVoidCallable(1);
		session.connection();
		sessionControl.setReturnValue(con, 1);
		con.isReadOnly();
		conControl.setReturnValue(false, 1);
		sfControl.activate();
		sessionControl.activate();
		txControl.activate();

		PlatformTransactionManager tm = new HibernateTransactionManager(sf, null);
		final TransactionTemplate tt = new TransactionTemplate(tm);
		try {
			tt.execute(new TransactionCallback() {
				public Object doInTransaction(TransactionStatus status) {
					return tt.execute(new TransactionCallback() {
						public Object doInTransaction(TransactionStatus status) {
							HibernateTemplate ht = new HibernateTemplate(sf);
							ht.setFlushMode(HibernateTemplate.FLUSH_EAGER);
							return ht.executeFind(new HibernateCallback() {
								public Object doInHibernate(Session session) {
									throw new RuntimeException("application exception");
								}
							});
						}
					});
				}
			});
			fail("Should not thrown RuntimeException");
		}
		catch (RuntimeException ex) {
			// expected
		}
		sfControl.verify();
		sessionControl.verify();
		txControl.verify();
	}

	public void testNestedTransactionRollbackOnly() throws HibernateException, SQLException {
		MockControl conControl = EasyMock.controlFor(Connection.class);
		Connection con = (Connection) conControl.getMock();
		MockControl sfControl = EasyMock.controlFor(SessionFactory.class);
		final SessionFactory sf = (SessionFactory) sfControl.getMock();
		MockControl sessionControl = EasyMock.controlFor(Session.class);
		Session session = (Session) sessionControl.getMock();
		MockControl txControl = EasyMock.controlFor(Transaction.class);
		Transaction tx = (Transaction) txControl.getMock();
		sf.openSession();
		sfControl.setReturnValue(session, 1);
		session.beginTransaction();
		sessionControl.setReturnValue(tx, 1);
		session.close();
		sessionControl.setReturnValue(null, 1);
		tx.rollback();
		txControl.setVoidCallable(1);
		session.connection();
		sessionControl.setReturnValue(con, 1);
		con.isReadOnly();
		conControl.setReturnValue(false, 1);
		sfControl.activate();
		sessionControl.activate();
		txControl.activate();

		PlatformTransactionManager tm = new HibernateTransactionManager(sf, null);
		final TransactionTemplate tt = new TransactionTemplate(tm);
		final List l = new ArrayList();
		l.add("test");

		try {
			tt.execute(new TransactionCallback() {
				public Object doInTransaction(TransactionStatus status) {
					return tt.execute(new TransactionCallback() {
						public Object doInTransaction(TransactionStatus status) {
							HibernateTemplate ht = new HibernateTemplate(sf);
							ht.execute(new HibernateCallback() {
								public Object doInHibernate(Session session) {
									return l;
								}
							});
							status.setRollbackOnly();
							return null;
						}
					});
				}
			});
		}
		catch (RuntimeException ex) {
			fail("Should not have thrown RuntimeException");
		}

		sfControl.verify();
		sessionControl.verify();
		txControl.verify();
	}

	public void testInvalidTimeout() throws HibernateException {
		MockControl sfControl = EasyMock.controlFor(SessionFactory.class);
		final SessionFactory sf = (SessionFactory) sfControl.getMock();
		MockControl sessionControl = EasyMock.controlFor(Session.class);
		Session session = (Session) sessionControl.getMock();
		sf.openSession();
		sfControl.setReturnValue(session, 1);
		sfControl.activate();
		sessionControl.activate();

		PlatformTransactionManager tm = new HibernateTransactionManager(sf, null);
		TransactionTemplate tt = new TransactionTemplate(tm);
		tt.setTimeout(10);
		try {
			tt.execute(new TransactionCallbackWithoutResult() {
				protected void doInTransactionWithoutResult(TransactionStatus status) {
				}
			});
			fail("Should have thrown InvalidTimeoutException");
		}
		catch (InvalidTimeoutException ex) {
			// expected
		}

		sfControl.verify();
		sessionControl.verify();
	}

	public void testJtaTransactionCommit() throws Exception {
		MockControl utControl = EasyMock.controlFor(UserTransaction.class);
		UserTransaction ut = (UserTransaction) utControl.getMock();
		ut.getStatus();
		utControl.setReturnValue(Status.STATUS_NO_TRANSACTION, 1);
		ut.begin();
		utControl.setVoidCallable(1);
		ut.commit();
		utControl.setVoidCallable(1);
		utControl.activate();

		MockControl sfControl = EasyMock.controlFor(SessionFactory.class);
		final SessionFactory sf = (SessionFactory) sfControl.getMock();
		final MockControl sessionControl = EasyMock.controlFor(Session.class);
		final Session session = (Session) sessionControl.getMock();
		sf.openSession();
		sfControl.setReturnValue(session, 1);
		session.flush();
		sessionControl.setVoidCallable(1);
		sfControl.activate();
		sessionControl.activate();

		TransactionTemplate tt = JtaTransactionTestSuite.getTransactionTemplateForJta(JtaTransactionManager.DEFAULT_USER_TRANSACTION_NAME, ut);
		final List l = new ArrayList();
		l.add("test");
		assertTrue("JTA synchronizations not active", !TransactionSynchronizationManager.isActive());

		try {
			Object result = tt.execute(new TransactionCallback() {
				public Object doInTransaction(TransactionStatus status) {
					assertTrue("JTA synchronizations active", TransactionSynchronizationManager.isActive());
					HibernateTemplate ht = new HibernateTemplate(sf);
					List htl = ht.executeFind(new HibernateCallback() {
						public Object doInHibernate(Session session) {
							return l;
						}
					});
					sessionControl.verify();
					sessionControl.reset();
					try {
						session.close();
					}
					catch (HibernateException e) {
					}
					sessionControl.setReturnValue(null);
					sessionControl.activate();
					return htl;
				}
			});
			assertTrue("Correct result list", result == l);
		}
		catch (RuntimeException ex) {
			fail("Should not have thrown RuntimeException: " + ex.getMessage());
		}

		assertTrue("JTA synchronizations not active", !TransactionSynchronizationManager.isActive());
		utControl.verify();
		sfControl.verify();
		sessionControl.verify();
	}

	public void testJtaTransactionRollback() throws Exception {
		MockControl utControl = EasyMock.controlFor(UserTransaction.class);
		UserTransaction ut = (UserTransaction) utControl.getMock();
		ut.getStatus();
		utControl.setReturnValue(Status.STATUS_NO_TRANSACTION, 1);
		ut.begin();
		utControl.setVoidCallable(1);
		ut.rollback();
		utControl.setVoidCallable(1);
		utControl.activate();

		MockControl sfControl = EasyMock.controlFor(SessionFactory.class);
		final SessionFactory sf = (SessionFactory) sfControl.getMock();
		final MockControl sessionControl = EasyMock.controlFor(Session.class);
		final Session session = (Session) sessionControl.getMock();
		sf.openSession();
		sfControl.setReturnValue(session, 1);
		session.flush();
		sessionControl.setVoidCallable(1);
		sfControl.activate();
		sessionControl.activate();

		TransactionTemplate tt = JtaTransactionTestSuite.getTransactionTemplateForJta(JtaTransactionManager.DEFAULT_USER_TRANSACTION_NAME, ut);
		final List l = new ArrayList();
		l.add("test");
		assertTrue("JTA synchronizations not active", !TransactionSynchronizationManager.isActive());

		try {
			Object result = tt.execute(new TransactionCallback() {
				public Object doInTransaction(TransactionStatus status) {
					assertTrue("JTA synchronizations active", TransactionSynchronizationManager.isActive());
					HibernateTemplate ht = new HibernateTemplate(sf);
					List htl = ht.executeFind(new HibernateCallback() {
						public Object doInHibernate(Session session) {
							return l;
						}
					});
					status.setRollbackOnly();
					sessionControl.verify();
					sessionControl.reset();
					try {
						session.close();
					}
					catch (HibernateException e) {
					}
					sessionControl.setReturnValue(null);
					sessionControl.activate();
					return htl;
				}
			});
			assertTrue("Correct result list", result == l);
		}
		catch (RuntimeException ex) {
			fail("Should not have thrown RuntimeException: " + ex.getMessage());
		}

		assertTrue("JTA synchronizations not active", !TransactionSynchronizationManager.isActive());
		utControl.verify();
		sfControl.verify();
		sessionControl.verify();
	}

	public void testTransactionCommitWithPrebound() throws HibernateException, SQLException {
		MockControl dsControl = EasyMock.controlFor(DataSource.class);
		final DataSource ds = (DataSource) dsControl.getMock();
		MockControl conControl = EasyMock.controlFor(Connection.class);
		Connection con = (Connection) conControl.getMock();
		MockControl sfControl = EasyMock.controlFor(SessionFactory.class);
		final SessionFactory sf = (SessionFactory) sfControl.getMock();
		MockControl sessionControl = EasyMock.controlFor(Session.class);
		Session session = (Session) sessionControl.getMock();
		MockControl txControl = EasyMock.controlFor(Transaction.class);
		Transaction tx = (Transaction) txControl.getMock();
		session.beginTransaction();
		sessionControl.setReturnValue(tx, 1);
		session.connection();
		sessionControl.setReturnValue(con, 4);
		con.getTransactionIsolation();
		conControl.setReturnValue(Connection.TRANSACTION_READ_COMMITTED);
		con.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
		conControl.setVoidCallable(1);
		con.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
		conControl.setVoidCallable(1);
		tx.commit();
		txControl.setVoidCallable();
		con.isReadOnly();
		conControl.setReturnValue(false, 1);
		dsControl.activate();
		conControl.activate();
		sfControl.activate();
		sessionControl.activate();
		txControl.activate();

		PlatformTransactionManager tm = new HibernateTransactionManager(sf, ds);
		TransactionTemplate tt = new TransactionTemplate(tm);
		tt.setIsolationLevel(TransactionDefinition.ISOLATION_SERIALIZABLE);
		final List l = new ArrayList();
		l.add("test");
		assertTrue("Hasn't thread connection", !DataSourceUtils.getThreadObjectManager().hasThreadObject(ds));
		assertTrue("JTA synchronizations not active", !TransactionSynchronizationManager.isActive());
		SessionFactoryUtils.getThreadObjectManager().bindThreadObject(sf, new SessionHolder(session));
		assertTrue("Has thread session", SessionFactoryUtils.getThreadObjectManager().hasThreadObject(sf));

		try {
			Object result = tt.execute(new TransactionCallback() {
				public Object doInTransaction(TransactionStatus status) {
					assertTrue("Has thread session", SessionFactoryUtils.getThreadObjectManager().hasThreadObject(sf));
					assertTrue("Has thread connection", DataSourceUtils.getThreadObjectManager().hasThreadObject(ds));
					HibernateTemplate ht = new HibernateTemplate(sf);
					return ht.executeFind(new HibernateCallback() {
						public Object doInHibernate(Session session) throws HibernateException {
							return l;
						}
					});
				}
			});
			assertTrue("Correct result list", result == l);
		}
		catch (RuntimeException ex) {
			fail("Should not have thrown RuntimeException");
		}

		assertTrue("Has thread session", SessionFactoryUtils.getThreadObjectManager().hasThreadObject(sf));
		SessionFactoryUtils.getThreadObjectManager().removeThreadObject(sf);
		assertTrue("Hasn't thread connection", !DataSourceUtils.getThreadObjectManager().hasThreadObject(ds));
		assertTrue("JTA synchronizations not active", !TransactionSynchronizationManager.isActive());
		dsControl.verify();
		conControl.verify();
		sfControl.verify();
		sessionControl.verify();
		txControl.verify();
	}

	public void testTransactionCommitWithEntityInterceptor() throws HibernateException, SQLException {
		MockControl interceptorControl = EasyMock.controlFor(net.sf.hibernate.Interceptor.class);
		Interceptor entityInterceptor = (Interceptor) interceptorControl.getMock();
		interceptorControl.activate();
		MockControl conControl = EasyMock.controlFor(Connection.class);
		Connection con = (Connection) conControl.getMock();
		MockControl sfControl = EasyMock.controlFor(SessionFactory.class);
		final SessionFactory sf = (SessionFactory) sfControl.getMock();
		MockControl sessionControl = EasyMock.controlFor(Session.class);
		Session session = (Session) sessionControl.getMock();
		MockControl txControl = EasyMock.controlFor(Transaction.class);
		Transaction tx = (Transaction) txControl.getMock();
		sf.openSession(entityInterceptor);
		sfControl.setReturnValue(session, 1);
		session.beginTransaction();
		sessionControl.setReturnValue(tx, 1);
		session.close();
		sessionControl.setReturnValue(null, 1);
		tx.commit();
		txControl.setVoidCallable();
		session.connection();
		sessionControl.setReturnValue(con, 1);
		con.isReadOnly();
		conControl.setReturnValue(false, 1);
		sfControl.activate();
		sessionControl.activate();
		txControl.activate();

		HibernateTransactionManager tm = new HibernateTransactionManager(sf, null);
		tm.setEntityInterceptor(entityInterceptor);
		TransactionTemplate tt = new TransactionTemplate(tm);
		final List l = new ArrayList();
		l.add("test");
		assertTrue("Hasn't thread session", !SessionFactoryUtils.getThreadObjectManager().hasThreadObject(sf));
		assertTrue("JTA synchronizations not active", !TransactionSynchronizationManager.isActive());

		try {
			Object result = tt.execute(new TransactionCallback() {
				public Object doInTransaction(TransactionStatus status) {
					assertTrue("Has thread session", SessionFactoryUtils.getThreadObjectManager().hasThreadObject(sf));
					HibernateTemplate ht = new HibernateTemplate(sf);
					return ht.executeFind(new HibernateCallback() {
						public Object doInHibernate(Session session) throws HibernateException {
							return l;
						}
					});
				}
			});
			assertTrue("Correct result list", result == l);
		}
		catch (RuntimeException ex) {
			fail("Should not have thrown RuntimeException");
		}

		assertTrue("Hasn't thread session", !SessionFactoryUtils.getThreadObjectManager().hasThreadObject(sf));
		assertTrue("JTA synchronizations not active", !TransactionSynchronizationManager.isActive());
		sfControl.verify();
		sessionControl.verify();
		txControl.verify();
	}

}
