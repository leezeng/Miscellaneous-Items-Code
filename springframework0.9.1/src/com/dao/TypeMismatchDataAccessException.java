/**
 * Generic framework code included with 
 * <a href="http://www.amazon.com/exec/obidos/tg/detail/-/1861007841/">Expert One-On-One J2EE Design and Development</a>
 * by Rod Johnson (Wrox, 2002). 
 * This code is free to use and modify. 
 * Please contact <a href="mailto:rod.johnson@com">rod.johnson@com</a>
 * for commercial support.
 */

package com.dao;

/**
 * Exception thrown on mismatch between Java type and database type:
 * for example on an attempt to set an object of the wrong type
 * in an RDBMS column.
 * @author Rod Johnson
 * @version $Id: TypeMismatchDataAccessException.java,v 1.2 2003/08/08 15:47:18 jhoeller Exp $
 */
public class TypeMismatchDataAccessException extends DataAccessException {

	/**
	 * Constructor for TypeMismatchDataAccessException.
	 * @param msg mesg
	 * @param ex root cause
	 */
	public TypeMismatchDataAccessException(String msg, Throwable ex) {
		super(msg, ex);
	}

}
