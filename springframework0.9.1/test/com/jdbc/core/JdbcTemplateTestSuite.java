package com.jdbc.core;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.util.LinkedList;
import java.util.List;

import javax.sql.DataSource;

import junit.framework.TestCase;

import org.easymock.EasyMock;
import org.easymock.MockControl;

import com.dao.CleanupFailureDataAccessException;
import com.dao.DataAccessException;
import com.dao.DataAccessResourceFailureException;
import com.dao.InvalidDataAccessApiUsageException;
import com.dao.UncategorizedDataAccessException;
import com.jdbc.datasource.SingleConnectionDataSource;

import com.mockobjects.sql.MockConnection;

/** 
 * Mock object based tests for JdbcTemplate
 * @author Rod Johnson
 * @version $Id: JdbcTemplateTestSuite.java,v 1.15 2003/07/18 11:25:37 jhoeller Exp $
 */
public class JdbcTemplateTestSuite extends TestCase {


	/** Creates new SeatingPlanTest */
	public JdbcTemplateTestSuite(String name) {
		super(name);
	}


	public void testBeanProperties() throws Exception {
		MockControl dsControl = EasyMock.controlFor(DataSource.class);
		DataSource ds = (DataSource) dsControl.getMock();
		ds.getConnection();
		dsControl.setReturnValue(new MockConnection());
		dsControl.activate();

		JdbcTemplate t = new JdbcTemplate(ds);
		assertTrue("datasource ok", t.getDataSource() == ds);
		assertTrue("ignores warnings by default", t.getIgnoreWarnings());
		t.setIgnoreWarnings(false);
		assertTrue("can set NOT to ignore warnings", !t.getIgnoreWarnings());
	}
	
	
	public void testCannotRunStaticSqlWithBindParameters() throws Exception {
		final String sql = "UPDATE FOO SET NAME='tony' WHERE ID > ?";
		MockControl dsControl = EasyMock.niceControlFor(DataSource.class);
		final int expectedRowsUpdated = 111;
		DataSource ds = (DataSource) dsControl.getMock();
		dsControl.activate();
	
		JdbcTemplate t = new JdbcTemplate(ds);
		try {
			t.query(sql, new RowCountCallbackHandler());
			fail("Should have objected to bind variables");
		}
		catch (InvalidDataAccessApiUsageException ex) {
			// Ok 
		}
		dsControl.verify();
	}

	public void testUpdateCount() throws Exception {
		final String sql = "UPDATE INVOICE SET DATE_DISPATCHED = SYSDATE WHERE ID = ?";
		
		class Dispatcher implements PreparedStatementCreator {
			private int id;
			public Dispatcher(int id) {
				this.id = id;
			}
			public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
				System.out.println("Connection is " + conn);
				PreparedStatement ps = conn.prepareStatement(sql);
				ps.setInt(1, id);
				return ps;
			}
			public String getSql() {
				return sql;
			}
		};
		
		
		int idParam = 11111;
		
		MockControl dsControl = EasyMock.controlFor(DataSource.class);
		DataSource ds = (DataSource) dsControl.getMock();
				
		
		Dispatcher d = new Dispatcher(idParam);
		
		
		MockConnection con = MockConnectionFactory.updateWithPreparedStatement(sql, new Object[] { new Integer(idParam) }, 1, true);

		ds.getConnection();
		dsControl.setReturnValue(con);
		dsControl.activate();
		
		JdbcTemplate template = new JdbcTemplate(ds);
		
		int rowsAffected = template.update(d);
		assertTrue("1 update affected 1 row", rowsAffected == 1);
		
		/*
		d = new Dispatcher(idParam);
		rowsAffected = template.update(d);
		assertTrue("bogus update affected 0 rows", rowsAffected == 0);
		*/
		
		dsControl.verify();
	}


	public void testBogusUpdate() throws Exception {
		final String sql = "UPDATE NOSUCHTABLE SET DATE_DISPATCHED = SYSDATE WHERE ID = ?";
		
		final int iParam = 6666;
		
		class Dispatcher implements PreparedStatementCreator {
			private int id;
			public Dispatcher(int id) {
				this.id = id;
			}
			public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
				PreparedStatement ps = conn.prepareStatement(sql);
				ps.setInt(1, id);
				return ps;
			}
			public String getSql() {
				return sql;
			}
		};
		
		
		// It's because Integers aren't canonical
		SQLException sex = new SQLException("bad update");
		MockConnection con = MockConnectionFactory.updateWithPreparedStatement(sql, new Object[] { new Integer(iParam) }, 1, true, sex, null);
		con.setExpectedCloseCalls(2);
		
		MockControl dsControl = EasyMock.controlFor(DataSource.class);
		DataSource ds = (DataSource) dsControl.getMock();
		ds.getConnection();
				dsControl.setReturnValue(con);
				dsControl.activate();
				
		Dispatcher d = new Dispatcher(iParam);
		JdbcTemplate template = new JdbcTemplate(ds);
		
		try {
			int rowsAffected = template.update(d);
			fail("Bogus update should throw exception");
		}
		catch (UncategorizedDataAccessException ex) {
			// pass
			System.out.println(ex);
			assertTrue("Correct exception", ex instanceof UncategorizedSQLException);
			UncategorizedSQLException je = (UncategorizedSQLException) ex;
			assertTrue("Root cause is correct", ex.getRootCause() == sex);
			//assertTrue("no update occurred", !je.getDataWasUpdated());
		}
		
		dsControl.verify();
	}

	public void testStrings() throws Exception {
		class StringHandler implements RowCallbackHandler {
			private List l = new LinkedList();

			public void processRow(ResultSet rs) throws SQLException {
				l.add(rs.getString(1));
			}

			public String[] getStrings() {
				return (String[]) l.toArray(new String[l.size()]);
			}
		}
		StringHandler sh = new StringHandler();
		
		
		String sql  = "SELECT FORENAME FROM CUSTMR";
		MockControl dsControl = EasyMock.controlFor(DataSource.class);
		DataSource ds = (DataSource) dsControl.getMock();

		String[][] results = {
			{ "rod" },
			{ "gary" },
			{" portia" }
		};
		
		MockConnection con = MockConnectionFactory.statement(sql, results, true, null, null);
		con.setExpectedCloseCalls(2);
		
		ds.getConnection();
		dsControl.setReturnValue(con);
		dsControl.activate();
		
		JdbcTemplate template = new JdbcTemplate(ds);
		
		template.query(sql, sh);
		
		// Match
		String[] forenames = sh.getStrings();
		assertTrue("same length", forenames.length == results.length);
		for (int i = 0; i < forenames.length; i++) {
			assertTrue("Row " + i + " matches", forenames[i].equals(results[i][0]));
		}
		
		dsControl.verify();
	}
	
	
	// REFACTOR!?
	public void testStringsWithPreparedStatement() throws Exception {
		class StringHandler implements RowCallbackHandler {
			private List l = new LinkedList();

			public void processRow(ResultSet rs) throws SQLException {
				l.add(rs.getString(1));
			}

			public String[] getStrings() {
				return (String[]) l.toArray(new String[l.size()]);
			}
		}
		StringHandler sh = new StringHandler();
		
		final String sql  = "SELECT FORENAME FROM CUSTMR WHERE ID>?";
		String[][] results = {
			{ "rod" },
			{ "gary" },
			{" portia" }
		};
		
		final MockConnection con = MockConnectionFactory.preparedStatement(sql, new Integer[] { new Integer(1) }, results, true);
		con.setExpectedCloseCalls(2);
		
		MockControl dsControl = EasyMock.controlFor(DataSource.class);
		DataSource ds = (DataSource) dsControl.getMock();
		ds.getConnection();
		dsControl.setReturnValue(con);
		dsControl.activate();
		
		JdbcTemplate template = new JdbcTemplate(ds);
		
		PreparedStatementCreator psc = new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
				assertTrue("Conn is correct", conn == con);
				PreparedStatement ps = conn.prepareStatement(sql);
				
				// FIX!>
				ps.setInt(1, 1);
				return ps;
			}
			public String getSql() {
				return sql;
			}
		};
		
		template.query(psc, sh);
		
		// Match
		String[] forenames = sh.getStrings();
		assertTrue("same length", forenames.length == results.length);
		for (int i = 0; i < forenames.length; i++) {
			assertTrue("Row " + i + " matches", forenames[i].equals(results[i][0]));
		}
		
		dsControl.verify();
	}
	
	
	public void testLeaveConnOpenOnRequest() throws Exception {
		
		String sql  = "SELECT ID, FORENAME FROM CUSTMR WHERE ID < 3";
		
		MockControl dsControl = EasyMock.controlFor(DataSource.class);
		
		DataSource ds = (DataSource) dsControl.getMock();
		MockConnection con = MockConnectionFactory.statement(sql, new Object[0][0], false, null, null);
		ds.getConnection();
		dsControl.setReturnValue(con);
		dsControl.activate();
		
		SingleConnectionDataSource scf = new SingleConnectionDataSource(ds.getConnection(), false);
		JdbcTemplate template2 = new JdbcTemplate(scf);
		RowCountCallbackHandler rcch = new RowCountCallbackHandler();
		template2.query(sql, rcch);
		
		//assertTrue("SingleConnectionFactory must return same connection", con == scf.getConnection());
		//assertTrue("Single connection " + con + " shouldn't have been closed", !con.isClosed());
	//	scf.close();
	
		dsControl.verify();
	}
	


	public void testCloseConnOnRequest() throws Exception {
		String sql = "SELECT ID, FORENAME FROM CUSTMR WHERE ID < 3";
		MockControl dsControl = EasyMock.controlFor(DataSource.class);
		DataSource ds = (DataSource) dsControl.getMock();
		
		MockConnection con = MockConnectionFactory.statement(sql, new Object[0][0], false, null, null);
		con.setExpectedCloseCalls(2);
		ds.getConnection();
		dsControl.setReturnValue(con, 2);
		dsControl.activate();
		
		JdbcTemplate template2 = new JdbcTemplate(ds);
		RowCountCallbackHandler rcch = new RowCountCallbackHandler();
		template2.query(sql, rcch);
		dsControl.verify();
		con.verify();
	}


// This really tests the translater: shouldn't the SQLTranslater have its own tests?
// We just need to check that the translater is invoked and that it's exception is correctly used
/*
	public void testSQLExceptionIsTranslated() throws Exception {
		
		class TestSqlExceptionTranslater implements SQLExceptionTranslater {
			private int invoked;
			public DataAccessException translate(String task, String sql, SQLException sqlex) {
				// Any subclass will do: can't really check all, can we?
				// YES, we can: loop throuigh with exception as parameter
				System.out.println("Our translater");
				++invoked;
				return new BadSqlGrammarException("Exception doing " + task,sql, sqlex);
			}
		}
		
		TestSqlExceptionTranslater trans = new TestSqlExceptionTranslater();
		
		String sql = "SELECT"; // doesn't really matter what this is
		
		SQLException sex = new SQLException("Any type of SQLException");
		//MockConnection con = MockConnectionFactory.preparedStatement(sql, null, new Object[0][0], false, sex, null);
		
		MockControl conControl = EasyMock.controlFor(Connection.class);
		Connection con = (Connection) conControl.getMock();
		con.prepareStatement(sql);
		
		MockControl psControl = EasyMock.controlFor(PreparedStatement.class);
		PreparedStatement ps = (PreparedStatement) psControl.getMock();
		ps.executeQuery();
		MockSingleRowResultSet rs = new MockSingleRowResultSet();
		rs.setExpectedCloseCalls(2);
		//rs.setupMetaData()
		
		psControl.setReturnValue(rs);
		psControl.activate();
		conControl.setReturnValue(ps);
		con.close();
		conControl.activate();

		MockControl dsControl = EasyMock.controlFor(DataSource.class);
		DataSource ds = (DataSource) dsControl.getMock();
		ds.getConnection();
		dsControl.setReturnValue(con);
		dsControl.activate();

		JdbcTemplate template = new JdbcTemplate(ds);
		template.setExceptionTranslater(trans);
		RowCountCallbackHandler rcch = new RowCountCallbackHandler();
		try {
			template.query(PreparedStatementCreatorFactory.newPreparedStatementCreator(sql), rcch);
			fail("Exceptioo should be translated");
		}
		catch (DataAccessException ex) {
			SQLException se2 = (SQLException) ex.getRootCause();
			assertTrue("Found SQL exception", se2 == sex);
			//System.out.println("VENDOR CODE IS " + sex.getErrorCode());
			//System.out.println("SQLSTATE IS " + sex.getSQLState());
		}
		dsControl.verify();
		conControl.verify();
	}
	*/


	/**
	 * Test that we see a runtime exception back
	 */
	public void testExceptionComesBack() throws Exception {
		
		final RuntimeException rex = new RuntimeException("What I want to see");
		final String sql = "SELECT ID FROM CUSTMR";
		
		MockControl dsControl = EasyMock.controlFor(DataSource.class);
		DataSource ds = (DataSource) dsControl.getMock();
		
		Object[][] results = new Object[][] {
			{ new Integer(1) },
			{ new Integer(2) }
		};
		Connection con = MockConnectionFactory.statement(sql, results, true, null, null);
		ds.getConnection();
		dsControl.setReturnValue(con);
		dsControl.activate();
		
		JdbcTemplate template = new JdbcTemplate(ds);
		try {
			template.query(sql, new RowCallbackHandler() {
				public void processRow(java.sql.ResultSet rs) throws java.sql.SQLException {
					throw rex;
				}
			});
			fail("Should have thrown exception");
		}
		catch (RuntimeException ex) {
			System.out.println(ex);
			assertTrue("Wanted same exception back, not " + ex, ex == rex);
		}	
	}
	
	
	/**
	 * Test update with static SQL
	 */
	
	public void testSqlUpdateEncountersSqlException() throws Exception {
		final String sql = "UPDATE NOSUCHTABLE SET DATE_DISPATCHED = SYSDATE WHERE ID = 4";
		
		MockControl dsControl = EasyMock.controlFor(DataSource.class);
		DataSource ds = (DataSource) dsControl.getMock();
		
		// It's because Integers aren't canonical
		SQLException sex = new SQLException("bad update");
		MockConnection con = MockConnectionFactory.updateWithPreparedStatement(sql, null, 0, true, sex, null);
		con.setExpectedCloseCalls(2);
		
		ds.getConnection();
		dsControl.setReturnValue(con);
		dsControl.activate();
		
		JdbcTemplate template = new JdbcTemplate(ds);
		
		try {
			template.update(sql);
		}
		catch (DataAccessException ex) {
			assertTrue("root cause is correct", ex.getRootCause() == sex);
			// CHECK SQL!?
		}
		
		dsControl.verify();
	}
	
	
	public void testSqlUpdate() throws Exception {
		final String sql = "UPDATE NOSUCHTABLE SET DATE_DISPATCHED = SYSDATE WHERE ID = 4";
		
		MockControl dsControl = EasyMock.controlFor(DataSource.class);
		DataSource ds = (DataSource) dsControl.getMock();
		int rowsAffected = 33;
		
		// It's because Integers aren't canonical
		MockConnection con = MockConnectionFactory.updateWithPreparedStatement(sql, null, rowsAffected, true, null, null);
		con.setExpectedCloseCalls(2);
		
		ds.getConnection();
		dsControl.setReturnValue(con);
		dsControl.activate();
		
		JdbcTemplate template = new JdbcTemplate(ds);
		
		int actualRowsAffected = template.update(sql);
		assertTrue("Actual rows affected is correct", actualRowsAffected == rowsAffected);
		
		dsControl.verify();
	}


	public void testSqlUpdateWithThreadConnection() throws Exception {
		final String sql = "UPDATE NOSUCHTABLE SET DATE_DISPATCHED = SYSDATE WHERE ID = 4";

		MockControl dsControl = EasyMock.controlFor(DataSource.class);
		DataSource ds = (DataSource) dsControl.getMock();
		int rowsAffected = 33;

		// It's because Integers aren't canonical
		MockConnection con = MockConnectionFactory.updateWithPreparedStatement(sql, null, rowsAffected, true, null, null);
		con.setExpectedCloseCalls(2);

		ds.getConnection();
		dsControl.setReturnValue(con);
		dsControl.activate();

		JdbcTemplate template = new JdbcTemplate(ds);

		int actualRowsAffected = template.update(sql);
		assertTrue("Actual rows affected is correct", actualRowsAffected == rowsAffected);

		dsControl.verify();
	}


	/**
	 * Check that a successful bulk update works
	 * @throws Exception
	 */
	public void testBatchUpdate() throws Exception {
		final String sql = "UPDATE NOSUCHTABLE SET DATE_DISPATCHED = SYSDATE WHERE ID = ?";
	
		MockControl dsControl = EasyMock.controlFor(DataSource.class);
		DataSource ds = (DataSource) dsControl.getMock();
		int rowsAffected = 2;
		final int [] ids = new int[] { 100, 200 };
		
		MockControl psControl = EasyMock.controlFor(PreparedStatement.class);
		PreparedStatement mockPs = (PreparedStatement) psControl.getMock();
		mockPs.setInt(1, ids[0]);
		psControl.setVoidCallable(1);
		mockPs.addBatch();
		psControl.setVoidCallable(1);
		mockPs.setInt(1, ids[1]);
		psControl.setVoidCallable(1);
		mockPs.addBatch();
		psControl.setVoidCallable(1);
		mockPs.executeBatch();
		psControl.setReturnValue(ids, 1);
		mockPs.close();
		psControl.setVoidCallable(1);
		psControl.activate();
		
		MockConnection con = MockConnectionFactory.update(sql, mockPs);
		con.setExpectedCloseCalls(2);
		ds.getConnection();
		dsControl.setReturnValue(con, 2);
		dsControl.activate();
		
		BatchPreparedStatementSetter setter = new BatchPreparedStatementSetter() {
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				ps.setInt(1, ids[i]);	
			}
			public int getBatchSize() { 
				return ids.length;
			}
		};
	
		JdbcTemplate template = new JdbcTemplate(ds);
	
		int[] actualRowsAffected = template.batchUpdate(sql, setter);
		assertTrue("executed 2 updates", actualRowsAffected.length == 2);
		
		//assertTrue("Actual rows affected is correct", actualRowsAffected == rowsAffected);
	
		dsControl.verify();
		con.verify();
		psControl.verify();
	}
	
	
	/**
	 * Test case where a batch update fails
	 * @throws Exception
	 */
	public void testBatchUpdateFails() throws Exception {
			final String sql = "UPDATE NOSUCHTABLE SET DATE_DISPATCHED = SYSDATE WHERE ID = ?";
	
			MockControl dsControl = EasyMock.controlFor(DataSource.class);
			DataSource ds = (DataSource) dsControl.getMock();
			int rowsAffected = 2;
			final int [] ids = new int[] { 100, 200 };
			
			SQLException sex = new SQLException();
		
			MockControl psControl = EasyMock.controlFor(PreparedStatement.class);
			PreparedStatement mockPs = (PreparedStatement) psControl.getMock();
			mockPs.setInt(1, ids[0]);
			psControl.setVoidCallable(1);
			mockPs.addBatch();
			psControl.setVoidCallable(1);
			mockPs.setInt(1, ids[1]);
			psControl.setVoidCallable(1);
			mockPs.addBatch();
			psControl.setVoidCallable(1);
			mockPs.executeBatch();
			psControl.setThrowable(sex);
			
			// Should we force the PreparedStatement to be closed as below?
			//mockPs.close();
			//psControl.setVoidCallable(1);
			psControl.activate();
		
			MockConnection con = MockConnectionFactory.update(sql, mockPs);
			con.setExpectedCloseCalls(2);
	
			ds.getConnection();
			dsControl.setReturnValue(con);
			dsControl.activate();
		
			BatchPreparedStatementSetter setter = new BatchPreparedStatementSetter() {
				public void setValues(PreparedStatement ps, int i) throws SQLException {
					ps.setInt(1, ids[i]);	
				}
				public int getBatchSize() { 
					return ids.length;
				}
			};
	
			try {
				JdbcTemplate template = new JdbcTemplate(ds);
				template.batchUpdate(sql, setter);
				fail("Should have failed because of SQLException in bulk update");
			}
			catch (DataAccessException ex) {
				assertTrue("Root cause is SQLException", ex.getRootCause() == sex);
				ex.printStackTrace();			
			}
	
			dsControl.verify();
			con.verify();
			psControl.verify();
		}


	public void testCouldntConnect() throws Exception {
		
		MockControl dsControl = EasyMock.controlFor(DataSource.class);
		DataSource ds = (DataSource) dsControl.getMock();
		
		SQLException sex = new SQLException("foo");
		ds.getConnection();
		dsControl.setThrowable(sex);
		dsControl.activate();
		
		try {
			JdbcTemplate template2 = new JdbcTemplate(ds);
			RowCountCallbackHandler rcch = new RowCountCallbackHandler();
			template2.query("SELECT ID, FORENAME FROM CUSTMR WHERE ID < 3", rcch);
			fail("Shouldn't have executed query without a connection");
		}
		catch (DataAccessResourceFailureException ex) {
			// pass
			System.out.println(ex);
			assertTrue("Check root cause", ex.getRootCause() == sex);
		}
	}
	
	public void testPreparedStatementSetterQueryWithNullArg() throws Exception { 
		final String sql = "SELECT * FROM FOO WHERE ID > 1"; 
		MockControl dsControl = EasyMock.niceControlFor(DataSource.class); 
		final int expectedRowsUpdated = 111; 
		DataSource ds = (DataSource) dsControl.getMock(); 
		// Don't expect any calls 
		dsControl.activate(); 

		class MockJdbcTemplate extends JdbcTemplate { 
				private boolean valid = false; 
				public MockJdbcTemplate(DataSource ds) { 
						super(ds); 
				} 
				// Override this so we don't need to get connection 
				public void query(String sql, RowCallbackHandler rch) { 
						valid = true; 
				} 
		} 

		MockJdbcTemplate mockTemplate = new MockJdbcTemplate(ds); 
		mockTemplate.query(sql, null, null); 
		assertTrue("invoked no arg query", mockTemplate.valid); 
		dsControl.verify(); 
	} 

	/** 
	 * Check that the absence of bind variables is an error 
	 * with null arg 
	 * @throws Exception 
	 */ 
	public void testPreparedStatementSetterQueryWithNullArgButRequiringBindVariables() throws Exception { 
		final String sql = "SELECT * FROM FOO WHERE ID > ?"; 
		MockControl dsControl = EasyMock.niceControlFor(DataSource.class); 
		final int expectedRowsUpdated = 111; 
		DataSource ds = (DataSource) dsControl.getMock(); 
		// Don't expect any calls 
		dsControl.activate(); 

		JdbcTemplate mockTemplate = new JdbcTemplate(ds); 
		try { 
				mockTemplate.query(sql, null, null); 
				fail("Should have recognized that bind variable was required"); 
		} 
		catch (InvalidDataAccessApiUsageException ex) { 
				// Ok 
		} 
    
		dsControl.verify(); 
	} 


	public void testNullSqlWithPreparedStatementSetterQuery() throws Exception { 
		final String sql = null; 
		MockControl dsControl = EasyMock.niceControlFor(DataSource.class); 
		final int expectedRowsUpdated = 111; 
		DataSource ds = (DataSource) dsControl.getMock(); 
		// Don't expect any calls 
		dsControl.activate(); 

		JdbcTemplate mockTemplate = new JdbcTemplate(ds); 
		try { 
				mockTemplate.query(sql, null, null); 
				fail("Null SQL isn't permitted"); 
		} 
		catch (InvalidDataAccessApiUsageException ex) { 
				// Ok 
		} 

		dsControl.verify(); 
	} 

	public void testNullSqlQuery() throws Exception { 
		final String sql = null; 
		MockControl dsControl = EasyMock.niceControlFor(DataSource.class); 
		final int expectedRowsUpdated = 111; 
		DataSource ds = (DataSource) dsControl.getMock(); 
		// Don't expect any calls 
		dsControl.activate(); 

		JdbcTemplate mockTemplate = new JdbcTemplate(ds); 
		try { 
				mockTemplate.query(sql, (RowCallbackHandler) null); 
				fail("Null SQL isn't permitted"); 
		} 
		catch (InvalidDataAccessApiUsageException ex) { 
				// Ok 
		} 

		dsControl.verify(); 
	} 


	
	/**
	 * Check that if we have a null PreparedStatementSetter we invoke the update method
	 * with static SQL
	 * @throws Exception
	 */
	public void testPreparedStatementSetterWithNullArg() throws Exception {
		final String sql = "UPDATE FOO SET NAME='tony' WHERE ID > 1";
		MockControl dsControl = EasyMock.niceControlFor(DataSource.class);
		final int expectedRowsUpdated = 111;
		DataSource ds = (DataSource) dsControl.getMock();
		// Don't expect any calls
		dsControl.activate();
		
		class MockJdbcTemplate extends JdbcTemplate {
			private boolean valid = false;
			public MockJdbcTemplate(DataSource ds) {
				super(ds);
			}
			public int update(String sql) {
				valid = true;
				return expectedRowsUpdated;
			}
		}
		
		MockJdbcTemplate mockTemplate = new MockJdbcTemplate(ds);
		int actualRowsUpdated = mockTemplate.update(sql, null);
		assertTrue("updated correct # of rows", actualRowsUpdated == expectedRowsUpdated);
		assertTrue("invoked update", mockTemplate.valid);
		dsControl.verify();
	}
	
	
	public void testPreparedStatementSetterSucceeds() throws Exception {
		final String sql = "UPDATE FOO SET NAME=? WHERE ID = 1";
		final String name = "Gary";
		MockControl dsControl = EasyMock.controlFor(DataSource.class);
		DataSource ds = (DataSource) dsControl.getMock();
		int expectedRowsUpdated = 1;
		
		MockControl psControl = EasyMock.controlFor(PreparedStatement.class);
		PreparedStatement mockPs = (PreparedStatement) psControl.getMock();
		mockPs.setString(1, name);
		psControl.setVoidCallable(1);
		mockPs.executeUpdate();
		psControl.setReturnValue(expectedRowsUpdated, 1);
		mockPs.close();
		psControl.setVoidCallable(1);
		psControl.activate();
		
		MockConnection con = MockConnectionFactory.update(sql, mockPs);
		con.setExpectedCloseCalls(2);
		ds.getConnection();
		dsControl.setReturnValue(con, 2);
		dsControl.activate();
		PreparedStatementSetter pss = new PreparedStatementSetter() {
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, name);
			}
		};
		int actualRowsUpdated = new JdbcTemplate(ds).update(sql, pss);
		assertTrue("updated correct # of rows", actualRowsUpdated == expectedRowsUpdated);
		dsControl.verify();
		con.verify();
		psControl.verify();
	}
	
	public void testPreparedStatementSetterFails() throws Exception {
		final String sql = "UPDATE FOO SET NAME=? WHERE ID = 1";
		final String name = "Gary";
		MockControl dsControl = EasyMock.controlFor(DataSource.class);
		DataSource ds = (DataSource) dsControl.getMock();
		int expectedRowsUpdated = 1;
	
		SQLException sex = new SQLException();
		MockControl psControl = EasyMock.controlFor(PreparedStatement.class);
		PreparedStatement mockPs = (PreparedStatement) psControl.getMock();
		mockPs.setString(1, name);
		psControl.setVoidCallable(1);
		mockPs.executeUpdate();
		psControl.setThrowable(sex);
		
		// Insist on trying to close PreparedStatement
		//mockPs.close();
		//psControl.setVoidCallable(1);
		psControl.activate();
	
		MockConnection con = MockConnectionFactory.update(sql, mockPs);
		con.setExpectedCloseCalls(2);

		ds.getConnection();
		dsControl.setReturnValue(con);
		dsControl.activate();
		PreparedStatementSetter pss = new PreparedStatementSetter() {
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, name);
			}
		};
		try {
			new JdbcTemplate(ds).update(sql, pss);
			fail("Should have failed with SQLException");
		}
		catch (DataAccessException ex) {
			assertTrue("root cause was preserved", ex.getRootCause() == sex);
		}
		
		dsControl.verify();
		con.verify();
		psControl.verify();
	}
	
	
	public void testCouldntClose() throws Exception {
		MockControl dsControl = EasyMock.controlFor(DataSource.class);
		DataSource ds = (DataSource) dsControl.getMock();
		
		MockControl conControl = EasyMock.controlFor(Connection.class);
		Connection con = (Connection) conControl.getMock();

		ds.getConnection();
		dsControl.setReturnValue(con);
		dsControl.activate();
		
		SQLException sex = new SQLException("bar");
		con.close();
		conControl.setThrowable(sex);
		conControl.activate();
		
		try {
			JdbcTemplate template2 = new JdbcTemplate(ds);
			RowCountCallbackHandler rcch = new RowCountCallbackHandler();
			template2.query("SELECT ID, FORENAME FROM CUSTMR WHERE ID < 3", rcch);
			fail("Should throw exception on failure to close");
		}
		catch (CleanupFailureDataAccessException ex) {
			// pass
			System.out.println(ex);
			assertTrue("Check root cause", ex.getRootCause() == sex);
		}
		
		dsControl.verify();
		conControl.verify();
	}


	/**
	 * Mock objects allow us to produce warnings at will
	 */
	
	public void testFatalWarning() throws Exception {
		String sql = "SELECT forename from custmr";
		
		MockControl dsControl = EasyMock.controlFor(DataSource.class);
		DataSource ds = (DataSource) dsControl.getMock();
		SQLWarning warnings = new SQLWarning("My warning");
		MockConnection con = MockConnectionFactory.statement(sql, new Object[0][0], true, null, warnings);
		
		ds.getConnection();
		dsControl.setReturnValue(con);
		dsControl.activate();
		
		JdbcTemplate t = new JdbcTemplate(ds);
		
		t.setIgnoreWarnings(false);
		try {
			t.query(sql, new RowCallbackHandler() {
				public void processRow(java.sql.ResultSet rs) throws java.sql.SQLException {
					rs.getByte(1);
				}
			});
			fail("Should have thrown exception on warning");
		}
		catch (SQLWarningException ex) {
			// Pass
			System.out.println("WARNING WAS " + ex);
			assertTrue("Root cause of warning was correct", ex.getRootCause() == warnings);
		}
		
		dsControl.verify();
	}


	public void testIgnoredWarning() throws Exception {
		// REFACTOR FROM ABOVE?
		String sql = "SELECT forename from custmr";
		MockControl dsControl = EasyMock.controlFor(DataSource.class);
			DataSource ds = (DataSource) dsControl.getMock();
			
		SQLWarning warnings = new SQLWarning("My warning");
		MockConnection con = MockConnectionFactory.statement(sql, new Object[0][0], true, null, warnings);
		
		ds.getConnection();
		dsControl.setReturnValue(con);
		dsControl.activate();
		
		// Too long: truncation
		JdbcTemplate t = new JdbcTemplate(ds);
		
		t.setIgnoreWarnings(true);
		t.query(sql, new RowCallbackHandler() {
			public void processRow(java.sql.ResultSet rs) throws java.sql.SQLException {
				rs.getByte(1);
			}
		});
		
		dsControl.verify();
	}
	
	/**
	 * Test that we see an SQLException translated using Error Code
	 */
	public void testSQLErrorCodeTranslation() throws Exception {
		
		final SQLException sex = new SQLException("I have a known problem", "99999", 1054);
		final String sql = "SELECT ID FROM CUSTOMER";
		
		MockControl dsControl = EasyMock.controlFor(DataSource.class);
		DataSource ds = (DataSource) dsControl.getMock();
		
		Object[][] results = new Object[][] {
			{ new Integer(1) },
			{ new Integer(2) }
		};
		MockControl conControl = EasyMock.controlFor(Connection.class);
		Connection con = MockConnectionFactory.statement(sql, results, true, null, null);
		MockControl dbmdControl = EasyMock.controlFor(DatabaseMetaData.class);
		DatabaseMetaData dbmd = (DatabaseMetaData) dbmdControl.getMock();

		dbmd.getDatabaseProductName();
		dbmdControl.setReturnValue("MySQL");
		dbmdControl.activate();

		((MockConnection) con).setupMetaData(dbmd);

		ds.getConnection();
		dsControl.setReturnValue(con);
		dsControl.activate();
		
		JdbcTemplate template = new JdbcTemplate(ds);
		try {
			template.query(sql, new RowCallbackHandler() {
				public void processRow(java.sql.ResultSet rs) throws SQLException {
					throw sex;
				}
			});
			fail("Should have thrown exception");
		}
		catch (BadSqlGrammarException ex) {
			System.out.println(ex);
			assertTrue("Wanted same exception back, not " + ex, sex == ex.getRootCause());
		}	
		catch (Exception ex) {
			System.out.println(ex);
			fail("Should have thrown BadSqlGrammarException exception, not " + ex);
		}	

	}
	
}
