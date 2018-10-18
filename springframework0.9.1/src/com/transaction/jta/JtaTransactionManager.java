package com.transaction.jta;

import javax.naming.NamingException;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.Status;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

import com.jndi.JndiTemplate;
import com.transaction.CannotCreateTransactionException;
import com.transaction.HeuristicCompletionException;
import com.transaction.InvalidIsolationException;
import com.transaction.NestedTransactionNotPermittedException;
import com.transaction.NoTransactionException;
import com.transaction.TransactionDefinition;
import com.transaction.TransactionStatus;
import com.transaction.TransactionSystemException;
import com.transaction.UnexpectedRollbackException;
import com.transaction.InvalidTimeoutException;
import com.jdbc.datasource.DataSourceTransactionManager;
import com.transaction.support.AbstractPlatformTransactionManager;
import com.orm.hibernate.HibernateTransactionManager;

/**
 * PlatformTransactionManager implementation for JTA.
 *
 * <p>This transaction manager is appropriate for handling distributed transactions,
 * i.e. transactions that span multiple resources, and for managing transactions
 * on a J2EE Connector (e.g. a persistence toolkit registered as JCA Connector).
 * For a single JDBC DataSource, DataSourceTransactionManager is perfectly sufficient,
 * and for accessing a single resource with Hibernate (including transactional cache),
 * HibernateTransactionManager is appropriate.
 *
 * <p>Transaction synchronization is active by default, as it will typically be
 * leveraged for transactional cache handling e.g. with Hibernate. Note that such
 * synchronization will only work when JtaTransactionManager actually drives the JTA
 * transactions. If taking part in existing transactions, e.g. in an EJB environment,
 * synchronization needs to be turned off to avoid dangling resource holders that
 * wait for afterTransactionCompletion callbacks.
 *
 * <p>Set "allowNonTransactionalExecution" to be able to fall back to
 * non-transactional execution if JTA isn't available in the container.
 * This can be handy for demo-ing applications e.g. on Tomcat.
 *
 * <p>Note: This implementation does not handle isolation levels. This needs
 * to be done by server-specific subclasses, overriding applyIsolationLevel.
 * Note that DataSourceTransactionManager and HibernateTransactionManager do
 * support custom isolation levels.
 *
 * @author Juergen Hoeller
 * @since 24.03.2003
 * @see #setAllowNonTransactionalExecution
 * @see #setTransactionSynchronization
 * @see #applyIsolationLevel
 * @see DataSourceTransactionManager
 * @see HibernateTransactionManager
 */
public class JtaTransactionManager extends AbstractPlatformTransactionManager {

	public static final String DEFAULT_USER_TRANSACTION_NAME = "java:comp/UserTransaction";

	private JndiTemplate jndiTemplate = new JndiTemplate();

	private String userTransactionName;

	/**
	 * Create a new JtaTransactionManager instance,
	 * with transaction synchronization activated by default.
	 * <p>Turn off transaction synchronization when using this manager
	 * with existing transactions like in an EJB environment. Keep it
	 * active when this manager actually drives the JTA transactions,
	 * to enable e.g. proper Hibernate cache synchronization callbacks.
	 * @see #setTransactionSynchronization
	 */
	public JtaTransactionManager() {
		setTransactionSynchronization(true);
	}

	/**
	 * Set the JndiTemplate to use for JNDI lookup.
	 * A default one is used if not set.
	 */
	public final void setJndiTemplate(JndiTemplate jndiTemplate) {
		if (this.jndiTemplate == null) {
			throw new IllegalArgumentException("jndiTemplate must not be null");
		}
		this.jndiTemplate = jndiTemplate;
	}

	/**
	 * Set the JNDI name of the UserTransaction.
	 * The default one is used if not set.
	 * @see #DEFAULT_USER_TRANSACTION_NAME
	 */
	public void setUserTransactionName(String userTransactionName) {
		this.userTransactionName = userTransactionName;
	}

	protected Object doGetTransaction() {
		try {
			return (UserTransaction) this.jndiTemplate.lookup(
				this.userTransactionName != null ? this.userTransactionName : DEFAULT_USER_TRANSACTION_NAME);
		}
		catch (NamingException ex) {
			throw new CannotCreateTransactionException("JTA is not available", ex);
		}
	}

	protected boolean isExistingTransaction(Object transaction) {
		try {
			return (((UserTransaction) transaction).getStatus() != Status.STATUS_NO_TRANSACTION);
		}
		catch (SystemException ex) {
			throw new TransactionSystemException("JTA failure on getStatus", ex);
		}
	}

	protected void doBegin(Object transaction, TransactionDefinition definition) {
		logger.debug("Beginning JTA transaction");
		UserTransaction ut = (UserTransaction) transaction;
		applyIsolationLevel(ut, definition.getIsolationLevel());
		if (definition.isReadOnly()) {
			logger.warn("JtaTransactionManager does not support read-only transactions: ignoring 'readOnly' hint");
		}
		try {
			if (definition.getTimeout() < TransactionDefinition.TIMEOUT_DEFAULT) {
				throw new InvalidTimeoutException("Invalid transaction timeout", definition.getTimeout());
			}
			else if (definition.getTimeout() > TransactionDefinition.TIMEOUT_DEFAULT) {
				ut.setTransactionTimeout(definition.getTimeout());
			}
			ut.begin();
		}
		catch (NotSupportedException ex) {
			// assume "nested transactions not supported"
			throw new NestedTransactionNotPermittedException(
				"JTA implementation does not support nested transactions",
				ex);
		}
		catch (UnsupportedOperationException ex) {
			// assume "nested transactions not supported"
			throw new NestedTransactionNotPermittedException(
				"JTA implementation does not support nested transactions",
				ex);
		}
		catch (SystemException ex) {
			throw new TransactionSystemException("JTA failure on begin", ex);
		}
	}

	/**
	 * Initialize the given UserTransaction with the given isolation level.
	 * <p>This standard JTA implementation simply ignores the isolation level.
	 * To be overridden by server-specific subclasses that actually handle
	 * the isolation level.
	 * @param ut UserTransaction instance representing the JTA transaction
	 * @param isolationLevel the isolation level to set
	 */
	protected void applyIsolationLevel(UserTransaction ut, int isolationLevel) {
		if (isolationLevel != TransactionDefinition.ISOLATION_DEFAULT) {
			throw new InvalidIsolationException("JtaTransactionManager does not support custom isolation levels");
		}
	}

	protected void doCommit(TransactionStatus status) {
		logger.debug("Committing JTA transaction");
		try {
			((UserTransaction) status.getTransaction()).commit();
		}
		catch (RollbackException ex) {
			throw new UnexpectedRollbackException("JTA transaction rolled back", ex);
		}
		catch (HeuristicMixedException ex) {
			throw new HeuristicCompletionException(HeuristicCompletionException.STATE_MIXED, ex);
		}
		catch (HeuristicRollbackException ex) {
			throw new HeuristicCompletionException(HeuristicCompletionException.STATE_ROLLED_BACK, ex);
		}
		catch (SystemException ex) {
			throw new TransactionSystemException("JTA failure on commit", ex);
		}
	}

	protected void doRollback(TransactionStatus status) {
		logger.debug("Rolling back JTA transaction");
		try {
			((UserTransaction) status.getTransaction()).rollback();
		}
		catch (SystemException ex) {
			throw new TransactionSystemException("JTA failure on rollback", ex);
		}
	}

	protected void doSetRollbackOnly(TransactionStatus status) {
		logger.debug("Setting JTA transaction rollback-only");
		try {
			((UserTransaction) status.getTransaction()).setRollbackOnly();
		}
		catch (IllegalStateException ex) {
			throw new NoTransactionException("No active JTA transaction");
		}
		catch (SystemException ex) {
			throw new TransactionSystemException("JTA failure on setRollbackOnly", ex);
		}
	}

}
