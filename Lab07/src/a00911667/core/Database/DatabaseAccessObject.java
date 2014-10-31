/**
 * Project: Assignment1(a00911667)
 * File: DatabaseAccessObject.java
 * Date: Oct 18, 2014
 * Time: 11:56:27 PM
 */
package a00911667.core.Database;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author Andrey Kolobov, A00911667
 *
 */
public class DatabaseAccessObject {

	private Connection connection;

	private DatabaseMetaData databaseMetadata;

	private Statement statement;

	private ResultSet resultSet;

	private ResultSetMetaData resultSetMetadata;

	/**
	 * Default constructor
	 */
	public DatabaseAccessObject() {
		super();

	}

	/**
	 * Releases this ResultSet object's database and JDBC resources immediately
	 * instead of waiting for this to happen when it is automatically closed.
	 * 
	 * @throws SQLException
	 */
	public void closeResultSet() throws SQLException {

		resultSet.close();
	}

	/**
	 * Releases this Statement object's database and JDBC resources immediately
	 * instead of waiting for this to happen when it is automatically closed.
	 * 
	 * @throws SQLException
	 */
	public void closeStatement() throws SQLException {

		statement.close();
	}

	/**
	 * Releases this Connection object's database and JDBC resources immediately
	 * instead of waiting for them to be automatically released.
	 * 
	 * @throws SQLException
	 */
	public void closeConnection() throws SQLException {

		connection.close();
	}

	/**
	 * Attempts to establish a connection to the given database URL. The
	 * DriverManager attempts to select an appropriate driver from the set of
	 * registered JDBC drivers.
	 * 
	 * @param url
	 *            The URL for the database.
	 * @param userName
	 *            The permitted user name. Enter "null" if not required.
	 * @param passWord
	 *            The permitted pass word. Enter "null" if not required.
	 * @throws SQLException
	 */
	public void connect(String url, String userName, String passWord)
			throws SQLException {

		if (userName == null || passWord == null) {

			connection = DriverManager.getConnection(url);

		} else {
			connection = DriverManager.getConnection(url, userName, passWord);
		}

	}

	/**
	 * Executes the given SQL statement, which may be an INSERT, UPDATE, or
	 * DELETE statement or an SQL statement that returns nothing, such as an SQL
	 * DDL statement.
	 * 
	 * @param updateQuery
	 *            The SQL UPDATE query for INSERT, UPDATE, and DELETE queries.
	 * @return The number of rows affected by this update query.
	 * @throws SQLException
	 */
	public void executeUpdateQuery(String updateQuery) throws SQLException {

		 statement.executeUpdate(updateQuery);

	}

	/**
	 * Executes the given SQL statement, which returns a single ResultSet
	 * object.
	 * 
	 * @param selectQuery
	 *            The SQL SELECT query
	 * @return The ResultSet that contains the data returned by the SELECT
	 *         query.
	 * @throws SQLException
	 */
	public ResultSet generateResultSet(String selectQuery) throws SQLException {

		resultSet = statement.executeQuery(selectQuery);

		return resultSet;
	}

	/**
	 * Returns the current Connection.
	 * 
	 * @return this Connection reference.
	 */
	public Connection getConnection() {
		return connection;
	}

	/**
	 * Retrieves a DatabaseMetaData object that contains meta data about the
	 * database to which this Connection object represents a connection.
	 * 
	 * @param connection
	 *            The established database connection.
	 * @return The meta data for the established database connection.
	 * @throws SQLException
	 */
	public DatabaseMetaData getDatabaseMetadata(Connection connection)
			throws SQLException {

		databaseMetadata = connection.getMetaData();

		return databaseMetadata;

	}

	/**
	 * Returns the current ResultSet.
	 * 
	 * @return this ResultSet reference
	 */
	public ResultSet getResultSet() {

		return resultSet;
	}

	/**
	 * Retrieves the number, types and properties of this ResultSet object's
	 * columns.
	 * 
	 * @param resultSet
	 *            The established ResultSet.
	 * @return The meta data for the established ResultSet.
	 * @throws SQLException
	 */
	public ResultSetMetaData getResultSetMetadata(ResultSet resultSet)
			throws SQLException {

		resultSetMetadata = resultSet.getMetaData();

		return resultSetMetadata;
	}

	/**
	 * Returns the current Statement.
	 * 
	 * @return this Statement reference
	 */
	public Statement getStatement() {

		return statement;
	}

	/**
	 * Creates a Statement object that will generate ResultSet objects with the
	 * given type and concurrency.
	 * 
	 * @param resultSetType
	 *            The type of ResultSet to be created. Use
	 *            ResultSet.TYPE_FORWARD_ONLY for default.
	 * @param resultSetConcurrency
	 *            The concurrency of the ResultSet to be created. Use
	 *            ResultSet.CONCUR_READ_ONLY for default.
	 * @throws SQLException
	 */
	public void initializeStatement(int resultSetType, int resultSetConcurrency)
			throws SQLException {

		if (resultSetType == ResultSet.TYPE_FORWARD_ONLY
				&& resultSetConcurrency == ResultSet.CONCUR_READ_ONLY) {

			statement = connection.createStatement();

		} else {
			statement = connection.createStatement(resultSetType,
					resultSetConcurrency);
		}
	}

	/**
	 * Returns the Class object associated with the class or interface with the
	 * given string name.
	 * 
	 * @param className
	 *            The full class name of the database driver object.
	 * @throws ClassNotFoundException
	 */
	public void loadDatabaseDriver(String className)
			throws ClassNotFoundException {

		Class.forName(className);
	}
}

