package edu.cmu.lti.bic.autoreviewer.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.SQLException;

import edu.cmu.lti.bic.autoreviewer.db.DBManager;

/**
 * @author haodongl Test Server
 */
public class TestServer {

	private static final int PORT = 8000;
	private static final int USERNAME_INDEX = 0;
	private static final int TIME_INDEX = 1;
	private static final int TYPE_INDEX = 2;
	private static final int CH1_INDEX = 3;
	private static final int CH2_INDEX = 4;
	private static final int CH3_INDEX = 5;
	private static final int CH4_INDEX = 6;

	/**
	 * @param args
	 *            some args.
	 * @throws IOException
	 *             some exceptions.
	 * @throws SQLException
	 */
	public static void main(final String[] args) throws IOException,
			SQLException {
		ServerSocket serverSocket = new ServerSocket(PORT);

		while (true) {
			// System.out.print(serverSocket.getLocalPort());
			Socket clientSocket = serverSocket.accept();

			PrintWriter out = new PrintWriter(clientSocket.getOutputStream(),
					true);

			BufferedReader in = new BufferedReader(new InputStreamReader(
					clientSocket.getInputStream()));

			String inputLine;

			ResultSet rs = null;

			String url = "jdbc:mysql://localhost:3306/testdb";
			String user = "testuser";
			String password = "test623";

			DBManager test = new DBManager(url, user, password);

			if (!test.connectDB()) {
				System.out.println("DB Connect Failed");
			}

			while ((inputLine = in.readLine()) != null) {
				System.out.println("Server: " + inputLine);
				out.println(inputLine);

//				String[] eegData = inputLine.split(",");
//
//				String eegUsername = eegData[USERNAME_INDEX];
//				String eegTime = eegData[TIME_INDEX];
//				String eegType = eegData[TYPE_INDEX];
//				String eegCh1 = eegData[CH1_INDEX];
//				String eegCh2 = eegData[CH2_INDEX];
//				String eegCh3 = eegData[CH3_INDEX];
//				String eegCh4 = eegData[CH4_INDEX];
//
//				String sqlStatement = "INSERT INTO EEG_DATA VALUES ( '"
//						+ eegUsername + "', '" + eegTime + "', '" + eegType
//						+ "', '" + eegCh1 + "," + eegCh2 + "," + eegCh3 + ","
//						+ eegCh4 + ")";
//
//				// test.executeSQLUpdate("CREATE TABLE EEG_DATA (USERNAME CHAR(20), TIME CHAR(20), TYPE CHAR(20), CH1 REAL,CH2 REAL,CH3 REAL, CH4 REAL);");
//				test.executeSQLUpdate("INSERT INTO EEG_DATA VALUES ( 'Test1', '2015-02-04 13:10:12', 'ALPHA', 0.1, 0.1, 0.2, 0.2); ");
//				test.executeSQLUpdate("INSERT INTO EEG_DATA VALUES ('Test1', '2015-02-04 13:10:13', 'ALPHA', 0.2, 0.1, 0.2, 0.2); ");
//				rs = test.executeSQLQuery("SELECT * FROM EEG_DATA");
//
//				while (rs.next()) {
//					System.out.println(rs.getString("USERNAME"));
//					System.out.println(rs.getString("TIME"));
//					System.out.println(rs.getFloat("CH1"));
//				}
//
//				test.closeDB();

			}
			out.close();
			in.close();
			clientSocket.close();
			serverSocket.close();
		}
	}
}
