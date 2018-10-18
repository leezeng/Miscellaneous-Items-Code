/**
 * Generic framework code included with
 * <a href="http://www.amazon.com/exec/obidos/tg/detail/-/1861007841/">Expert One-On-One J2EE Design and Development</a>
 * by Rod Johnson (Wrox, 2002).
 * This code is free to use and modify. However, please
 * acknowledge the source and include the above URL in each
 * class using or derived from this code.
 * Please contact <a href="mailto:rod.johnson@com">rod.johnson@com</a>
 * for commercial support.
 */

package com.jdbc.object;

import com.dao.InvalidDataAccessApiUsageException;
import com.jdbc.core.JdbcTemplate;
import com.jdbc.core.PreparedStatementCreator;
import com.jdbc.core.PreparedStatementCreatorFactory;
import com.jdbc.util.JdbcUtils;

/**
 * RdbmsOperation using a JdbcTemplate and representing a SQL-based
 * operation such as a query or update, as opposed to a stored procedure.
 *
 * <p>Configures a PreparedStatementCreatorFactory based on the declared
 * parameters.
 *
 * @author Rod Johnson
 * @version $Id: SqlOperation.java,v 1.6 2003/07/24 15:20:54 jhoeller Exp $
 */
public abstract class SqlOperation extends RdbmsOperation {

	/** Lower-level class used to execute SQL */
	private JdbcTemplate jdbcTemplate;

	/**
	 * Object enabling us to create PreparedStatementCreators
	 * efficiently, based on this class's declared parameters
	 */
	private PreparedStatementCreatorFactory preparedStatementFactory;

	/**
	 * Create a new SqlOperation.
	 */
	public SqlOperation() {
	}

	/**
	 * Return the JdbcTemplate object used by this object
	 */
	protected final JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	/**
	 * Return a PreparedStatementCreator to perform an operation
	 * with this parameters
	 * @param params parameters. May be null.
	 */
	protected final PreparedStatementCreator newPreparedStatementCreator(Object[] params) {
		return this.preparedStatementFactory.newPreparedStatementCreator(params);
	}

	/**
	 * Overriden method to configure the PreparedStatementCreatorFactory
	 * based on our declared parameters.
	 * @see RdbmsOperation#compileInternal()
	 */
	protected final void compileInternal() {
		this.jdbcTemplate = new JdbcTemplate(getDataSource());
		// Validate parameter count
		int bindVarCount = 0;
		try {
			bindVarCount = JdbcUtils.countParameterPlaceholders(getSql(), '?', '\'');
		}
		catch (IllegalArgumentException e) {
			// Transform jdbc-agnostic error to data-access error
			throw new InvalidDataAccessApiUsageException(e.getMessage());
		}
		if (bindVarCount != getDeclaredParameters().size())
			throw new InvalidDataAccessApiUsageException("SQL '" + getSql() + "' requires " + bindVarCount +
			                                             " bind variables, but " + getDeclaredParameters().size() + " variables were declared for this object");

		this.preparedStatementFactory = new PreparedStatementCreatorFactory(getSql(), getDeclaredParameters());
		onCompileInternal();
	}

	/**
	 * Hook method that subclasses may override to react to compilation.
	 * This implementation does nothing.
	 */
	protected void onCompileInternal() {
	}

}
