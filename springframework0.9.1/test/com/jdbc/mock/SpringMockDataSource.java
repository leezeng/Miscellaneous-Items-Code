/*
 * SpringMockDataSource.java
 *
 * Copyright (C) 2002 by Interprise Software.  All rights reserved.
 */
package com.jdbc.mock;

import com.jdbc.datasource.SmartDataSource;
import com.mockobjects.sql.MockDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

/**
 * @author <a href="mailto:tcook@interprisesoftware.com">Trevor D. Cook</a>
 * @version $Id: SpringMockDataSource.java,v 1.3 2003/05/07 07:17:10 jhoeller Exp $
 * @task enter type comments
 */
public class SpringMockDataSource extends MockDataSource implements SmartDataSource {

    /**
     * Constructor for SpringMockDataSource.
     */
    public SpringMockDataSource() {
        super();
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return null;
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return false;
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return null;
    }

    /**
     * @see com.jdbc.datasource.SmartDataSource#shouldClose(java.sql.Connection)
     */
    public boolean shouldClose(Connection conn) {
        return false;
    }

}
