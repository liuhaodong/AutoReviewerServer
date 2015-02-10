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
			test.executeSQLUpdate("CREATE TABLE EEG_DATA (USERNAME CHAR(20), TIME CHAR(20), TYPE CHAR(20), CH1 REAL,CH2 REAL,CH3 REAL, CH4 REAL);");
			test.executeSQLUpdate("INSERT INTO EEG_DATA VALUES ( 'Test1', '2015-02-04 13:10:12', 'ALPHA', 0.1, 0.1, 0.2, 0.2); ");
			test.executeSQLUpdate("INSERT INTO EEG_DATA VALUES ('Test1', '2015-02-04 13:10:13', 'ALPHA', 0.2, 0.1, 0.2, 0.2); ");
			rs = test.executeSQLQuery("SELECT * FROM EEG_DATA");
		}
		
		while (rs.next()) {
			System.out.println(rs.getString("USERNAME"));
			System.out.println(rs.getString("TIME"));
			System.out.println(rs.getFloat("CH1"));
		}
		
		test.closeDB();

	}
}
