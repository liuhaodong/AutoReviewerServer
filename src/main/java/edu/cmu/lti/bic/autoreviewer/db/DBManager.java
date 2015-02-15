package edu.cmu.lti.bic.autoreviewer.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import edu.cmu.lti.bic.autoreviewer.config.ServerConfiguration;

/**
 * @author haodongl Helper class to manage DB manipulation.
 */
public class DBManager {

	private Connection connection = null;
	private Statement statement = null;
	private ResultSet resultSet = null;

	private String url;
	private String username;
	private String password;

	/**
	 * Default constructor using default configurations.
	 */
	public DBManager() {
		url = ServerConfiguration.DEFAULT_DB_URL;
		username = ServerConfiguration.DEFAULT_DB_USERNAME;
		password = ServerConfiguration.DEFAULT_DB_PASSWORD;
	}

	/**
	 * @param pURL
	 *            DB URL
	 * @param pUsername
	 *            username
	 * @param pPassword
	 *            password
	 */
	public DBManager(final String pURL, final String pUsername,
			final String pPassword) {
		this.url = pURL;
		this.username = pUsername;
		this.password = pPassword;
	}

	/**
	 * @return true if connect succeed, else false.
	 */
	public final boolean connectDB() {
		try {
			this.connection = DriverManager.getConnection(url, username,
					password);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * @return true if close succeed, else false.
	 */
	public final boolean closeDB() {
		try {
			if (resultSet != null) {
				resultSet.close();
			}
			if (statement != null) {
				statement.close();
			}
			if (connection != null) {
				connection.close();
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * @param pStatement
	 *            statement.
	 * @return result set.
	 */
	public final ResultSet executeSQLQuery(final String pStatement) {
		if (connection == null) {
			if (!this.connectDB()) {
				return null;
			}
		}

		try {
			this.statement = connection.createStatement();
			resultSet = statement.executeQuery(pStatement);
			return resultSet;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * @param pStatement
	 *            statement
	 * @return result.
	 */
	public final int executeSQLUpdate(final String pStatement) {
		int result = 0;
		if (connection == null) {
			if (!this.connectDB()) {
				return result;
			}
		}

		try {
			if (this.statement == null) {
				this.statement = connection.createStatement();
			}
			result = statement.executeUpdate(pStatement);
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			return result;
		}
	}

}
