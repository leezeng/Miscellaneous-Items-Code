/*
 * The Spring Framework is published under the terms
 * of the Apache Software License.
 */
 
package com.transaction.interceptor;

import com.transaction.PlatformTransactionManager;
import com.transaction.TransactionDefinition;
import com.transaction.TransactionStatus;

/**
 * Used for testing only (for example, when we must replace the
 * behaviour of a PlatformTransactionManager bean we don't have access to).
 * Allows behaviour of an entire class to change with static delegate change.
 * Not multithreaded.
 * @author Rod Johnson
 * @since 26-Apr-2003
 * @version $Revision: 1.1 $
 */
public class PlatformTransactionManagerFacade implements PlatformTransactionManager {
	
	/**
	 * This member can be changed to change behaviour class-wide.
	 */
	public static PlatformTransactionManager delegate;

	/**
	 * @see com.transaction.PlatformTransactionManager#getTransaction(com.transaction.TransactionDefinition)
	 */
	public TransactionStatus getTransaction(TransactionDefinition definition) {
		return delegate.getTransaction(definition);
	}

	/**
	 * @see com.transaction.PlatformTransactionManager#commit(com.transaction.TransactionStatus)
	 */
	public void commit(TransactionStatus status) {
		delegate.commit(status);
	}

	/**
	 * @see com.transaction.PlatformTransactionManager#rollback(com.transaction.TransactionStatus)
	 */
	public void rollback(TransactionStatus status) {
		delegate.rollback(status);
	}

}
