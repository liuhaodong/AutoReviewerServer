package edu.cmu.lti.bic.autoreviewer.db;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author haodongl
 *
 */
public class TestDB {

	/**
	 * @param args args
	 * @throws SQLException some exceptions.
	 */
	public static void main(final String[] args) throws SQLException {

		ResultSet rs = null;

		String url = "jdbc:mysql://localhost:3306/testdb";
		String user = "testuser";
		String password = "test623";

		DBManager test = new DBManager(url, user, password);

		if (test.connectDB()) {
			test.executeSQLUpdate("CREATE TABLE STATION (ID INTEGER PRIMARY KEY, CITY CHAR(20), STATE CHAR(2), LAT_N REAL, LONG_W REAL);");
			test.executeSQLUpdate("INSERT INTO STATION VALUES (13, 'Phoenix', 'AZ', 33, 112); ");
			test.executeSQLUpdate("INSERT INTO STATION VALUES (44, 'Denver', 'CO', 40, 105); ");
			rs = test.executeSQLQuery("SELECT * FROM STATION");
		}

		while (rs.next()) {
			System.out.println(rs.getString("CITY"));
			System.out.println(rs.getString("ID"));
			System.out.println(rs.getString("STATE"));
		}

		test.closeDB();

	}
}
