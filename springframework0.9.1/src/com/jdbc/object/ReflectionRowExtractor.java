package com.jdbc.object;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.beans.BeanWrapper;
import com.beans.BeanWrapperImpl;
import com.beans.BeansException;
import com.beans.MutablePropertyValues;
import com.beans.PropertyValue;
import com.dao.InvalidDataAccessApiUsageException;
import com.jdbc.core.ResultReader;
import com.jdbc.core.RowCountCallbackHandler;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * DEMONSTRATION OF THIS APPROACH ONLY: NOT FULLY IMPLEMENTED,
 * DOCUMENTED OR TESTED.
 */
public class ReflectionRowExtractor extends RowCountCallbackHandler implements ResultReader {

	protected final Log logger = LogFactory.getLog(getClass());

	private List l;

	private Class resultClass;

	/** Column extractor to use */
	//private ColumnExtractor columnExtractor;

	public ReflectionRowExtractor(Class resultClass, int rowsExpected) throws InvalidDataAccessApiUsageException {
		// Use the more efficient collection if we know how many rows to expect
		this.l = (rowsExpected > 0) ? (List) new ArrayList(rowsExpected) : (List) new LinkedList();

		this.resultClass = resultClass;
		//this.columnExtractor = new DefaultColumnExtractor();
		try {
			new BeanWrapperImpl(resultClass);
		}
		catch (BeansException ex) {
			// TODO: CORRECT EXCEPTION TYPE?
			throw new InvalidDataAccessApiUsageException("Can't introspect results: " + ex);
		}
	}

	protected void processRow(ResultSet rs, int rowNum) throws SQLException, InvalidDataAccessApiUsageException {
		//l.add(columnExtractor.extractColumn(1, requiredType, rs));
		MutablePropertyValues pvs = new MutablePropertyValues();
		for (int i = 0; i < getColumnCount(); i++) {
			String colname = getColumnNames()[i];

			// HACK!!!!
			colname = colname.toLowerCase();

			// TODO: clean up types
			PropertyValue pv = new PropertyValue(colname, rs.getObject(colname));
			logger.info("Found property value " + pv);
			pvs.addPropertyValue(pv);
		}
		try {
			BeanWrapper bw = new BeanWrapperImpl(resultClass);
			bw.setEventPropagationEnabled(false);
			bw.setPropertyValues(pvs);
			l.add(bw.getWrappedInstance());
		}
		catch (BeansException ex) {
			throw new InvalidDataAccessApiUsageException("Can't add row results: " + ex);
		}
	}


	public List getResults() {
		return l;
	}

}