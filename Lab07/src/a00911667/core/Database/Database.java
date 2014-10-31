/**
 * Project: Lab07
 * File: Database.java
 * Date: Oct 23, 2014
 * Time: 10:37:35 AM
 */
package a00911667.core.Database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Andrey Kolobov, A00911667
 * 
 */
public class Database {

	private DatabaseAccessObject _database;

	/**
	 * 
	 */
	public Database(DatabaseAccessObject dao) {
		_database = dao;
	}

	/**
	 * Adds data to the table
	 * 
	 * @param statement
	 *            to read to add
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	public void insert(String query) {
		Statement statement = null;
		int rowAffected = 0;
		try {
			// _database.initializeStatement(ResultSet.TYPE_FORWARD_ONLY,
			// ResultSet.CONCUR_READ_ONLY);
			Connection connection = _database.getConnection();
			statement = connection.createStatement();
			if (query != null) {
				int rowsAffected = statement.executeUpdate(query);
			}
		} catch (Exception e) {
		} finally {
			try {
				if (statement != null) {
					statement.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * select from database. Database name will be provided by user. Put all
	 * information into collection to be able to retrieve it later
	 * 
	 * @param query
	 *            provided by the user. Query to sent to database
	 * @return list of column data
	 */
	public List<String> select(String query) {
		Statement statement = null;
		ResultSetMetaData rsmd = null;
		ResultSet result = null;
		List<String> data = new ArrayList<String>();
		String columnInfo;
		try {
			Connection connection = _database.getConnection();
			statement = connection.createStatement();
			result = statement.executeQuery(query);
			rsmd = result.getMetaData();
			while (result.next()) {
				for (int i = 1; i <= rsmd.getColumnCount(); i++) {
					int type = rsmd.getColumnType(i);
					if (type == Types.VARCHAR || type == Types.CHAR) {
						columnInfo = result.getString(i);
					} else if (type == Types.BOOLEAN) {
						columnInfo = String.valueOf(result.getBoolean(i));
					} else if (type == Types.DATE) {
						columnInfo = String.valueOf(result.getDate(i));
					} else {
						columnInfo = String.valueOf(result.getLong(i));
					}
					data.add(columnInfo);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	}

	/**
	 * Read the query and get the number of columns
	 * 
	 * @param query
	 *            to read
	 * @return number of columns
	 */
	public int getColumnCount(String query) {
		Statement statement = null;
		ResultSetMetaData rsmd = null;
		ResultSet result = null;
		int columnCount = 0;
		try {
			Connection connection = _database.getConnection();
			statement = connection.createStatement();
			result = statement.executeQuery(query);
			rsmd = result.getMetaData();
			columnCount = rsmd.getColumnCount();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return columnCount;
	}

	/**
	 * Get information about a table
	 * 
	 * @param query
	 *            to process
	 * @return ResultSetMetaData
	 */
	public ResultSetMetaData getResultSetMetaData(String query) {
		Statement statement = null;
		ResultSetMetaData rsmd = null;
		ResultSet result = null;
		try {
			Connection connection = _database.getConnection();
			statement = connection.createStatement();
			result = statement.executeQuery(query);
			rsmd = result.getMetaData();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rsmd;
	}

}
