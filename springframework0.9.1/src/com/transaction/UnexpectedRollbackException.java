/*
 * The Spring Framework is published under the terms
 * of the Apache Software License.
 */
 
package com.transaction;

/**
 * Thrown when an attempt to commit a transaction resulted
 * in an unexpected rollback
 * @author Rod Johnson
 * @since 17-Mar-2003
 * @version $Revision: 1.2 $
 */
public class UnexpectedRollbackException extends TransactionException {

	public UnexpectedRollbackException(String msg, Throwable ex) {
		super(msg, ex);
	}

}
