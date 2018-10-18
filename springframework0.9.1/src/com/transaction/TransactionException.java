/*
 * The Spring Framework is published under the terms
 * of the Apache Software License.
 */
 
package com.transaction;

import com.core.NestedRuntimeException;

/**
 * Superclass for all transaction exceptions.
 * @author Rod Johnson
 * @since 17-Mar-2003
 * @version $Revision: 1.2 $
 */
public abstract class TransactionException extends NestedRuntimeException {

	public TransactionException(String msg) {
		super(msg);
	}

	public TransactionException(String msg, Throwable ex) {
		super(msg, ex);
	}

}
